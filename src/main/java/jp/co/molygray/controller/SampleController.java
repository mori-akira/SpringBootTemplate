package jp.co.molygray.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.molygray.parameter.sample.SampleParameter;
import jp.co.molygray.parameter.sample.validator.SampleValidator;
import jp.co.molygray.response.sample.SampleResponse;
import jp.co.molygray.util.SystemConstants;

@RestController
@RequestMapping("/web/sample")
public class SampleController {

    /** Sampleパラメータのバリデータ */
    @Autowired
    private SampleValidator sampleValidator;
    /** システム定数 */
    @Autowired
    private SystemConstants systemConstants;

    /**
     * {@link org.springframework.web.bind.WebDataBinder
     * WebDataBinder}にバリデータを登録するメソッド
     *
     * @param binder WebDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(sampleValidator);
    }

    /**
     * sampleリクエスト
     *
     * @param param パラメータ
     * @return レスポンス
     */
    @PutMapping("/add")
    public SampleResponse sample(@RequestBody @Validated SampleParameter param) {
        Long sub = null;
        if (param.getValidDateFrom() != null && param.getValidDateTo() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    systemConstants.getConstant("system.default.dateTimeFormat.date"));
            LocalDate fromDate = LocalDate.parse(param.getValidDateFrom(), formatter);
            LocalDate toDate = LocalDate.parse(param.getValidDateTo(), formatter);
            sub = ChronoUnit.DAYS.between(fromDate, toDate);
        }
        return SampleResponse.builder()
                .id(1)
                .validDuring(sub)
                .build();
    }
}
