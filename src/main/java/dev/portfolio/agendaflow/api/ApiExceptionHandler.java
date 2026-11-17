package dev.portfolio.agendaflow.api;
import java.util.NoSuchElementException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice public class ApiExceptionHandler {
 @ExceptionHandler(NoSuchElementException.class) ResponseEntity<ProblemDetail> notFound(NoSuchElementException e){return response(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ProblemDetail> invalid(IllegalArgumentException e){return response(HttpStatus.BAD_REQUEST,e.getMessage());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ProblemDetail> validation(MethodArgumentNotValidException e){return response(HttpStatus.BAD_REQUEST,"Dados inválidos");}
 @ExceptionHandler(IllegalStateException.class) ResponseEntity<ProblemDetail> conflict(IllegalStateException e){return response(HttpStatus.CONFLICT,e.getMessage());}
 private ResponseEntity<ProblemDetail> response(HttpStatus status,String message){return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status,message));}
}
