package jp.co.molygray.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.groups.Default;
import jp.co.molygray.model.QualificationModel;
import jp.co.molygray.parameter.qualification.DeleteParameter;
import jp.co.molygray.parameter.qualification.GetParameter;
import jp.co.molygray.parameter.qualification.ListParameter;
import jp.co.molygray.parameter.qualification.RegisterParameter;
import jp.co.molygray.parameter.qualification.RegisterParameter.Patch;
import jp.co.molygray.parameter.qualification.RegisterParameter.Put;
import jp.co.molygray.response.qualification.DeleteResponse;
import jp.co.molygray.response.qualification.GetResponse;
import jp.co.molygray.response.qualification.ListResponse;
import jp.co.molygray.response.qualification.PatchResponse;
import jp.co.molygray.response.qualification.PutResponse;
import jp.co.molygray.service.QualificationService;

/**
 * 資格APIのコントローラークラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/qualification")
public class QualificationController {

  /** 資格サービス */
  @Autowired
  private QualificationService qualificationService;

  /**
   * 資格Get APIエントリポイント
   *
   * @param parameter Getパラメータ
   * @return Getレスポンス
   * @throws Exception 例外発生時
   */
  @GetMapping("/get/{qualificationId}")
  public GetResponse get(@Validated GetParameter parameter)
      throws Exception {
    Long id = Long.valueOf(parameter.getQualificationId());
    QualificationModel model = qualificationService.get(id);
    return new GetResponse(model);
  }

  /**
   * 資格List APIエントリポイント
   *
   * @param parameter Listパラメータ
   * @return Listレスポンス
   * @throws Exception 例外発生時
   */
  @GetMapping("/list")
  public ListResponse list(@Validated ListParameter parameter)
      throws Exception {
    Integer validPeriodYears = Optional.ofNullable(parameter.getValidPeriodYears())
        .map(Integer::valueOf)
        .orElse(null);
    List<QualificationModel> modelList = qualificationService.searchList(
        parameter.getQualificationName(), parameter.getQualificationAbbreviatedName(),
        validPeriodYears, parameter.getProvider());
    return new ListResponse(modelList);
  }

  /**
   * 資格Put APIエントリポイント
   *
   * @param parameter Putパラメータ
   * @return Putレスポンス
   * @throws Exception 例外発生時
   */
  @PutMapping("/put")
  @ResponseStatus(value = HttpStatus.CREATED)
  public PutResponse put(
      @Validated({Put.class, Default.class}) @RequestBody RegisterParameter parameter)
      throws Exception {
    QualificationModel model = convertParameterToModel(parameter);
    return new PutResponse(qualificationService.insert(model));
  }

  /**
   * 資格Patch APIエントリポイント
   *
   * @param parameter Patchパラメータ
   * @return Patchレスポンス
   * @throws Exception 例外発生時
   */
  @PatchMapping("/patch")
  public PatchResponse patch(
      @Validated({Patch.class, Default.class}) @RequestBody RegisterParameter parameter)
      throws Exception {
    QualificationModel model = convertParameterToModel(parameter);
    qualificationService.update(model);
    return new PatchResponse();
  }

  /**
   * 資格Delete APIエントリポイント
   *
   * @param parameter Deleteパラメータ
   * @return Deleteレスポンス
   * @throws Exception 例外発生時
   */
  @DeleteMapping("/delete")
  public DeleteResponse delete(@Validated DeleteParameter parameter)
      throws Exception {
    Long qualificationId = Long.valueOf(parameter.getQualificationId());
    qualificationService.delete(qualificationId, parameter.getExclusiveFlg());
    return new DeleteResponse();
  }

  /**
   * {@link RegisterParameter}を{@link QualificationModel}に変換するメソッド
   *
   * @param parameter 資格登録パラメータ
   * @return 資格モデル
   */
  private QualificationModel convertParameterToModel(RegisterParameter parameter) {
    return QualificationModel.builder()
        .qualificationId(
            Optional.ofNullable(parameter.getQualificationId())
                .map(Long::valueOf)
                .orElse(null))
        .exclusiveFlg(parameter.getExclusiveFlg())
        .qualificationName(parameter.getQualificationName())
        .qualificationAbbreviatedName(parameter.getQualificationAbbreviatedName())
        .validPeriodYears(
            Optional.ofNullable(parameter.getValidPeriodYears())
                .map(Integer::valueOf)
                .orElse(null))
        .provider(parameter.getProvider())
        .build();
  }
}
