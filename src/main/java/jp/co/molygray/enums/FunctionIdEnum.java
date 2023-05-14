package jp.co.molygray.enums;

import java.util.Arrays;
import java.util.Map.Entry;
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
    API_DEP_DELETE(new ApiRequestInfo("/department/delete.*")),
    /** 社員情報Get API */
    API_EMP_GET_INFO(new ApiRequestInfo("/employee/getInfo.*")),
    /** 資格Get API */
    API_QUA_GET(new ApiRequestInfo("/qualification/get.*")),
    /** 資格List API */
    API_QUA_LIST(new ApiRequestInfo("/qualification/list.*")),
    /** 資格Put API */
    API_QUA_PUT(new ApiRequestInfo("/qualification/put.*")),
    /** 資格Patch API */
    API_QUA_PATCH(new ApiRequestInfo("/qualification/patch.*")),
    /** 資格Delete API */
    API_QUA_DELETE(new ApiRequestInfo("/qualification/delete.*"));

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
   * {@link HttpServletRequest}から機能IDを取得するメソッド
   *
   * @param request {@link HttpServletRequest}
   * @return 機能ID (対応する機能IDが存在しない場合はnull)
   */
  @Nullable
  public static FunctionIdEnum ofRequest(@Nullable HttpServletRequest request) {
    if (request == null) {
      return null;
    }
    return Arrays.stream(FunctionIdEnum.values())
        // リクエストパスでフィルタリング
        .filter(e -> request.getServletPath()
            .matches(e.getRequestInfo().getRequestPathRegex()))
        // リクエストパラメータでフィルタリング
        .filter(e -> {
          for (Entry<String, Object> f : e.getRequestInfo().getRequestParameter().entrySet()) {
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
