package jp.co.molygray.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性別の列挙型
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    /** 男性 */
    MAN(0),
    /** 女性 */
    WOMAN(1);

  /** 性別の区分値 */
  private int value;

  /**
   * 区分値から列挙型を取得するメソッド
   *
   * @param value 区分値
   * @return 性別の列挙型
   */
  public static GenderEnum of(Integer value) {
    return Arrays.stream(GenderEnum.values())
        .filter(e -> e.value == value)
        .findFirst()
        .orElse(null);
  }
}
