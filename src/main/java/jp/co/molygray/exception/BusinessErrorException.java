package jp.co.molygray.exception;

import java.util.List;
import java.util.Optional;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;
import lombok.Getter;

/**
 * ビジネスチェックによるエラーに対応する例外クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class BusinessErrorException extends RuntimeException {

  /** エラー概要 */
  @Getter
  private ErrorSummaryEnum errorSummary;
  /** エラー詳細リスト */
  @Getter
  private List<ErrorDetail> errorDetailList;

  /**
   * コンストラクタ
   *
   * @param errorDetail エラー詳細
   */
  public BusinessErrorException(ErrorDetail errorDetail) {
    this(List.of(errorDetail));
  }

  /**
   * コンストラクタ
   *
   * @param errorDetail エラー詳細
   * @param errorSummary エラー概要
   */
  public BusinessErrorException(ErrorDetail errorDetail, ErrorSummaryEnum errorSummary) {
    this(List.of(errorDetail), errorSummary);
  }

  /**
   * コンストラクタ
   *
   * @param errorDetail エラー詳細
   * @param errorSummary エラー概要
   * @param message 例外メッセージ
   */
  public BusinessErrorException(ErrorDetail errorDetail, ErrorSummaryEnum errorSummary,
      String message) {
    this(List.of(errorDetail), errorSummary, message);
  }

  /**
   * コンストラクタ
   *
   * @param errorDetail エラー詳細
   * @param errorSummary エラー概要
   * @param message 例外メッセージ
   * @param cause 原因
   */
  public BusinessErrorException(ErrorDetail errorDetail, ErrorSummaryEnum errorSummary,
      String message, Throwable cause) {
    this(List.of(errorDetail), errorSummary, message, cause);
  }

  /**
   * コンストラクタ
   *
   * @param errorDetail エラー詳細リスト
   */
  public BusinessErrorException(List<ErrorDetail> errorDetail) {
    this(errorDetail, null);
  }

  /**
   * コンストラクタ
   *
   * @param errorDetail エラー詳細リスト
   * @param errorSummary エラー概要
   */
  public BusinessErrorException(List<ErrorDetail> errorDetail, ErrorSummaryEnum errorSummary) {
    this(errorDetail, null, null);
  }

  /**
   * コンストラクタ
   *
   * @param errorDetail エラー詳細リスト
   * @param errorSummary エラー概要
   * @param message 例外メッセージ
   */
  public BusinessErrorException(List<ErrorDetail> errorDetail, ErrorSummaryEnum errorSummary,
      String message) {
    this(errorDetail, null, null, null);
  }

  /**
   * コンストラクタ
   *
   * @param errorDetail エラー詳細リスト
   * @param errorSummary エラー概要
   * @param message 例外メッセージ
   * @param cause 原因
   */
  public BusinessErrorException(List<ErrorDetail> errorDetail, ErrorSummaryEnum errorSummary,
      String message, Throwable cause) {
    super(message, cause);
    this.errorDetailList = errorDetail;
    this.errorSummary = Optional.ofNullable(errorSummary)
        .orElse(ErrorSummaryEnum.BUISINESS_ERROR);
  }
}
