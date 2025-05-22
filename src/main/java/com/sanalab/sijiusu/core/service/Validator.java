package com.sanalab.sijiusu.core.service;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class Validator {
    private final EmailValidator EMAIL_VALIDATOR = new EmailValidator();

    public boolean isEmail(String str) {
        return EMAIL_VALIDATOR.isValid(str, null);
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    public boolean isNim(String str) {
        return str.length() == 9 && isNumeric(str);
    }

    public boolean isNidn(String str) {
        return str.length() == 10 && isNumeric(str);
    }

    public boolean isNip(String str) {
        return str.length() == 18 && isNumeric(str);
    }
}
