package jp.co.molygray.parameter.qualification;

import jakarta.validation.constraints.NotEmpty;
import jp.co.molygray.constraints.LongField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 資格Get APIパラメータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetParameter {

  /** 資格ID */
  @NotEmpty
  @LongField
  private String qualificationId;
}
