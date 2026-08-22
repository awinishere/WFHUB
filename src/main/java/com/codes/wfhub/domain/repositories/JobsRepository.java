package com.codes.wfhub.domain.repositories;

import com.codes.wfhub.domain.entities.Jobs;
import com.codes.wfhub.domain.entities.extension.CategoriesType;
import com.codes.wfhub.domain.entities.extension.JobType;
import com.codes.wfhub.domain.entities.extension.SourceType;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JobsRepository extends JpaRepository<Jobs, UUID> {

    Optional<Jobs> findByExternalIdAndSource(String externalId, SourceType source);

    @Query("""
    SELECT j FROM Jobs j
    WHERE (:keyword IS NULL OR
           LOWER(j.title) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%')) OR
           LOWER(j.companyName) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%')) OR
           LOWER(j.tags) LIKE LOWER(CONCAT('%', CAST(:keyword AS string), '%')))
      AND (:category IS NULL OR j.categories = :category)
      AND (:jobType IS NULL OR j.jobType = :jobType)
    ORDER BY j.publishedAt DESC
    """)
    Page<Jobs> search(
            @Param("keyword") String keyword,
            @Param("category") CategoriesType category,
            @Param("jobType") JobType jobType,
            Pageable pageable
    );
}
