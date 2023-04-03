package jp.co.molygray.parameter.sample;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jp.co.molygray.constraints.FormatDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Sampleのパラメータ
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
public class SampleParameter {

    /** 社員番号 */
    @NotNull
    @Size(min = 9, max = 10)
    private String employeeNumber;
    /** 社員名 */
    @NotNull
    @Size(min = 1, max = 50)
    private String employeeName;
    /** 有効日(From) */
    @FormatDate(format = "uuuu-MM-dd")
    private String validDateFrom;
    /** 有効日(To) */
    @FormatDate(format = "uuuu-MM-dd")
    private String validDateTo;
}
