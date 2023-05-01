package jp.co.molygray.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;
import jp.co.molygray.util.message.MultiMessageSource;

/**
 * AOPを用いて排他チェックを実施するアドバイザ
 * <p>
 * 対象のメソッドにて、排他フラグによる更新条件が存在する前提で排他チェックを行う。 この時、メソッドの戻り値は更新件数である必要がある。<br>
 * 主に、UPDATEクエリに対応するメソッドに付与する。
 * </p>
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Aspect
@Component
public class ExclusiveCheckAdvice {

  /** メッセージソース */
  @Autowired
  private MultiMessageSource messageSource;

  /**
   * 排他チェックを行うメソッド
   *
   * @throws Throwable 例外発生時
   */
  @AfterReturning(value = "@annotation(jp.co.molygray.annotation.WithExclusiveCheck)",
      returning = "returnValue")
  public void checkExclusive(JoinPoint jp, Object returnValue)
      throws Throwable {
    // メソッド結果から数値への変換を試みる
    Number num = null;
    if (Integer.class.isInstance(returnValue)) {
      num = Integer.class.cast(returnValue);
    } else if (Long.class.isInstance(returnValue)) {
      num = Long.class.cast(returnValue);
    }
    if (num == null) {
      // 数値に変換できない場合、何もしない
      return;
    } else if (num.longValue() <= 0) {
      // 数値に変換でき、かつ件数が0以下の場合、排他エラーとして扱う
      String errorCode = "exclusiveError";
      String errorMessage =
          messageSource.getMessage(jp.getSignature().getDeclaringType(), errorCode);
      throw new BusinessErrorException(
          ErrorDetail.builder()
              .errorCode(errorCode)
              .errorMessage(errorMessage)
              .build());
    }
  }
}
