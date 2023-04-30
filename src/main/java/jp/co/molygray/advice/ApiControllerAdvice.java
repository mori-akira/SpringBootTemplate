package jp.co.molygray.advice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.response.common.ErrorResponse;
import jp.co.molygray.util.message.MultiMessageSource;
import lombok.extern.slf4j.Slf4j;

/**
 * コントローラーのアドバイザを定義
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@RestControllerAdvice
@Slf4j
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

  /** システムエラーのメッセージキー */
  public static final String MESSAGE_KEY_SYSTEM_ERROR = "systemError";
  /** メッセージソース */
  @Autowired
  private MultiMessageSource messageSource;

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    log.error("BindException occured.", ex);
    return handleExceptionInternal(ex,
        toErrorResponse(ex.getAllErrors(), ErrorSummaryEnum.INPUT_ERROR), headers,
        status, request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    log.error("MethodArgumentNotValidException occured.", ex);
    return handleExceptionInternal(ex,
        toErrorResponse(ex.getAllErrors(), ErrorSummaryEnum.INPUT_ERROR), headers,
        status, request);
  }

  /**
   * ビジネスエラーをハンドリングするメソッド
   *
   * @param ex 発生した例外
   * @return エラーレスポンス・エンティティ
   */
  @ExceptionHandler(BusinessErrorException.class)
  public ResponseEntity<ErrorResponse> handleBusinessErrorException(BusinessErrorException ex) {
    log.error("BusinessErrorException occured.", ex);
    HttpHeaders header = new HttpHeaders();
    return new ResponseEntity<>(
        ErrorResponse.builder()
            .errorSummary(ex.getErrorSummary().getSummary())
            .errorDetailList(ex.getErrorDetailList())
            .build(),
        header, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * システムエラーをハンドリングするメソッド
   *
   * @param ex 発生した例外
   * @return エラーレスポンス・エンティティ
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex) {
    log.error("Unexpected Exception occured.", ex);
    HttpHeaders header = new HttpHeaders();
    return new ResponseEntity<>(getSystemErrorResponse(), header, HttpStatus.INTERNAL_SERVER_ERROR);
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
            .map(e -> {
              String field =
                  FieldError.class.isInstance(e) ? FieldError.class.cast(e).getField() : null;
              return new ErrorResponse.ErrorDetail(e.getCode(), e.getDefaultMessage(), field);
            })
            .toList())
        .build();
  }

  /**
   * システムエラーのレスポンスを取得するメソッド
   *
   * @return システムエラーのレスポンス
   */
  private ErrorResponse getSystemErrorResponse() {
    return ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.SYSTEM_ERROR.getSummary())
        .errorDetailList(List
            .of(new ErrorResponse.ErrorDetail(MESSAGE_KEY_SYSTEM_ERROR,
                messageSource.getMessage(this.getClass(), MESSAGE_KEY_SYSTEM_ERROR), null)))
        .build();
  }
}
