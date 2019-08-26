package com.etnetera.hr.error;

import com.etnetera.hr.data.dto.ApiErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Custom error handling for "client error" situations
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ClientErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientErrorHandler.class);
    private static final String LOG_MESSAGE = "Processed client error exception.";

    private final MessageSource messageSource;

    @Autowired
    public ClientErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({NotFoundException.class, BadRequestException.class})
    @ResponseBody
    public ResponseEntity<ApiErrorDto> handleNotFoundException(final ClientErrorException ex, final WebRequest request) {
        logger.debug(LOG_MESSAGE, ex);
        final ApiErrorDto apiError = new ApiErrorDto.Builder()
            .withStatus(ex.getHttpStatus().value())
            .withError(ex.getHttpStatus().getReasonPhrase())
            .withMessage(messageSource.getMessage(ex.getMessage(), ex.getParameters().toArray(), request.getLocale()))
            .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(apiError);
    }

}
