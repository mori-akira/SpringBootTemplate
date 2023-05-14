package jp.co.molygray.dao.entity;

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
import jp.co.molygray.dto.DtoBase;
import jp.co.molygray.dto.EmployeeAddressDto;
import jp.co.molygray.dto.EmployeeQualificationMapDto;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;

/**
 * {@link EmployeeQualificationMapDao}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeQualificationMapDaoTest {

  /** 社員所持資格Dao */
  @Autowired
  private EmployeeQualificationMapDao employeeQualificationMapDao;

  /**
   * {@link EmployeeAddressDao#select(long)}のテストメソッド
   */
  @Test
  @Order(1)
  public void selectTest() {
    Optional<EmployeeQualificationMapDto> expected =
        Optional.of(EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(1l)
            .employeeId(1l)
            .qualificationId(1l)
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
    Optional<EmployeeQualificationMapDto> actual = employeeQualificationMapDao.select(1l);
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
    List<EmployeeQualificationMapDto> expected = List.of(
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(1l)
            .employeeId(1l)
            .qualificationId(1l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(2l)
            .employeeId(1l)
            .qualificationId(2l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(3l)
            .employeeId(1l)
            .qualificationId(4l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(4l)
            .employeeId(1l)
            .qualificationId(5l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(5l)
            .employeeId(1l)
            .qualificationId(6l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(6l)
            .employeeId(1l)
            .qualificationId(7l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(7l)
            .employeeId(1l)
            .qualificationId(8l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(8l)
            .employeeId(1l)
            .qualificationId(9l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(9l)
            .employeeId(1l)
            .qualificationId(10l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(10l)
            .employeeId(2l)
            .qualificationId(1l)
            .build(),
        EmployeeQualificationMapDto.builder()
            .employeeQualificationMapId(11l)
            .employeeId(2l)
            .qualificationId(2l)
            .build());
    expected.forEach(e -> BeanUtils.copyProperties(base, e));
    List<EmployeeQualificationMapDto> actual = employeeQualificationMapDao.selectList();
    assertEquals(expected, actual);
  }

  /**
   * {@link EmployeeAddressDao#insert(EmployeeAddressDto)}のテストメソッド
   */
  @Test
  @Order(3)
  public void insertTest() {
    // Nullable項目設定
    EmployeeQualificationMapDto expected = EmployeeQualificationMapDto.builder()
        .employeeQualificationMapId(12l)
        .employeeId(3l)
        .qualificationId(1l)
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
    EmployeeQualificationMapDto actual = new EmployeeQualificationMapDto();
    BeanUtils.copyProperties(expected, actual);
    actual.setEmployeeQualificationMapId(null);
    assertEquals(1, employeeQualificationMapDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("abc");
    expected.setNewExclusiveFlg(null);
    actual =
        employeeQualificationMapDao.select(actual.getEmployeeQualificationMapId()).orElse(null);
    assertEquals(expected, actual);

    // Nullable項目未設定
    expected = EmployeeQualificationMapDto.builder()
        .employeeQualificationMapId(13l)
        .employeeId(3l)
        .qualificationId(2l)
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
    actual = new EmployeeQualificationMapDto();
    BeanUtils.copyProperties(expected, actual);
    actual.setEmployeeQualificationMapId(null);
    assertEquals(1, employeeQualificationMapDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("def");
    expected.setNewExclusiveFlg(null);
    actual =
        employeeQualificationMapDao.select(actual.getEmployeeQualificationMapId()).orElse(null);
    assertEquals(expected, actual);
  }

  /**
   * {@link EmployeeAddressDao#update(EmployeeAddressDto)}のテストメソッド
   */
  @Test
  @Order(4)
  public void updateTest() {
    // Nullable項目設定
    EmployeeQualificationMapDto dto = EmployeeQualificationMapDto.builder()
        .employeeQualificationMapId(1l)
        .employeeId(3l)
        .qualificationId(1l)
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
    assertEquals(1, employeeQualificationMapDao.update(dto));
    EmployeeQualificationMapDto expected = employeeQualificationMapDao.select(1l).orElse(null);
    dto.setExclusiveFlg("abc");
    dto.setNewExclusiveFlg(null);
    assertEquals(expected, dto);

    // Nullable項目未設定
    dto = EmployeeQualificationMapDto.builder()
        .employeeQualificationMapId(2l)
        .employeeId(3l)
        .qualificationId(2l)
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
    assertEquals(1, employeeQualificationMapDao.update(dto));
    expected = employeeQualificationMapDao.select(2l).orElse(null);
    dto.setExclusiveFlg("def");
    dto.setNewExclusiveFlg(null);
    assertEquals(expected, dto);
  }

  /**
   * {@link EmployeeAddressDao#update(EmployeeAddressDto)}の排他チェックのテストメソッド
   */
  @Test
  @Order(5)
  public void updateTestExclusiveCheck() {
    EmployeeQualificationMapDto dto = EmployeeQualificationMapDto.builder()
        .employeeQualificationMapId(3l)
        .employeeId(3l)
        .qualificationId(3l)
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
        assertThrowsExactly(BusinessErrorException.class,
            () -> employeeQualificationMapDao.update(dto));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "社員所持資格が更新されています。", null)),
        ex.getErrorDetailList());
  }

  /**
   * {@link EmployeeAddressDao#delete(long, String)}のテストメソッド
   */
  @Test
  @Order(6)
  public void deleteTest() {
    assertEquals(1, employeeQualificationMapDao.delete(3l, "xxx"));
    assertNull(employeeQualificationMapDao.select(3l).orElse(null));
  }

  /**
   * {@link EmployeeAddressDao#delete(long, String)}の排他チェックのテストメソッド
   */
  @Test
  @Order(8)
  public void deleteTestExclusiveCheck() {
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> employeeQualificationMapDao.delete(4l, "xxxx"));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "社員所持資格が更新されています。", null)),
        ex.getErrorDetailList());
  }
}
