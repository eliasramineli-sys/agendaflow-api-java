package dev.portfolio.agendaflow.service;
import dev.portfolio.agendaflow.api.Requests.AppointmentCreate;
import dev.portfolio.agendaflow.domain.Professional;
import dev.portfolio.agendaflow.repository.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AppointmentServiceTest {
 @Test void rejectsOverlappingAppointment(){
  var professionals=mock(ProfessionalRepository.class);var appointments=mock(AppointmentRepository.class);
  var service=new AppointmentService(professionals,appointments);
  var start=Instant.now().plusSeconds(7200);var end=start.plusSeconds(1800);
  when(professionals.findLockedById(1L)).thenReturn(Optional.of(new Professional("Ana","Dentista")));
  // Entidade sem ID persistido; o mock recebe null, como seria antes de persistência.
  when(appointments.hasOverlap(null,start,end)).thenReturn(true);
  assertThrows(IllegalStateException.class,()->service.schedule(new AppointmentCreate(1L,"Cliente","cliente@example.com",start,end)));
 }
 @Test void rejectsPastDate(){
  var service=new AppointmentService(mock(ProfessionalRepository.class),mock(AppointmentRepository.class));
  var start=Instant.now().minusSeconds(3600);
  assertThrows(IllegalArgumentException.class,()->service.schedule(new AppointmentCreate(1L,"Cliente","cliente@example.com",start,start.plusSeconds(1800))));
 }
}
