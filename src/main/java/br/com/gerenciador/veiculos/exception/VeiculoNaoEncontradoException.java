package br.com.gerenciador.veiculos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VeiculoNaoEncontradoException extends Exception {

    public VeiculoNaoEncontradoException(String vehicleName) {
        super(String.format("Veiculo %s não encontrado no sistema.", vehicleName));
    }

    public VeiculoNaoEncontradoException(Long id) {
        super(String.format("Veiculo com id %s não encontrado no sistema", id));
    }
}
