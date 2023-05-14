package jp.co.molygray.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 社員Dtoクラス
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
public class EmployeeDto extends DtoBase {

  /** 社員ID */
  private Long employeeId;
  /** 部署ID */
  private Long departmentId;
  /** 社員番号 */
  private String employeeNumber;
  /** 姓 */
  private String sei;
  /** 名 */
  private String mei;
  /** 姓カナ */
  private String seiKana;
  /** 名カナ */
  private String meiKana;
  /** 性別 */
  private Integer gender;
  /** 生年月日 */
  private OffsetDateTime birthDate;
  /** 入社日 */
  private OffsetDateTime hireDate;
}
