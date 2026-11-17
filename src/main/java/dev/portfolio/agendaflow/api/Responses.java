package dev.portfolio.agendaflow.api;
import dev.portfolio.agendaflow.domain.*;
import java.time.Instant;
public final class Responses {
 private Responses(){}
 public record ProfessionalView(Long id,String name,String specialty){public static ProfessionalView from(Professional p){return new ProfessionalView(p.getId(),p.getName(),p.getSpecialty());}}
 public record AppointmentView(Long id,Long professionalId,String customerName,String customerEmail,Instant startsAt,Instant endsAt,AppointmentStatus status){
  public static AppointmentView from(Appointment a){return new AppointmentView(a.getId(),a.getProfessional().getId(),a.getCustomerName(),a.getCustomerEmail(),a.getStartsAt(),a.getEndsAt(),a.getStatus());}
 }
}
