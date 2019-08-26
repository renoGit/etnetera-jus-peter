package com.etnetera.hr.error;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class NotFoundExceptionTest {

    private static final String DUMMY_MESSAGE = "dummy message";

    @Test
    public void getHttpStatus() {
        //given & when
        final NotFoundException actual = new NotFoundException(DUMMY_MESSAGE);
        // then
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getParameters() {
        //given & when
        final NotFoundException actual = new NotFoundException(DUMMY_MESSAGE, 1L, "2");
        // then
        assertThat(actual.getParameters()).containsExactlyInAnyOrder(1L, "2");
    }

    @Test
    public void getParameters_givenNoParameters() {
        //given & when
        final NotFoundException actual = new NotFoundException(DUMMY_MESSAGE);
        // then
        assertThat(actual.getParameters()).isEmpty();
    }

    @Test
    public void getMessage() {
        //given & when
        final NotFoundException actual = new NotFoundException(DUMMY_MESSAGE, 1L, "2");
        // then
        assertThat(actual.getMessage()).isEqualTo(DUMMY_MESSAGE);
    }

}
