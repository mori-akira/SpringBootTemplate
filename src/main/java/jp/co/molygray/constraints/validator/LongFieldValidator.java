package jp.co.molygray.constraints.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.molygray.constraints.LongField;

/**
 * {@link jp.co.molygray.constraints.LongField LongParsable}のバリデーション実装クラス
 *
 * @author Moriaki Kogure
 * @version 0.0.1
 */
@Component
public class LongFieldValidator implements ConstraintValidator<LongField, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(LongField formatDate) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        try {
            Long.valueOf(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
