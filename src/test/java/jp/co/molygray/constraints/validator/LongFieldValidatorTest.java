package jp.co.molygray.constraints.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * {@link LongFieldValidator}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class LongFieldValidatorTest {

    /**
     * 空検証の入力値
     *
     * @return 空扱いの値
     */
    public static Stream<String> emptySource() {
        return Stream.of(null, "");
    }

    /**
     * フォーマット・日付の組み合わせ検証の入力値
     *
     * @return フォーマット・日付の組み合わせ
     */
    public static Stream<Map.Entry<String, Boolean>> patternSrouce() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("1", true);
        map.put("0", true);
        map.put("-1", true);
        map.put("9223372036854775807", true);
        map.put("9223372036854775808", false);
        map.put("-9223372036854775808", true);
        map.put("-9223372036854775809", false);
        map.put("0.1", false);
        map.put("abc", false);
        map.put(" 1 ", false);
        map.put("　1　", false);
        map.put(" ", false);
        return map.entrySet()
                .stream();
    }

    /**
     * {@link LongFieldValidator#isValid(String, jakarta.validation.ConstraintValidatorContext)}
     * の空検証を行うテストクラス
     *
     * @param value 入力パターン
     */
    @ParameterizedTest
    @MethodSource("emptySource")
    public void isValidTestEmpty(String value) {
        LongFieldValidator validator = new LongFieldValidator();
        assertTrue(validator.isValid(value, null));
    }

    /**
     * {@link LongFieldValidator#isValid(String, jakarta.validation.ConstraintValidatorContext)}
     * のパターン検証を行うテストクラス
     *
     * @param source 入力パターン
     * @throws Throwable エラー発生時
     */
    @ParameterizedTest
    @MethodSource("patternSrouce")
    public void isValidTestFormatPattern(Entry<String, Boolean> source) throws Throwable {
        LongFieldValidator validator = new LongFieldValidator();
        assertEquals(source.getValue(), validator.isValid(source.getKey(), null));
    }
}
