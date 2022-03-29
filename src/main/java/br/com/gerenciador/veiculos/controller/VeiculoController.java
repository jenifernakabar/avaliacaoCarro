package br.com.gerenciador.veiculos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gerenciador.veiculos.dto.VeiculoDTO;
import br.com.gerenciador.veiculos.entity.Veiculo;
import br.com.gerenciador.veiculos.exception.VeiculoJaRegistradoException;
import br.com.gerenciador.veiculos.exception.VeiculoNaoEncontradoException;
import br.com.gerenciador.veiculos.service.VeiculoService;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

	@Autowired
	private VeiculoService veiculoService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VeiculoDTO criarVeiculo(@RequestBody VeiculoDTO veiculoDTO) throws VeiculoJaRegistradoException {
		return veiculoService.criarVeiculo(veiculoDTO);
	}

	@GetMapping
	public ResponseEntity<List<Veiculo>> listar() {
		return ResponseEntity.ok().body(veiculoService.listar());
	}

	@GetMapping("/{id}")
	public VeiculoDTO detalharVeiculo(@PathVariable Long id) throws VeiculoNaoEncontradoException {
		return veiculoService.buscarDetalhesVeiculo(id);
	}
	
	@PatchMapping("/{id}")
	public VeiculoDTO atualizarStatus(@PathVariable Long id,@RequestBody VeiculoDTO veiculoDTO) throws VeiculoNaoEncontradoException {
		return veiculoService.atualizarStatusVeiculo(id,veiculoDTO);
	}
	
	@DeleteMapping("/{id}")
	public VeiculoDTO delecaoLogica(@PathVariable Long id) throws VeiculoNaoEncontradoException {
		return veiculoService.delecaoVeiculo(id);
	}

}
