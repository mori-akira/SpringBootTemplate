package jp.co.molygray.response.department;

import jp.co.molygray.response.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 部署Put APIレスポンスクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutResponse extends ResponseBase {

    /** 部署ID */
    private Long id;
}
