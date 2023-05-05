package jp.co.molygray.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.molygray.dao.entity.EmployeeDao;
import jp.co.molygray.dto.EmployeeDto;

/**
 * {@link EmployeeDao}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
public class EmployeeDaoTest {

  /** 社員DAO */
  @Autowired
  private EmployeeDao employeeDao;

  /**
   * テストデータを取得するメソッド
   *
   * @param idList 社員IDリスト
   * @return 社員のテストデータリスト
   */
  private List<EmployeeDto> getTestData(List<Long> idList) {
    return employeeDao.selectList()
        .stream()
        .filter(e -> idList.contains(e.getEmployeeId()))
        .toList();
  }
}
