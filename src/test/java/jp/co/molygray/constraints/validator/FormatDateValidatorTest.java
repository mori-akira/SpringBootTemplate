package jp.co.molygray.constraints.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * {@link FormatDateValidator}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class FormatDateValidatorTest {

  /**
   * 空検証の入力値
   *
   * @return 空扱いの値
   */
  public static Stream<String> emptySource() {
    return Stream.of(null, "");
  }

  /**
   * {@link FormatDateValidator#isValid(String, ConstraintValidatorContext)}
   * の検証を行うための、データの組み合わせを定義するサブクラス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  @AllArgsConstructor
  @Getter
  @ToString
  public static class FormatDatePatternSource {

    private String format;
    private String dateValue;
    private Boolean expected;
  }

  /**
   * フォーマット・日付の組み合わせ検証の入力値
   *
   * @return フォーマット・日付の組み合わせ
   */
  public static Stream<FormatDatePatternSource> patternSrouce() {
    return Stream.of(
        new FormatDatePatternSource("uuuu/MM/dd", "2023/04/01", true),
        new FormatDatePatternSource("uuuu/MM/dd", "2023/4/1", false),
        new FormatDatePatternSource("uuuu/MM/dd", "/4/1", false),
        new FormatDatePatternSource("uuuu/MM/dd", "2/4/1", false),
        new FormatDatePatternSource("uuuu/MM/dd", "20/4/1", false),
        new FormatDatePatternSource("uuuu/MM/dd", "202/4/1", false),
        new FormatDatePatternSource("uuuu/MM/dd", "20230/4/1", false),
        new FormatDatePatternSource("uuuu/MM/dd", "2023//1", false),
        new FormatDatePatternSource("uuuu/MM/dd", "2023/004/1", false),
        new FormatDatePatternSource("uuuu/MM/dd", "2023/4/", false),
        new FormatDatePatternSource("uuuu/MM/dd", "2023/4/001", false),
        new FormatDatePatternSource("uuuu-MM-dd", "2023-04-01", true),
        new FormatDatePatternSource("uuuu-MM-dd", "2023/04/01", false),
        new FormatDatePatternSource("uuuu-MM-dd", "2023-13-01", false),
        new FormatDatePatternSource("uuuu-MM-dd", "2023-12-32", false),
        new FormatDatePatternSource("uuuu-MM-dd", "2023-02-29", false));
  }

  /**
   * {@link FormatDateValidator#isValid(String, ConstraintValidatorContext)}の空検証を行うテストクラス
   *
   * @param value 空扱いの値
   */
  @ParameterizedTest
  @MethodSource("emptySource")
  public void isValidTestEmpty(String value) {
    FormatDateValidator validator = new FormatDateValidator();
    assertTrue(validator.isValid(value, null));
  }

  /**
   * {@link FormatDateValidator#isValid(String, ConstraintValidatorContext)}
   * のパターン検証を行うテストクラス
   *
   * @param source 入力パターン
   * @throws Throwable エラー発生時
   */
  @ParameterizedTest
  @MethodSource("patternSrouce")
  public void isValidTestFormatPattern(FormatDatePatternSource source)
      throws Throwable {
    FormatDateValidator validator = new FormatDateValidator();
    FieldUtils.writeField(FormatDateValidator.class.getDeclaredField("format"), validator,
        source.format, true);
    assertEquals(source.expected, validator.isValid(source.dateValue, null));
  }
}
