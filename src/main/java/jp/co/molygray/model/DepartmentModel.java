package jp.co.molygray.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部署モデルクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentModel {

  /** 部署ID */
  private Long departmentId;
  /** 排他フラグ */
  private String exclusiveFlg;
  /** 親部署ID */
  private Long parentDepartmentId;
  /** 部署名 */
  private String departmentName;
  /** 部署正式名 */
  private String departmentFullName;
}
