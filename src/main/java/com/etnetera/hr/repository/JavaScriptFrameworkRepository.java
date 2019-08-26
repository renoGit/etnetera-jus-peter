package com.etnetera.hr.repository;

import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.data.enitity.JavaScriptFramework;

import java.util.Set;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    Iterable<JavaScriptFramework> findByNameAndVersion(String name, String version);

    Iterable<JavaScriptFramework> findByName(String name);

    Iterable<JavaScriptFramework> findByVersion(String version);

    Set<JavaScriptFramework> findByIdOrNameAndVersion(Long id, String name, String version);

    boolean existsByNameAndVersion(String name, String version);

}
