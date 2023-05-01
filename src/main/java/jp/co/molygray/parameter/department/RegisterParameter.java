package jp.co.molygray.parameter.department;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jp.co.molygray.constraints.Empty;
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
   * patchのマーカー・インターフェイス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  public interface Patch {
  };

  /** 部署ID */
  @Empty(groups = {Put.class})
  @NotEmpty(groups = {Patch.class})
  @LongField(groups = {Patch.class})
  private String departmentId;
  /** 排他フラグ */
  @Empty(groups = {Put.class})
  @NotEmpty(groups = {Patch.class})
  private String exclusiveFlg;
  /** 親部署ID */
  @LongField
  private String parentDepartmentId;
  /** 部署名 */
  @NotEmpty
  @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 64)
  private String departmentName;
  /** 部署正式名 */
  @NotEmpty
  @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 128)
  private String departmentFullName;
}
