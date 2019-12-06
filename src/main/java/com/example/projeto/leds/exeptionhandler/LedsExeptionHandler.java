package com.example.projeto.leds.exeptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//O controller Advice observa/responde toda a aplicação
@ControllerAdvice
public class LedsExeptionHandler extends ResponseEntityExceptionHandler{
	@Autowired
	private MessageSource mensagemSource;
	 
	/*
	 * Método usado para detectar/tratar campos a mais que vem do cliente que não consegue ler
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario = this.mensagemSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() == null ? ex.toString() : ex.getCause().toString();
		/*
		 * O objetivo de criar uma lista de erros é para o caso de existir mais de um campo que não existe
		 */
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/*
	 * Método usado para validar campos que vieram errados do cliente como campos nulos
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		/*
		 * O objetivo de criar uma lista de erros é para o caso de existir mais de um campo inválido
		 * O Binding Result traz uma lista de todos os erros
		 */
		List<Erro> erros = this.criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	 
	/*
	 * Método para tratar de tentativas de deleção falidas
	 * Consigo tratar as exeções exatas que dá adicionando ao parametro da Anotação
	 * Está em chaves "{}" pois indica uma lista já que vc pode passar mais de uma classe de erro
	 */
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
		
		String mensagemUsuario = this.mensagemSource.getMessage("mensagem.no-content", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		/*
		 * O objetivo de criar uma lista de erros é para o caso de existir mais de um campo que não existe
		 */
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	
	/*
	 * Método para tratar quando o cliente passa um Recurso não existente no banco
	 * Para o PUT
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
		
		String mensagemUsuario = this.mensagemSource.getMessage("mensagem.no-content", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		/*
		 * O objetivo de criar uma lista de erros é para o caso de existir mais de um campo que não existe
		 */
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/*
	 * Método criado para tratar os erros de Integridade de chave estrangeira
	 */	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		
		String mensagemUsuario = this.mensagemSource.getMessage("mensagem.fail",null , LocaleContextHolder.getLocale());
		/*
		 * A Classe ExceptionUtils trata os erros de forma mais clara */	
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
		/*
		 * O objetivo de criar uma lista de erros é para o caso de existir mais de um campo que não existe
		 */
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	
	private List<Erro> criarListaDeErros(BindingResult bidingResult){
		List<Erro> erros = new ArrayList<LedsExeptionHandler.Erro>();
		/*
		 * Tratando os erros que vem do Spring
		 */
		for(FieldError fieldError :bidingResult.getFieldErrors()) {
			
			String mensagemUsuario = mensagemSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
		
		return erros;
	}
	
	public static class Erro {
		
		String mensagemUsuario;
		String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}
		
		public String getMensagemUsuario() {
			return this.mensagemUsuario;
		}
		
		public String getMensagemDesenvolvedor() {
			return this.mensagemDesenvolvedor;
		}
		
	}
}
