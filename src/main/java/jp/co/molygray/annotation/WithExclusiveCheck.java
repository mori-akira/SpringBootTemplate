package jp.co.molygray.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 排他チェックを行うことを示すアノテーション
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Target({METHOD})
@Retention(RUNTIME)
@Documented
public @interface WithExclusiveCheck {
}
