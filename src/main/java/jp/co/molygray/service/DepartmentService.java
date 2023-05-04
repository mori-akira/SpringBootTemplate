package jp.co.molygray.service;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jp.co.molygray.dao.entity.DepartmentDao;
import jp.co.molygray.dto.DepartmentDto;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;
import jp.co.molygray.util.DtoCommonFieldSetter;
import jp.co.molygray.util.message.MultiMessageSource;

/**
 * 部署サービスクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Service
public class DepartmentService {

  /** 部署Dao */
  @Autowired
  private DepartmentDao departmentDao;
  /** メッセージソース */
  @Autowired
  private MultiMessageSource messageSource;
  /** Dto共通フィールドセッター */
  @Autowired
  private DtoCommonFieldSetter dtoCommonFieldSetter;

  /**
   * 部署を一件取得するメソッド
   *
   * @param departmentId 部署ID
   * @return 部署 (取得できない場合はnull)
   */
  public DepartmentModel get(long departmentId) {
    return departmentDao.select(departmentId).map(e -> {
      DepartmentModel model = new DepartmentModel();
      BeanUtils.copyProperties(e, model);
      return model;
    }).orElse(null);
  }

  /**
   * 部署を一覧検索するメソッド
   *
   * @param parentparentDepartmentIdList 親部署IDリスト
   * @param departmentName 部署名検索テキスト
   * @param departmentFullName 部署正式名検索テキスト
   * @return 部署リスト (取得できない場合は空リスト)
   */
  public List<DepartmentModel> searchList(List<Long> parentparentDepartmentIdList,
      String departmentName,
      String departmentFullName) {
    List<DepartmentDto> dtoList = departmentDao.searchList(parentparentDepartmentIdList,
        departmentName, departmentFullName);
    return dtoList.stream()
        .map(e -> {
          DepartmentModel model = new DepartmentModel();
          BeanUtils.copyProperties(e, model);
          return model;
        })
        .toList();
  }

  /**
   * 部署を登録するメソッド
   *
   * @param model 部署モデル
   * @return 登録された部署ID
   */
  public Long insert(DepartmentModel model) {
    checkDuplicateDepartmentName(model.getDepartmentName());
    checkDuplicateDepartmentFullName(model.getDepartmentFullName());
    DepartmentDto dto = new DepartmentDto();
    BeanUtils.copyProperties(model, dto);
    dtoCommonFieldSetter.setCommonFieldWhenInsert(dto);
    departmentDao.insert(dto);
    return dto.getDepartmentId();
  }

  /**
   * 部署を更新するメソッド
   *
   * @param model 部署モデル
   */
  public void update(DepartmentModel model) {
    DepartmentDto dto = getAndCheckExistenceDepartment(model.getDepartmentId());
    if (!StringUtils.equals(model.getDepartmentName(), dto.getDepartmentName())) {
      checkDuplicateDepartmentName(model.getDepartmentName());
    }
    if (!StringUtils.equals(model.getDepartmentFullName(), dto.getDepartmentFullName())) {
      checkDuplicateDepartmentFullName(model.getDepartmentFullName());
    }
    BeanUtils.copyProperties(model, dto);
    dtoCommonFieldSetter.setCommonFieldWhenUpdate(dto);
    departmentDao.update(dto);
  }

  /**
   * 部署を削除するメソッド
   *
   * @param departmentId 部署ID
   * @param exclusiveFlg 排他フラグ
   */
  public void delete(Long departmentId, String exclusiveFlg) {
    getAndCheckExistenceDepartment(departmentId);
    departmentDao.delete(departmentId, exclusiveFlg);
  }

  /**
   * 部署名の重複チェックを行うメソッド
   *
   * @param departmentName 部署名
   */
  private void checkDuplicateDepartmentName(String departmentName) {
    List<DepartmentDto> modelList = departmentDao.searchList(null, departmentName, null);
    if (CollectionUtils.isNotEmpty(modelList)) {
      String errorCode = "duplicateDepartmentName";
      String errorMessage = messageSource.getMessage(this.getClass(), errorCode);
      throw new BusinessErrorException(
          ErrorDetail.builder()
              .errorCode(errorCode)
              .errorMessage(errorMessage)
              .errorItem("departmentName")
              .build());
    }
  }

  /**
   * 部署正式名の重複チェックを行うメソッド
   *
   * @param departmentFullName 部署正式名
   */
  private void checkDuplicateDepartmentFullName(String departmentFullName) {
    List<DepartmentDto> modelList = departmentDao.searchList(null, null, departmentFullName);
    if (CollectionUtils.isNotEmpty(modelList)) {
      String errorCode = "duplicateDepartmentFullName";
      String errorMessage = messageSource.getMessage(this.getClass(), errorCode);
      throw new BusinessErrorException(
          ErrorDetail.builder()
              .errorCode(errorCode)
              .errorMessage(errorMessage)
              .errorItem("departmentFullName")
              .build());
    }
  }

  /**
   * 部署の存在チェックを行うメソッド
   *
   * @param departmentId 部署ID
   * @return 部署DTO
   */
  private DepartmentDto getAndCheckExistenceDepartment(Long departmentId) {
    DepartmentDto dto = departmentDao.select(departmentId).orElse(null);
    if (dto == null) {
      String errorCode = "notExistsDepartment";
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
