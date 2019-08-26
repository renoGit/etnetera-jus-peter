package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */
@RestController
@CrossOrigin
public class JavaScriptFrameworkController {

    private final JavaScriptFrameworkService service;

    @Autowired
    public JavaScriptFrameworkController(JavaScriptFrameworkService service) {
        this.service = service;
    }

    /**
     * The  "get frameworks" operation provides a list of all
     * JS frameworks kept in the DB matching given (optional) query
     * parameters. The expected "matching" is exact (i.e. no wild-cards).
     */
    @GetMapping("/frameworks")
    @ResponseBody
    public Iterable<JavaScriptFrameworkDto> frameworks(
            @Valid @RequestParam(name = "name", required = false) @Size(max = 30) String name,
            @Valid @RequestParam(name = "version", required = false) @Size(max = 120) String version) {
        return service.findJavaScriptFrameworks(name, version);
    }

    /**
     * The "add framework" operation persists a new JS framework
     * and return the new entity ID.
     */
    @PostMapping("/frameworks")
    @ResponseBody
    public Long addFramework(@Valid @RequestBody JavaScriptFrameworkDto framework) {
        return service.addJavaScriptFramework(framework);
    }

    /**
     * The "update framework" operation updates an existing JS framework
     * record specified by ID with a new set of values. A complete overwrite
     * of the existing entity is performed (i.e. no merge of old and new fields
     * is done).
     */
    @PutMapping("/frameworks/{id}")
    @ResponseBody
    public void updateFramework(
            @PathVariable("id") Long id,
            @Valid @RequestBody JavaScriptFrameworkDto framework) {
        service.updateJavaScriptFramework(id, framework);
    }

    /**
     * The "delete framework" operation removes an existing JS framework
     * record specified by ID from the framework database.
     */
    @DeleteMapping("/frameworks/{id}")
    @ResponseBody
    public void deleteFramework(@PathVariable("id") Long id) {
        service.deleteJavaScriptFramework(id);
    }

}
