package edu.axboot.domain.education;

import com.chequer.axboot.core.domain.base.AXBootJPAQueryDSLRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhmGridRepository extends AXBootJPAQueryDSLRepository<NhmGrid, Long> {
}
