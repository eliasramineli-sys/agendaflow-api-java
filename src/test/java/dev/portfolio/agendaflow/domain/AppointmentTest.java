package dev.portfolio.agendaflow.domain;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
class AppointmentTest {
 @Test void canCancelAppointment(){var p=new Professional("Ana","Dentista");var start=Instant.now().plusSeconds(3600);var a=new Appointment(p,"Cliente","cliente@example.com",start,start.plusSeconds(1800));a.cancel();assertEquals(AppointmentStatus.CANCELLED,a.getStatus());}
 @Test void cannotCancelTwice(){var p=new Professional("Ana","Dentista");var start=Instant.now().plusSeconds(3600);var a=new Appointment(p,"Cliente","cliente@example.com",start,start.plusSeconds(1800));a.cancel();assertThrows(IllegalStateException.class,a::cancel);}
 @Test void rejectsInvalidInterval(){var p=new Professional("Ana","Dentista");var start=Instant.now().plusSeconds(3600);assertThrows(IllegalArgumentException.class,()->new Appointment(p,"Cliente","cliente@example.com",start,start));}
}
