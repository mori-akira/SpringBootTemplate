package jp.co.molygray.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * {@link jp.co.molygray.parameter.department.validator
 * validator}配下のValidatorクラスのvalidate()メソッドに対し、 単項目チェックエラーが存在する場合に処理を実行しない共通処理を追加するアドバイザ
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
@Aspect
public class ValidatorInterruptAdvice {

  /**
   * 単項目チェックエラーが存在する場合に処理を中断するメソッド
   *
   * @param joinPoint メソッド呼び出し情報
   * @throws Throwable エラー発生時
   */
  @Around(value = "execution(* jp.co.molygray.parameter..validator.*Validator.validate(..))")
  public void interrupt(ProceedingJoinPoint joinPoint)
      throws Throwable {
    Object[] args = joinPoint.getArgs();
    // 引数が2つ以上の場合に処理
    if (args.length >= 1) {
      try {
        // エラーがある場合はメソッドを呼び出さない
        Errors errors = Errors.class.cast(args[1]);
        if (errors.hasErrors()) {
          return;
        }
      } catch (ClassCastException ex) {
      }
    }
    // メソッド呼び出し
    joinPoint.proceed(joinPoint.getArgs());
  }
}
