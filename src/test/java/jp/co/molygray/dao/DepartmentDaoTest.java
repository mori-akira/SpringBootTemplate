package jp.co.molygray.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.molygray.dao.entity.DepartmentDao;
import jp.co.molygray.dto.DepartmentDto;
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
   * テストデータを取得するメソッド
   *
   * @param idList 部署IDリスト
   * @return 部署のテストデータリスト
   */
  private List<DepartmentDto> getTestData(List<Long> idList) {
    return departmentDao.selectList()
        .stream()
        .filter(e -> idList.contains(e.getDepartmentId()))
        .toList();
  }

  /**
   * {@link DepartmentDao#selectList()}に対する入力と期待値の組み合わせを管理するクラス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  @Data
  @AllArgsConstructor
  private static class SelectListParamAndExpected {

    /** 親部署IDリスト */
    private List<Long> parentDepartmentIdList;
    /** 部署名 */
    private String departmentName;
    /** 部署正式名 */
    private String departmentFullName;
    /** 期待値のIDリスト */
    private List<Long> expected;
  }

  /**
   * 一覧選択メソッド用のテストデータ
   *
   * @return テストデータ
   */
  private static Stream<SelectListParamAndExpected> selectListSource() {
    return Stream.of(
        new SelectListParamAndExpected(null, null, null, List.of(1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l)),
        new SelectListParamAndExpected(List.of(), "", "", List.of(1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l)),
        new SelectListParamAndExpected(List.of(1l), null, null, List.of(2l)),
        new SelectListParamAndExpected(null, "IT", null, List.of(1l)),
        new SelectListParamAndExpected(null, null, "東京", List.of(3l)),
        new SelectListParamAndExpected(List.of(2l), "大阪", "IT", List.of(4l)),
        new SelectListParamAndExpected(List.of(10l), null, null, List.of()));
  }

  /**
   * {@link DepartmentDao#selectList()}のテスト・メソッド
   *
   * @param input テストデータ
   */
  @ParameterizedTest
  @MethodSource("selectListSource")
  public void selectListTest(SelectListParamAndExpected input) {
    List<DepartmentDto> expected = getTestData(input.getExpected());
    List<DepartmentDto> actual = departmentDao.searchList(input.getParentDepartmentIdList(),
        input.getDepartmentName(), input.getDepartmentFullName());
    assertEquals(expected, actual);
  }
}
