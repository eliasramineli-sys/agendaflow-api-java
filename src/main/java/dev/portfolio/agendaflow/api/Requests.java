package dev.portfolio.agendaflow.api;
import jakarta.validation.constraints.*;
import java.time.Instant;
public final class Requests {
 private Requests(){}
 public record ProfessionalCreate(@NotBlank @Size(max=120) String name,@NotBlank @Size(max=120) String specialty){}
 public record AppointmentCreate(@NotNull @Positive Long professionalId,@NotBlank @Size(max=120) String customerName,@NotBlank @Email String customerEmail,@NotNull Instant startsAt,@NotNull Instant endsAt){}
}
