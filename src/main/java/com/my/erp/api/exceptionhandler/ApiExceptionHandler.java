package com.my.erp.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.my.erp.core.validation.ValidacaoException;
import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.EntidadeNaoEncontradaException;
import com.my.erp.domain.exception.NegocioException;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema. Tente novamente e se o problema persistir, entre em contato "
			+ "com o administrador do sistema";

	@Autowired
	private MessageSource messageSource;

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail).timestamp(OffsetDateTime.now());
	}

	private String joinPath(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value())
					.userMesage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		} else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value())
					.userMesage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException e, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = e.getMessage();

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(detail).build();

		return handleExceptionInternal(e, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException e, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = e.getMessage();

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(detail).build();

		return handleExceptionInternal(e, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = e.getMessage();

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(detail).build();

		return handleExceptionInternal(e, problema, new HttpHeaders(), status, request);
	}

	@ExceptionHandler({ ValidacaoException.class })
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUncaught(Exception e, WebRequest request) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_SISTEMA;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

		e.printStackTrace();

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(detail).build();

		return handleExceptionInternal(e, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso %s que você tentou acessar é inexistente", ex.getRequestURL());

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		}
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de síntaxe";
		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', "
						+ "que é de tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problema, headers, status, request);

	}

	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = joinPath(ex.getPath());

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String
				.format("A propriedade %s não existe. " + "Corrija ou remova essa propriedade e tente novamente", path);

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problema, headers, status, request);

	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format(
				"O parametro de URL %s recebeu valor %s, que é do tipo inválido."
						+ " Corrija e informe um valor compatível com o tipo %s",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(MSG_ERRO_GENERICA_USUARIO_FINAL)
				.build();

		return handleExceptionInternal(ex, problema, headers, status, request);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
	}

	private ResponseEntity<Object> handleValidationInternal(Exception e, BindingResult bindingResult,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			String name = objectError.getObjectName();

			return Problem.Object.builder().name(name).userMesage(message).build();
		}).collect(Collectors.toList());

		Problem problema = createProblemBuilder(status, problemType, detail).userMesage(detail).fields(problemObjects)
				.build();

		return handleExceptionInternal(e, problema, headers, status, request);
	}

}