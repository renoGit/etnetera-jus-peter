package com.etnetera.hr.data.enitity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera
 */
@Entity
@Table(name = "JAVASCRIPT_FRAMEWORK", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "version"})})
public class JavaScriptFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 120)
    private String version;

    @Column
    private LocalDate deprecationDate;

    @Column
    @Min(1)
    @Max(100)
    private Integer hypeLevel;

    public JavaScriptFramework() {
    }

    /**
     * Used in tests...
     *
     * @param name
     * @param version
     * @param deprecationDate
     * @param hypeLevel
     */
    public JavaScriptFramework(String name, String version, LocalDate deprecationDate, Integer hypeLevel) {
        this.name = name;
        this.version = version;
        this.deprecationDate = deprecationDate;
        this.hypeLevel = hypeLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDate getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(LocalDate deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    public Integer getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(Integer hypeLevel) {
        this.hypeLevel = hypeLevel;
    }

    @Override
    public String toString() {
        return "JavaScriptFramework{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", deprecationDate=" + deprecationDate +
                ", hypeLevel=" + hypeLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaScriptFramework that = (JavaScriptFramework) o;
        return id.equals(that.id) &&
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
