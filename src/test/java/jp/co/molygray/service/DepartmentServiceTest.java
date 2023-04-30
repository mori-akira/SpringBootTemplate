package jp.co.molygray.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jp.co.molygray.dao.entity.DepartmentDao;
import jp.co.molygray.dto.DepartmentDto;
import jp.co.molygray.model.DepartmentModel;

/**
 * {@link DepartmentService}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
public class DepartmentServiceTest {

  /** {@link DepartmentDao}のモック・インスタンス */
  @Mock
  private DepartmentDao departmentDao;
  /** {@link DepartmentService}のモック・インスタンス */
  @InjectMocks
  private DepartmentService departmentService;
  /** MockitoのMock管理インスタンス */
  private AutoCloseable closeable;

  /**
   * 前処理メソッド
   */
  @BeforeEach
  public void init() {
    closeable = MockitoAnnotations.openMocks(this);
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
  }

  /**
   * {@link DepartmentService#get()}のテストメソッド
   * <p>
   * 検索ヒットありの場合
   * </p>
   */
  @Test
  public void getTest1() {
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
  public void getTest2() {
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
  public void searchListTest1() {
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
            .build(),
        DepartmentModel.builder()
            .departmentId(2l)
            .parentDepartmentId(1l)
            .departmentName("fuga")
            .departmentFullName("fugafuga")
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
  public void searchListTest2() {
    when(departmentDao.searchList(any(), any(), any())).thenReturn(Collections.emptyList());
    List<DepartmentModel> expected = Collections.emptyList();
    List<DepartmentModel> actual = departmentService.searchList(null, null, null);
    assertEquals(expected, actual);
    verify(departmentDao).searchList(null, null, null);
  }
}
