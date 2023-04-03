package jp.co.molygray.response.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * エラー用のレスポンスクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * エラー詳細クラス
     *
     * @author Moriaki Kogure
     * @version 0.0.1
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDetail {

        /** エラーコード */
        private String errorCode;
        /** エラーメッセージ */
        private String errorMessage;
    }

    /** エラー概要 */
    private String errorSummary;
    /** エラー詳細リスト */
    private List<ErrorDetail> errorDetailList;
}
