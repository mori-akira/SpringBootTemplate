package jp.co.molygray.response.department;

import java.util.List;
import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.response.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 部署List APIレスポンスクラス
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
public class ListResponse extends ResponseBase {

  /** 部署リスト */
  private List<DepartmentModel> departmentList;
}
