package jp.co.molygray.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.molygray.dao.entity.DepartmentDao;
import jp.co.molygray.dto.DepartmentDto;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;
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
   * {@link DepartmentDao#selectList()}のテストメソッド
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

  /**
   * {@link DepartmentDao#insert()}のテストメソッド
   */
  @Test
  public void insertTest() {
    // Nullable項目設定
    DepartmentDto expected = DepartmentDto.builder()
        .departmentId(9l)
        .parentDepartmentId(1l)
        .departmentName("hoge")
        .departmentFullName("hogehoge")
        .deleteFlg(false)
        .newExclusiveFlg("abc")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyy")
        .deleteDatetime(
            ZonedDateTime.of(2023, 3, 3, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .deleteUser(2l)
        .deleteFunction("zzz")
        .build();
    DepartmentDto actual = DepartmentDto.builder()
        .parentDepartmentId(1l)
        .departmentName("hoge")
        .departmentFullName("hogehoge")
        .deleteFlg(false)
        .newExclusiveFlg("abc")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyy")
        .deleteDatetime(
            ZonedDateTime.of(2023, 3, 3, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .deleteUser(2l)
        .deleteFunction("zzz")
        .build();
    departmentDao.insert(actual);
    assertEquals(expected, actual);
    expected.setExclusiveFlg("abc");
    expected.setNewExclusiveFlg(null);
    actual = departmentDao.select(actual.getDepartmentId()).orElse(null);
    assertEquals(expected, actual);

    // Nullable項目未設定
    expected = DepartmentDto.builder()
        .departmentId(10l)
        .parentDepartmentId(null)
        .departmentName("hoge2")
        .departmentFullName("hogehoge2")
        .deleteFlg(true)
        .newExclusiveFlg("def")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyyy")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build();
    actual = DepartmentDto.builder()
        .parentDepartmentId(null)
        .departmentName("hoge2")
        .departmentFullName("hogehoge2")
        .deleteFlg(true)
        .newExclusiveFlg("def")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyyy")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build();
    departmentDao.insert(actual);
    assertEquals(expected, actual);
    expected.setExclusiveFlg("def");
    expected.setNewExclusiveFlg(null);
    actual = departmentDao.select(actual.getDepartmentId()).orElse(null);
    assertEquals(expected, actual);
  }

  /**
   * {@link DepartmentDao#update()}のテストメソッド
   */
  @Test
  public void updateTest() {
    // Nullable項目設定
    DepartmentDto expected = DepartmentDto.builder()
        .departmentId(1l)
        .parentDepartmentId(1l)
        .departmentName("fuga")
        .departmentFullName("fugafuga")
        .deleteFlg(false)
        .exclusiveFlg("abc")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyy")
        .deleteDatetime(
            ZonedDateTime.of(2023, 3, 3, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .deleteUser(2l)
        .deleteFunction("zzz")
        .build();
    DepartmentDto dto = DepartmentDto.builder()
        .departmentId(1l)
        .parentDepartmentId(1l)
        .departmentName("fuga")
        .departmentFullName("fugafuga")
        .deleteFlg(false)
        .exclusiveFlg("xxx")
        .newExclusiveFlg("abc")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyy")
        .deleteDatetime(
            ZonedDateTime.of(2023, 3, 3, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .deleteUser(2l)
        .deleteFunction("zzz")
        .build();
    int count = departmentDao.update(dto);
    assertEquals(1, count);
    DepartmentDto actual = departmentDao.select(1l).orElse(null);
    assertEquals(expected, actual);

    // Nullable項目未設定
    expected = DepartmentDto.builder()
        .departmentId(2l)
        .parentDepartmentId(null)
        .departmentName("fuga2")
        .departmentFullName("fugafuga2")
        .deleteFlg(true)
        .exclusiveFlg("def")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyyy")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build();
    dto = DepartmentDto.builder()
        .departmentId(2l)
        .parentDepartmentId(null)
        .departmentName("fuga2")
        .departmentFullName("fugafuga2")
        .deleteFlg(true)
        .exclusiveFlg("xxx")
        .newExclusiveFlg("def")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyyy")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build();
    count = departmentDao.update(dto);
    assertEquals(1, count);
    actual = departmentDao.select(2l).orElse(null);
    assertEquals(expected, actual);
  }

  /**
   * {@link DepartmentDao#update()}の排他チェックのテストメソッド
   */
  @Test
  public void updateTestExclusiveCheck() {
    DepartmentDto dto = DepartmentDto.builder()
        .departmentId(1l)
        .parentDepartmentId(1l)
        .departmentName("fuga")
        .departmentFullName("fugafuga")
        .deleteFlg(false)
        .exclusiveFlg("yyy")
        .newExclusiveFlg("abc")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("xxx")
        .updateDatetime(
            ZonedDateTime.of(2023, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("yyy")
        .deleteDatetime(
            ZonedDateTime.of(2023, 3, 3, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .deleteUser(2l)
        .deleteFunction("zzz")
        .build();
    BusinessErrorException ex =
        assertThrowsExactly(BusinessErrorException.class, () -> departmentDao.update(dto));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "部署が更新されています。", null)),
        ex.getErrorDetailList());
  }
}
