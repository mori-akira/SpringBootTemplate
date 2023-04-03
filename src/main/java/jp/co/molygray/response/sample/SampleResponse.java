package jp.co.molygray.response.sample;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Sampleのレスポンス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleResponse {

    /** ID */
    @NonNull
    private Integer id;
    /** 有効期間 */
    private Long validDuring;
}
