package jp.co.molygray.parameter.department;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jp.co.molygray.constraints.LongField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部署Put APIパラメータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterParameter {

  /**
   * putのマーカー・インターフェイス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  public interface Put {
  };

  /**
   * postのマーカー・インターフェイス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  public interface Post {
  };

  /** 部署ID */
  @LongField(groups = {Put.class})
  private Long departmentId;
  /** 親部署ID */
  @LongField(groups = {Put.class, Post.class})
  private Long parentDepartmentId;
  /** 部署名 */
  @NotNull(groups = {Put.class, Post.class})
  @NotEmpty(groups = {Put.class, Post.class})
  @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 64, groups = {
      Put.class, Post.class})
  private String departmentName;
  /** 部署正式名 */
  @NotNull(groups = {Put.class, Post.class})
  @NotEmpty(groups = {Put.class, Post.class})
  @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 128, groups = {
      Put.class, Post.class})
  private String departmentFullName;
}
