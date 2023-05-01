package jp.co.molygray.constraints.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.molygray.constraints.Empty;

/**
 * {@link Empty}のバリデーション実装クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class EmptyValidator implements ConstraintValidator<Empty, String> {

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(Empty empty) {}

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(String value,
      ConstraintValidatorContext context) {
    return StringUtils.isEmpty(value);
  }
}
