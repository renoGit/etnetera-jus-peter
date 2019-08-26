package com.etnetera.hr.error;

import org.springframework.http.HttpStatus;

/**
 * Custom handling of HTTP 400 / Bad Request situations
 */
public class BadRequestException extends ClientErrorException {

    public BadRequestException(String message, Object ... parameters) {
        super(message, parameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
