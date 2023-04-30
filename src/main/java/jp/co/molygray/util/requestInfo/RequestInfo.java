package jp.co.molygray.util.requestInfo;

import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 機能ごとのリクエスト情報
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@AllArgsConstructor
public class RequestInfo {

  /** リクエストパスの正規表現 */
  private String requestPathRegex;
  /** リクエストパラメータ */
  private Map<String, Object> requestParameter;

  /**
   * コンストラクタ
   *
   * @param requestPathRegex リクエストパスの正規表現
   */
  public RequestInfo(String requestPathRegex) {
    this.requestPathRegex = requestPathRegex;
    this.requestParameter = Collections.emptyMap();
  }
}
