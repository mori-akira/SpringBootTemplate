package jp.co.molygray.parameter.employee.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import jp.co.molygray.parameter.employee.GetInfoParameter;
import jp.co.molygray.util.message.MultiMessageSource;

/**
 * {@link GetInfoParameter}のバリデータ
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class GetInfoValidator implements Validator {

  /** メッセージソース */
  @Autowired
  private MultiMessageSource messageSource;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean supports(Class<?> clazz) {
    if (clazz == null) {
      return false;
    }
    return GetInfoParameter.class.isAssignableFrom(clazz);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validate(Object target,
      Errors errors) {
    GetInfoParameter param = GetInfoParameter.class.cast(target);

    String employeeId = param.getEmployeeId();
    String employeeNumber = param.getEmployeeNumber();

    // employeeIdとemployeeNumberの両方が未指定の場合エラー
    if (StringUtils.isEmpty(employeeId) && StringUtils.isEmpty(employeeNumber)) {
      String key = "requireEmployeeIdOrEmployeeNumber";
      String message = messageSource.getMessage(this.getClass(), key);
      errors.reject(key, message);
    }
  }

}
