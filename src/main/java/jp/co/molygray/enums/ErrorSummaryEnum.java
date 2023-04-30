package jp.co.molygray.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * エラータイトルの列挙型
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Getter
@AllArgsConstructor
public enum ErrorSummaryEnum {

    /** 入力エラー */
    INPUT_ERROR("Input Error"),
    /** ビジネスエラー */
    BUISINESS_ERROR("Business Error"),
    /** システムエラー */
    SYSTEM_ERROR("System Error");

  /** エラー概要 */
  private String summary;
}
