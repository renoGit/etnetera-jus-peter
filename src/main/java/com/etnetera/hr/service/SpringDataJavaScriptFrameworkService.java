package com.etnetera.hr.service;

import com.etnetera.hr.data.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.data.enitity.JavaScriptFramework;
import com.etnetera.hr.error.BadRequestException;
import com.etnetera.hr.error.NotFoundException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * {@inheritDoc}
 */
@Service
public class SpringDataJavaScriptFrameworkService implements JavaScriptFrameworkService {

    private static final String ERROR_FRAMEWORK_REGISTERED = "com.etnetera.hr.error.client.framework_registered";
    private static final String ERROR_UNKNOWN_FRAMEWORK_ID = "com.etnetera.hr.error.client.unknown_framework_id";


    private final JavaScriptFrameworkRepository repository;

    public SpringDataJavaScriptFrameworkService(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<JavaScriptFrameworkDto> findJavaScriptFrameworks(String name, String version) {
        final Supplier<Iterable<JavaScriptFramework>> findSupplier = chooseRepoMethod(name, version);
        return StreamSupport.stream(findSupplier.get().spliterator(), false)
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long addJavaScriptFramework(JavaScriptFrameworkDto dto) {
        if (repository.existsByNameAndVersion(dto.getName(), dto.getVersion())) {
            throw new BadRequestException(ERROR_FRAMEWORK_REGISTERED, dto.getName(), dto.getVersion());
        }
        return repository.save(update(new JavaScriptFramework(), dto)).getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateJavaScriptFramework(Long id, JavaScriptFrameworkDto dto) {
        Set<JavaScriptFramework> frameworks = repository.findByIdOrNameAndVersion(id, dto.getName(), dto.getVersion());
        // Thanks to the unique constraint on name & version, we know there can be at most two records matching the criteria
        if (frameworks.size() > 1) {
            throw new BadRequestException(ERROR_FRAMEWORK_REGISTERED, dto.getName(), dto.getVersion());
        } else if (frameworks.isEmpty() || frameworks.iterator().next().getId() != id) {
            throw new NotFoundException(ERROR_UNKNOWN_FRAMEWORK_ID, id);
        }
        repository.save(update(frameworks.iterator().next(), dto));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteJavaScriptFramework(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(ERROR_UNKNOWN_FRAMEWORK_ID, id);
        }
        repository.deleteById(id);
    }

    private Supplier<Iterable<JavaScriptFramework>> chooseRepoMethod(String name, String version) {
        if (StringUtils.isEmpty(name)) {
            return StringUtils.isEmpty(version) ?
                    () -> repository.findAll() :
                    () -> repository.findByVersion(version);
        } else {
            return StringUtils.isEmpty(version) ?
                    () -> repository.findByName(name) :
                    () -> repository.findByNameAndVersion(name, version);
        }
    }

    private JavaScriptFrameworkDto convert(JavaScriptFramework entity) {
        return new JavaScriptFrameworkDto(
            entity.getId(),
            entity.getName(),
            entity.getVersion(),
            entity.getDeprecationDate(),
            entity.getHypeLevel()
        );
    }

    private JavaScriptFramework update(JavaScriptFramework entity, JavaScriptFrameworkDto dto) {
        entity.setName(dto.getName());
        entity.setVersion(dto.getVersion());
        entity.setDeprecationDate(dto.getDeprecationDate());
        entity.setHypeLevel(dto.getHypeLevel());
        return entity;
    }

}
