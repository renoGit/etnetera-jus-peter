package com.etnetera.hr.error;

import com.etnetera.hr.data.dto.ApiErrorDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ClientErrorHandlerTest {

    @Mock
    MessageSource messageSource;
    @Mock
    WebRequest request;

    ClientErrorHandler handler;

    @Before
    public void setUp() {
        handler = new ClientErrorHandler(messageSource);
    }

    @Test
    public void handleNotFoundException() {
        // given
        final String DUMMY_MESSAGE = "Dummy message";
        final BadRequestException exception = new BadRequestException(DUMMY_MESSAGE);
        doReturn(DUMMY_MESSAGE).when(messageSource).getMessage(anyString(), any(Object[].class), isNull());
        // when
        final ResponseEntity<ApiErrorDto> actual = handler.handleNotFoundException(exception, request);
        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(exception.getHttpStatus());
        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody().getStatus()).isEqualTo(exception.getHttpStatus().value());
        assertThat(actual.getBody().getError()).isEqualTo(exception.getHttpStatus().getReasonPhrase());
        assertThat(actual.getBody().getMessage()).isEqualTo(DUMMY_MESSAGE);
    }

}
