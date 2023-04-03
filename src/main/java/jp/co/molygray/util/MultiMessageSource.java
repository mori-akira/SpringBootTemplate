package jp.co.molygray.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import net.rakugakibox.util.YamlResourceBundle;

/**
 * 多言語化用のメッセージソース
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 * @see org.springframework.context.MessageSource
 */
public class MultiMessageSource extends ResourceBundleMessageSource {

    /**
     * HTTPリクエスト情報
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * メッセージを取得するメソッド
     *
     * @param key メッセージキー
     * @param param メッセージパラメータ
     * @return メッセージ
     * @throws NoSuchMessageExeption メッセージキーに対応するメッセージが存在しない場合
     */
    public String getMessage(String key, Object... param) {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.getDefault());
        return super.getMessage(key, param, localeResolver.resolveLocale(request));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale)
            throws MissingResourceException {
        return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control.INSTANCE);
    }
}
