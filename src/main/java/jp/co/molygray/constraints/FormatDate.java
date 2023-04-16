package jp.co.molygray.constraints;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.co.molygray.constraints.FormatDate.List;
import jp.co.molygray.constraints.validator.FormatDateValidator;

/**
 * 日付フォーマットによる検証を行うための独自アノテーション
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = {FormatDateValidator.class})
public @interface FormatDate {

  /** エラーメッセージ */
  String message() default "{jp.co.molygray.constraints.FormatDate.message}";

  /** フォーマット */
  String format() default "uuuu-MM-dd";

  /** グループ */
  Class<?>[] groups() default {};

  /** カテゴリ情報 */
  Class<? extends Payload>[] payload() default {};

  /**
   * 複数の{@link FormatDate}を指定する場合に使用する独自アノテーション
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   * @see FormatDate
   */
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
  @Retention(RUNTIME)
  @Documented
  @interface List {

    FormatDate[] value();
  }
}
