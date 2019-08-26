package com.etnetera.hr.service;

import com.etnetera.hr.data.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.data.enitity.JavaScriptFramework;
import com.etnetera.hr.error.BadRequestException;
import com.etnetera.hr.error.NotFoundException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SpringDataJavaScriptFrameworkServiceTest {

    private static final String NAME = "Angular";
    private static final String VERSION = "6.0.0";

    @Mock
    private JavaScriptFrameworkRepository repository;
    private SpringDataJavaScriptFrameworkService service;

    private JavaScriptFramework framework;
    private JavaScriptFrameworkDto expected;

    @Before
    public void setUp() {
        service = new SpringDataJavaScriptFrameworkService(repository);
        framework = new JavaScriptFramework(NAME, VERSION, null, null);
        expected = new JavaScriptFrameworkDto(null, NAME, VERSION, null, null);
    }

    @Test
    public void findJavaScriptFrameworks_givenNameAndVersion() {
        // given
        doReturn(Collections.singletonList(framework)).when(repository).findByNameAndVersion(NAME, VERSION);
        // when
        final Iterable<JavaScriptFrameworkDto> actual = service.findJavaScriptFrameworks(NAME, VERSION);
        // then
        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void findJavaScriptFrameworks_givenNullNameNullVersion() {
        // given
        doReturn(Collections.singletonList(framework)).when(repository).findAll();
        // when
        final Iterable<JavaScriptFrameworkDto> actual = service.findJavaScriptFrameworks(null, null);
        // then
        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void findJavaScriptFrameworks_givenNullVersion() {
        // given
        doReturn(Collections.singletonList(framework)).when(repository).findByName(NAME);
        // when
        final Iterable<JavaScriptFrameworkDto> actual = service.findJavaScriptFrameworks(NAME, null);
        // then
        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void findJavaScriptFrameworks_givenNullName() {
        // given
        doReturn(Collections.singletonList(framework)).when(repository).findByVersion(VERSION);
        // when
        final Iterable<JavaScriptFrameworkDto> actual = service.findJavaScriptFrameworks(null, VERSION);
        // then
        assertThat(actual).containsExactly(expected);
    }

    @Test
    public void addJavaScriptFramework() {
        // given
        framework.setId(12345L);
        doReturn(Boolean.FALSE).when(repository).existsByNameAndVersion(expected.getName(), expected.getVersion());
        doReturn(framework).when(repository).save(Mockito.any(JavaScriptFramework.class));
        // when & then
        assertThat(service.addJavaScriptFramework(expected)).isEqualTo(framework.getId());
    }

    @Test(expected = BadRequestException.class)
    public void addJavaScriptFramework_givenExistingNameAndVersion() {
        // given
        doReturn(Boolean.TRUE).when(repository).existsByNameAndVersion(expected.getName(), expected.getVersion());
        // when & then
        service.addJavaScriptFramework(expected);
    }

    @Test
    public void updateJavaScriptFramework() {
        // given
        final Long ID = 12345L;
        framework.setId(ID);
        doReturn(Collections.singleton(framework)).when(repository).findByIdOrNameAndVersion(ID, expected.getName(), expected.getVersion());
        // when
        service.updateJavaScriptFramework(ID, expected);
        // then
        Mockito.verify(repository).save(framework);
    }

    @Test(expected = BadRequestException.class)
    public void updateJavaScriptFramework_givenExistingNameAndVersion() {
        // given
        final Long ID = 12345L;
        framework.setId(ID);
        final JavaScriptFramework another = new JavaScriptFramework(NAME, VERSION, null, null);
        doReturn(new HashSet<>(Arrays.asList(framework, another))).when(repository).findByIdOrNameAndVersion(ID, expected.getName(), expected.getVersion());
        // when
        service.updateJavaScriptFramework(ID, expected);
    }

    @Test(expected = NotFoundException.class)
    public void updateJavaScriptFramework_givenUnknownId() {
        // given
        final Long ID = 12345L;
        framework.setId(54321L);
        doReturn(Collections.singleton(framework)).when(repository).findByIdOrNameAndVersion(ID, expected.getName(), expected.getVersion());
        // when
        service.updateJavaScriptFramework(ID, expected);
    }

    @Test(expected = NotFoundException.class)
    public void updateJavaScriptFramework_givenNoRecordFound() {
        // given
        doReturn(Collections.emptySet()).when(repository).findByIdOrNameAndVersion(anyLong(), anyString(), anyString());
        // when
        service.updateJavaScriptFramework(1234L, expected);
    }

    @Test
    public void deleteJavaScriptFramework() {
        // given
        final Long ID = 12345L;
        doReturn(Boolean.TRUE).when(repository).existsById(ID);
        // when
        service.deleteJavaScriptFramework(ID);
        // then
        verify(repository).deleteById(ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteJavaScriptFramework_givenUnknownId() {
        // given
        final Long ID = 12345L;
        doReturn(Boolean.FALSE).when(repository).existsById(ID);
        // when
        service.deleteJavaScriptFramework(ID);
    }

}
