package com.etnetera.hr.error;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class BadRequestExceptionTest {

    private static final String DUMMY_MESSAGE = "dummy message";

    @Test
    public void getHttpStatus() {
        //given & when
        final BadRequestException actual = new BadRequestException(DUMMY_MESSAGE);
        // then
        assertThat(actual.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getParameters() {
        //given & when
        final BadRequestException actual = new BadRequestException(DUMMY_MESSAGE, 1L, "2");
        // then
        assertThat(actual.getParameters()).containsExactlyInAnyOrder(1L, "2");
    }

    @Test
    public void getParameters_givenNoParameters() {
        //given & when
        final BadRequestException actual = new BadRequestException(DUMMY_MESSAGE);
        // then
        assertThat(actual.getParameters()).isEmpty();
    }

    @Test
    public void getMessage() {
        //given & when
        final BadRequestException actual = new BadRequestException(DUMMY_MESSAGE, 1L, "2");
        // then
        assertThat(actual.getMessage()).isEqualTo(DUMMY_MESSAGE);
    }

}
