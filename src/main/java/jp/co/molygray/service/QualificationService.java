package jp.co.molygray.service;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.molygray.dao.entity.QualificationDao;
import jp.co.molygray.dto.QualificationDto;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.model.QualificationModel;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;
import jp.co.molygray.util.DtoCommonFieldSetter;
import jp.co.molygray.util.message.MultiMessageSource;

/**
 * 資格サービスクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Service
public class QualificationService {

  /** 資格Dao */
  @Autowired
  private QualificationDao qualificationDao;
  /** メッセージソース */
  @Autowired
  private MultiMessageSource messageSource;
  /** Dto共通フィールドセッター */
  @Autowired
  private DtoCommonFieldSetter dtoCommonFieldSetter;

  /**
   * 資格を一件取得するメソッド
   *
   * @param qualificationId 資格ID
   * @return 資格 (取得できない場合はnull)
   */
  public QualificationModel get(long qualificationId) {
    return qualificationDao.select(qualificationId).map(e -> {
      QualificationModel model = new QualificationModel();
      BeanUtils.copyProperties(e, model);
      return model;
    }).orElse(null);
  }

  /**
   * 資格を一覧検索するメソッド
   *
   * @param qualificationName 資格名
   * @param qualificationAbbreviatedName 資格省略名
   * @param validPeriodYears 有効年数
   * @param provider 提供組織
   * @return 資格リスト (取得できない場合は空リスト)
   */
  public List<QualificationModel> searchList(String qualificationName,
      String qualificationAbbreviatedName,
      Integer validPeriodYears,
      String provider) {
    List<QualificationDto> dtoList = qualificationDao.searchList(qualificationName,
        qualificationAbbreviatedName, validPeriodYears, provider);
    return dtoList.stream()
        .map(e -> {
          QualificationModel model = new QualificationModel();
          BeanUtils.copyProperties(e, model);
          return model;
        })
        .toList();
  }

  /**
   * 資格を登録するメソッド
   *
   * @param model 資格モデル
   * @return 登録された資格ID
   */
  public Long insert(QualificationModel model) {
    checkDuplicateQualificationName(model.getQualificationName());
    QualificationDto dto = new QualificationDto();
    BeanUtils.copyProperties(model, dto);
    dtoCommonFieldSetter.setCommonFieldWhenInsert(dto);
    qualificationDao.insert(dto);
    return dto.getQualificationId();
  }

  /**
   * 資格を更新するメソッド
   *
   * @param model 資格モデル
   */
  public void update(QualificationModel model) {
    QualificationDto dto = getAndCheckExistenceQualification(model.getQualificationId());
    if (!StringUtils.equals(model.getQualificationName(), dto.getQualificationName())) {
      checkDuplicateQualificationName(model.getQualificationName());
    }
    BeanUtils.copyProperties(model, dto);
    dtoCommonFieldSetter.setCommonFieldWhenUpdate(dto);
    qualificationDao.update(dto);
  }

  /**
   * 資格を削除するメソッド
   *
   * @param qualificationId 資格ID
   * @param exclusiveFlg 排他フラグ
   */
  public void delete(Long qualificationId, String exclusiveFlg) {
    getAndCheckExistenceQualification(qualificationId);
    qualificationDao.delete(qualificationId, exclusiveFlg);
  }

  /**
   * 資格名の重複チェックを行うメソッド
   *
   * @param qualificationName 資格名
   */
  private void checkDuplicateQualificationName(String qualificationName) {
    List<QualificationDto> modelList =
        qualificationDao.searchList(qualificationName, null, null, null);
    if (CollectionUtils.isNotEmpty(modelList)) {
      String errorCode = "duplicateQualificationName";
      String errorMessage = messageSource.getMessage(this.getClass(), errorCode);
      throw new BusinessErrorException(
          ErrorDetail.builder()
              .errorCode(errorCode)
              .errorMessage(errorMessage)
              .errorItem("qualificationName")
              .build());
    }
  }

  /**
   * 資格の存在チェックを行うメソッド
   *
   * @param qualificationId 資格ID
   * @return 資格DTO
   */
  private QualificationDto getAndCheckExistenceQualification(Long qualificationId) {
    QualificationDto dto = qualificationDao.select(qualificationId).orElse(null);
    if (dto == null) {
      String errorCode = "notExistsQualification";
      String errorMessage = messageSource.getMessage(this.getClass(), errorCode);
      throw new BusinessErrorException(
          ErrorDetail.builder()
              .errorCode(errorCode)
              .errorMessage(errorMessage)
              .build());
    }
    return dto;
  }
}
