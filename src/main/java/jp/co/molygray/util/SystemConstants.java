package jp.co.molygray.util;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Setter;

/**
 * システムの定数を管理するクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
@Setter
@ConfigurationProperties(prefix = "system")
public class SystemConstants {

  /** 定数マッパー */
  private Map<String, String> constants;

  /**
   * 定数を取得するメソッド
   *
   * @param key 定数のキー
   * @return 定数値 (キーに対応する定数がない場合はnull)
   */
  public String getConstants(String key) {
    return constants.get(key);
  }

  /**
   * デフォルト値を指定して、定数を取得するメソッド
   *
   * @param key 定数のキー
   * @param defaultValue デフォルト値
   * @return 定数値 (キーに対応する定数がない場合はデフォルト値)
   */
  public String getConstantsWithDefault(String key,
      String defaultValue) {
    return constants.getOrDefault(key, defaultValue);
  }
}
