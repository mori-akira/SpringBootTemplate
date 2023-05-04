package jp.co.molygray.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 資格DTOクラス
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
public class QualificationDto extends DtoBase {

  /** 資格ID */
  private Long qualificationId;
  /** 資格名 */
  private String qualificationName;
  /** 資格省略名 */
  private String qualificationAbbreviatedName;
  /** 有効年数 */
  private Long validPeriodYears;
  /** 提供組織 */
  private String provider;
}
