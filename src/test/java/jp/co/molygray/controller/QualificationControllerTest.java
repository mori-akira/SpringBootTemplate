package jp.co.molygray.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import jp.co.molygray.model.QualificationModel;
import jp.co.molygray.parameter.qualification.DeleteParameter;
import jp.co.molygray.parameter.qualification.GetParameter;
import jp.co.molygray.parameter.qualification.ListParameter;
import jp.co.molygray.parameter.qualification.RegisterParameter;
import jp.co.molygray.response.common.ErrorResponse;
import jp.co.molygray.response.qualification.DeleteResponse;
import jp.co.molygray.response.qualification.GetResponse;
import jp.co.molygray.response.qualification.ListResponse;
import jp.co.molygray.response.qualification.PatchResponse;
import jp.co.molygray.response.qualification.PutResponse;
import jp.co.molygray.service.QualificationService;

/**
 * {@link QualificationController}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class QualificationControllerTest {

  /** {@link QualificationService}のモック・インスタンス */
  @MockBean
  private QualificationService qualificationService;
  /** {@link QualificationController}のモック・インスタンス */
  @SpyBean
  private QualificationController qualificationController;
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
   * {@link QualificationController#get(GetParameter)}の正常系のテストメソッド1
   * <p>
   * 取得できる場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void getTestOk1()
      throws Exception {
    QualificationModel model = QualificationModel.builder()
        .qualificationId(1l)
        .exclusiveFlg("xxx")
        .qualificationName("hoge")
        .qualificationAbbreviatedName("fuga")
        .validPeriodYears(123)
        .provider("piyo")
        .build();
    // サービスのMock設定
    when(qualificationService.get(anyLong())).thenReturn(model);
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/qualification/get/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new GetResponse(model))));
    // メソッド呼び出し検証
    verify(qualificationController).get(eq(new GetParameter("1")));
    verify(qualificationService).get(eq(1l));
  }

  /**
   * {@link QualificationController#get(GetParameter)}の正常系のテストメソッド2
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
    when(qualificationService.get(anyLong())).thenReturn(null);
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/qualification/get/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new GetResponse(null))));
    // メソッド呼び出し検証
    verify(qualificationController).get(eq(new GetParameter("1")));
    verify(qualificationService).get(eq(1l));
  }

  /**
   * {@link QualificationController#get(GetParameter)}のバリデーションのテストメソッド1
   * <p>
   * 資格IDに空文字を指定する場合
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
                .errorMessage("資格IDを入力してください。")
                .errorItem("qualificationId")
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/qualification/get/1")
            .queryParam("qualificationId", ""))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).get(any());
    verify(qualificationService, never()).get(anyLong());
  }

  /**
   * {@link QualificationController#get(GetParameter)}のバリデーションのテストメソッド2
   * <p>
   * 資格IDにLongに変換できない値を指定する場合
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
                .errorMessage("資格IDは整数で入力してください。")
                .errorItem("qualificationId")
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/qualification/get/a"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).get(any());
    verify(qualificationService, never()).get(anyLong());
  }

  /**
   * {@link QualificationController#list(ListParameter)}の正常系のテストメソッド1
   * <p>
   * パラメータを全て設定し、取得できる場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestOk1()
      throws Exception {
    List<QualificationModel> modelList = List.of(
        QualificationModel.builder()
            .qualificationId(1l)
            .exclusiveFlg("xxx")
            .qualificationName("hoge1")
            .qualificationAbbreviatedName("fuga1")
            .validPeriodYears(123)
            .provider("piyo1")
            .build(),
        QualificationModel.builder()
            .qualificationId(2l)
            .exclusiveFlg("yyy")
            .qualificationName("hoge2")
            .qualificationAbbreviatedName("fuga2")
            .validPeriodYears(456)
            .provider("piyo2")
            .build(),
        QualificationModel.builder()
            .qualificationId(3l)
            .exclusiveFlg("zzz")
            .qualificationName("hoge3")
            .qualificationAbbreviatedName("fuga3")
            .validPeriodYears(789)
            .provider("piyo3")
            .build());
    // サービスのMock設定
    when(qualificationService.searchList(any(), any(), any(), any()))
        .thenReturn(modelList);
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/qualification/list")
            .queryParam("qualificationName", "aaa")
            .queryParam("qualificationAbbreviatedName", "bbb")
            .queryParam("validPeriodYears", "111")
            .queryParam("provider", "ccc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new ListResponse(modelList))));
    // メソッド呼び出し検証
    verify(qualificationController).list(eq(new ListParameter("aaa", "bbb", "111", "ccc")));
    verify(qualificationService).searchList(eq("aaa"), eq("bbb"), eq(111), eq("ccc"));
  }

  /**
   * {@link QualificationController#list(ListParameter)}の正常系のテストメソッド2
   * <p>
   * パラメータを全て設定せず、取得できる場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestOk2()
      throws Exception {
    List<QualificationModel> modelList = List.of(
        QualificationModel.builder()
            .qualificationId(1l)
            .exclusiveFlg("xxx")
            .qualificationName("hoge1")
            .qualificationAbbreviatedName("fuga1")
            .validPeriodYears(123)
            .provider("piyo1")
            .build(),
        QualificationModel.builder()
            .qualificationId(2l)
            .exclusiveFlg("yyy")
            .qualificationName("hoge2")
            .qualificationAbbreviatedName("fuga2")
            .validPeriodYears(456)
            .provider("piyo2")
            .build(),
        QualificationModel.builder()
            .qualificationId(3l)
            .exclusiveFlg("zzz")
            .qualificationName("hoge3")
            .qualificationAbbreviatedName("fuga3")
            .validPeriodYears(789)
            .provider("piyo3")
            .build());
    // サービスのMock設定
    when(qualificationService.searchList(any(), any(), any(), any()))
        .thenReturn(modelList);
    // APIを呼び出して検証
    mockMvc.perform(get("/api/qualification/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new ListResponse(modelList))));
    // メソッド呼び出し検証
    verify(qualificationController).list(eq(new ListParameter()));
    verify(qualificationService).searchList(eq(null), eq(null), eq(null), eq(null));
  }

  /**
   * {@link QualificationController#list(ListParameter)}の正常系のテストメソッド3
   * <p>
   * パラメータを全て設定せず、取得できない場合
   * </p>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestOk3()
      throws Exception {
    List<QualificationModel> modelList = Collections.emptyList();
    // サービスのMock設定
    when(qualificationService.searchList(any(), any(), any(), any()))
        .thenReturn(modelList);
    // APIを呼び出して検証
    mockMvc.perform(get("/api/qualification/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(new ListResponse(modelList))));
    // メソッド呼び出し検証
    verify(qualificationController).list(eq(new ListParameter()));
    verify(qualificationService).searchList(eq(null), eq(null), eq(null), eq(null));
  }

  /**
   * {@link QualificationController#list(ListParameter)}のバリデーションのテストメソッド1
   * <ul>
   * <li>資格名に129文字の文字列を指定</li>
   * <li>資格省略名に129文字の文字列を指定</li>
   * <li>有効年数にIntegerに変換できない文字列を指定</li>
   * <li>提供組織に129文字の文字列を指定</li>
   * </ul>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestValidation1()
      throws Exception {
    ErrorResponse expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("資格名は128文字以下で入力してください。")
                .errorItem("qualificationName")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("資格省略名は128文字以下で入力してください。")
                .errorItem("qualificationAbbreviatedName")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("IntegerField")
                .errorMessage("有効年数は整数で入力してください。")
                .errorItem("validPeriodYears")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("提供組織は128文字以下で入力してください。")
                .errorItem("provider")
                .build()))
        .build();
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/qualification/list")
            .queryParam("qualificationName", Stream.generate(() -> "a")
                .limit(129)
                .collect(Collectors.joining()))
            .queryParam("qualificationAbbreviatedName", Stream.generate(() -> "a")
                .limit(129)
                .collect(Collectors.joining()))
            .queryParam("validPeriodYears", "c")
            .queryParam("provider", Stream.generate(() -> "d")
                .limit(129)
                .collect(Collectors.joining())))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).list(any());
    verify(qualificationService, never()).searchList(any(), any(), any(), any());
  }

  /**
   * {@link QualificationController#list(ListParameter)}の境界値のテストメソッド1
   * <ul>
   * <li>資格名に128文字の文字列を指定</li>
   * <li>資格省略名に128文字の文字列を指定</li>
   * <li>提供組織に128文字の文字列を指定</li>
   * </ul>
   *
   * @throws Exception 例外発生時
   */
  @Test
  public void listTestBorder1()
      throws Exception {
    // サービスのMock設定
    when(qualificationService.searchList(any(), any(), any(), any()))
        .thenReturn(Collections.emptyList());
    // APIを呼び出して検証
    mockMvc.perform(
        get("/api/qualification/list")
            .queryParam("qualificationName", Stream.generate(() -> "a")
                .limit(128)
                .collect(Collectors.joining()))
            .queryParam("qualificationAbbreviatedName", Stream.generate(() -> "a")
                .limit(128)
                .collect(Collectors.joining()))
            .queryParam("provider", Stream.generate(() -> "d")
                .limit(128)
                .collect(Collectors.joining())))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(new ListResponse(Collections.emptyList()))));
    // メソッド呼び出し検証
    verify(qualificationController).list(any());
    verify(qualificationService).searchList(any(), any(), any(), any());
  }

  /**
   * {@link QualificationController#put(RegisterParameter)}の正常系のテストメソッド
   */
  @Test
  public void putTestOk()
      throws Exception {
    // サービスのMock設定
    when(qualificationService.insert(any()))
        .thenReturn(1l);
    // APIを呼び出して検証
    mockMvc.perform(
        put("/api/qualification/put")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new HashMap<>() {

              {
                put("qualificationName", "hoge");
                put("qualificationAbbreviatedName", "fuga");
                put("validPeriodYears", "123");
                put("provider", "piyo");
              }
            })))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(new PutResponse(1l))));
    // メソッド呼び出し検証
    verify(qualificationController).put(
        eq(new RegisterParameter(null, null, "hoge", "fuga", "123", "piyo")));
    verify(qualificationService).insert(
        eq(new QualificationModel(null, null, "hoge", "fuga", 123, "piyo")));
  }

  /**
   * {@link QualificationController#put(RegisterParameter)}のバリデーションのテストメソッド1
   * <ul>
   * <li>資格IDに空文字以外の値を設定する</li>
   * <li>排他フラグに空文字以外の値を設定する</li>
   * <li>資格名に空文字以外の値を設定する</li>
   * <li>資格省略名に129文字の文字列を設定する</li>
   * <li>有効年数にIntegerで認識できない値を設定する</li>
   * <li>提供組織に129文字の文字列を設定する</li>
   * </ul>
   */
  @Test
  public void putTestValidation1()
      throws Exception {
    // サービスのMock設定
    when(qualificationService.insert(any()))
        .thenReturn(1l);
    // APIを呼び出して検証
    ErrorResponse expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Empty")
                .errorMessage("資格IDは入力できません。")
                .errorItem("qualificationId")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Empty")
                .errorMessage("排他フラグは入力できません。")
                .errorItem("exclusiveFlg")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("NotEmpty")
                .errorMessage("資格名を入力してください。")
                .errorItem("qualificationName")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("資格省略名は128文字以下で入力してください。")
                .errorItem("qualificationAbbreviatedName")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("IntegerField")
                .errorMessage("有効年数は整数で入力してください。")
                .errorItem("validPeriodYears")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("提供組織は128文字以下で入力してください。")
                .errorItem("provider")
                .build()))
        .build();
    mockMvc.perform(
        put("/api/qualification/put")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new HashMap<>() {

              {
                put("qualificationId", "1");
                put("exclusiveFlg", "xxx");
                put("qualificationName", "");
                put("qualificationAbbreviatedName", Stream.generate(() -> "a")
                    .limit(129)
                    .collect(Collectors.joining()));
                put("validPeriodYears", "xxx");
                put("provider", Stream.generate(() -> "b")
                    .limit(129)
                    .collect(Collectors.joining()));
              }
            })))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).put(any(RegisterParameter.class));
    verify(qualificationService, never()).insert(any(QualificationModel.class));
  }

  /**
   * {@link QualificationController#put(RegisterParameter)}のバリデーションのテストメソッド2
   * <ul>
   * <li>資格名に129文字の文字列を設定する</li>
   * </ul>
   */
  @Test
  public void putTestValidation2()
      throws Exception {
    // サービスのMock設定
    when(qualificationService.insert(any()))
        .thenReturn(1l);
    // APIを呼び出して検証
    ErrorResponse expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("資格名は128文字以下で入力してください。")
                .errorItem("qualificationName")
                .build()))
        .build();
    mockMvc.perform(
        put("/api/qualification/put")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new HashMap<>() {

              {
                put("qualificationName",
                    Stream.generate(() -> "x").limit(129).collect(Collectors.joining()));
              }
            })))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).put(any(RegisterParameter.class));
    verify(qualificationService, never()).insert(any(QualificationModel.class));
  }

  /**
   * {@link QualificationController#put(RegisterParameter)}の境界値のテストメソッド
   * <ul>
   * <li>資格名に128文字の値を設定する</li>
   * <li>資格省略名に128文字の値を設定する</li>
   * <li>提供組織に128文字の値を設定する</li>
   * </ul>
   */
  @Test
  public void putTestBorder()
      throws Exception {
    // サービスのMock設定
    when(qualificationService.insert(any()))
        .thenReturn(1l);
    // APIを呼び出して検証
    mockMvc.perform(
        put("/api/qualification/put")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new HashMap<>() {

              {
                put("qualificationName",
                    Stream.generate(() -> "x").limit(128).collect(Collectors.joining()));
                put("qualificationAbbreviatedName",
                    Stream.generate(() -> "x").limit(128).collect(Collectors.joining()));
                put("provider",
                    Stream.generate(() -> "x").limit(128).collect(Collectors.joining()));
              }
            })))
        .andExpect(status().isCreated());
    // メソッド呼び出し検証
    verify(qualificationController).put(any(RegisterParameter.class));
    verify(qualificationService).insert(any(QualificationModel.class));
  }

  /**
   * {@link QualificationController#patch(RegisterParameter)}の正常系のテストメソッド
   */
  @Test
  public void patchTestOk()
      throws Exception {
    // サービスのMock設定
    doAnswer(invocation -> null).when(qualificationService).update(any());
    // APIを呼び出して検証
    mockMvc.perform(
        patch("/api/qualification/patch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new HashMap<>() {

              {
                put("qualificationId", "1");
                put("exclusiveFlg", "xxx");
                put("qualificationName", "hoge");
                put("qualificationAbbreviatedName", "fuga");
                put("validPeriodYears", "123");
                put("provider", "piyo");
              }
            })))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(new PatchResponse())));
    // メソッド呼び出し検証
    verify(qualificationController).patch(
        eq(new RegisterParameter("1", "xxx", "hoge", "fuga", "123", "piyo")));
    verify(qualificationService).update(
        eq(new QualificationModel(1l, "xxx", "hoge", "fuga", 123, "piyo")));
  }

  /**
   * {@link QualificationController#patch(RegisterParameter)}のバリデーションのテストメソッド1
   * <ul>
   * <li>資格IDに空文字を設定する</li>
   * <li>排他フラグに空文字を設定する</li>
   * <li>資格名に空文字以外の値を設定する</li>
   * <li>資格省略名に129文字の文字列を設定する</li>
   * <li>有効年数にIntegerで認識できない値を設定する</li>
   * <li>提供組織に129文字の文字列を設定する</li>
   * </ul>
   */
  @Test
  public void patchTestValidation1()
      throws Exception {
    // サービスのMock設定
    doAnswer(invocation -> null).when(qualificationService).update(any());
    // APIを呼び出して検証
    ErrorResponse expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("NotEmpty")
                .errorMessage("資格IDを入力してください。")
                .errorItem("qualificationId")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("NotEmpty")
                .errorMessage("排他フラグを入力してください。")
                .errorItem("exclusiveFlg")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("NotEmpty")
                .errorMessage("資格名を入力してください。")
                .errorItem("qualificationName")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("資格省略名は128文字以下で入力してください。")
                .errorItem("qualificationAbbreviatedName")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("IntegerField")
                .errorMessage("有効年数は整数で入力してください。")
                .errorItem("validPeriodYears")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("提供組織は128文字以下で入力してください。")
                .errorItem("provider")
                .build()))
        .build();
    mockMvc.perform(
        patch("/api/qualification/patch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new HashMap<>() {

              {
                put("qualificationId", "");
                put("exclusiveFlg", "");
                put("qualificationName", "");
                put("qualificationAbbreviatedName", Stream.generate(() -> "a")
                    .limit(129)
                    .collect(Collectors.joining()));
                put("validPeriodYears", "xxx");
                put("provider", Stream.generate(() -> "b")
                    .limit(129)
                    .collect(Collectors.joining()));
              }
            })))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).patch(any(RegisterParameter.class));
    verify(qualificationService, never()).update(any(QualificationModel.class));
  }

  /**
   * {@link QualificationController#patch(RegisterParameter)}のバリデーションのテストメソッド2
   * <ul>
   * <li>資格名に129文字の文字列を設定する</li>
   * </ul>
   */
  @Test
  public void patchTestValidation2()
      throws Exception {
    // サービスのMock設定
    doAnswer(invocation -> null).when(qualificationService).update(any());
    // APIを呼び出して検証
    ErrorResponse expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("Size")
                .errorMessage("資格名は128文字以下で入力してください。")
                .errorItem("qualificationName")
                .build()))
        .build();
    mockMvc.perform(
        patch("/api/qualification/patch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new HashMap<>() {

              {
                put("qualificationId", "1");
                put("exclusiveFlg", "xxx");
                put("qualificationName",
                    Stream.generate(() -> "x").limit(129).collect(Collectors.joining()));
              }
            })))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).patch(any(RegisterParameter.class));
    verify(qualificationService, never()).update(any(QualificationModel.class));
  }

  /**
   * {@link QualificationController#delete(DeleteParameter)}の正常系のテストメソッド
   */
  @Test
  public void deleteTestOk()
      throws Exception {
    // サービスのMock設定
    doAnswer(invocation -> null).when(qualificationService).delete(anyLong(), any());
    // APIを呼び出して検証
    mockMvc.perform(
        delete("/api/qualification/delete")
            .queryParam("qualificationId", "1")
            .queryParam("exclusiveFlg", "xxx"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(new DeleteResponse())));
    // メソッド呼び出し検証
    verify(qualificationController).delete(
        eq(new DeleteParameter("1", "xxx")));
    verify(qualificationService).delete(eq(1l), eq("xxx"));
  }

  /**
   * {@link QualificationController#delete(DeleteParameter)}のバリデーションのテストメソッド1
   * <ul>
   * <li>資格IDが空文字の場合</li>
   * <li>排他フラグが空文字の場合</li>
   * </ul>
   */
  @Test
  public void deleteTestValidation1()
      throws Exception {
    // サービスのMock設定
    doAnswer(invocation -> null).when(qualificationService).delete(anyLong(), any());
    // APIを呼び出して検証
    ErrorResponse expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("NotEmpty")
                .errorMessage("資格IDを入力してください。")
                .errorItem("qualificationId")
                .build(),
            ErrorResponse.ErrorDetail.builder()
                .errorCode("NotEmpty")
                .errorMessage("排他フラグを入力してください。")
                .errorItem("exclusiveFlg")
                .build()))
        .build();
    mockMvc.perform(
        delete("/api/qualification/delete")
            .queryParam("qualificationId", "")
            .queryParam("exclusiveFlg", ""))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).delete(any());
    verify(qualificationService, never()).delete(anyLong(), any());
  }

  /**
   * {@link QualificationController#delete(DeleteParameter)}のバリデーションのテストメソッド2
   * <ul>
   * <li>資格IDがLongに変換出来ない値の場合</li>
   * </ul>
   */
  @Test
  public void deleteTestValidation2()
      throws Exception {
    // サービスのMock設定
    doAnswer(invocation -> null).when(qualificationService).delete(anyLong(), any());
    // APIを呼び出して検証
    ErrorResponse expected = ErrorResponse.builder()
        .errorSummary(ErrorSummaryEnum.INPUT_ERROR.getSummary())
        .errorDetailList(Arrays.asList(
            ErrorResponse.ErrorDetail.builder()
                .errorCode("LongField")
                .errorMessage("資格IDは整数で入力してください。")
                .errorItem("qualificationId")
                .build()))
        .build();
    mockMvc.perform(
        delete("/api/qualification/delete")
            .queryParam("qualificationId", "xxx")
            .queryParam("exclusiveFlg", "xxx"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(
            objectMapper.writeValueAsString(expected)));
    // メソッド呼び出し検証
    verify(qualificationController, never()).delete(any());
    verify(qualificationService, never()).delete(anyLong(), any());
  }
}
