package jp.co.molygray;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
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
   * リクエストに対するセキュリティを設定するメソッド
   *
   * @param http Httpセキュリティ
   * @return Httpセキュリティ設定
   * @throws Exception 例外発生時
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    http.authorizeHttpRequests(
        authorize -> authorize
            .requestMatchers("/api/**")
            .permitAll()
            // TODO: 死活監視のリクエストに何かしら制限を設けたい
            .requestMatchers("/actuator/**")
            .permitAll()
            // TODO: H2コンソールのリクエストに何かしら制限を設けたい
            .requestMatchers(toH2Console())
            .permitAll());
    // H2コンソール接続設定
    http.headers()
        .frameOptions()
        .disable();
    http.csrf(csrf -> csrf
        .ignoringRequestMatchers(toH2Console()));
    // TODO: CSRF検証の正式実装
    http.csrf(csrf -> csrf
        .ignoringRequestMatchers("/api/**"));
    return http.build();
  }
}
