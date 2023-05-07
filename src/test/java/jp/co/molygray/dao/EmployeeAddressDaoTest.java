package jp.co.molygray.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.molygray.dao.entity.EmployeeAddressDao;
import jp.co.molygray.dto.DtoBase;
import jp.co.molygray.dto.EmployeeAddressDto;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;

/**
 * {@link EmployeeAddressDao}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeAddressDaoTest {

  /** 社員住所Dao */
  @Autowired
  private EmployeeAddressDao employeeAddressDao;

  /**
   * {@link EmployeeAddressDao#select()}のテストメソッド
   */
  @Test
  @Order(1)
  public void selectTest() {
    Optional<EmployeeAddressDto> expected = Optional.of(EmployeeAddressDto.builder()
        .employeeAddressId(1l)
        .employeeId(1l)
        .country("日本")
        .prefecture("神奈川県")
        .city("川崎市")
        .ward("宮前区")
        .detail1("宮崎1-1-1")
        .detail2("XXXマンション　105号室")
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
    Optional<EmployeeAddressDto> actual = employeeAddressDao.select(1l);
    assertEquals(expected, actual);
  }

  /**
   * {@link EmployeeAddressDao#selectList()}のテストメソッド
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
    List<EmployeeAddressDto> expected = List.of(
        EmployeeAddressDto.builder()
            .employeeAddressId(1l)
            .employeeId(1l)
            .country("日本")
            .prefecture("神奈川県")
            .city("川崎市")
            .ward("宮前区")
            .detail1("宮崎1-1-1")
            .detail2("XXXマンション　105号室")
            .build(),
        EmployeeAddressDto.builder()
            .employeeAddressId(2l)
            .employeeId(2l)
            .country("日本")
            .prefecture("埼玉県")
            .city("春日部市")
            .ward(null)
            .detail1("春日部町1-1-1")
            .detail2(null)
            .build(),
        EmployeeAddressDto.builder()
            .employeeAddressId(3l)
            .employeeId(3l)
            .country("日本")
            .prefecture("東京都")
            .city(null)
            .ward("大田区")
            .detail1("北糀谷1-1-1")
            .detail2("XXXアパート　203号室")
            .build(),
        EmployeeAddressDto.builder()
            .employeeAddressId(4l)
            .employeeId(4l)
            .country("日本")
            .prefecture("東京都")
            .city(null)
            .ward("荒川区")
            .detail1("南千住7-7-7")
            .detail2("XXXハイツ　301号室")
            .build(),
        EmployeeAddressDto.builder()
            .employeeAddressId(5l)
            .employeeId(5l)
            .country("日本")
            .prefecture("東京都")
            .city(null)
            .ward("足立区")
            .detail1("西新井5-5-5")
            .detail2("コンバハイツXXX　202号室")
            .build(),
        EmployeeAddressDto.builder()
            .employeeAddressId(6l)
            .employeeId(6l)
            .country("日本")
            .prefecture("神奈川県")
            .city("横浜市")
            .ward("神奈川区")
            .detail1("松本町3-3-3")
            .detail2("コンバースビル　101号室")
            .build(),
        EmployeeAddressDto.builder()
            .employeeAddressId(7l)
            .employeeId(7l)
            .country("日本")
            .prefecture("千葉県")
            .city("市川市")
            .ward(null)
            .detail1("原木2-2")
            .detail2(null)
            .build());
    expected.forEach(e -> BeanUtils.copyProperties(base, e));
    List<EmployeeAddressDto> actual = employeeAddressDao.selectList();
    assertEquals(expected, actual);
  }

  /**
   * {@link EmployeeAddressDao#insert()}のテストメソッド
   */
  @Test
  @Order(3)
  public void insertTest() {
    // Nullable項目設定
    EmployeeAddressDto expected = EmployeeAddressDto.builder()
        .employeeAddressId(8l)
        .employeeId(8l)
        .country("hoge")
        .prefecture("fuga")
        .city("piyo")
        .ward("hogehoge")
        .detail1("fugafuga")
        .detail2("piyopiyo")
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
    EmployeeAddressDto actual = new EmployeeAddressDto();
    BeanUtils.copyProperties(expected, actual);
    actual.setEmployeeAddressId(null);
    assertEquals(1, employeeAddressDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("abc");
    expected.setNewExclusiveFlg(null);
    actual = employeeAddressDao.select(actual.getEmployeeAddressId()).orElse(null);
    assertEquals(expected, actual);

    // Nullable項目未設定
    expected = EmployeeAddressDto.builder()
        .employeeAddressId(9l)
        .employeeId(9l)
        .country("hoge2")
        .prefecture("fuga2")
        .city("piyo2")
        .ward("hogehoge2")
        .detail1("fugafuga2")
        .detail2("piyopiyo2")
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
    actual = new EmployeeAddressDto();
    BeanUtils.copyProperties(expected, actual);
    actual.setEmployeeAddressId(null);
    assertEquals(1, employeeAddressDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("def");
    expected.setNewExclusiveFlg(null);
    actual = employeeAddressDao.select(actual.getEmployeeAddressId()).orElse(null);
    assertEquals(expected, actual);
  }

  /**
   * {@link EmployeeAddressDao#update()}のテストメソッド
   */
  @Test
  @Order(4)
  public void updateTest() {
    // Nullable項目設定
    EmployeeAddressDto dto = EmployeeAddressDto.builder()
        .employeeAddressId(1l)
        .employeeId(1l)
        .country("hoge")
        .prefecture("fuga")
        .city("piyo")
        .ward("hogehoge")
        .detail1("fugafuga")
        .detail2("piyopiyo")
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
    assertEquals(1, employeeAddressDao.update(dto));
    EmployeeAddressDto expected = employeeAddressDao.select(1l).orElse(null);
    dto.setExclusiveFlg("abc");
    dto.setNewExclusiveFlg(null);
    assertEquals(expected, dto);

    // Nullable項目未設定
    dto = EmployeeAddressDto.builder()
        .employeeAddressId(2l)
        .employeeId(2l)
        .country("hoge2")
        .prefecture("fuga2")
        .city("piyo2")
        .ward("hogehoge2")
        .detail1("fugafuga2")
        .detail2("piyopiyo2")
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
    assertEquals(1, employeeAddressDao.update(dto));
    expected = employeeAddressDao.select(2l).orElse(null);
    dto.setExclusiveFlg("def");
    dto.setNewExclusiveFlg(null);
    assertEquals(expected, dto);
  }

  /**
   * {@link EmployeeAddressDao#update()}の排他チェックのテストメソッド
   */
  @Test
  @Order(5)
  public void updateTestExclusiveCheck() {
    EmployeeAddressDto dto = EmployeeAddressDto.builder()
        .employeeAddressId(3l)
        .employeeId(3l)
        .country("hoge")
        .prefecture("fuga")
        .city("piyo")
        .ward("hogehoge")
        .detail1("fugafuga")
        .detail2("piyopiyo")
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
        assertThrowsExactly(BusinessErrorException.class, () -> employeeAddressDao.update(dto));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "社員住所が更新されています。", null)),
        ex.getErrorDetailList());
  }

  /**
   * {@link EmployeeAddressDao#delete()}のテストメソッド
   */
  @Test
  @Order(6)
  public void deleteTest() {
    assertEquals(1, employeeAddressDao.delete(3l, "xxx"));
    assertNull(employeeAddressDao.select(3l).orElse(null));
  }

  /**
   * {@link EmployeeAddressDao#delete()}の排他チェックのテストメソッド
   */
  @Test
  @Order(8)
  public void deleteTestExclusiveCheck() {
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> employeeAddressDao.delete(4l, "xxxx"));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "社員住所が更新されています。", null)),
        ex.getErrorDetailList());
  }
}
