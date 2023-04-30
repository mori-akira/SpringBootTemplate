package jp.co.molygray.util.accessor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import jp.co.molygray.util.user.UserInfo;

/**
 * ユーザ情報へのアクセサ・クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class UserInfoAccessor {

  /** ユーザ情報へアクセスするキー名 */
  public static String KEY_NAME_USER_INFO = "userInfo";

  /**
   * ユーザ情報を設定するメソッド
   *
   * @param userInfo ユーザ情報
   */
  public void setUserInfo(UserInfo userInfo) {
    RequestContextHolder.currentRequestAttributes()
        .setAttribute(KEY_NAME_USER_INFO, userInfo, RequestAttributes.SCOPE_REQUEST);
  }

  /**
   * ユーザ情報を取得するメソッド
   *
   * @return ユーザ情報
   */
  public UserInfo getUserInfo() {
    return UserInfo.class.cast(
        RequestContextHolder.currentRequestAttributes()
            .getAttribute(KEY_NAME_USER_INFO, RequestAttributes.SCOPE_REQUEST));
  }
}
