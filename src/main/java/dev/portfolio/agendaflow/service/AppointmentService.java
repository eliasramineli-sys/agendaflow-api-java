package dev.portfolio.agendaflow.service;
import dev.portfolio.agendaflow.api.Requests.*;
import dev.portfolio.agendaflow.api.Responses.*;
import dev.portfolio.agendaflow.domain.*;
import dev.portfolio.agendaflow.repository.*;
import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service public class AppointmentService {
 private final ProfessionalRepository professionals;private final AppointmentRepository appointments;
 public AppointmentService(ProfessionalRepository professionals,AppointmentRepository appointments){this.professionals=professionals;this.appointments=appointments;}
 @Transactional public ProfessionalView createProfessional(ProfessionalCreate r){return ProfessionalView.from(professionals.saveAndFlush(new Professional(r.name().trim(),r.specialty().trim())));}
 @Transactional(readOnly=true) public List<ProfessionalView> professionals(){return professionals.findAll().stream().map(ProfessionalView::from).toList();}
 @Transactional public AppointmentView schedule(AppointmentCreate r){
  validateInterval(r.startsAt(),r.endsAt());
  // Serializa marcações por profissional por meio de lock na linha do profissional.
  Professional professional=professionals.findLockedById(r.professionalId()).orElseThrow(()->new NoSuchElementException("Profissional não encontrado"));
  if(appointments.hasOverlap(professional.getId(),r.startsAt(),r.endsAt()))throw new IllegalStateException("Horário indisponível");
  return AppointmentView.from(appointments.saveAndFlush(new Appointment(professional,r.customerName().trim(),r.customerEmail().trim(),r.startsAt(),r.endsAt())));
 }
 @Transactional public AppointmentView cancel(Long id){
  Appointment appointment=appointments.findById(id).orElseThrow(()->new NoSuchElementException("Agendamento não encontrado"));
  appointment.cancel();return AppointmentView.from(appointments.saveAndFlush(appointment));
 }
 @Transactional(readOnly=true) public AppointmentView find(Long id){return AppointmentView.from(appointments.findById(id).orElseThrow(()->new NoSuchElementException("Agendamento não encontrado")));}
 @Transactional(readOnly=true) public List<AppointmentView> byProfessional(Long id){
  if(!professionals.existsById(id))throw new NoSuchElementException("Profissional não encontrado");
  return appointments.findByProfessionalIdOrderByStartsAtAsc(id).stream().map(AppointmentView::from).toList();
 }
 static void validateInterval(Instant start,Instant end){
  if(start==null||end==null||!end.isAfter(start))throw new IllegalArgumentException("O término deve ser posterior ao início");
  if(!start.isAfter(Instant.now()))throw new IllegalArgumentException("O início deve estar no futuro");
  if(Duration.between(start,end).compareTo(Duration.ofHours(8))>0)throw new IllegalArgumentException("Duração máxima: 8 horas");
 }
}
