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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gerenciador.veiculos.dto.VeiculoDTO;
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
	public ResponseEntity<VeiculoDTO> criarVeiculo(@RequestBody VeiculoDTO veiculoDTO) throws VeiculoJaRegistradoException {
		veiculoDTO = veiculoService.criarVeiculo(veiculoDTO);
		return new ResponseEntity<VeiculoDTO>(veiculoDTO,HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<VeiculoDTO>> listar() throws VeiculoNaoEncontradoException {
		return ResponseEntity.ok().body(veiculoService.listar());
	}

	@GetMapping("/{id}")
	public ResponseEntity<VeiculoDTO> detalharVeiculo(@PathVariable Long id) throws VeiculoNaoEncontradoException {
		VeiculoDTO veiculoDTO = veiculoService.buscarDetalhesVeiculo(id);
		return ResponseEntity.ok().body(veiculoDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<VeiculoDTO> atualizarVeiculo(@PathVariable Long id,@RequestBody VeiculoDTO veiculoDTO) throws VeiculoNaoEncontradoException {
		VeiculoDTO DTO = veiculoService.atualizarVeiculo(id,veiculoDTO);
		return ResponseEntity.ok().body(DTO);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<VeiculoDTO>  atualizarStatus(@PathVariable Long id,@RequestBody VeiculoDTO veiculoDTO) throws VeiculoNaoEncontradoException {
		VeiculoDTO DTO = veiculoService.atualizarStatusVeiculo(id,veiculoDTO);
		return ResponseEntity.ok().body(DTO);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<VeiculoDTO> delecaoLogica(@PathVariable Long id) throws VeiculoNaoEncontradoException {
		veiculoService.delecaoVeiculo(id);
		return new ResponseEntity<VeiculoDTO>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/busca")
	public ResponseEntity<List<VeiculoDTO>> buscaDinamica(
			@RequestParam(value ="fabricante",required = false, defaultValue="")String fabricante,
			@RequestParam(value ="nome",required = false, defaultValue="") String nome,
			@RequestParam(value ="ano",required = false, defaultValue="0") int ano) throws VeiculoNaoEncontradoException {
		return ResponseEntity.ok().body(veiculoService.listadinamica(fabricante,nome,ano));
    }

}
