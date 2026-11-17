package dev.portfolio.agendaflow.api;
import dev.portfolio.agendaflow.api.Requests.*;
import dev.portfolio.agendaflow.api.Responses.*;
import dev.portfolio.agendaflow.service.AppointmentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1") public class AppointmentController {
 private final AppointmentService service;
 public AppointmentController(AppointmentService service){this.service=service;}
 @PostMapping("/professionals") public ResponseEntity<ProfessionalView> professional(@Valid @RequestBody ProfessionalCreate r){var p=service.createProfessional(r);return ResponseEntity.created(URI.create("/api/v1/professionals/"+p.id())).body(p);}
 @GetMapping("/professionals") public List<ProfessionalView> professionals(){return service.professionals();}
 @PostMapping("/appointments") public ResponseEntity<AppointmentView> schedule(@Valid @RequestBody AppointmentCreate r){var a=service.schedule(r);return ResponseEntity.created(URI.create("/api/v1/appointments/"+a.id())).body(a);}
 @GetMapping("/appointments/{id}") public AppointmentView find(@PathVariable Long id){return service.find(id);}
 @GetMapping("/professionals/{id}/appointments") public List<AppointmentView> byProfessional(@PathVariable Long id){return service.byProfessional(id);}
 @PatchMapping("/appointments/{id}/cancel") public AppointmentView cancel(@PathVariable Long id){return service.cancel(id);}
}
