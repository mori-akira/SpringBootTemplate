package jp.co.molygray.parameter.qualification;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jp.co.molygray.constraints.Empty;
import jp.co.molygray.constraints.IntegerField;
import jp.co.molygray.constraints.LongField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 資格登録 APIパラメータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterParameter {

  /**
   * putのマーカー・インターフェイス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  public interface Put {
  };

  /**
   * patchのマーカー・インターフェイス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  public interface Patch {
  };

  /** 資格ID */
  @Empty(groups = {Put.class})
  @NotEmpty(groups = {Patch.class})
  @LongField(groups = {Patch.class})
  private String qualificationId;
  /** 排他フラグ */
  @Empty(groups = {Put.class})
  @NotEmpty(groups = {Patch.class})
  private String exclusiveFlg;
  /** 資格名 */
  @NotEmpty
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
