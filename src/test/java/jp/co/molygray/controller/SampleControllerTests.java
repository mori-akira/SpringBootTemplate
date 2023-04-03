package jp.co.molygray.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.molygray.parameter.sample.SampleParameter;
import lombok.extern.slf4j.Slf4j;

/**
 * Sampleコントローラーのテストクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class SampleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 正常系テスト
     */
    @Test
    void ok() throws Exception {
        SampleParameter param = SampleParameter.builder()
                .employeeNumber("220150071")
                .employeeName("木暮 守晶")
                .validDateFrom("2023/03/01")
                .validDateTo("2023/03/31")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = this.mockMvc
                .perform(post("/web/sample/add").content(mapper.writeValueAsString(param))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        log.info(result.getResponse()
                .getContentAsString());
        assertEquals(HttpStatus.OK.value(), result.getResponse()
                .getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, result.getResponse()
                .getContentType());
    }
}
