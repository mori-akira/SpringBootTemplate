package jp.co.molygray.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 社員住所DTOクラス
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
public class EmployeeAddressDto extends DtoBase {

  /** 社員住所ID */
  private Long employeeAddressId;
  /** 社員ID */
  private Long employeeId;
  /** 国 */
  private String country;
  /** 県 */
  private String prefecture;
  /** 市 */
  private String city;
  /** 区 */
  private String ward;
  /** 詳細1 */
  private String detail1;
  /** 詳細2 */
  private Integer detail2;
}
