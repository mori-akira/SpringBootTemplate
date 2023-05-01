package jp.co.molygray.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
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
import org.springframework.boot.test.context.SpringBootTest;
import jp.co.molygray.dao.entity.DepartmentDao;
import jp.co.molygray.dto.DepartmentDto;
import jp.co.molygray.dto.DtoBase;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.exception.BusinessErrorException;
import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.response.common.ErrorResponse.ErrorDetail;
import jp.co.molygray.util.DtoCommonFieldSetter;
import jp.co.molygray.util.message.MultiMessageSource;

/**
 * {@link DepartmentService}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
public class DepartmentServiceTest {

  /** {@link DepartmentDao}のモック・インスタンス */
  @Mock
  private DepartmentDao departmentDao;
  /** {@link MultiMessageSource}のモック・インスタンス */
  @Mock
  private MultiMessageSource messageSource;
  @Mock
  private DtoCommonFieldSetter dtoCommonFieldSetter;
  /** {@link DepartmentService}のモック・インスタンス */
  @InjectMocks
  private DepartmentService departmentService;
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
    uuidMock = Mockito.mockStatic(UUID.class, Mockito.CALLS_REAL_METHODS);
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
    }).when(dtoCommonFieldSetter).setCommonFieldWhenInsert(any(DepartmentDto.class));
    when(messageSource.getMessage(DepartmentService.class, "duplicateDepartmentName"))
        .thenReturn("deplicate department name");
    when(messageSource.getMessage(DepartmentService.class, "duplicateDepartmentFullName"))
        .thenReturn("deplicate department full name");
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
   * {@link DepartmentService#get()}のテストメソッド
   * <p>
   * 検索ヒットありの場合
   * </p>
   */
  @Test
  public void getTestOk1() {
    when(departmentDao.select(anyLong())).thenReturn(
        Optional.of(DepartmentDto.builder()
            .departmentId(1l)
            .parentDepartmentId(null)
            .departmentName("hoge")
            .departmentFullName("hogehoge")
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
    DepartmentModel expected = DepartmentModel.builder()
        .departmentId(1l)
        .parentDepartmentId(null)
        .departmentName("hoge")
        .departmentFullName("hogehoge")
        .exclusiveFlg("xxx")
        .build();
    DepartmentModel actual = departmentService.get(1l);
    assertEquals(expected, actual);
    verify(departmentDao).select(eq(1l));
  }

  /**
   * {@link DepartmentService#get()}のテストメソッド
   * <p>
   * 検索ヒットなしの場合
   * </p>
   */
  @Test
  public void getTestOk2() {
    when(departmentDao.select(anyLong())).thenReturn(
        Optional.empty());
    DepartmentModel expected = null;
    DepartmentModel actual = departmentService.get(1l);
    assertEquals(expected, actual);
    verify(departmentDao).select(eq(1l));
  }

  /**
   * {@link DepartmentService#searchList()}のテストメソッド
   * <p>
   * 検索条件あり、検索ヒットありの場合
   * </p>
   */
  @Test
  public void searchListTestOk1() {
    when(departmentDao.searchList(any(), any(), any())).thenReturn(List.of(
        DepartmentDto.builder()
            .departmentId(1l)
            .parentDepartmentId(null)
            .departmentName("hoge")
            .departmentFullName("hogehoge")
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
        DepartmentDto.builder()
            .departmentId(2l)
            .parentDepartmentId(1l)
            .departmentName("fuga")
            .departmentFullName("fugafuga")
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
    List<DepartmentModel> expected = List.of(
        DepartmentModel.builder()
            .departmentId(1l)
            .parentDepartmentId(null)
            .departmentName("hoge")
            .departmentFullName("hogehoge")
            .exclusiveFlg("xxx")
            .build(),
        DepartmentModel.builder()
            .departmentId(2l)
            .parentDepartmentId(1l)
            .departmentName("fuga")
            .departmentFullName("fugafuga")
            .exclusiveFlg("xxx")
            .build());
    List<DepartmentModel> actual = departmentService.searchList(List.of(), "", "");
    assertEquals(expected, actual);
    verify(departmentDao).searchList(List.of(), "", "");
  }

  /**
   * {@link DepartmentService#searchList()}のテストメソッド
   * <p>
   * 検索条件なし、検索ヒットなしの場合
   * </p>
   */
  @Test
  public void searchListTestOk2() {
    when(departmentDao.searchList(any(), any(), any())).thenReturn(Collections.emptyList());
    List<DepartmentModel> expected = Collections.emptyList();
    List<DepartmentModel> actual = departmentService.searchList(null, null, null);
    assertEquals(expected, actual);
    verify(departmentDao).searchList(null, null, null);
  }

  /**
   * {@link DepartmentService#insert()}のテストメソッド
   */
  @Test
  public void insertTestOk() {
    when(departmentDao.searchList(eq(null), eq("hoge"), eq(null)))
        .thenReturn(Collections.emptyList());
    when(departmentDao.searchList(eq(null), eq(null), eq("fuga")))
        .thenReturn(Collections.emptyList());
    doAnswer(invocation -> {
      DepartmentDto dto = DepartmentDto.class.cast(invocation.getArgument(0));
      dto.setDepartmentId(3l);
      return null;
    }).when(departmentDao).insert(any(DepartmentDto.class));
    DepartmentModel model = DepartmentModel.builder()
        .parentDepartmentId(2l)
        .departmentName("hoge")
        .departmentFullName("fuga")
        .build();
    Long actual = departmentService.insert(model);
    assertEquals(3l, actual);
    verify(departmentDao).insert(eq(DepartmentDto.builder()
        .departmentId(3l)
        .parentDepartmentId(2l)
        .departmentName("hoge")
        .departmentFullName("fuga")
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
   * {@link DepartmentService#insert()}のテストメソッド
   * <p>
   * 部署名の重複エラー発生時
   * </p>
   */
  @Test
  public void insertTestError1() {
    when(departmentDao.searchList(eq(null), eq("hoge"), eq(null)))
        .thenReturn(List.of(new DepartmentDto()));
    when(departmentDao.searchList(eq(null), eq(null), eq("fuga")))
        .thenReturn(Collections.emptyList());
    doAnswer(invocation -> {
      DepartmentDto dto = DepartmentDto.class.cast(invocation.getArgument(0));
      dto.setDepartmentId(3l);
      return null;
    }).when(departmentDao).insert(any(DepartmentDto.class));
    DepartmentModel model = DepartmentModel.builder()
        .parentDepartmentId(2l)
        .departmentName("hoge")
        .departmentFullName("fuga")
        .build();
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> departmentService.insert(model));
    assertEquals(
        List.of(ErrorDetail.builder()
            .errorCode("duplicateDepartmentName")
            .errorMessage("deplicate department name")
            .errorItem("departmentName")
            .build()),
        ex.getErrorDetailList());
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
  }

  /**
   * {@link DepartmentService#insert()}のテストメソッド
   * <p>
   * 部署正式名の重複エラー発生時
   * </p>
   */
  @Test
  public void insertTestError2() {
    when(departmentDao.searchList(eq(null), eq("hoge"), eq(null)))
        .thenReturn(Collections.emptyList());
    when(departmentDao.searchList(eq(null), eq(null), eq("fuga")))
        .thenReturn(List.of(new DepartmentDto()));
    doAnswer(invocation -> {
      DepartmentDto dto = DepartmentDto.class.cast(invocation.getArgument(0));
      dto.setDepartmentId(3l);
      return null;
    }).when(departmentDao).insert(any(DepartmentDto.class));
    DepartmentModel model = DepartmentModel.builder()
        .parentDepartmentId(2l)
        .departmentName("hoge")
        .departmentFullName("fuga")
        .build();
    BusinessErrorException ex = assertThrowsExactly(BusinessErrorException.class,
        () -> departmentService.insert(model));
    assertEquals(
        List.of(ErrorDetail.builder()
            .errorCode("duplicateDepartmentFullName")
            .errorMessage("deplicate department full name")
            .errorItem("departmentFullName")
            .build()),
        ex.getErrorDetailList());
    assertEquals(ErrorSummaryEnum.BUISINESS_ERROR, ex.getErrorSummary());
  }

  /**
   * {@link DepartmentService#update()}のテストメソッド
   */
  @Test
  public void updateTestOk() {
    when(departmentDao.select(eq(1l))).thenReturn(Optional.of(
        DepartmentDto.builder()
            .departmentId(1l)
            .parentDepartmentId(2l)
            .departmentName("hoge")
            .departmentFullName("fuga")
            .deleteFlg(false)
            .exclusiveFlg("xxx")
            .insertDatetime(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .insertUser(1l)
            .insertFunction("func")
            .updateDatetime(
                ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
            .updateUser(1l)
            .updateFunction("func")
            .deleteDatetime(null)
            .deleteUser(null)
            .deleteFunction(null)
            .build()));
    when(departmentDao.searchList(eq(null), eq("hoge"), eq(null)))
        .thenReturn(Collections.emptyList());
    when(departmentDao.searchList(eq(null), eq(null), eq("fuga")))
        .thenReturn(Collections.emptyList());
    when(departmentDao.update(any(DepartmentDto.class))).thenReturn(1);
    DepartmentModel model = DepartmentModel.builder()
        .departmentId(1l)
        .exclusiveFlg("xxx")
        .parentDepartmentId(2l)
        .departmentName("hoge")
        .departmentFullName("fuga")
        .build();
    assertDoesNotThrow(() -> departmentService.update(model));
    verify(departmentDao).update(eq(DepartmentDto.builder()
        .departmentId(1l)
        .parentDepartmentId(2l)
        .departmentName("hoge")
        .departmentFullName("fuga")
        .deleteFlg(false)
        .exclusiveFlg("xxx")
        .newExclusiveFlg("aaaaaaaabbbbccccddddeeeeeeeeeeee")
        .insertDatetime(
            ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(0l)
        .insertFunction("func")
        .updateDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("func")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build()));
  }
}
