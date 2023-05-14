package jp.co.molygray.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.context.request.WebRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.molygray.controller.DepartmentController;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.parameter.department.ListParameter;
import jp.co.molygray.response.common.ErrorResponse;

/**
 * {@link ApiControllerAdvice}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiControllerAdviceTest {

  /** SpringMVCモック */
  private MockMvc mockMvc;
  /** {@link DepartmentController}のモック・インスタンス */
  @Mock
  private DepartmentController departmentController;
  /** Apiコントローラのアドバイザ */
  @Autowired
  private ApiControllerAdvice apiControllerAdvice;
  /** オブジェクト・マッパー */
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * 前処理メソッド
   */
  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
        .setControllerAdvice(apiControllerAdvice)
        .build();
  }

  /**
   * {@link ApiControllerAdvice#handleBindException(BindException, HttpHeaders, HttpStatusCode, WebRequest)}のテストメソッド
   * <p>
   * エラーメッセージが1つの場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void handleBindExceptionTest1()
      throws Exception {
    BindingResult result = new DataBinder(new ListParameter()).getBindingResult();
    result.rejectValue("departmentName", "hoge", "fuga");
    when(departmentController.list(any())).thenThrow(new BindException(result));
    // API呼び出し
    var response = mockMvc.perform(get("/api/department/list"))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();
    // レスポンス比較
    var actual = objectMapper.readValue(response, ErrorResponse.class);
    var expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(ErrorResponse.ErrorDetail.builder()
            .errorCode("hoge")
            .errorMessage("fuga")
            .errorItem("departmentName")
            .build()))
        .build();
    assertEquals(expected, actual);
  }

  /**
   * {@link ApiControllerAdvice#handleBindException(BindException, HttpHeaders, HttpStatusCode, WebRequest)}のテストメソッド
   * <p>
   * エラーメッセージが複数の場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void handleBindExceptionTest2()
      throws Exception {
    BindingResult result = new DataBinder(new ListParameter()).getBindingResult();
    result.rejectValue("departmentName", "hoge1", "fuga1");
    result.rejectValue("departmentName", "hoge2", "fuga2");
    when(departmentController.list(any())).thenThrow(new BindException(result));
    // API呼び出し
    var response = mockMvc.perform(get("/api/department/list"))
        .andExpect(status().isBadRequest())
        .andReturn()
        .getResponse()
        .getContentAsString();
    // レスポンス比較
    var actual = objectMapper.readValue(response, ErrorResponse.class);
    var expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("hoge1")
                .errorMessage("fuga1")
                .errorItem("departmentName")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("hoge2")
                .errorMessage("fuga2")
                .errorItem("departmentName")
                .build()))
        .build();
    assertEquals(expected, actual);
  }

  /**
   * {@link ApiControllerAdvice#handleUnexpectedException(Exception))}のテストメソッド
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void handleUnexpectedExceptionTest1()
      throws Exception {
    when(departmentController.list(any())).thenThrow(new RuntimeException());
    // API呼び出し
    var response = mockMvc.perform(get("/api/department/list"))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn()
        .getResponse()
        .getContentAsString(StandardCharsets.UTF_8);
    // レスポンス比較
    var actual = objectMapper.readValue(response, ErrorResponse.class);
    var expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.SYSTEM_ERROR.getSummary())
        .errorDetailList(
            List.of(ErrorResponse.ErrorDetail.builder()
                .errorCode("systemError")
                .errorMessage("システムエラーが発生しました。")
                .build()))
        .build();
    assertEquals(expected, actual);
  }
}
