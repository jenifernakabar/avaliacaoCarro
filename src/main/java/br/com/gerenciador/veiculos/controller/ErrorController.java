package br.com.gerenciador.veiculos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> handleException(HttpMessageNotReadableException exception) {
        return new ResponseEntity("Status inválido", HttpStatus.BAD_REQUEST);
    }

}
