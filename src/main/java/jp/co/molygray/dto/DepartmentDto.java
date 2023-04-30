package jp.co.molygray.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 部署DTOクラス
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
public class DepartmentDto extends DtoBase {

  /** 部署ID */
  private Long departmentId;
  /** 親部署ID */
  private Long parentDepartmentId;
  /** 部署名 */
  private String departmentName;
  /** 部署正式名 */
  private String departmentFullName;
}
