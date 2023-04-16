package jp.co.molygray.constraints;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.co.molygray.constraints.LongField.List;
import jp.co.molygray.constraints.validator.LongFieldValidator;

/**
 * Long変換による検証を行うための独自アノテーション
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = {LongFieldValidator.class})
public @interface LongField {

  /** エラーメッセージ */
  String message() default "{jp.co.molygray.constraints.LongField.message}";

  /** グループ */
  Class<?>[] groups() default {};

  /** カテゴリ情報 */
  Class<? extends Payload>[] payload() default {};

  /**
   * 複数の{@link LongField}を指定する場合に使用する独自アノテーション
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   * @see LongField
   */
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
  @Retention(RUNTIME)
  @Documented
  @interface List {

    LongField[] value();
  }
}
