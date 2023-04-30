package jp.co.molygray.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.molygray.util.accessor.UserInfoAccessor;
import jp.co.molygray.util.user.SystemUser;
import jp.co.molygray.util.user.UserInfo;

/**
 * APIリクエストのインターセプター
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class ApiInterceptor implements HandlerInterceptor {

  /** ユーザ情報へのアクセサ */
  @Autowired
  private UserInfoAccessor userInfoAccessor;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    setUserInfo(request);
    return true;
  }

  /**
   * ユーザ情報を設定するメソッド
   *
   * @param request
   */
  private void setUserInfo(HttpServletRequest request) {
    // TODO: リクエスト情報(Cookie等)からユーザを取得する処理追加
    UserInfo userInfo = new SystemUser();
    userInfoAccessor.setUserInfo(userInfo);
  }
}
