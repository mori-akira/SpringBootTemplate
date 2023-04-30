package jp.co.molygray.util.accessor;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.molygray.enums.FunctionIdEnum;
import lombok.Getter;

/**
 * 機能IDへのアクセサ・クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
@RequestScope
public class FunctionIdAccessor {

  /** 機能ID */
  @Getter
  private FunctionIdEnum functionId;

  /**
   * デフォルト・コンストラクタ
   */
  public FunctionIdAccessor() {
    HttpServletRequest request = ServletRequestAttributes.class
        .cast(RequestContextHolder.currentRequestAttributes()).getRequest();
    this.functionId = FunctionIdEnum.ofRequest(request);
  }
}
