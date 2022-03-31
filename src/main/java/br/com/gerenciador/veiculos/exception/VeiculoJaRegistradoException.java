package br.com.gerenciador.veiculos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VeiculoJaRegistradoException extends Exception{
	
	public VeiculoJaRegistradoException() {
		super("Veículo já registrado no sistema.");
    }

}
