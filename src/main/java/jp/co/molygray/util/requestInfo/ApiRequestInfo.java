package jp.co.molygray.util.requestInfo;

/**
 * API機能のリクエスト情報
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class ApiRequestInfo extends RequestInfo {

  /** APIリクエストの接頭辞 */
  public static final String PREFIX_API_REQUEST_PATH = "/api";

  /**
   * コンストラクタ
   *
   * @param apiRequestPathRegex
   */
  public ApiRequestInfo(String apiRequestPathRegex) {
    super(PREFIX_API_REQUEST_PATH.concat(apiRequestPathRegex));
  }
}
