package jp.co.molygray.parameter.department;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import jp.co.molygray.constraints.LongField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部署List APIパラメータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListParameter {

    /** 親部署ID */
    @Valid
    private List<@LongField String> parentDepartmentIdList;
    /** 部署名 */
    @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 64)
    private String departmentName;
    /** 部署正式名 */
    @Size(message = "{jakarta.validation.constraints.Size.max.message}", max = 128)
    private String departmentFullName;
}
