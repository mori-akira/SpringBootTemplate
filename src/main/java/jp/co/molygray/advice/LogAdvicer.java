package jp.co.molygray.advice;

import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * ログのアドバイザを定義
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
@Aspect
@Slf4j
public class LogAdvicer {

  /**
   * コントローラの処理前にログ出力を差し込むメソッド
   *
   * @param joinPoint メソッド呼び出し情報
   */
  @Before("execution(public * jp.co.molygray.controller.*Controller.*(..))")
  public void logBeforeController(JoinPoint joinPoint) {
    String methodName = getMethodName(joinPoint);
    List<Object> args = getArguments(joinPoint);
    log.info("before entry: {}, args: {}", methodName, args);
  }

  /**
   * コントローラの処理後にログ出力を差し込むメソッド
   *
   * @param joinPoint メソッド呼び出し情報
   * @param returnValue 戻り値
   */
  @AfterReturning(value = "execution(public * jp.co.molygray.controller.*Controller.*(..))",
      returning = "returnValue")
  public void logAfterController(JoinPoint joinPoint, Object returnValue) {
    String methodName = getMethodName(joinPoint);
    log.info("after entry: {}, return: {}", methodName, returnValue);
  }

  /**
   * {@link JoinPoint}からメソッド名を取得するメソッド
   *
   * @param joinPoint メソッド呼び出し情報
   * @return メソッド名
   */
  private String getMethodName(JoinPoint joinPoint) {
    Signature signature = joinPoint.getSignature();
    return signature.getDeclaringTypeName() + "#" + signature.getName();
  }

  /**
   * {@link JoinPoint}から引数を取得するメソッド
   *
   * @param joinPoint メソッド呼び出し情報
   * @return 引数
   */
  private List<Object> getArguments(JoinPoint joinPoint) {
    return Arrays.asList(joinPoint.getArgs());
  }
}
