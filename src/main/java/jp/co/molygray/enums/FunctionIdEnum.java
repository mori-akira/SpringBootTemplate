package jp.co.molygray.enums;

import java.util.Arrays;
import java.util.Map;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.molygray.util.requestInfo.ApiRequestInfo;
import jp.co.molygray.util.requestInfo.RequestInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 機能IDの列挙型
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Getter
@AllArgsConstructor
public enum FunctionIdEnum {

    /** 部署Get API */
    API_DEP_GET(new ApiRequestInfo("/department/get.*")),
    /** 部署List API */
    API_DEP_LIST(new ApiRequestInfo("/department/list.*")),
    /** 部署Put API */
    API_DEP_PUT(new ApiRequestInfo("/department/put.*")),
    /** 部署Patch API */
    API_DEP_PATCH(new ApiRequestInfo("/department/patch.*")),
    /** 部署Delete API */
    API_DEP_DELETE(new ApiRequestInfo("/department/delete.*"));

  /** 機能に紐づくリクエスト情報 */
  private RequestInfo requestInfo;

  /**
   * リクエスト情報から機能IDを取得するメソッド
   *
   * @param requestInfo リクエスト情報
   * @return 機能ID (対応する機能IDが存在しない場合はnull)
   */
  @Nullable
  public static FunctionIdEnum ofRequestInfo(@NonNull RequestInfo requestInfo) {
    return Arrays.stream(FunctionIdEnum.values())
        .filter(e -> e.requestInfo.equals(requestInfo))
        .findFirst()
        .orElse(null);
  }

  /**
   * @param request
   * @return
   */
  @Nullable
  public static FunctionIdEnum ofRequest(@NonNull HttpServletRequest request) {
    return Arrays.stream(FunctionIdEnum.values())
        // リクエストパスでフィルタリング
        .filter(e -> request.getServletPath()
            .matches(e.getRequestInfo().getRequestPathRegex()))
        // リクエストパラメータでフィルタリング
        .filter(e -> {
          for (Map.Entry<String, Object> f : e.getRequestInfo().getRequestParameter().entrySet()) {
            Object value = request.getAttribute(f.getKey());
            if (value != null && f.getValue().equals(value)) {
              continue;
            } else {
              return false;
            }
          }
          return true;
        })
        .findFirst()
        .orElse(null);
  }
}
