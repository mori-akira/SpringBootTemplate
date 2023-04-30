package jp.co.molygray.util.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.hibernate.validator.internal.engine.MessageInterpolatorContext;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.validation.metadata.ConstraintDescriptor;

/**
 * {@link CustomMessageInterpolator}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class CustomMessageInterpolatorTest {

  /** {@link MultiMessageSource}のモック・インスタンス */
  @Mock
  private MultiMessageSource messageSource;
  /** {@link MessageInterpolatorContext}のモック・インスタンス */
  @Mock
  private MessageInterpolatorContext context;
  /** {@link ConstraintDescriptor}のモック・インスタンス */
  @Mock
  private ConstraintDescriptor descriptor;
  /** {@link CustomMessageInterpolator}のモック・インスタンス */
  @InjectMocks
  private CustomMessageInterpolator customMessageInterpolator;
  /** Mockitoのテストクラス・インスタンス */
  private AutoCloseable closeable;
  /** テスト用のメッセージを定義 */
  private static final Map<String, String> MESSAGE_MAPPER = new HashMap<>() {

    {
      put("hoge", "Test Message hoge");
      put("fuga", "Test Message fuga: {field}={value}");
      put("hogehoge", "Test Message hogehoge: {key1},{key3}");
      put("fugafuga", "Test Message fugafuga: {field}={value}: {key1},{key2}");
    }
  };

  /**
   * 前処理メソッド
   */
  @BeforeEach
  public void init() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  /**
   * 後処理メソッド
   *
   * @throws Exception 例外が発生した場合
   */
  @AfterEach
  public void tearDown()
      throws Exception {
    closeable.close();
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * メッセージを直接使用、置換なし
   * </p>
   */
  @Test
  public void interpolateTest1() {
    setMock("foo", "bar");
    String actual = customMessageInterpolator.interpolate("Test Message", context);
    assertEquals("Test Message", actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * メッセージを直接使用、フィールド・検証値置換あり
   * </p>
   */
  @Test
  public void interpolateTest2() {
    setMock("foo", "bar");
    String actual = customMessageInterpolator.interpolate("Test Message: {field}={value}",
        context, Locale.JAPANESE);
    assertEquals("Test Message: foo=bar", actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * メッセージを直接使用、残りのプレースホルダあり
   * </p>
   */
  @Test
  public void interpolateTest3() {
    setMock("foo", "bar");
    String actual = customMessageInterpolator.interpolate("Test Message: {key1},{key3}",
        context);
    assertEquals("Test Message: val1,{key3}", actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * メッセージを直接使用、フィールド・検証値置換あり、検証値=null、残りのプレースホルダあり
   * </p>
   */
  @Test
  public void interpolateTest4() {
    setMock("foo", null);
    String actual = customMessageInterpolator.interpolate(
        "Test Message: {field}={value}: {key1},{key2}", context, Locale.ENGLISH);
    assertEquals("Test Message: foo=null: val1,val2", actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * メッセージを置換使用、置換なし
   * </p>
   */
  @Test
  public void interpolateTest5() {
    setMock("foo", "bar");
    String actual = customMessageInterpolator.interpolate("{hoge}", context);
    assertEquals("Test Message hoge", actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * メッセージを直接使用、フィールド・検証値置換あり
   * </p>
   */
  @Test
  public void interpolateTest6() {
    setMock("foo", "bar");
    String actual = customMessageInterpolator.interpolate("{fuga}", context);
    assertEquals("Test Message fuga: foo=bar", actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * メッセージを直接使用、残りのプレースホルダあり
   * </p>
   */
  @Test
  public void interpolateTest7() {
    setMock("foo", "bar");
    String actual = customMessageInterpolator.interpolate("{hogehoge}", context,
        Locale.JAPANESE);
    assertEquals("Test Message hogehoge: val1,{key3}", actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * メッセージを直接使用、フィールド・検証値置換あり、検証値=null、残りのプレースホルダあり
   * </p>
   */
  @Test
  public void interpolateTest8() {
    setMock("foo", null);
    String actual = customMessageInterpolator.interpolate("{fugafuga}", context);
    assertEquals("Test Message fugafuga: foo=null: val1,val2", actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * プレースホルダ検知の正常確認
   * </p>
   */
  @Test
  public void interpolateTest9() {
    setMock("foo", "bar");
    String actual = customMessageInterpolator.interpolate(
        "{{field }{ field}{filedd}{{field}}{value }{ value}{valuee}{{value}}{key1 }{ key1}{key3}{{key1}}}",
        context);
    assertEquals(
        "{field }{ field}{filedd}{foo}{value }{ value}{valuee}{bar}{key1 }{ key1}{key3}{val1}",
        actual);
  }

  /**
   * {@link CustomMessageInterpolator#interpolate(String, jakarta.validation.MessageInterpolator.Context)}のテストメソッド
   * <p>
   * プレースホルダの重複確認
   * </p>
   */
  @Test
  public void interpolateTest10() {
    setMock("foo", "bar");
    String actual = customMessageInterpolator.interpolate(
        "{{key1}{{key1}}{{{key1}}}}", context);
    assertEquals(
        "val1{val1}{{val1}}", actual);
  }

  /**
   * モックを設定するメソッド
   *
   * @param fieldName フィールド名
   * @param validatedValue 検証値
   */
  private void setMock(String fieldName,
      String validatedValue) {
    // messageSourceのモック
    doAnswer(invocation -> {
      String key = String.class.cast(invocation.getArgument(0));
      return MESSAGE_MAPPER.getOrDefault(key, key);
    }).when(messageSource)
        .getMessage(any(String.class));

    // contextのモック
    when(context.getPropertyPath()).thenReturn(PathImpl.createPathFromString(fieldName));
    when(context.getValidatedValue()).thenReturn(validatedValue);
    when(context.getConstraintDescriptor()).thenReturn(descriptor);

    // descriptorのモック
    when(descriptor.getAttributes()).thenReturn(new HashMap<>() {

      {
        put("key1", "val1");
        put("key2", "val2");
      }
    });
  }
}
