package jp.co.molygray.parameter.department;

import jakarta.validation.constraints.NotEmpty;
import jp.co.molygray.constraints.LongField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部署Delete APIパラメータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteParameter {

  /** 部署ID */
  @NotEmpty
  @LongField
  private String departmentId;
  /** 排他フラグ */
  @NotEmpty
  private String exclusiveFlg;
}
