package br.com.gerenciador.veiculos.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.gerenciador.veiculos.entity.DetalhesErro;
import br.com.gerenciador.veiculos.exception.VeiculoJaRegistradoException;
import br.com.gerenciador.veiculos.exception.VeiculoNaoEncontradoException;

@ControllerAdvice
public class ErrorController {
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DetalhesErro> handleException(HttpMessageNotReadableException e) {
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400L);
		erro.setTitulo("Erro na conversão do campo Status");
		erro.setMensagemDesenvolvedor("Status Inválido! Certifique-se de usar alguns dos Status a seguir:ACTIVATED, DEACTIVATED, RESERVED ou RENTED.");
		erro.setTimestamp(System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(VeiculoNaoEncontradoException.class)
	public ResponseEntity<DetalhesErro> handlerRecursoNaoEncontrado(VeiculoNaoEncontradoException e) {

		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(404L);
		erro.setTitulo(e.getMessage());
		erro.setMensagemDesenvolvedor("O veículo não foi encontrado no Banco de Dados! Tente refazer a pesquisa com outros parametros");
		erro.setTimestamp(System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(VeiculoJaRegistradoException.class)
	public ResponseEntity<DetalhesErro> handlerRecursoDuplicado(VeiculoJaRegistradoException e) {

		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(400L);
		erro.setTitulo(e.getMessage());
		erro.setMensagemDesenvolvedor("Não é possivel cadastrar dois veículos com mesma placa e/ou chassi!");
		erro.setTimestamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

}
