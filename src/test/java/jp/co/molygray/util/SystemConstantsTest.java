package jp.co.molygray.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

/**
 * {@link SystemConstants}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class SystemConstantsTest {

  /**
   * {@link SystemConstants#getConstants(String)}のテストメソッド
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getConstantsTest()
      throws Exception {
    SystemConstants systemConstants = new SystemConstants();
    Map<String, String> constants = new HashMap<>() {

      {
        put("k1", "v1");
        put("k3", "v3");
      }
    };
    FieldUtils.writeField(SystemConstants.class.getDeclaredField("constants"), systemConstants,
        constants, true);
    assertEquals("v1", systemConstants.getConstants("k1"));
    assertEquals(null, systemConstants.getConstants("k2"));
  }

  /**
   * {@link SystemConstants#getConstantsWithDefault(String, String)}のテストメソッド
   *
   * @throws Exception
   */
  @Test
  public void getConstantsWithDefault()
      throws Exception {
    SystemConstants systemConstants = new SystemConstants();
    Map<String, String> constants = new HashMap<>() {

      {
        put("k1", "v1");
        put("k3", "v3");
      }
    };
    FieldUtils.writeField(SystemConstants.class.getDeclaredField("constants"), systemConstants,
        constants, true);
    assertEquals("v3", systemConstants.getConstantsWithDefault("k3", "V3"));
    assertEquals("V4", systemConstants.getConstantsWithDefault("k4", "V4"));
  }
}
