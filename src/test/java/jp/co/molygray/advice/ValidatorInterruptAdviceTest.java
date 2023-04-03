package jp.co.molygray.advice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import jp.co.molygray.parameter.department.RegisterParameter;
import jp.co.molygray.parameter.department.validator.RegisterValidator;

/**
 * {@link ValidatorInterruptAdvice}のテストクラス
 *
 * <p>
 * {@link AspectJProxyFactory}を用いてAOPを設定したバリデータのプロキシを生成し、テストする。
 * </p>
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class ValidatorInterruptAdviceTest {

    /** AspectJのプロキシ・ファクトリ */
    private AspectJProxyFactory factory;
    /** バリデータのプロキシ */
    private Validator proxy;
    /** {@link RegisterValidator}のモック・インスタンス */
    @Mock
    private RegisterValidator registerValidator;

    /**
     * 初期化メソッド
     */
    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        factory = new AspectJProxyFactory(registerValidator);
        factory.addAspect(new ValidatorInterruptAdvice());
        proxy = factory.getProxy();
    }

    /**
     * {@link ValidatorInterruptAdvice#interrupt(org.aspectj.lang.ProceedingJoinPoint)}のテストメソッド
     *
     * <p>
     * 単項目エラーなし＆相関エラーなしの場合
     * </p>
     *
     * @throws Throwable 例外発生時
     */
    @Test
    public void interruptTest1() throws Throwable {
        doAnswer(invocation -> {
            return null;
        }).when(registerValidator)
                .validate(any(RegisterParameter.class), any(Errors.class));
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(null, "hoge");
        proxy.validate(new RegisterParameter(), errors);
        assertFalse(errors.hasErrors());
        verify(registerValidator).validate(any(RegisterParameter.class), any(Errors.class));
    }

    /**
     * {@link ValidatorInterruptAdvice#interrupt(org.aspectj.lang.ProceedingJoinPoint)}のテストメソッド
     *
     * <p>
     * 単項目エラーありの場合
     * </p>
     *
     * @throws Throwable 例外発生時
     */
    @Test
    public void interruptTest2() throws Throwable {
        doAnswer(invocation -> {
            return null;
        }).when(registerValidator)
                .validate(any(RegisterParameter.class), any(Errors.class));
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(null, "hoge");
        errors.reject("fuga");
        proxy.validate(new RegisterParameter(), errors);
        assertEquals(1, errors.getErrorCount());
        verify(registerValidator, never()).validate(any(RegisterParameter.class),
                any(Errors.class));
    }

    /**
     * {@link ValidatorInterruptAdvice#interrupt(org.aspectj.lang.ProceedingJoinPoint)}のテストメソッド
     *
     * <p>
     * 単項目エラーなし＆相関エラーありの場合
     * </p>
     *
     * @throws Throwable 例外発生時
     */
    @Test
    public void interruptTest3() throws Throwable {
        doAnswer(invocation -> {
            Errors error = Errors.class.cast(invocation.getArgument(1));
            error.reject("fuga");
            return null;
        }).when(registerValidator)
                .validate(any(RegisterParameter.class), any(Errors.class));
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(null, "hoge");
        proxy.validate(new RegisterParameter(), errors);
        assertEquals(List
                .of(new ObjectError("hoge", new String[] { "fuga.hoge", "fuga" }, null, null)),
                errors.getAllErrors());
        verify(registerValidator).validate(any(RegisterParameter.class), any(Errors.class));
    }
}
