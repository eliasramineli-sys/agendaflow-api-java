package dev.portfolio.agendaflow.repository;
import dev.portfolio.agendaflow.domain.Appointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.List;
public interface AppointmentRepository extends JpaRepository<Appointment,Long>{
 @Query("select (count(a)>0) from Appointment a where a.professional.id=:professionalId and a.status=dev.portfolio.agendaflow.domain.AppointmentStatus.SCHEDULED and a.startsAt < :end and a.endsAt > :start")
 boolean hasOverlap(@Param("professionalId") Long professionalId,@Param("start") Instant start,@Param("end") Instant end);
 List<Appointment> findByProfessionalIdOrderByStartsAtAsc(Long professionalId);
}
