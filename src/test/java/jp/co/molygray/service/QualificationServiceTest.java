package jp.co.molygray.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import jp.co.molygray.dao.entity.QualificationDao;
import jp.co.molygray.dto.DtoBase;
import jp.co.molygray.dto.QualificationDto;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.model.QualificationModel;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;
import jp.co.molygray.util.DtoCommonFieldSetter;
import jp.co.molygray.util.message.MultiMessageSource;

/**
 * {@link QualificationService}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class QualificationServiceTest {

  /** {@link qualificationDao}のモック・インスタンス */
  @Mock
  private QualificationDao qualificationDao;
  /** {@link MultiMessageSource}のモック・インスタンス */
  @Mock
  private MultiMessageSource messageSource;
  @Mock
  private DtoCommonFieldSetter dtoCommonFieldSetter;
  /** {@link qualificationService}のモック・インスタンス */
  @InjectMocks
  private QualificationService qualificationService;
  /** Mockitoのテストクラス・インスタンス */
  private AutoCloseable closeable;
  /** {@link UUID}のモック・インスタンス */
  private MockedStatic<UUID> uuidMock;

  /**
   * 前処理メソッド
   */
  @BeforeEach
  public void init() {
    closeable = MockitoAnnotations.openMocks(this);
    // UUID#randomUUIDのMock設定
    UUID uuid = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
    uuidMock = Mockito.mockStatic(UUID.class);
    uuidMock.when(UUID::randomUUID)
        .thenReturn(uuid);
    // 共通処理のMock設定
    doAnswer(invocation -> {
      DtoBase dto = DtoBase.class.cast(invocation.getArgument(0));
      dto.setDeleteFlg(false);
      dto.setNewExclusiveFlg(UUID.randomUUID().toString().replace("-", ""));
      dto.setInsertDatetime(
          ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime());
      dto.setInsertUser(0l);
      dto.setInsertFunction("func");
      dto.setUpdateDatetime(
          ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime());
      dto.setUpdateUser(0l);
      dto.setUpdateFunction("func");
      dto.setDeleteDatetime(null);
      dto.setDeleteUser(null);
      dto.setDeleteFunction(null);
      return null;
    }).when(dtoCommonFieldSetter).setCommonFieldWhenInsert(any(QualificationDto.class));
    doAnswer(invocation -> {
      DtoBase dto = DtoBase.class.cast(invocation.getArgument(0));
      dto.setNewExclusiveFlg(UUID.randomUUID().toString().replace("-", ""));
      dto.setUpdateDatetime(
          ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime());
      dto.setUpdateUser(0l);
      dto.setUpdateFunction("func");
      dto.setDeleteDatetime(null);
      dto.setDeleteUser(null);
      dto.setDeleteFunction(null);
      return null;
    }).when(dtoCommonFieldSetter).setCommonFieldWhenUpdate(any(QualificationDto.class));
    doAnswer(invocation -> {
      DtoBase dto = DtoBase.class.cast(invocation.getArgument(0));
      dto.setDeleteFlg(true);
      dto.setNewExclusiveFlg(UUID.randomUUID().toString().replace("-", ""));
      dto.setDeleteDatetime(
          ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime());
      dto.setDeleteUser(0l);
      dto.setDeleteFunction("func");
      return null;
    }).when(dtoCommonFieldSetter).setCommonFieldWhenDelete(any(QualificationDto.class));
    when(messageSource.getMessage(QualificationService.class, "duplicateQualificationName"))
        .thenReturn("deplicate qualification name");
    when(messageSource.getMessage(QualificationService.class, "notExistsQualification"))
        .thenReturn("not exist qualification");
  }

  /**
   * 後処理メソッド
   *
   * @throws Exception 例外が発生した場合
   */
  @AfterEach
  public void tearDown()
      throws Exception {
    closeable.close();
    uuidMock.close();
  }

  /**
   * {@link QualificationService#get()}のテストメソッド
   * <p>
   * 検索ヒットありの場合
   * </p>
   */
  @Test
  public void getTestOk1() {
    when(qualificationDao.select(anyLong())).thenReturn(
        Optional.of(QualificationDto.builder()
            .qualificationId(1l)
            .qualificationName("hoge")
            .qualificationAbbreviatedName("fuga")
            .validPeriodYears(123)
            .provider("piyo")
            .deleteFlg(false)
            .exclusiveFlg("xxx")
            .insertDatetime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(99999),
                ZoneId.systemDefault()).toOffsetDateTime())
            .insertUser(0l)
            .insertFunction("test")
            .updateDatetime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(99999),
                ZoneId.systemDefault()).toOffsetDateTime())
            .updateUser(0l)
            .updateFunction("test")
            .build()));
    QualificationModel expected = QualificationModel.builder()
        .qualificationId(1l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .build();
    QualificationModel actual = qualificationService.get(1l);
    assertEquals(expected, actual);
    verify(qualificationDao).select(eq(1l));
  }

  /**
   * {@link QualificationService#get()}のテストメソッド
   * <p>
   * 検索ヒットなしの場合
   * </p>
   */
  @Test
  public void getTestOk2() {
    when(qualificationDao.select(anyLong())).thenReturn(
        Optional.empty());
    QualificationModel expected = null;
    QualificationModel actual = qualificationService.get(1l);
    assertEquals(expected, actual);
    verify(qualificationDao).select(eq(1l));
  }

  /**
   * {@link QualificationService#searchList(String, String, Integer, String))}のテストメソッド
   * <p>
   * 検索条件あり、検索ヒットありの場合
   * </p>
   */
  @Test
  public void searchListTestOk1() {
    when(qualificationDao.searchList(any(), any(), any(), any())).thenReturn(List.of(
        QualificationDto.builder()
            .qualificationId(1l)
            .qualificationName("hoge1")
            .qualificationAbbreviatedName("fuga1")
            .validPeriodYears(111)
            .provider("piyo1")
            .deleteFlg(false)
            .exclusiveFlg("xxx")
            .insertDatetime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(99999),
                ZoneId.systemDefault()).toOffsetDateTime())
            .insertUser(0l)
            .insertFunction("test")
            .updateDatetime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(99999),
                ZoneId.systemDefault()).toOffsetDateTime())
            .updateUser(0l)
            .updateFunction("test")
            .build(),
        QualificationDto.builder()
            .qualificationId(2l)
            .qualificationName("hoge2")
            .qualificationAbbreviatedName("fuga2")
            .validPeriodYears(222)
            .provider("piyo2")
            .deleteFlg(false)
            .exclusiveFlg("xxx")
            .insertDatetime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(99999),
                TimeZone.getDefault().toZoneId()).toOffsetDateTime())
            .insertUser(0l)
            .insertFunction("test")
            .updateDatetime(ZonedDateTime.ofInstant(Instant.ofEpochMilli(99999),
                TimeZone.getDefault().toZoneId()).toOffsetDateTime())
            .updateUser(0l)
            .updateFunction("test")
            .build()));
    List<QualificationModel> expected = List.of(
        QualificationModel.builder()
            .qualificationId(1l)
            .qualificationName("hoge1")
            .qualificationAbbreviatedName("fuga1")
            .validPeriodYears(111)
            .provider("piyo1")
            .exclusiveFlg("xxx")
            .build(),
        QualificationModel.builder()
            .qualificationId(2l)
            .qualificationName("hoge2")
            .qualificationAbbreviatedName("fuga2")
            .validPeriodYears(222)
            .provider("piyo2")
            .exclusiveFlg("xxx")
            .build());
    List<QualificationModel> actual = qualificationService.searchList("", "", 0, "");
    assertEquals(expected, actual);
    verify(qualificationDao).searchList("", "", 0, "");
  }

  /**
   * {@link QualificationService#searchList()}のテストメソッド
   * <p>
   * 検索条件なし、検索ヒットなしの場合
   * </p>
   */
  @Test
  public void searchListTestOk2() {
    when(qualificationDao.searchList(any(), any(), any(), any())).thenReturn(Collections.emptyList());
    List<QualificationModel> expected = Collections.emptyList();
    List<QualificationModel> actual = qualificationService.searchList(null, null, null, null);
    assertEquals(expected, actual);
    verify(qualificationDao).searchList(null, null, null, null);
  }

  /**
   * {@link QualificationService#insert()}のテストメソッド
   */
  @Test
  public void insertTestOk() {
    when(qualificationDao.searchList(eq("hoge"), eq(null), eq(null), eq(null)))
        .thenReturn(Collections.emptyList());
    doAnswer(invocation -> {
      QualificationDto dto = QualificationDto.class.cast(invocation.getArgument(0));
      dto.setQualificationId(3l);
      return null;
    }).when(qualificationDao).insert(any(QualificationDto.class));
    QualificationModel model = QualificationModel.builder()
        .qualificationId(3l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .build();
    Long actual = qualificationService.insert(model);
    assertEquals(3l, actual);
    verify(qualificationDao).insert(eq(QualificationDto.builder()
        .qualificationId(3l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .deleteFlg(false)
        .newExclusiveFlg("aaaaaaaabbbbccccddddeeeeeeeeeeee")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("func")
        .updateDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(0l)
        .updateFunction("func")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build()));
  }

  /**
   * {@link QualificationService#insert()}のテストメソッド
   * <p>
   * 資格名の重複エラー発生時
   * </p>
   */
  @Test
  public void insertTestError() {
    when(qualificationDao.searchList(eq("hoge"), eq(null), eq(null), eq(null)))
        .thenReturn(List.of(new QualificationDto()));
    doAnswer(invocation -> {
      QualificationDto dto = QualificationDto.class.cast(invocation.getArgument(0));
      dto.setQualificationId(3l);
      return null;
    }).when(qualificationDao).insert(any(QualificationDto.class));
    QualificationModel model = QualificationModel.builder()
        .qualificationId(3l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .build();
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> qualificationService.insert(model));
    assertEquals(
        List.of(ErrorDetail.builder()
            .errorCode("duplicateQualificationName")
            .errorMessage("deplicate qualification name")
            .errorItem("qualificationName")
            .build()),
        ex.getErrorDetailList());
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
  }

  /**
   * {@link QualificationService#update()}の正常系のテストメソッド1
   * <p>
   * 部署名と部署正式名を更新する場合
   * </p>
   */
  @Test
  public void updateTestOk1() {
    when(qualificationDao.select(eq(3l))).thenReturn(Optional.of(
        QualificationDto.builder()
            .qualificationId(3l)
            .exclusiveFlg("xxx")
            .qualificationName("hoge")
            .qualificationAbbreviatedName("fuga")
            .validPeriodYears(123)
            .provider("piyo")
            .deleteFlg(false)
            .exclusiveFlg("xxx")
            .insertDatetime(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .insertUser(1l)
            .insertFunction("f")
            .updateDatetime(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .updateUser(1l)
            .updateFunction("f")
            .deleteDatetime(null)
            .deleteUser(null)
            .deleteFunction(null)
            .build()));
    when(qualificationDao.searchList(eq("hogehoge"), eq(null), eq(null), eq(null)))
        .thenReturn(Collections.emptyList());
    when(qualificationDao.update(any(QualificationDto.class))).thenReturn(1);
    QualificationModel model = QualificationModel.builder()
        .qualificationId(3l)
        .exclusiveFlg("xxx")
        .qualificationName("hogehoge")
        .qualificationAbbreviatedName("fugafuga")
        .validPeriodYears(456)
        .provider("piyopiyo")
        .build();
    assertDoesNotThrow(() -> qualificationService.update(model));
    verify(qualificationDao).update(eq(QualificationDto.builder()
        .qualificationId(3l)
        .exclusiveFlg("xxx")
        .qualificationName("hogehoge")
        .qualificationAbbreviatedName("fugafuga")
        .validPeriodYears(456)
        .provider("piyopiyo")
        .deleteFlg(false)
        .exclusiveFlg("xxx")
        .newExclusiveFlg("aaaaaaaabbbbccccddddeeeeeeeeeeee")
        .insertDatetime(
            ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(1l)
        .insertFunction("f")
        .updateDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(0l)
        .updateFunction("func")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build()));
  }

  /**
   * {@link QualificationService#update()}の正常系のテストメソッド2
   * <p>
   * 部署名と部署正式名を更新しない場合
   * </p>
   */
  @Test
  public void updateTestOk2() {
    when(qualificationDao.select(eq(4l))).thenReturn(Optional.of(
        QualificationDto.builder()
            .qualificationId(4l)
            .exclusiveFlg("xxx")
            .qualificationName("hoge")
            .qualificationAbbreviatedName("fuga")
            .validPeriodYears(123)
            .provider("piyo")
            .deleteFlg(false)
            .exclusiveFlg("xxx")
            .insertDatetime(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .insertUser(1l)
            .insertFunction("f")
            .updateDatetime(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .updateUser(1l)
            .updateFunction("f")
            .deleteDatetime(null)
            .deleteUser(null)
            .deleteFunction(null)
            .build()));
    when(qualificationDao.searchList(eq("hoge"), eq(null), eq(null), eq(null)))
        .thenReturn(Collections.emptyList());
    when(qualificationDao.update(any(QualificationDto.class))).thenReturn(1);
    QualificationModel model = QualificationModel.builder()
        .qualificationId(4l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .build();
    assertDoesNotThrow(() -> qualificationService.update(model));
    verify(qualificationDao).update(eq(QualificationDto.builder()
        .qualificationId(4l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .deleteFlg(false)
        .exclusiveFlg("xxx")
        .newExclusiveFlg("aaaaaaaabbbbccccddddeeeeeeeeeeee")
        .insertDatetime(
            ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(1l)
        .insertFunction("f")
        .updateDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(0l)
        .updateFunction("func")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build()));
  }

  /**
   * {@link QualificationService#update()}のテストメソッド
   * <p>
   * 資格の存在エラー発生時
   * </p>
   */
  @Test
  public void updateTestError1() {
    when(qualificationDao.select(eq(5l))).thenReturn(Optional.empty());
    when(qualificationDao.searchList(eq("hoge"), eq(null), eq(null), eq(null)))
        .thenReturn(Collections.emptyList());
    when(qualificationDao.update(any(QualificationDto.class))).thenReturn(1);
    QualificationModel model = QualificationModel.builder()
        .qualificationId(5l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .build();
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> qualificationService.update(model));
    assertEquals(
        List.of(ErrorDetail.builder()
            .errorCode("notExistsQualification")
            .errorMessage("not exist qualification")
            .build()),
        ex.getErrorDetailList());
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    verify(qualificationDao, never()).update(any());
  }

  /**
   * {@link QualificationService#update()}のテストメソッド
   * <p>
   * 資格名の重複エラー発生時
   * </p>
   */
  @Test
  public void updateTestError2() {
    when(qualificationDao.select(eq(6l))).thenReturn(Optional.of(new QualificationDto()));
    when(qualificationDao.searchList(eq("hoge"), eq(null), eq(null), eq(null)))
        .thenReturn(List.of(new QualificationDto()));
    when(qualificationDao.update(any(QualificationDto.class))).thenReturn(1);
    QualificationModel model = QualificationModel.builder()
        .qualificationId(6l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .build();
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> qualificationService.update(model));
    assertEquals(
        List.of(ErrorDetail.builder()
            .errorCode("duplicateQualificationName")
            .errorMessage("deplicate qualification name")
            .errorItem("qualificationName")
            .build()),
        ex.getErrorDetailList());
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    verify(qualificationDao, never()).update(any());
  }

  /**
   * {@link QualificationService#delete()}の正常系のテストメソッド
   */
  @Test
  public void deleteTestOk() {
    when(qualificationDao.select(eq(7l))).thenReturn(Optional.of(
        QualificationDto.builder()
            .qualificationId(7l)
            .exclusiveFlg("xxx")
            .qualificationName("hoge")
            .qualificationAbbreviatedName("fuga")
            .validPeriodYears(123)
            .provider("piyo")
            .deleteFlg(false)
            .exclusiveFlg("xxx")
            .insertDatetime(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .insertUser(1l)
            .insertFunction("f")
            .updateDatetime(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .updateUser(1l)
            .updateFunction("f")
            .deleteDatetime(null)
            .deleteUser(null)
            .deleteFunction(null)
            .build()));
    when(qualificationDao.delete(any(Long.class), any(String.class))).thenReturn(1);
    assertDoesNotThrow(() -> qualificationService.delete(7l, "xxx"));
    verify(qualificationDao).delete(eq(7l), eq("xxx"));
  }

  /**
   * {@link QualificationService#delete()}のエラーのテストメソッド
   * <p>
   * 資格の存在エラー発生時
   * </p>
   */
  @Test
  public void deleteTestError() {
    when(qualificationDao.select(eq(8l))).thenReturn(Optional.empty());
    when(qualificationDao.delete(any(Long.class), any(String.class))).thenReturn(1);
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> qualificationService.delete(8l, "xxx"));
    assertEquals(
        List.of(ErrorDetail.builder()
            .errorCode("notExistsQualification")
            .errorMessage("not exist qualification")
            .build()),
        ex.getErrorDetailList());
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
    verify(qualificationDao, never()).delete(anyLong(), any());
  }
}
