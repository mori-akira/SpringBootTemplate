package jp.co.molygray.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 社員所持資格Dtoクラス
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
public class EmployeeQualificationMapDto extends DtoBase {

  /** 社員所持資格ID */
  private Long employeeQualificationMapId;
  /** 社員ID */
  private Long employeeId;
  /** 資格ID */
  private Long qualificationId;
}
