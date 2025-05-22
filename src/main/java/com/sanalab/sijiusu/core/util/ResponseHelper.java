package com.sanalab.sijiusu.core.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ResponseHelper {
    public static ResponseStatusException responseException(
        int code,
        String message
    ) {
        return new ResponseStatusException(HttpStatusCode.valueOf(code), message);
    }

    public static ResponseStatusException responseException(
        HttpStatus code,
        String message
    ) {
        return new ResponseStatusException(code, message);
    }
}
