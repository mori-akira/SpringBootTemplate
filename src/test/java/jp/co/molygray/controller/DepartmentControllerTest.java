package jp.co.molygray.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.parameter.department.GetParameter;
import jp.co.molygray.parameter.department.ListParameter;
import jp.co.molygray.parameter.department.validator.RegisterValidator;
import jp.co.molygray.response.common.ErrorResponse;
import jp.co.molygray.response.department.GetResponse;
import jp.co.molygray.response.department.ListResponse;
import jp.co.molygray.service.DepartmentService;

/**
 * {@link DepartmentController}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DepartmentControllerTest {

  /** {@link DepartmentService}のモック・インスタンス */
  @MockBean
  private DepartmentService departmentService;
  /** {@link RegisterValidator}のモックインスタンス */
  @MockBean
  private RegisterValidator registerValidator;
  /** {@link DepartmentController}のモック・インスタンス */
  @SpyBean
  private DepartmentController departmentController;
  /** MockMVCインスタンス */
  @Autowired
  private MockMvc mockMvc;
  /** MockitoのMock管理インスタンス */
  private AutoCloseable closeable;
  /** オブジェクト・マッパー */
  @Autowired
  private ObjectMapper objectMapper;

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
   * {@link DepartmentController#get()}の正常系のテストメソッド1
   * <p>
   * 取得できる場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getTestOk1()
      throws Exception {
    DepartmentModel model = DepartmentModel.builder()
        .departmentId(1l)
        .parentDepartmentId(null)
        .departmentName("開発部")
        .departmentFullName("開発部")
        .build();
    // サービスのMock設定
    when(departmentService.get(anyLong())).thenReturn(model);
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/department/get/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new GetResponse(model))));
    // メソッド呼び出し検証
    verify(departmentController).get(eq(new GetParameter("1")));
    verify(departmentService).get(eq(1l));
  }

  /**
   * {@link DepartmentController#get()}の正常系のテストメソッド2
   * <p>
   * 取得できない場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getTestOk2()
      throws Exception {
    // サービスのMock設定
    when(departmentService.get(anyLong())).thenReturn(null);
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/department/get/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new GetResponse(null))));
    // メソッド呼び出し検証
    verify(departmentController).get(eq(new GetParameter("1")));
    verify(departmentService).get(eq(1l));
  }

  /**
   * {@link DepartmentController#get()}のバリデーションのテストメソッド1
   * <p>
   * 部署IDに空文字を指定する場合
   * <p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getTestValidation1()
      throws Exception {
    var expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("NotEmpty")
                .errorMessage("部署IDを入力してください。")
                .errorItem("departmentId")
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/department/get/1")
            .queryParam("departmentId", ""))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(departmentController, never()).get(any());
    verify(departmentService, never()).get(anyLong());
  }

  /**
   * {@link DepartmentController#get()}のバリデーションのテストメソッド2
   * <p>
   * 部署IDにLongに変換できない値を指定する場合
   * <p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getTestValidation2()
      throws Exception {
    var expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("LongField")
                .errorMessage("部署IDは整数で入力してください。")
                .errorItem("departmentId")
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/department/get/a"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(departmentController, never()).get(any());
    verify(departmentService, never()).get(anyLong());
  }

  /**
   * {@link DepartmentController#list()}の正常系のテストメソッド1
   * <p>
   * パラメータを全て設定し、取得できる場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestOk1()
      throws Exception {
    List<DepartmentModel> modelList = List.of(
        DepartmentModel.builder()
            .departmentId(1l)
            .parentDepartmentId(null)
            .departmentName("開発部")
            .departmentFullName("開発部")
            .build(),
        DepartmentModel.builder()
            .departmentId(2l)
            .parentDepartmentId(1l)
            .departmentName("開発課A")
            .departmentFullName("開発部開発課A")
            .build(),
        DepartmentModel.builder()
            .departmentId(3l)
            .parentDepartmentId(1l)
            .departmentName("開発課B")
            .departmentFullName("開発部開発課B")
            .build());
    // サービスのMock設定
    when(departmentService.searchList(any(), any(), any()))
        .thenReturn(modelList);
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/department/list")
            .queryParam("parentDepartmentIdList", "1", "2", "3", "4")
            .queryParam("departmentName", "開発")
            .queryParam("departmentFullName", "開発"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new ListResponse(modelList))));
    // メソッド呼び出し検証
    verify(departmentController)
        .list(eq(new ListParameter(List.of("1", "2", "3", "4"), "開発", "開発")));
    verify(departmentService).searchList(eq(List.of(1l, 2l, 3l, 4l)), eq("開発"), eq("開発"));
  }

  /**
   * {@link DepartmentController#list()}の正常系のテストメソッド2
   * <p>
   * パラメータを全て設定せず、取得できる場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestOk2()
      throws Exception {
    List<DepartmentModel> modelList = List.of(
        DepartmentModel.builder()
            .departmentId(1l)
            .parentDepartmentId(null)
            .departmentName("開発部")
            .departmentFullName("開発部")
            .build(),
        DepartmentModel.builder()
            .departmentId(2l)
            .parentDepartmentId(1l)
            .departmentName("開発課A")
            .departmentFullName("開発部開発課A")
            .build(),
        DepartmentModel.builder()
            .departmentId(3l)
            .parentDepartmentId(1l)
            .departmentName("開発課B")
            .departmentFullName("開発部開発課B")
            .build());
    // サービスのMock設定
    when(departmentService.searchList(any(), any(), any()))
        .thenReturn(modelList);
    // APIを呼び出して検証
    mockMvc.perform(get("/api/department/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new ListResponse(modelList))));
    // メソッド呼び出し検証
    verify(departmentController).list(eq(new ListParameter()));
    verify(departmentService).searchList(eq(null), eq(null), eq(null));
  }

  /**
   * {@link DepartmentController#list()}の正常系のテストメソッド3
   * <p>
   * パラメータを全て設定せず、取得できない場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestOk3()
      throws Exception {
    List<DepartmentModel> modelList = Collections.emptyList();
    // サービスのMock設定
    when(departmentService.searchList(any(), any(), any()))
        .thenReturn(modelList);
    // APIを呼び出して検証
    mockMvc.perform(get("/api/department/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new ListResponse(modelList))));
    // メソッド呼び出し検証
    verify(departmentController).list(eq(new ListParameter()));
    verify(departmentService).searchList(eq(null), eq(null), eq(null));
  }

  /**
   * {@link DepartmentController#list()}のバリデーションのテストメソッド1
   * <ul>
   * <li>親部署IDにLongと認識できない文字列を指定</li>
   * <li>部署名に65文字の文字列を指定</li>
   * <li>部署正式名に129文字の文字列を指定</li>
   * </ul>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestValidation1()
      throws Exception {
    var expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("NotEmpty")
                .errorMessage("親部署IDリストを入力してください。")
                .errorItem("parentDepartmentIdList[0]")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("LongField")
                .errorMessage("親部署IDリストは整数で入力してください。")
                .errorItem("parentDepartmentIdList[1]")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("部署名は64文字以下で入力してください。")
                .errorItem("departmentName")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("部署正式名は128文字以下で入力してください。")
                .errorItem("departmentFullName")
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/department/list")
            .queryParam("parentDepartmentIdList", "")
            .queryParam("parentDepartmentIdList", "a")
            .queryParam("departmentName", Stream.generate(() -> "b")
                .limit(65)
                .collect(Collectors.joining()))
            .queryParam("departmentFullName", Stream.generate(() -> "c")
                .limit(129)
                .collect(Collectors.joining())))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(departmentController, never()).list(any());
    verify(departmentService, never()).searchList(any(), any(), any());
  }

  /**
   * {@link DepartmentController#list()}の境界値のテストメソッド1
   * <ul>
   * <li>部署名に64文字の文字列を指定</li>
   * <li>部署正式名に128文字の文字列を指定</li>
   * </ul>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestBorder1()
      throws Exception {
    // サービスのMock設定
    when(departmentService.searchList(any(), any(), any()))
        .thenReturn(Collections.emptyList());
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/department/list")
            .queryParam("departmentName", Stream.generate(() -> "b")
                .limit(64)
                .collect(Collectors.joining()))
            .queryParam("departmentFullName", Stream.generate(() -> "c")
                .limit(128)
                .collect(Collectors.joining())))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(new ListResponse(Collections.emptyList()))));
    // メソッド呼び出し検証
    verify(departmentController).list(any());
    verify(departmentService).searchList(any(), any(), any());
  }
}
