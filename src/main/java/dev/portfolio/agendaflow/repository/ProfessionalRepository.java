package dev.portfolio.agendaflow.repository;
import dev.portfolio.agendaflow.domain.Professional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
public interface ProfessionalRepository extends JpaRepository<Professional,Long>{
 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("select p from Professional p where p.id=:id")
 Optional<Professional> findLockedById(@Param("id") Long id);
}
