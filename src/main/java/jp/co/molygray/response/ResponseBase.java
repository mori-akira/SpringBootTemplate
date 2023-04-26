package jp.co.molygray.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * レスポンス共通部
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@SuperBuilder
public class ResponseBase {

  /** メッセージリスト */
  private List<String> messageList;

  /**
   * デフォルト・コンストラクタ
   */
  public ResponseBase() {
    messageList = new ArrayList<>();
  }
}
