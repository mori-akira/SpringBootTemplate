package jp.co.molygray.response.qualification;

import jp.co.molygray.response.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 資格Put APIレスポンスクラス
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
public class PutResponse extends ResponseBase {

  /** 資格ID */
  private Long qualificationId;
}
