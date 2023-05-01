package jp.co.molygray.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.groups.Default;
import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.parameter.department.GetParameter;
import jp.co.molygray.parameter.department.ListParameter;
import jp.co.molygray.parameter.department.RegisterParameter;
import jp.co.molygray.parameter.department.RegisterParameter.Put;
import jp.co.molygray.parameter.department.validator.RegisterValidator;
import jp.co.molygray.response.department.GetResponse;
import jp.co.molygray.response.department.ListResponse;
import jp.co.molygray.response.department.PutResponse;
import jp.co.molygray.service.DepartmentService;

/**
 * 部署APIのコントローラークラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {

  /** 部署サービス */
  @Autowired
  private DepartmentService departmentService;
  /** 登録パラメータの相関チェックバリデータ */
  @Autowired
  private RegisterValidator registerValidator;

  /**
   * 初期バインド定義
   *
   * @param binder バインダー
   */
  @InitBinder
  void initBinder(WebDataBinder binder) {
    if (registerValidator.supports(binder.getTarget().getClass())) {
      binder.addValidators(registerValidator);
    }
  }

  /**
   * 部署Get APIエントリポイント
   *
   * @param parameter Getパラメータ
   * @return Getレスポンス
   * @throws Exception 例外発生時
   */
  @GetMapping("/get/{departmentId}")
  public GetResponse get(@Validated GetParameter parameter)
      throws Exception {
    Long id = Long.valueOf(parameter.getDepartmentId());
    DepartmentModel model = departmentService.get(id);
    return new GetResponse(model);
  }

  /**
   * 部署List APIエントリポイント
   *
   * @param parameter Listパラメータ
   * @return Listレスポンス
   * @throws Exception 例外発生時
   */
  @GetMapping("/list")
  public ListResponse list(@Validated ListParameter parameter)
      throws Exception {
    List<Long> parentDepartmentIdList = Optional
        .ofNullable(parameter.getParentDepartmentIdList())
        .map(e -> e.stream()
            .map(Long::valueOf)
            .toList())
        .orElse(null);
    List<DepartmentModel> modelList = departmentService.searchList(parentDepartmentIdList,
        parameter.getDepartmentName(), parameter.getDepartmentFullName());
    return new ListResponse(modelList);
  }

  /**
   * 部署Put APIエントリポイント
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
    DepartmentModel model = convertParameterToModel(parameter);
    return new PutResponse(departmentService.insert(model));
  }

  /**
   * {@link RegisterParameter}を{@link DepartmentModel}に変換するメソッド
   *
   * @param parameter 部署登録パラメータ
   * @return 部署モデル
   */
  private DepartmentModel convertParameterToModel(RegisterParameter parameter) {
    return DepartmentModel.builder()
        .departmentId(
            Optional.ofNullable(parameter.getDepartmentId())
                .map(Long::valueOf)
                .orElse(null))
        .parentDepartmentId(
            Optional.ofNullable(parameter.getParentDepartmentId())
                .map(Long::valueOf)
                .orElse(null))
        .departmentName(parameter.getDepartmentName())
        .departmentFullName(parameter.getDepartmentFullName())
        .build();
  }
}
