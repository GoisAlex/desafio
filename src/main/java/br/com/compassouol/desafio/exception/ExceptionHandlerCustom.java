package br.com.compassouol.desafio.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerCustom extends ResponseEntityExceptionHandler {

  private <T extends Exception> ResponseEntity<ApiError> handle(T t, WebRequest request,
      HttpStatus status) {
    final ApiError error = new ApiError.Builder()
        .data(LocalDateTime.now())
        .mensagem(t.getMessage())
        .detalhes(request.getDescription(false))
        .build();

    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler(IllegalAccessException.class)
  public final ResponseEntity<ApiError> handleIllegalAccessException(
      final IllegalAccessException ex, final WebRequest request) {
    return handle(ex, request, HttpStatus.FORBIDDEN);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    if (ex.getCause() instanceof UnrecognizedPropertyException) {
      return handelUnrecognizedPropertyException((UnrecognizedPropertyException) ex.getCause(),
          request);
    } else if (ex.getCause() instanceof JsonMappingException) {
      return handleJsonMappingException((JsonMappingException) ex.getCause(), request);
    }

    final ApiError error = new ApiError.Builder()
        .data(LocalDateTime.now())
        .mensagem("Json enviado possui erro de sintaxe.")
        .detalhes(request.getDescription(false))
        .build();

    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler(InvalidFormatException.class)
  public final ResponseEntity<ApiError> handleInvalidFormatException(
      final InvalidFormatException ex, final WebRequest request) {

    String caminhoPropriedade = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(
        Collectors.joining("."));

    final ApiError error = new ApiError.Builder()
        .data(LocalDateTime.now())
        .mensagem(String
            .format("A propriedade %s, com valor %s, está incorreta. Informe o tipo correto: %s",
                caminhoPropriedade, ex.getValue(), ex.getTargetType().getSimpleName()))
        .detalhes(request.getDescription(false))
        .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  private final ResponseEntity<Object> handleJsonMappingException(final JsonMappingException ex,
      final WebRequest request) {

    String caminhoPropriedade = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(
        Collectors.joining("."));

    final ApiError error = new ApiError.Builder()
        .data(LocalDateTime.now())
        .mensagem(String
            .format("A propriedade '%s', possui erro na formatação do JSON.", caminhoPropriedade))
        .detalhes(request.getDescription(false))
        .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  private final ResponseEntity<Object> handelUnrecognizedPropertyException(
      final UnrecognizedPropertyException ex, final WebRequest request) {
    String caminhoPropriedade = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(
        Collectors.joining("."));

    final ApiError error = new ApiError.Builder()
        .data(LocalDateTime.now())
        .mensagem(String.format("Propriedade '%s' é inválida.", caminhoPropriedade))
        .detalhes(request.getDescription(false))
        .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public final ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(
      final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    final ApiError error = new ApiError.Builder()
        .data(LocalDateTime.now())
        .mensagem(String
            .format("O parametro informado '%s' é do tipo inválido. Tipo requerido é '%s'",
                ex.getValue(), ex.getRequiredType().getSimpleName()))
        .detalhes(request.getDescription(false))
        .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  protected final ResponseEntity<ApiError> handleExceptionNaoTratada(final Exception e,
      final WebRequest request) {
    final ApiError error = new ApiError.Builder()
        .data(LocalDateTime.now())
        .mensagem(String.format("Erro inesperado '%s'. Contate o administrador do sistema.",
            e.getCause().getClass().getSimpleName()))
        .detalhes(request.getDescription(false))
        .build();

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({EstadoNaoEncontradoException.class, ClienteNaoEncontradoException.class})
  public final ResponseEntity<ApiError> handleEntidadeNaoEncontradaException(
      final RuntimeException ex, final WebRequest request) {
    return handle(ex, request, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return super.handleHttpMessageNotWritable(ex, headers, status, request);
  }
}
