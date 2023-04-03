package jp.co.molygray.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * レスポンス共通部
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
public class ResponseBase {

    /** メッセージリスト */
    private List<String> messageList = new ArrayList<>();
}
