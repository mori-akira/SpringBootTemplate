package jp.co.molygray.util.message;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import jakarta.servlet.http.HttpServletRequest;
import net.rakugakibox.util.YamlResourceBundle;

/**
 * 独自メッセージソース
 * <p>
 * 次をカスタムするため作成する。
 * </p>
 * <ol>
 * <li>メッセージの多言語化</li>
 * <li>呼び出しクラスによるメッセージキーの共通化</li>
 * <li>Yamlによるメッセージリソース定義</li>
 * </ol>
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 * @see org.springframework.context.MessageSource
 */
public class MultiMessageSource extends ResourceBundleMessageSource {

  /** メッセージキーの接尾辞 */
  public static final String MESSAGE_KEY_SUFFIX = "message";
  /** HTTPリクエスト情報 */
  @Autowired
  private HttpServletRequest request;

  /**
   * メッセージを取得するメソッド
   *
   * @param sourceClass 呼び出しクラス
   * @param key メッセージキー
   * @param param メッセージパラメータ
   * @return メッセージ
   * @throws NoSuchMessageExeption メッセージキーに対応するメッセージが存在しない場合
   */
  public String getMessage(Class<?> sourceClass,
      String key,
      Object... param) {
    key = String.join(".", sourceClass.getName(), key, MESSAGE_KEY_SUFFIX);
    return this.getMessage(key, param);
  }

  /**
   * メッセージを取得するメソッド
   *
   * @param key メッセージキー
   * @param param メッセージパラメータ
   * @return メッセージ
   * @throws NoSuchMessageExeption メッセージキーに対応するメッセージが存在しない場合
   */
  public String getMessage(String key,
      Object... param) {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(Locale.getDefault());
    return super.getMessage(key, param, localeResolver.resolveLocale(request));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResourceBundle doGetBundle(String basename,
      Locale locale)
      throws MissingResourceException {
    return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control.INSTANCE);
  }
}
