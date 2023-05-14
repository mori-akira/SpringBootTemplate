package jp.co.molygray.dao.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.molygray.dto.DepartmentDto;
import jp.co.molygray.dto.DtoBase;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentDaoTest {

  /** 部署DAO */
  @Autowired
  private DepartmentDao departmentDao;

  /**
   * {@link DepartmentDao#select(long)}のテストメソッド
   */
  @Test
  @Order(1)
  public void selectTest() {
    Optional<DepartmentDto> expected =
        Optional.of(DepartmentDto.builder()
            .departmentId(1l)
            .parentDepartmentId(null)
            .departmentName("IT事業部")
            .departmentFullName("IT事業部")
            .deleteFlg(false)
            .exclusiveFlg("xxx")
            .insertDatetime(
                ZonedDateTime.of(2023, 3, 1, 12, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .insertUser(0l)
            .insertFunction("manual")
            .updateDatetime(
                ZonedDateTime.of(2023, 3, 1, 12, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .updateUser(0l)
            .updateFunction("manual")
            .build());
    Optional<DepartmentDto> actual = departmentDao.select(1l);
    assertEquals(expected, actual);
  }

  /**
   * {@link DepartmentDao#selectList()}のテストメソッド
   */
  @Test
  @Order(2)
  public void selectListTest() {
    DtoBase base = DtoBase.builder()
        .deleteFlg(false)
        .exclusiveFlg("xxx")
        .insertDatetime(
            ZonedDateTime.of(2023, 3, 1, 12, 0, 0, 0, ZoneId.systemDefault())
                .toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("manual")
        .updateDatetime(
            ZonedDateTime.of(2023, 3, 1, 12, 0, 0, 0, ZoneId.systemDefault())
                .toOffsetDateTime())
        .updateUser(0l)
        .updateFunction("manual")
        .build();
    List<DepartmentDto> expected = List.of(
        DepartmentDto.builder()
            .departmentId(1l)
            .parentDepartmentId(null)
            .departmentName("IT事業部")
            .departmentFullName("IT事業部")
            .build(),
        DepartmentDto.builder()
            .departmentId(2l)
            .parentDepartmentId(1l)
            .departmentName("システム開発部")
            .departmentFullName("IT事業部　システム開発部")
            .build(),
        DepartmentDto.builder()
            .departmentId(3l)
            .parentDepartmentId(2l)
            .departmentName("東京開発課")
            .departmentFullName("IT事業部　システム開発部　東京開発課")
            .build(),
        DepartmentDto.builder()
            .departmentId(4l)
            .parentDepartmentId(2l)
            .departmentName("大阪開発課")
            .departmentFullName("IT事業部　システム開発部　大阪開発課")
            .build(),
        DepartmentDto.builder()
            .departmentId(5l)
            .parentDepartmentId(null)
            .departmentName("管理本部")
            .departmentFullName("管理本部")
            .build(),
        DepartmentDto.builder()
            .departmentId(6l)
            .parentDepartmentId(5l)
            .departmentName("経理課")
            .departmentFullName("管理本部　経理課")
            .build(),
        DepartmentDto.builder()
            .departmentId(7l)
            .parentDepartmentId(5l)
            .departmentName("総務課")
            .departmentFullName("管理本部　総務課")
            .build(),
        DepartmentDto.builder()
            .departmentId(8l)
            .parentDepartmentId(5l)
            .departmentName("教育課")
            .departmentFullName("管理本部　教育課")
            .build());
    expected.forEach(e -> BeanUtils.copyProperties(base, e));
    List<DepartmentDto> actual = departmentDao.selectList();
    assertEquals(expected, actual);
  }

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
   * {@link DepartmentDao#searchList(List, String, String)}に対する入力と期待値の組み合わせを管理するクラス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  @Data
  @AllArgsConstructor
  private static class SearchtListParamAndExpected {

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
   * {@link DepartmentDao#searchList(List, String, String)}のテストデータ
   *
   * @return テストデータ
   */
  private static Stream<SearchtListParamAndExpected> searchListSource() {
    return Stream.of(
        new SearchtListParamAndExpected(null, null, null, List.of(1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l)),
        new SearchtListParamAndExpected(List.of(), "", "", List.of(1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l)),
        new SearchtListParamAndExpected(List.of(1l), null, null, List.of(2l)),
        new SearchtListParamAndExpected(null, "IT", null, List.of(1l)),
        new SearchtListParamAndExpected(null, null, "東京", List.of(3l)),
        new SearchtListParamAndExpected(List.of(2l), "大阪", "IT", List.of(4l)),
        new SearchtListParamAndExpected(List.of(10l), null, null, List.of()));
  }

  /**
   * {@link DepartmentDao#searchList(List, String, String)}のテストメソッド
   *
   * @param input テストデータ
   */
  @ParameterizedTest
  @MethodSource("searchListSource")
  @Order(3)
  public void searchListTest(SearchtListParamAndExpected input) {
    List<DepartmentDto> expected = getTestData(input.getExpected());
    List<DepartmentDto> actual = departmentDao.searchList(input.getParentDepartmentIdList(),
        input.getDepartmentName(), input.getDepartmentFullName());
    assertEquals(expected, actual);
  }

  /**
   * {@link DepartmentDao#insert(DepartmentDto)}のテストメソッド
   */
  @Test
  @Order(4)
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
    assertEquals(1, departmentDao.insert(actual));
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
        .deleteFlg(false)
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
        .deleteFlg(false)
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
    assertEquals(1, departmentDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("def");
    expected.setNewExclusiveFlg(null);
    actual = departmentDao.select(actual.getDepartmentId()).orElse(null);
    assertEquals(expected, actual);
  }

  /**
   * {@link DepartmentDao#update(DepartmentDto)}のテストメソッド
   */
  @Test
  @Order(5)
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
        .deleteFlg(false)
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
        .deleteFlg(false)
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
   * {@link DepartmentDao#update(DepartmentDto)}の排他チェックのテストメソッド
   */
  @Test
  @Order(6)
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

  /**
   * {@link DepartmentDao#delete(long, String)}のテストメソッド
   */
  @Test
  @Order(7)
  public void deleteTest() {
    int actual = departmentDao.delete(3l, "xxx");
    assertEquals(1, actual);
    DepartmentDto dto = departmentDao.select(3l).orElse(null);
    assertNull(dto);
  }

  /**
   * {@link DepartmentDao#delete(long, String)}の排他チェックのテストメソッド
   */
  @Test
  @Order(8)
  public void deleteTestExclusiveCheck() {
    BusinessErrorException ex =
        assertThrowsExactly(BusinessErrorException.class, () -> departmentDao.delete(4l, "xxxx"));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "部署が更新されています。", null)),
        ex.getErrorDetailList());
  }
}
