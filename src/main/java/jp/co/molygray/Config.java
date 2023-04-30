package jp.co.molygray;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.CustomValidatorBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.MessageInterpolator;
import jp.co.molygray.advice.ApiInterceptor;
import jp.co.molygray.util.SystemConstants;
import jp.co.molygray.util.message.CustomMessageInterpolator;
import jp.co.molygray.util.message.MultiMessageSource;
import lombok.extern.slf4j.Slf4j;

/**
 * 設定クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Configuration
@Slf4j
public class Config implements WebMvcConfigurer {

  /** システム定数 */
  @Autowired
  private SystemConstants systemConstants;
  /** API処理のインターセプター */
  @Autowired
  private ApiInterceptor apiInterceptor;

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
    Locale.setDefault(LocaleUtils
        .toLocale(systemConstants.getConstantsWithDefault("default.locale", "ja")));
    TimeZone.setDefault(TimeZone
        .getTimeZone(systemConstants.getConstantsWithDefault("default.timezone", "Asia/Tokyo")));
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(apiInterceptor).order(0);
  }
}
