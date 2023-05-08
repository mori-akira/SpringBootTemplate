package jp.co.molygray.constraints.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import jakarta.validation.ConstraintValidatorContext;

/**
 * {@link IntegerFieldValidator}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class IntegerFieldValidatorTest {

  /**
   * 空検証の入力値
   *
   * @return 空扱いの値
   */
  public static Stream<String> emptySource() {
    return Stream.of(null, "");
  }

  /**
   * 検証の入力値
   *
   * @return 入力・結果の組み合わせ
   */
  public static Stream<Entry<String, Boolean>> patternSrouce() {
    Map<String, Boolean> map = new HashMap<>();
    map.put("1", true);
    map.put("0", true);
    map.put("-1", true);
    map.put("2147483647", true);
    map.put("2147483648", false);
    map.put("-2147483648", true);
    map.put("-2147483649", false);
    map.put("0.1", false);
    map.put("abc", false);
    map.put(" 1 ", false);
    map.put("　1　", false);
    map.put(" ", false);
    return map.entrySet()
        .stream();
  }

  /**
   * {@link IntegerFieldValidator#isValid(String, ConstraintValidatorContext)} の空検証を行うテストクラス
   *
   * @param value 入力パターン
   */
  @ParameterizedTest
  @MethodSource("emptySource")
  public void isValidTestEmpty(String value) {
    IntegerFieldValidator validator = new IntegerFieldValidator();
    assertTrue(validator.isValid(value, null));
  }

  /**
   * {@link IntegerFieldValidator#isValid(String, jConstraintValidatorContext)}
   * のパターン検証を行うテストクラス
   *
   * @param source 入力パターン
   * @throws Throwable エラー発生時
   */
  @ParameterizedTest
  @MethodSource("patternSrouce")
  public void isValidTestFormatPattern(Entry<String, Boolean> source)
      throws Throwable {
    IntegerFieldValidator validator = new IntegerFieldValidator();
    assertEquals(source.getValue(), validator.isValid(source.getKey(), null));
  }
}
