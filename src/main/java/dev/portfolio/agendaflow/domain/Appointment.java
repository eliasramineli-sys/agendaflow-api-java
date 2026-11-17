package dev.portfolio.agendaflow.domain;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="appointments") public class Appointment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Professional professional;
 @Column(nullable=false,length=120) private String customerName;
 @Column(nullable=false,length=254) private String customerEmail;
 @Column(nullable=false) private Instant startsAt;
 @Column(nullable=false) private Instant endsAt;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private AppointmentStatus status=AppointmentStatus.SCHEDULED;
 protected Appointment(){}
 public Appointment(Professional professional,String customerName,String customerEmail,Instant startsAt,Instant endsAt){
  if(professional==null||customerName==null||customerName.isBlank()||customerEmail==null||customerEmail.isBlank()||startsAt==null||endsAt==null||!endsAt.isAfter(startsAt))throw new IllegalArgumentException("Agendamento inválido");
  this.professional=professional;this.customerName=customerName;this.customerEmail=customerEmail;this.startsAt=startsAt;this.endsAt=endsAt;
 }
 public void cancel(){if(status==AppointmentStatus.CANCELLED)throw new IllegalStateException("Agendamento já cancelado");status=AppointmentStatus.CANCELLED;}
 public Long getId(){return id;} public Professional getProfessional(){return professional;}
 public String getCustomerName(){return customerName;} public String getCustomerEmail(){return customerEmail;}
 public Instant getStartsAt(){return startsAt;} public Instant getEndsAt(){return endsAt;} public AppointmentStatus getStatus(){return status;}
}
