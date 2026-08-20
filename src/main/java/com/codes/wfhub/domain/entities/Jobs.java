package com.codes.wfhub.domain.entities;

import com.codes.wfhub.domain.entities.extension.CategoriesType;
import com.codes.wfhub.domain.entities.extension.JobType;
import com.codes.wfhub.domain.entities.extension.SourceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs", uniqueConstraints = @UniqueConstraint(columnNames = {"external_id", "source"}))
@SQLRestriction("deleted_at IS NULL")
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "external_id", updatable = false, nullable = false)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", updatable = true, nullable = false)
    private SourceType source;

    @Column(name = "title", updatable = true, nullable = false)
    private String title;

    @Column(name = "company_name", updatable = true, nullable = false)
    private String companyName;

    @Column(name = "company_logo", updatable = true, nullable = true)
    private String companyLogo;

    @Column(name = "location", updatable = true, nullable = true)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "jobType", updatable = true, nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "categories", updatable = true, nullable = false)
    private CategoriesType categories;

    @Column(name = "tags", updatable = true, nullable = false)
    private String tags;

    @Column(name = "description", updatable = true, nullable = false)
    private String description;

    @Column(name = "apply_url", updatable = true, nullable = false)
    private String applyUrl;

    @Column(name = "salary_minimum", updatable = true, nullable = true)
    private Integer salaryMinimum;

    @Column(name = "salary_maximum", updatable = true, nullable = true)
    private Integer salaryMaximum;

    @Column(name = "published_at", updatable = false, nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "fetched_at", updatable = false, nullable = false)
    private LocalDateTime fetchedAt;

    @Column(name = "updated_at", updatable = true, nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", updatable = false, nullable = true)
    private LocalDateTime deletedAt;

}
