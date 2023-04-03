package jp.co.molygray.advice;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.response.common.ErrorResponse;

/**
 * コントローラーのアドバイザを定義
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex,
                toErrorResponse(ex.getAllErrors(), ErrorSummaryEnum.INPUT_ERROR), headers,
                status, request);
    }

    /**
     * {@link ObjectError}リストを{@link ErrorResponse}に変換するメソッド
     *
     * @param errors {@link ObjectError}リスト
     * @param errorSummary エラー概要
     * @return エラーレスポンス
     */
    private ErrorResponse toErrorResponse(List<ObjectError> errors,
            ErrorSummaryEnum errorSummary) {
        return ErrorResponse.builder()
                .errorSummary(errorSummary.getSummary())
                .errorDetailList(errors.stream()
                        .map(e -> new ErrorResponse.ErrorDetail(e.getCode(),
                                e.getDefaultMessage()))
                        .toList())
                .build();
    }
}
