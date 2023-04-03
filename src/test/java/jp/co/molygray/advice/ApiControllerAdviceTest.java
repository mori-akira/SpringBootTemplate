package jp.co.molygray.advice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.molygray.controller.DepartmentController;
import jp.co.molygray.enums.ErrorSummaryEnum;
import jp.co.molygray.response.common.ErrorResponse;

/**
 * {@link ApiControllerAdvice}のテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@SpringBootTest
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
     * 初期化メソッド
     */
    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setControllerAdvice(apiControllerAdvice)
                .build();
    }

    /**
     * {@link ApiControllerAdvice#handleBindException()}のテストメソッド
     *
     * <p>
     * エラーメッセージが1つの場合
     * </p>
     *
     * @throws Exception 例外発生時
     */
    @Test
    public void handleBindExceptionTest1() throws Exception {
        BindingResult result = new DataBinder(new Object()).getBindingResult();
        result.reject("hoge", "fuga");
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
                        .build()))
                .build();
        assertEquals(expected, actual);
    }

    /**
     * {@link ApiControllerAdvice#handleBindException()}のテストメソッド
     *
     * <p>
     * エラーメッセージが複数の場合
     * </p>
     *
     * @throws Exception 例外発生時
     */
    @Test
    public void handleBindExceptionTest2() throws Exception {
        BindingResult result = new DataBinder(new Object()).getBindingResult();
        result.reject("hoge1", "fuga1");
        result.reject("hoge2", "fuga2");
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
                        .errorCode("hoge1")
                        .errorMessage("fuga1")
                        .build(),
                        ErrorResponse.ErrorDetail.builder()
                                .errorCode("hoge2")
                                .errorMessage("fuga2")
                                .build()))
                .build();
        assertEquals(expected, actual);
    }
}
