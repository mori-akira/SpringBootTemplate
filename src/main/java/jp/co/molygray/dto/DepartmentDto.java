package jp.co.molygray.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 部署DTOクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DepartmentDto extends DtoBase {

  /** 部署ID */
  private long departmentId;
  /** 親部署ID */
  private Long parentDepartmentId;
  /** 部署名 */
  private String departmentName;
  /** 部署正式名 */
  private String departmentFullName;
}
