package jp.co.molygray.parameter.employee.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import jp.co.molygray.parameter.department.validator.RegisterValidator;
import jp.co.molygray.parameter.employee.GetInfoParameter;
import jp.co.molygray.util.message.MultiMessageSource;

/**
 * {@link GetInfoValidator}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class GetInfoValidatorTest {

  /** {@link MultiMessageSource}のモック・インスタンス */
  @Mock
  private MultiMessageSource messageSource;
  /** {@link RegisterValidator}のモック・インスタンス */
  @InjectMocks
  private GetInfoValidator getInfoValidator;
  /** Mockitoインスタンス */
  private AutoCloseable closeable;

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
   * {@link GetInfoValidator#validate()}のテストメソッド
   * <p>
   * OKの場合をテスト
   * </p>
   */
  @Test
  public void validateTestOk() {
    when(messageSource.getMessage(any(Class.class), any(String.class), any(Object[].class)))
        .thenReturn("err");
    BeanPropertyBindingResult errors = new BeanPropertyBindingResult(null, "hoge");
    // 社員ID・社員番号を指定する場合
    GetInfoParameter param = GetInfoParameter.builder()
        .employeeId("fuga")
        .employeeNumber("piyo")
        .build();
    getInfoValidator.validate(param, errors);
    assertFalse(errors.hasErrors());
    verify(messageSource, never())
        .getMessage(any(Class.class), any(String.class), any(Object[].class));

    // 社員IDを指定しない場合
    param = GetInfoParameter.builder()
        .employeeId(null)
        .employeeNumber("piyo")
        .build();
    getInfoValidator.validate(param, errors);
    assertFalse(errors.hasErrors());
    verify(messageSource, never())
        .getMessage(any(Class.class), any(String.class), any(Object[].class));

    // 社員番号を指定しない場合
    param = GetInfoParameter.builder()
        .employeeId("fuga")
        .employeeNumber(null)
        .build();
    getInfoValidator.validate(param, errors);
    assertFalse(errors.hasErrors());
    verify(messageSource, never())
        .getMessage(any(Class.class), any(String.class), any(Object[].class));
  }

  /**
   * {@link GetInfoValidator#validate()}のテストメソッド
   * <p>
   * NGの場合をテスト
   * </p>
   */
  @Test
  public void validateTestNg() {
    when(messageSource.getMessage(any(Class.class), any(String.class), any(Object[].class)))
      .thenReturn("err");
    BeanPropertyBindingResult errors = new BeanPropertyBindingResult(null, "hoge");
    GetInfoParameter param = GetInfoParameter.builder()
        .employeeId(null)
        .employeeNumber(null)
        .build();
    getInfoValidator.validate(param, errors);
    assertEquals(List.of(new ObjectError("hoge",
        new String[] {"requireEmployeeIdOrEmployeeNumber.hoge",
            "requireEmployeeIdOrEmployeeNumber"}, null, null)),
        errors.getAllErrors());
    verify(messageSource).getMessage(any(Class.class), any(String.class),
        any(DefaultMessageSourceResolvable.class), any(DefaultMessageSourceResolvable.class));
  }
}
