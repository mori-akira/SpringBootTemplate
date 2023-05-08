package jp.co.molygray.parameter.qualification;

import jakarta.validation.constraints.Size;
import jp.co.molygray.constraints.IntegerField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 資格List APIパラメータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListParameter {

  /** 資格名 */
  @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 128)
  private String qualificationName;
  /** 資格省略名 */
  @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 128)
  private String qualificationAbbreviatedName;
  /** 有効年数 */
  @IntegerField
  private String validPeriodYears;
  /** 提供組織 */
  @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 128)
  private String provider;
}
