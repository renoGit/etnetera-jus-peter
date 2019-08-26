package com.etnetera.hr.data.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO defining request / response JSON message structure
 */
public class JavaScriptFrameworkDto {

    private Long id;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 120)
    private String version;

    private LocalDate deprecationDate;

    @Min(1)
    @Max(100)
    private Integer hypeLevel;

    private JavaScriptFrameworkDto() {
        // for JSON marshalling / un-marshalling only
    }

    //TODO: this is better to be replaced by a builder...
    public JavaScriptFrameworkDto(Long id, String name, String version, LocalDate deprecationDate, Integer hypeLevel) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.deprecationDate = deprecationDate;
        this.hypeLevel = hypeLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public LocalDate getDeprecationDate() {
        return deprecationDate;
    }

    public Integer getHypeLevel() {
        return hypeLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaScriptFrameworkDto that = (JavaScriptFrameworkDto) o;
        return Objects.equals(id, that.id) &&
                name.equals(that.name) &&
                version.equals(that.version) &&
                Objects.equals(deprecationDate, that.deprecationDate) &&
                Objects.equals(hypeLevel, that.hypeLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, version, deprecationDate, hypeLevel);
    }

}
