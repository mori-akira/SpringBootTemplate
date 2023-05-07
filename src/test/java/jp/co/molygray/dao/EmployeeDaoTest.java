package jp.co.molygray.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.springframework.util.CollectionUtils;
import jp.co.molygray.dao.entity.EmployeeDao;
import jp.co.molygray.dto.DtoBase;
import jp.co.molygray.dto.EmployeeDto;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;

/**
 * {@link EmployeeDao}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDaoTest {

  /** 社員DAO */
  @Autowired
  private EmployeeDao employeeDao;

  /**
   * {@link EmployeeDao#select()}のテストメソッド
   */
  @Test
  @Order(1)
  public void selectTest() {
    Optional<EmployeeDto> expected = Optional.of(EmployeeDto.builder()
        .employeeId(1l)
        .departmentId(3l)
        .employeeNumber("0000000001")
        .sei("佐藤")
        .mei("太郎")
        .seiKana("サトウ")
        .meiKana("タロウ")
        .gender(0)
        .birthDate(
            ZonedDateTime.of(1996, 4, 15, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .hireDate(
            ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
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
    Optional<EmployeeDto> actual = employeeDao.select(1l);
    assertEquals(expected, actual);
  }

  /**
   * {@link EmployeeDao#selectList()}のテストメソッド
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
    List<EmployeeDto> expected = List.of(
        EmployeeDto.builder()
            .employeeId(1l)
            .departmentId(3l)
            .employeeNumber("0000000001")
            .sei("佐藤")
            .mei("太郎")
            .seiKana("サトウ")
            .meiKana("タロウ")
            .gender(0)
            .birthDate(
                ZonedDateTime.of(1996, 4, 15, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .hireDate(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .build(),
        EmployeeDto.builder()
            .employeeId(2l)
            .departmentId(3l)
            .employeeNumber("0000000002")
            .sei("木村")
            .mei("花子")
            .seiKana("キムラ")
            .meiKana("ハナコ")
            .gender(1)
            .birthDate(
                ZonedDateTime.of(1996, 7, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .hireDate(
                ZonedDateTime.of(2020, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .build(),
        EmployeeDto.builder()
            .employeeId(3l)
            .departmentId(3l)
            .employeeNumber("0000000003")
            .sei("青木")
            .mei("愛華")
            .seiKana("アオキ")
            .meiKana("アイカ")
            .gender(1)
            .birthDate(
                ZonedDateTime.of(1996, 12, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .hireDate(
                ZonedDateTime.of(2020, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .build(),
        EmployeeDto.builder()
            .employeeId(4l)
            .departmentId(4l)
            .employeeNumber("0000000004")
            .sei("斎藤")
            .mei("直樹")
            .seiKana("サイトウ")
            .meiKana("ナオキ")
            .gender(0)
            .birthDate(
                ZonedDateTime.of(1996, 12, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .hireDate(
                ZonedDateTime.of(2020, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .build(),
        EmployeeDto.builder()
            .employeeId(5l)
            .departmentId(6l)
            .employeeNumber("0000000005")
            .sei("井上")
            .mei("翔吾")
            .seiKana("イノウエ")
            .meiKana("ショウゴ")
            .gender(0)
            .birthDate(
                ZonedDateTime.of(1996, 12, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .hireDate(
                ZonedDateTime.of(2020, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .build(),
        EmployeeDto.builder()
            .employeeId(6l)
            .departmentId(7l)
            .employeeNumber("0000000006")
            .sei("田中")
            .mei("隆氏")
            .seiKana("タナカ")
            .meiKana("タカシ")
            .gender(0)
            .birthDate(
                ZonedDateTime.of(1996, 8, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .hireDate(
                ZonedDateTime.of(2020, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .build(),
        EmployeeDto.builder()
            .employeeId(7l)
            .departmentId(8l)
            .employeeNumber("0000000007")
            .sei("加藤")
            .mei("鈴")
            .seiKana("カトウ")
            .meiKana("リン")
            .gender(1)
            .birthDate(
                ZonedDateTime.of(1996, 8, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .hireDate(
                ZonedDateTime.of(2020, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault())
                    .toOffsetDateTime())
            .build());
    expected.forEach(e -> BeanUtils.copyProperties(base, e));
    List<EmployeeDto> actual = employeeDao.selectList();
    assertEquals(expected, actual);
  }

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

  /**
   * {@link EmployeeDao#selectByEmployeeNumber()}のテストデータ
   *
   * @return テストデータ
   */
  private static Stream<Entry<String, Long>> selectByEmployeeNumberSource() {
    Map<String, Long> map = new HashMap<>();
    map.put("0000000001", 1l);
    map.put("0000000008", null);
    return map.entrySet().stream();
  }

  /**
   * {@link EmployeeDao#selectByEmployeeNumber()}のテストメソッド
   *
   * @param source テストデータ
   */
  @ParameterizedTest
  @MethodSource("selectByEmployeeNumberSource")
  @Order(3)
  public void selectByEmployeeNumberTest(Entry<String, Long> source) {
    EmployeeDto expected = Optional.ofNullable(source.getValue())
        .map(e -> CollectionUtils.firstElement(getTestData(List.of(e))))
        .orElse(null);
    EmployeeDto actual = employeeDao.selectByEmployeeNumber(source.getKey());
    assertEquals(expected, actual);
  }

  /**
   * {@link EmployeeDao#insert()}のテストメソッド
   */
  @Test
  @Order(4)
  public void insertTest() {
    // Nullable項目設定
    EmployeeDto expected = EmployeeDto.builder()
        .employeeId(8l)
        .departmentId(1l)
        .employeeNumber("hoge")
        .sei("fuga")
        .mei("piyo")
        .seiKana("hogehoge")
        .meiKana("fugafuga")
        .gender(0)
        .birthDate(
            ZonedDateTime.of(2000, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .hireDate(
            ZonedDateTime.of(2023, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
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
    EmployeeDto actual = new EmployeeDto();
    BeanUtils.copyProperties(expected, actual);
    actual.setEmployeeId(null);
    assertEquals(1l, employeeDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("abc");
    expected.setNewExclusiveFlg(null);
    actual = employeeDao.select(actual.getEmployeeId()).orElse(null);
    assertEquals(expected, actual);

    // Nullable項目未設定
    expected = EmployeeDto.builder()
        .employeeId(9l)
        .departmentId(2l)
        .employeeNumber("hoge2")
        .sei("fuga2")
        .mei("piyo2")
        .seiKana("hogehoge2")
        .meiKana("fugafuga2")
        .gender(1)
        .birthDate(null)
        .hireDate(
            ZonedDateTime.of(2022, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .deleteFlg(false)
        .newExclusiveFlg("def")
        .insertDatetime(
            ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(3l)
        .insertFunction("sss")
        .updateDatetime(
            ZonedDateTime.of(2022, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(4l)
        .updateFunction("ttt")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build();
    BeanUtils.copyProperties(expected, actual);
    actual.setEmployeeId(null);
    assertEquals(1, employeeDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("def");
    expected.setNewExclusiveFlg(null);
    actual = employeeDao.select(actual.getEmployeeId()).orElse(null);
    assertEquals(expected, actual);
  }

  /**
   * {@link EmployeeDao#update()}のテストメソッド
   */
  @Test
  @Order(5)
  public void updateTest() {
    // Nullable項目設定
    EmployeeDto dto = EmployeeDto.builder()
        .employeeId(1l)
        .departmentId(1l)
        .employeeNumber("hoge3")
        .sei("fuga")
        .mei("piyo")
        .seiKana("hogehoge")
        .meiKana("fugafuga")
        .gender(0)
        .birthDate(
            ZonedDateTime.of(2000, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .hireDate(
            ZonedDateTime.of(2023, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
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
    assertEquals(1, employeeDao.update(dto));
    EmployeeDto expected = employeeDao.select(1l).orElse(null);
    dto.setExclusiveFlg("abc");
    dto.setNewExclusiveFlg(null);
    assertEquals(expected, dto);

    // Nullable項目未設定
    dto = EmployeeDto.builder()
        .employeeId(2l)
        .departmentId(2l)
        .employeeNumber("hoge4")
        .sei("fuga2")
        .mei("piyo2")
        .seiKana("hogehoge2")
        .meiKana("fugafuga2")
        .gender(1)
        .birthDate(null)
        .hireDate(
            ZonedDateTime.of(2022, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .deleteFlg(false)
        .exclusiveFlg("xxx")
        .newExclusiveFlg("def")
        .insertDatetime(
            ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(3l)
        .insertFunction("sss")
        .updateDatetime(
            ZonedDateTime.of(2022, 2, 2, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(4l)
        .updateFunction("ttt")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build();
    assertEquals(1, employeeDao.update(dto));
    expected = employeeDao.select(2l).orElse(null);
    dto.setExclusiveFlg("def");
    dto.setNewExclusiveFlg(null);
    assertEquals(expected, dto);
  }

  /**
   * {@link EmployeeDao#update()}の排他チェックのテストメソッド
   */
  @Test
  @Order(6)
  public void updateTestExclusiveCheck() {
    EmployeeDto dto = EmployeeDto.builder()
        .employeeId(3l)
        .departmentId(1l)
        .employeeNumber("hoge")
        .sei("fuga")
        .mei("piyo")
        .seiKana("hogehoge")
        .meiKana("fugafuga")
        .gender(0)
        .birthDate(
            ZonedDateTime.of(2000, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .hireDate(
            ZonedDateTime.of(2023, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
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
        assertThrowsExactly(BusinessErrorException.class, () -> employeeDao.update(dto));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "社員が更新されています。", null)),
        ex.getErrorDetailList());
  }

  /**
   * {@link EmployeeDao#delete()}のテストメソッド
   */
  @Test
  @Order(7)
  public void deleteTest() {
    assertEquals(1, employeeDao.delete(3l, "xxx"));
    assertNull(employeeDao.select(3l).orElse(null));
  }

  /**
   * {@link EmployeeDao#delete()}の排他チェックのテストメソッド
   */
  @Test
  @Order(8)
  public void deleteTestExclusiveCheck() {
    BusinessErrorException ex =
        assertThrowsExactly(BusinessErrorException.class, () -> employeeDao.delete(4l, "xxxx"));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "社員が更新されています。", null)),
        ex.getErrorDetailList());
  }
}
