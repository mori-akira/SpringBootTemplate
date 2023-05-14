package jp.co.molygray.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import jp.co.molygray.dto.DtoBase;
import jp.co.molygray.enums.FunctionIdEnum;
import jp.co.molygray.util.accessor.FunctionIdAccessor;
import jp.co.molygray.util.accessor.RequestTimeAccessor;
import jp.co.molygray.util.accessor.UserInfoAccessor;
import jp.co.molygray.util.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * {@link DtoCommonFieldSetterTest}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@ExtendWith(MockitoExtension.class)
public class DtoCommonFieldSetterTest {

  /** {@link RequestTimeAccessor}のモック・インスタンス */
  @Mock
  private RequestTimeAccessor requestTimeAccessor;
  /** {@link UserInfoAccessor}のモック・インスタンス */
  @Mock
  private UserInfoAccessor userInfoAccessor;
  /** {@link FunctionIdAccessor}のモック・インスタンス */
  @Mock
  private FunctionIdAccessor functionIdAccessor;
  /** {@link DtoCommonFieldSetter}のモック・インスタンス */
  @InjectMocks
  private DtoCommonFieldSetter dtoCommonFieldSetter;
  /** Mockitoインスタンス */
  private AutoCloseable closeable;
  /** {@link UUID}のモック・インスタンス */
  private MockedStatic<UUID> uuidMock;

  /**
   * 前処理メソッド
   */
  @BeforeEach
  public void init() {
    closeable = MockitoAnnotations.openMocks(this);
    UUID uuid = UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee");
    uuidMock = Mockito.mockStatic(UUID.class, Mockito.CALLS_REAL_METHODS);
    uuidMock.when(UUID::randomUUID)
        .thenReturn(uuid);
    when(requestTimeAccessor.getRequestTime())
        .thenReturn(ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()));
    when(userInfoAccessor.getUserInfo())
        .thenReturn(UserInfo.builder()
            .userId(1l)
            .userName("hogehoge")
            .build());
    when(functionIdAccessor.getFunctionId()).thenReturn(FunctionIdEnum.API_DEP_GET);
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
   * テスト用のDtoクラス
   *
   * @author Moriaki Kogure
   * @version 0.0.1
   */
  @Data
  @SuperBuilder
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString(callSuper = true)
  @EqualsAndHashCode(callSuper = true)
  private static class HogeDto extends DtoBase {

    private String hoge;
    private String fuga;
  }

  /**
   * {@link DtoCommonFieldSetter#setCommonFieldWhenInsert()}のテストメソッド
   */
  @Test
  public void setCommonFieldWhenInsertTest() {
    HogeDto expected = HogeDto.builder()
        .hoge("hoge")
        .fuga(null)
        .deleteFlg(false)
        .newExclusiveFlg("aaaaaaaabbbbccccddddeeeeeeeeeeee")
        .insertDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .insertUser(1l)
        .insertFunction("API_DEP_GET")
        .updateDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("API_DEP_GET")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build();
    HogeDto actual = HogeDto.builder()
        .hoge("hoge")
        .fuga(null)
        .build();
    dtoCommonFieldSetter.setCommonFieldWhenInsert(actual);
    assertEquals(expected, actual);
  }

  /**
   * {@link DtoCommonFieldSetter#setCommonFieldWhenUpdate()}のテストメソッド
   */
  @Test
  public void setCommonFieldWhenUpdateTest() {
    HogeDto expected = HogeDto.builder()
        .hoge("hoge")
        .fuga(null)
        .deleteFlg(false)
        .newExclusiveFlg("aaaaaaaabbbbccccddddeeeeeeeeeeee")
        .insertDatetime(null)
        .insertUser(null)
        .insertFunction(null)
        .updateDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .updateUser(1l)
        .updateFunction("API_DEP_GET")
        .deleteDatetime(null)
        .deleteUser(null)
        .deleteFunction(null)
        .build();
    HogeDto actual = HogeDto.builder()
        .hoge("hoge")
        .fuga(null)
        .deleteFlg(false)
        .newExclusiveFlg("xxx")
        .build();
    dtoCommonFieldSetter.setCommonFieldWhenUpdate(actual);
    assertEquals(expected, actual);
  }

  /**
   * {@link DtoCommonFieldSetter#setCommonFieldWhenDelete()}のテストメソッド
   */
  @Test
  public void setCommonFieldWhenDeleteTest() {
    HogeDto expected = HogeDto.builder()
        .hoge("hoge")
        .fuga(null)
        .deleteFlg(true)
        .newExclusiveFlg("aaaaaaaabbbbccccddddeeeeeeeeeeee")
        .insertDatetime(null)
        .insertUser(null)
        .insertFunction(null)
        .updateDatetime(null)
        .updateUser(null)
        .updateFunction(null)
        .deleteDatetime(
            ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .deleteUser(1l)
        .deleteFunction("API_DEP_GET")
        .build();
    HogeDto actual = HogeDto.builder()
        .hoge("hoge")
        .fuga(null)
        .deleteFlg(false)
        .newExclusiveFlg("xxx")
        .build();
    dtoCommonFieldSetter.setCommonFieldWhenDelete(actual);
    assertEquals(expected, actual);
  }
}
