package dev.portfolio.agendaflow.domain;
import jakarta.persistence.*;
@Entity @Table(name="professionals") public class Professional {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String name;
 @Column(nullable=false,length=120) private String specialty;
 protected Professional(){}
 public Professional(String name,String specialty){this.name=name;this.specialty=specialty;}
 public Long getId(){return id;} public String getName(){return name;} public String getSpecialty(){return specialty;}
}
