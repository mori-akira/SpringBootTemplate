package jp.co.molygray.parameter.sample.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import jp.co.molygray.parameter.sample.SampleParameter;
import jp.co.molygray.util.MultiMessageSource;
import jp.co.molygray.util.SystemConstants;

/**
 * {@link jp.co.molygray.parameter.sample.SampleParameter SampleParameter}のバリデータクラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class SampleValidator implements Validator {

    /** メッセージソース */
    @Autowired
    private MultiMessageSource messageSource;
    /** システム定数 */
    @Autowired
    private SystemConstants systemConstants;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return SampleParameter.class.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {
        SampleParameter param = SampleParameter.class.cast(target);

        String from = param.getValidDateFrom();
        String to = param.getValidDateTo();

        // fromとtoどちらか一方だけがnullはエラー
        if (from != null && to == null || from == null && to != null) {
            String msg = messageSource.getMessage("error.sample.E001",
                    new Object[] { new DefaultMessageSourceResolvable("validDateFrom"),
                            new DefaultMessageSourceResolvable("validDateTo") }
            );
            errors.reject(msg);
        }

        // from>toはエラー
        if (from != null && to != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    systemConstants.getConstant("system.default.dateTimeFormat.date"));
            LocalDate fromDate = LocalDate.parse(from, formatter);
            LocalDate toDate = LocalDate.parse(to, formatter);
            if (fromDate.isAfter(toDate)) {
                String msg = messageSource.getMessage("error.sample.E002",
                        new Object[] { new DefaultMessageSourceResolvable("validDateFrom"),
                                new DefaultMessageSourceResolvable("validDateTo") });
                errors.reject(msg);
            }
        }
    }
}
