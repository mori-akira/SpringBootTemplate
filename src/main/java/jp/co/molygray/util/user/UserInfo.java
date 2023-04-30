package jp.co.molygray.util.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * ユーザ情報
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@AllArgsConstructor
@Builder
public class UserInfo {

  /** ユーザID */
  private Long userId;
  /** ユーザ名 */
  private String userName;
}
