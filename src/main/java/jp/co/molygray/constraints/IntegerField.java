package jp.co.molygray.constraints;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.co.molygray.constraints.IntegerField.List;
import jp.co.molygray.constraints.validator.IntegerFieldValidator;

/**
 * Integer変換による検証を行うための独自アノテーション
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = {IntegerFieldValidator.class})
public @interface IntegerField {

  /** エラーメッセージ */
  String message() default "{jp.co.molygray.constraints.IntegerField.message}";

  /** グループ */
  Class<?>[] groups() default {};

  /** カテゴリ情報 */
  Class<? extends Payload>[] payload() default {};

  /**
   * 複数の{@link IntegerField}を指定する場合に使用する独自アノテーション
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   * @see IntegerField
   */
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
  @Retention(RUNTIME)
  @Documented
  @interface List {

    IntegerField[] value();
  }
}
