package jp.co.molygray.response.employee;

import jp.co.molygray.model.EmployeeModel;
import jp.co.molygray.response.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 社員情報Get APIレスポンスクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetInfoResponse extends ResponseBase {

  /** 社員 */
  private EmployeeModel employee;
}
