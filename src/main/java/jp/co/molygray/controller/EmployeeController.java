package jp.co.molygray.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jp.co.molygray.parameter.employee.GetInfoParameter;
import jp.co.molygray.parameter.employee.validator.GetInfoValidator;
import jp.co.molygray.response.employee.GetInfoResponse;
import jp.co.molygray.service.EmployeeService;

/**
 * 社員APIのコントローラークラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

  /** 社員サービス */
  @Autowired
  private EmployeeService employeeService;
  /** 情報取得パラメータの相関チェックバリデータ */
  @Autowired
  private GetInfoValidator getInfoValidator;

  /**
   * 初期バインド定義
   *
   * @param binder バインダー
   */
  @InitBinder
  void initBinder(WebDataBinder binder) {
    Class<?> target = Optional.ofNullable(binder.getTarget()).map(Object::getClass).orElse(null);
    if (getInfoValidator.supports(target)) {
      binder.addValidators(getInfoValidator);
    }
  }

  /**
   * 社員情報Get APIエントリポイント
   *
   * @param param 情報Getパラメータ
   * @return 情報Getレスポンス
   * @throws Exception 例外発生時
   */
  @GetMapping("/getInfo")
  public GetInfoResponse getInfo(@Validated GetInfoParameter param)
      throws Exception {
    Long employeeId = Optional.ofNullable(param.getEmployeeId()).map(Long::valueOf).orElse(null);
    return new GetInfoResponse(
        employeeService.getEmployeeInfo(employeeId, param.getEmployeeNumber()));
  }
}
