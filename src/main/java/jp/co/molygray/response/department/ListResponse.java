package jp.co.molygray.response.department;

import java.util.List;

import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.response.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 部署List APIレスポンスクラス
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
public class ListResponse extends ResponseBase {

    /** 部署リスト */
    private List<DepartmentModel> departmentList;
}
