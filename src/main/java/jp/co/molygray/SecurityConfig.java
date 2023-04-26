package jp.co.molygray;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * セキュリティ関連設定クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Configuration
public class SecurityConfig {

  /**
   * リクエストの承認方法を決定するBeanを定義するメソッド
   *
   * @param http セキュリティHTTP情報
   * @return セキュリティ定義
   * @throws Exception 例外発生時
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    http.authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/api/**")
        .permitAll()
        .requestMatchers("/actuator/**")
        // TODO: 死活監視のリクエストに何かしら制限は設けたい
        .permitAll());
    return http.build();
  }
}
