package jp.co.molygray.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 社員DTOクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EmployeeDto extends DtoBase {

  /** ID */
  private long id;
  /** 社員番号 */
  private String employeeNumber;
  /** 名前 */
  private String name;
}
