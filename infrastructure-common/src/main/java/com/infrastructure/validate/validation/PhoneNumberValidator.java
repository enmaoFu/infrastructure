package com.infrastructure.validate.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话号码验证器
 *
 * @author tyq
 * @date 2016/1/11
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private Pattern pattern = Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");

    @Override
    public void initialize(PhoneNumber phoneNumber) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        // 没有填写
        if (!StringUtils.hasText(s)) {
            return false;
        }
        // 匹配正则
        Matcher m = pattern.matcher(s);
        return m.matches();
    }
}
