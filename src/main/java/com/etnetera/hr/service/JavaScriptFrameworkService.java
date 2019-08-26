package com.etnetera.hr.service;

import com.etnetera.hr.data.dto.JavaScriptFrameworkDto;

/**
 * API specification for the JS framework "management" service.
 */
public interface JavaScriptFrameworkService {

    /**
     * Implementation of this method must provide a list of all
     * JS frameworks kept in the DB matching given (optional)
     * name and version parameters. The expected parameter "matching"
     * is exact (i.e. no wild-cards).
     *
     * @return Iterable<JavaScriptFrameworkDto> A sequence of found JS framework records
     */
    Iterable<JavaScriptFrameworkDto> findJavaScriptFrameworks(String name, String version);

    /**
     * Implementation of this method must persist given JS framework
     * and return new entity's ID.
     *
     * @param dto Contents of the of the new JS framework record
     * @return An ID of the newly created JS framework record.
     */
    Long addJavaScriptFramework(JavaScriptFrameworkDto dto);

    /**
     * The "update framework" operation updates an existing JS framework
     * record specified by ID with a new set of values. A complete overwrite
     * of the existing entity is performed (i.e. no merge of old and new fields
     * is done).
     *
     * @param id An of an existing JS framework record
     * @param dto New contents of the of the given JS framework record
     */
    void updateJavaScriptFramework(Long id, JavaScriptFrameworkDto dto);

    /**
     * Implementation of this method must remove an existing JS framework
     * record specified by ID from the framework database.
     *
     * @param id An of an existing JS framework record
     */
    void deleteJavaScriptFramework(Long id);

}
