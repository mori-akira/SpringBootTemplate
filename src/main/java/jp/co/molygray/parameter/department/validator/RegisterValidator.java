package jp.co.molygray.parameter.department.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import jp.co.molygray.parameter.department.RegisterParameter;
import jp.co.molygray.util.message.MultiMessageSource;

/**
 * {@link RegisterParameter}のバリデータ
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class RegisterValidator implements Validator {

  /** メッセージソース */
  @Autowired
  private MultiMessageSource messageSource;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return RegisterParameter.class.isAssignableFrom(clazz);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void validate(Object target,
      Errors errors) {
    RegisterParameter param = RegisterParameter.class.cast(target);

    String departmentName = param.getDepartmentName();
    String departmentFullName = param.getDepartmentFullName();

    // departmentFullNameがdepartmentNameで終わらない場合エラー
    if (!departmentFullName.endsWith(departmentName)) {
      String key = "departmentFullNameNotEndsWithDepartmentName";
      String message = messageSource.getMessage(this.getClass(), key,
          new Object[] {new DefaultMessageSourceResolvable("departmentFullName"),
              new DefaultMessageSourceResolvable("departmentName")});
      errors.reject(key, message);
    }
  }
}
