package jp.co.molygray.constraints.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.molygray.constraints.FormatDate;

/**
 * {@link FormatDate}のバリデーション実装クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class FormatDateValidator implements ConstraintValidator<FormatDate, String> {

  /** 日付フォーマット */
  private String format;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize(FormatDate formatDate) {
    this.format = formatDate.format();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isValid(String value,
      ConstraintValidatorContext context) {
    if (StringUtils.isEmpty(value)) {
      return true;
    }
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)
          .withResolverStyle(ResolverStyle.STRICT);
      LocalDate.parse(value, formatter);
      return true;
    } catch (DateTimeParseException ex) {
      return false;
    }
  }
}
