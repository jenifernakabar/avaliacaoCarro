package br.com.gerenciador.veiculos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gerenciador.veiculos.dto.VeiculoDTO;
import br.com.gerenciador.veiculos.entity.Veiculo;
import br.com.gerenciador.veiculos.exception.VeiculoJaRegistradoException;
import br.com.gerenciador.veiculos.exception.VeiculoNaoEncontradoException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Gerenciamento de veiculos")
public interface VeiculoControllerDocs {

	 @ApiOperation(value = "Criar um novo veiculo")
	    @ApiResponses(value = {
	            @ApiResponse(code = 201, message = "Veiculo criado com sucesso!"),
	            @ApiResponse(code = 400, message = "Campos informados inválidos") })
	public VeiculoDTO criarVeiculo(@RequestBody VeiculoDTO veiculoDTO) throws VeiculoJaRegistradoException ;

	 @ApiOperation(value = "Listar todos os veiculos")
	    @ApiResponses(value = {
	    		@ApiResponse(code = 200, message = "Veiculo encontrado!"),
	            @ApiResponse(code = 404, message = "Veiculo informado não encontrado") })
	public ResponseEntity<List<Veiculo>> listar();

	@ApiOperation(value = "Buscar um veiculo na base de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Veiculo encontrado!"),
            @ApiResponse(code = 404, message = "Veiculo informado não encontrado") })
	public VeiculoDTO detalharVeiculo(@PathVariable Long id) throws VeiculoNaoEncontradoException;
	
	@ApiOperation(value = "Atualizar um veiculo na base de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Veiculo atualizado com sucesso!"),
            @ApiResponse(code = 404, message = "Veiculo informado não encontrado") })
	public ResponseEntity<VeiculoDTO> atualizarVeiculo(@PathVariable Long id,@RequestBody VeiculoDTO veiculoDTO) throws VeiculoNaoEncontradoException;
	
	@ApiOperation(value = "Atualizar o status do veiculo na base de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status do veiculo atualizado com sucesso!"),
            @ApiResponse(code = 404, message = "Veiculo informado não encontrado") })
	public VeiculoDTO atualizarStatus(@PathVariable Long id,@RequestBody VeiculoDTO veiculoDTO) throws VeiculoNaoEncontradoException;
	
	@ApiOperation(value = "Deletar um veiculo na base de dados")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Veiculo deletado com sucesso!"),
            @ApiResponse(code = 404, message = "Veiculo informado não encontrado") })
	public VeiculoDTO delecaoLogica(@PathVariable Long id) throws VeiculoNaoEncontradoException ;
	
	@ApiOperation(value = "Realizar Busca Dinamica")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Veiculo encontrado!"),
            @ApiResponse(code = 400, message = "Veiculo informado não encontrado") })
	public ResponseEntity<List<VeiculoDTO>> buscaDinamica(
			@RequestParam(value ="fabricante",required = false, defaultValue="")String fabricante,
			@RequestParam(value ="nome",required = false, defaultValue="") String nome,
			@RequestParam(value ="ano",required = false, defaultValue="0") int ano) throws VeiculoNaoEncontradoException;
   

}
