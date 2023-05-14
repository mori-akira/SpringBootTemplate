package jp.co.molygray.parameter.employee;

import jakarta.validation.constraints.Size;
import jp.co.molygray.constraints.LongField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 社員情報Get APIパラメータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInfoParameter {

  /** 社員ID */
  @LongField
  private String employeeId;
  /** 社員番号 */
  @Size(min = 9, max = 10)
  private String employeeNumber;
}
