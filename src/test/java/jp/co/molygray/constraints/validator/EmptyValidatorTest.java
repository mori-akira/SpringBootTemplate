package jp.co.molygray.constraints.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * {@link FormatDateValidator}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class EmptyValidatorTest {

  /**
   * {@link EmptyValidator#isValid()}の検証を行うための、データの組み合わせを定義する内部クラス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  @AllArgsConstructor
  @Getter
  @ToString
  public static class EmptyPatternSource {

    private String value;
    private Boolean expected;
  }

  /**
   * 入力値・結果の組み合わせを取得するメソッド
   *
   * @return 入力値・結果の組み合わせ
   */
  public static Stream<EmptyPatternSource> patternSrouce() {
    return Stream.of(
        new EmptyPatternSource("", true),
        new EmptyPatternSource(null, true),
        new EmptyPatternSource("xxx", false));
  }

  /**
   * {@link FormatDateValidator#isValid(String, jakarta.validation.ConstraintValidatorContext)}
   * のパターン検証を行うテストクラス
   *
   * @param source 入力パターン
   * @throws Throwable エラー発生時
   */
  @ParameterizedTest
  @MethodSource("patternSrouce")
  public void isValidTestFormatPattern(EmptyPatternSource source)
      throws Throwable {
    EmptyValidator validator = new EmptyValidator();
    assertEquals(source.expected, validator.isValid(source.getValue(), null));
  }
}
