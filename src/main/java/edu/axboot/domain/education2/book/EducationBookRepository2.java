package edu.axboot.domain.education2.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationBookRepository2 extends JpaRepository<EducationBook2, Long> {
}