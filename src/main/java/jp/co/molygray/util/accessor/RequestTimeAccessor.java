package jp.co.molygray.util.accessor;

import java.time.ZonedDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import lombok.Getter;

/**
 * リクエスト時間へのアクセサ・クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
@RequestScope
public class RequestTimeAccessor {

  /** リクエスト時間 */
  @Getter
  private ZonedDateTime requestTime;

  /**
   * デフォルト・コンストラクタ
   */
  public RequestTimeAccessor() {
    this.requestTime = ZonedDateTime.now();
  }
}
