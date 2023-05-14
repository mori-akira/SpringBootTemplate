package jp.co.molygray.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
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
import org.springframework.validation.Errors;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.enums.GenderEnum;
import jp.co.molygray.model.DepartmentModel;
import jp.co.molygray.model.EmployeeModel;
import jp.co.molygray.model.EmployeeModel.EmployeeAddressModel;
import jp.co.molygray.model.QualificationModel;
import jp.co.molygray.parameter.department.validator.RegisterValidator;
import jp.co.molygray.parameter.employee.GetInfoParameter;
import jp.co.molygray.parameter.employee.validator.GetInfoValidator;
import jp.co.molygray.response.common.ErrorResponse;
import jp.co.molygray.response.employee.GetInfoResponse;
import jp.co.molygray.service.EmployeeService;

/**
 * {@link EmployeeController}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

  /** {@link EmployeeService}のモック・インスタンス */
  @MockBean
  private EmployeeService employeeService;
  /** {@link RegisterValidator}のモックインスタンス */
  @SpyBean
  private GetInfoValidator getInfoValidator;
  /** {@link EmployeeController}のモック・インスタンス */
  @SpyBean
  private EmployeeController employeeController;
  /** MockMVCインスタンス */
  @Autowired
  private MockMvc mockMvc;
  /** Mockitoインスタンス */
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
   * {@link EmployeeController#getInfo(GetInfoParameter)}の正常系のテストメソッド1
   * <p>
   * 取得できる場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getInfoTestOk1()
      throws Exception {
    DepartmentModel department = DepartmentModel.builder()
        .departmentId(3l)
        .exclusiveFlg("xxx")
        .parentDepartmentId(2l)
        .departmentName("東京開発課")
        .departmentFullName("IT事業部　システム開発部　東京開発課")
        .build();
    EmployeeAddressModel employeeAddress = EmployeeAddressModel.builder()
        .employeeAddressId(1l)
        .exclusiveFlg("xxx")
        .country("日本")
        .prefecture("神奈川県")
        .city("川崎市")
        .ward("宮前区")
        .detail1("宮崎1-1-1")
        .detail2("XXXマンション　105号室")
        .build();
    List<QualificationModel> qualificationList = List.of(
        QualificationModel.builder()
            .qualificationId(1l)
            .exclusiveFlg("xxx")
            .qualificationName("ITパスポート試験")
            .qualificationAbbreviatedName("IP")
            .provider("IPA")
            .build());
    EmployeeModel model = EmployeeModel.builder()
        .employeeId(1l)
        .employeeNumber("0000000001")
        .exclusiveFlg("xxx")
        .sei("佐藤")
        .mei("太郎")
        .seiKana("サトウ")
        .meiKana("タロウ")
        .gender(GenderEnum.MAN)
        .birthDate(
            ZonedDateTime.of(1996, 4, 15, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .hireDate(
            ZonedDateTime.of(2022, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toOffsetDateTime())
        .department(department)
        .employeeAddress(employeeAddress)
        .qualificationList(qualificationList)
        .build();
    // サービスのMock設定
    when(employeeService.getEmployeeInfo(any(), any())).thenReturn(model);
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/employee/getInfo")
            .queryParam("employeeId", "1")
            .queryParam("employeeNumber", "0000000001"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new GetInfoResponse(model))));
    // メソッド呼び出し検証
    verify(employeeController).getInfo(eq(new GetInfoParameter("1", "0000000001")));
    verify(employeeService).getEmployeeInfo(eq(1l), eq("0000000001"));
    verify(getInfoValidator).validate(eq(new GetInfoParameter("1", "0000000001")),
        any(Errors.class));
  }

  /**
   * {@link EmployeeController#getInfo(GetInfoParameter)}の正常系のテストメソッド2
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
    when(employeeService.getEmployeeInfo(any(), any())).thenReturn(null);
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/employee/getInfo")
            .queryParam("employeeId", "2")
            .queryParam("employeeNumber", "0000000002"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new GetInfoResponse(null))));
    // メソッド呼び出し検証
    verify(employeeController).getInfo(eq(new GetInfoParameter("2", "0000000002")));
    verify(employeeService).getEmployeeInfo(eq(2l), eq("0000000002"));
    verify(getInfoValidator).validate(eq(new GetInfoParameter("2", "0000000002")),
        any(Errors.class));
  }

  /**
   * {@link EmployeeController#getInfo(GetInfoParameter)}のバリデーションのテストメソッド1
   * <ul>
   * <li>社員IDに数値で認識できない文字列を指定する場合</li>
   * <li>社員番号に8文字の文字列を指定する場合</li>
   * </ul>
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
                .errorCode("LongField")
                .errorMessage("社員IDは整数で入力してください。")
                .errorItem("employeeId")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("社員番号は9文字~10文字で入力してください。")
                .errorItem("employeeNumber")
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/employee/getInfo")
            .queryParam("employeeId", "abc")
            .queryParam("employeeNumber", "12345678"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(employeeController, never()).getInfo(any());
    verify(employeeService, never()).getEmployeeInfo(any(), any());
    verify(getInfoValidator, never()).validate(any(), any());
  }

  /**
   * {@link EmployeeController#getInfo(GetInfoParameter)}のバリデーションのテストメソッド2
   * <ul>
   * <li>社員番号に11文字の文字列を指定する場合</li>
   * </ul>
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
                .errorCode("Size")
                .errorMessage("社員番号は9文字~10文字で入力してください。")
                .errorItem("employeeNumber")
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/employee/getInfo")
            .queryParam("employeeId", "1")
            .queryParam("employeeNumber", "12345678901"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(employeeController, never()).getInfo(any());
    verify(employeeService, never()).getEmployeeInfo(any(), any());
    verify(getInfoValidator, never()).validate(any(), any());
  }

  /**
   * {@link EmployeeController#getInfo(GetInfoParameter)}のバリデーションのテストメソッド3
   * <ul>
   * <li>社員IDと社員番号が未指定の場合</li>
   * </ul>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getTestValidation3()
      throws Exception {
    var expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("requireEmployeeIdOrEmployeeNumber")
                .errorMessage("社員IDまたは社員番号のどちらかは必須です。")
                .errorItem(null)
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/employee/getInfo"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(employeeController, never()).getInfo(any());
    verify(employeeService, never()).getEmployeeInfo(any(), any());
    verify(getInfoValidator).validate(eq(new GetInfoParameter()), any(Errors.class));
  }

  /**
   * {@link EmployeeController#getInfo(GetInfoParameter)}の境界値のテストメソッド1
   * <p>
   * 社員番号に9文字の文字列を指定する場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getTestBorder1()
      throws Exception {
    // サービスのMock設定
    when(employeeService.getEmployeeInfo(any(), any())).thenReturn(new EmployeeModel());
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/employee/getInfo")
            .queryParam("employeeNumber", "123456789"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(new GetInfoResponse(new EmployeeModel()))));
    // メソッド呼び出し検証
    verify(employeeController).getInfo(eq(new GetInfoParameter(null, "123456789")));
    verify(employeeService).getEmployeeInfo(eq(null), eq("123456789"));
    verify(getInfoValidator).validate(eq(new GetInfoParameter(null, "123456789")),
        any(Errors.class));
  }

  /**
   * {@link EmployeeController#getInfo(GetInfoParameter)}の境界値のテストメソッド2
   * <p>
   * 社員番号に10文字の文字列を指定する場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getTestBorder2()
      throws Exception {
    // サービスのMock設定
    when(employeeService.getEmployeeInfo(any(), any())).thenReturn(new EmployeeModel());
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/employee/getInfo")
            .queryParam("employeeNumber", "1234567890"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(new GetInfoResponse(new EmployeeModel()))));
    // メソッド呼び出し検証
    verify(employeeController).getInfo(eq(new GetInfoParameter(null, "1234567890")));
    verify(employeeService).getEmployeeInfo(eq(null), eq("1234567890"));
    verify(getInfoValidator).validate(eq(new GetInfoParameter(null, "1234567890")),
        any(Errors.class));
  }
}
