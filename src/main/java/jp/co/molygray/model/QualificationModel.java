package jp.co.molygray.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 資格モデルクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualificationModel {

  /** 資格ID */
  private Long qualificationId;
  /** 排他フラグ */
  private String exclusiveFlg;
  /** 資格名 */
  private String qualificationName;
  /** 資格省略名 */
  private String qualificationAbbreviatedName;
  /** 有効年数 */
  private Integer validPeriodYears;
  /** 提供組織 */
  private String provider;
}
