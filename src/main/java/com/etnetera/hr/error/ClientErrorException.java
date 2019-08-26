package com.etnetera.hr.error;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Parent of custom client error exceptions (e.g. to cover situations
 * such as bad request or not found)
 */
public abstract class ClientErrorException extends RuntimeException {

    private final List<Object> parameters;

    public ClientErrorException(String message, Object ... parameters) {
        super(message);
        this.parameters = parameters != null ? Arrays.asList(parameters) : Collections.emptyList();
    }

    public List<Object> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    /**
     * Implementation should return it's own specific HttpStatus
     * value. (i.e. there must be always just one value per implementation)
     */
    abstract public HttpStatus getHttpStatus();

}
