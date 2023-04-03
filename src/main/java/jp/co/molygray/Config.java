package jp.co.molygray;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.MessageInterpolator;
import jp.co.molygray.util.CustomMessageInterpolator;
import jp.co.molygray.util.MultiMessageSource;
import jp.co.molygray.util.SystemConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * 設定クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Configuration
@Slf4j
public class Config {

    /** システム定数 */
    @Autowired
    private SystemConstants systemConstants;

    /**
     * DI完了後に呼び出されるメソッド
     * <p>
     * サーバー起動時の処理などを記述する。
     * </p>
     */
    @PostConstruct
    public void initAfterStartup() {
        log.info("Start server process.");
        // デフォルトロケールを設定
        Locale.setDefault(LocaleUtils.toLocale(
                systemConstants.getConstantWithDefault("system.default.locale", "ja")));
    }

    /**
     * インスタンスがコンテナから廃棄される前に呼び出されるメソッド
     * <p>
     * サーバー終了時の処理などを記述する。
     * </p>
     */
    @PreDestroy
    public void cleanupBeforeExit() {
        log.info("Finish server process.");
    }

    /**
     * 多言語化用メッセージソースのBeanを定義するメソッド
     *
     * @return 多言語化用メッセージソースのBean
     */
    @Bean
    MultiMessageSource messageSource() {
        MultiMessageSource messageSource = new MultiMessageSource();
        messageSource.setBasenames("messages/messages", "messages/fields",
                "messages/validation");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setAlwaysUseMessageFormat(false);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(true);
        return messageSource;
    }

    /**
     * バリデータのBeanを定義するメソッド
     *
     * @return バリデータのBean
     */
    @Bean
    CustomValidatorBean validator() {
        CustomValidatorBean validator = new CustomValidatorBean();
        validator.setMessageInterpolator(messageInterpolator());
        return validator;
    }

    /**
     * メッセージを構成するBeanを定義するメソッド
     *
     * @return メッセージを構成するBean
     */
    @Bean
    MessageInterpolator messageInterpolator() {
        return new CustomMessageInterpolator();
    }
}
