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
import jp.co.molygray.dto.QualificationDto;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;

/**
 * {@link QualificationDao}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QualificationDaoTest {

  /** 資格Dao */
  @Autowired
  private QualificationDao qualificationDao;

  /**
   * {@link QualificationDao#select()}のテストメソッド
   */
  @Test
  @Order(1)
  public void selectTest() {
    Optional<QualificationDto> expected = Optional.of(QualificationDto.builder()
        .qualificationId(1l)
        .qualificationName("ITパスポート試験")
        .qualificationAbbreviatedName("IP")
        .validPeriodYears(null)
        .provider("IPA")
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
    Optional<QualificationDto> actual = qualificationDao.select(1l);
    assertEquals(expected, actual);
  }

  /**
   * {@link QualificationDao#selectList()}のテストメソッド
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
    List<QualificationDto> expected = List.of(
        QualificationDto.builder()
            .qualificationId(1l)
            .qualificationName("ITパスポート試験")
            .qualificationAbbreviatedName("IP")
            .validPeriodYears(null)
            .provider("IPA")
            .build(),
        QualificationDto.builder()
            .qualificationId(2l)
            .qualificationName("基本情報技術者試験")
            .qualificationAbbreviatedName("FE")
            .validPeriodYears(null)
            .provider("IPA")
            .build(),
        QualificationDto.builder()
            .qualificationId(3l)
            .qualificationName("応用情報技術者試験")
            .qualificationAbbreviatedName("AP")
            .validPeriodYears(null)
            .provider("IPA")
            .build(),
        QualificationDto.builder()
            .qualificationId(4l)
            .qualificationName("ORACLE MASTER Bronze Oracle Database 12c")
            .qualificationAbbreviatedName(null)
            .validPeriodYears(null)
            .provider("Oracle")
            .build(),
        QualificationDto.builder()
            .qualificationId(5l)
            .qualificationName("Oracle Certified Java Programmer, Silver SE 8")
            .qualificationAbbreviatedName(null)
            .validPeriodYears(null)
            .provider("Oracle")
            .build(),
        QualificationDto.builder()
            .qualificationId(6l)
            .qualificationName("Oracle Certified Java Programmer, Gold SE 8")
            .qualificationAbbreviatedName(null)
            .validPeriodYears(null)
            .provider("Oracle")
            .build(),
        QualificationDto.builder()
            .qualificationId(7l)
            .qualificationName("HTML5プロフェッショナル認定 レベル1")
            .qualificationAbbreviatedName("HTML5(CSS3) レベル1")
            .validPeriodYears(5)
            .provider("LPI")
            .build(),
        QualificationDto.builder()
            .qualificationId(8l)
            .qualificationName("HTML5プロフェッショナル認定 レベル2")
            .qualificationAbbreviatedName("HTML5(CSS3) レベル2")
            .validPeriodYears(5)
            .provider("LPI")
            .build(),
        QualificationDto.builder()
            .qualificationId(9l)
            .qualificationName("Python 3 エンジニア認定基礎試験")
            .qualificationAbbreviatedName(null)
            .validPeriodYears(null)
            .provider("一般社団法人Pythonエンジニア育成推進協会")
            .build(),
        QualificationDto.builder()
            .qualificationId(10l)
            .qualificationName("Google Certification Associate Cloud Engineer")
            .qualificationAbbreviatedName(null)
            .validPeriodYears(3)
            .provider("Google")
            .build());
    expected.forEach(e -> BeanUtils.copyProperties(base, e));
    List<QualificationDto> actual = qualificationDao.selectList();
    assertEquals(expected, actual);
  }

  /**
   * {@link QualificationDao#insert()}のテストメソッド
   */
  @Test
  @Order(3)
  public void insertTest() {
    // Nullable項目設定
    QualificationDto expected = QualificationDto.builder()
        .qualificationId(11l)
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
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
    QualificationDto actual = new QualificationDto();
    BeanUtils.copyProperties(expected, actual);
    actual.setQualificationId(null);
    assertEquals(1, qualificationDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("abc");
    expected.setNewExclusiveFlg(null);
    actual = qualificationDao.select(actual.getQualificationId()).orElse(null);
    assertEquals(expected, actual);

    // Nullable項目未設定
    expected = QualificationDto.builder()
        .qualificationId(12l)
        .qualificationName("hoge2")
        .qualificationAbbreviatedName("fuga2")
        .validPeriodYears(456)
        .provider("piyo2")
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
    actual = new QualificationDto();
    BeanUtils.copyProperties(expected, actual);
    actual.setQualificationId(null);
    assertEquals(1, qualificationDao.insert(actual));
    assertEquals(expected, actual);
    expected.setExclusiveFlg("def");
    expected.setNewExclusiveFlg(null);
    actual = qualificationDao.select(actual.getQualificationId()).orElse(null);
    assertEquals(expected, actual);
  }

  /**
   * {@link QualificationDao#update()}のテストメソッド
   */
  @Test
  @Order(4)
  public void updateTest() {
    // Nullable項目設定
    QualificationDto dto = QualificationDto.builder()
        .qualificationId(1l)
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
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
    assertEquals(1, qualificationDao.update(dto));
    QualificationDto expected = qualificationDao.select(1l).orElse(null);
    dto.setExclusiveFlg("abc");
    dto.setNewExclusiveFlg(null);
    assertEquals(expected, dto);

    // Nullable項目未設定
    dto = QualificationDto.builder()
        .qualificationId(2l)
        .qualificationName("hoge2")
        .qualificationAbbreviatedName("fuga2")
        .validPeriodYears(456)
        .provider("piyo2")
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
    assertEquals(1, qualificationDao.update(dto));
    expected = qualificationDao.select(2l).orElse(null);
    dto.setExclusiveFlg("def");
    dto.setNewExclusiveFlg(null);
    assertEquals(expected, dto);
  }

  /**
   * {@link QualificationDao#update()}の排他チェックのテストメソッド
   */
  @Test
  @Order(5)
  public void updateTestExclusiveCheck() {
    QualificationDto dto = QualificationDto.builder()
        .qualificationId(3l)
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
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
        assertThrowsExactly(BusinessErrorException.class, () -> qualificationDao.update(dto));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "資格が更新されています。", null)),
        ex.getErrorDetailList());
  }

  /**
   * {@link QualificationDao#delete()}のテストメソッド
   */
  @Test
  @Order(6)
  public void deleteTest() {
    assertEquals(1, qualificationDao.delete(3l, "xxx"));
    assertNull(qualificationDao.select(3l).orElse(null));
  }

  /**
   * {@link QualificationDao#delete()}の排他チェックのテストメソッド
   */
  @Test
  @Order(8)
  public void deleteTestExclusiveCheck() {
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> qualificationDao.delete(4l, "xxxx"));
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    assertEquals(List.of(new ErrorDetail("exclusiveError", "資格が更新されています。", null)),
        ex.getErrorDetailList());
  }
}
