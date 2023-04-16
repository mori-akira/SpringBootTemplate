package jp.co.molygray.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {@link DepartmentDao}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
public class DepartmentDaoTest {

  /** 部署DAO */
  @Autowired
  private DepartmentDao departmentDao;

  /**
   * {@link DepartmentDao#selectList()}に対する入力と期待値の組み合わせを管理するクラス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  @Data
  @AllArgsConstructor
  public static class SelectListParamAndExpected {

    /** 親部署IDリスト */
    private List<Long> parentDepartmentIdList;
    /** 部署名 */
    private String departmentName;
    /** 部署正式名 */
    private String departmentFullName;
    /** 期待値 */
    private Map<String, Object> expected;
  }

  public static Stream<SelectListParamAndExpected> selectListSource() {
    return Stream.of();
  }
}
