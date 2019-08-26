package com.etnetera.hr.data.dto;

import java.util.Objects;

/**
 * DTO representing the error response of the JavaScriptFramework
 * "manager" app.
 */
public class ApiErrorDto {

    private Integer status;
    private String error;
    private String message;

    private ApiErrorDto() {
        // for JSON marshalling / un-marshalling only
    }

    private ApiErrorDto(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.message = builder.message;
    }

    public static class Builder {

        private Integer status;
        private String error;
        private String message;

        public Builder withStatus(Integer status) {
           this.status = status;
           return this;
        }

        public Builder withError(String error) {
            this.error = error;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiErrorDto build() {
            return new ApiErrorDto(this);
        }
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiErrorDto that = (ApiErrorDto) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(error, that.error) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, error, message);
    }

}
