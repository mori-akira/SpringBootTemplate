package jp.co.molygray.parameter.qualification;

import jakarta.validation.constraints.NotEmpty;
import jp.co.molygray.constraints.LongField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 資格Delete APIパラメータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteParameter {

  /** 資格ID */
  @NotEmpty
  @LongField
  private String qualificationId;
  /** 排他フラグ */
  @NotEmpty
  private String exclusiveFlg;
}
