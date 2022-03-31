package br.com.gerenciador.veiculos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import br.com.gerenciador.veiculos.dto.VeiculoDTO;
import br.com.gerenciador.veiculos.entity.Veiculo;
import br.com.gerenciador.veiculos.enums.Status;
import br.com.gerenciador.veiculos.exception.VeiculoJaRegistradoException;
import br.com.gerenciador.veiculos.exception.VeiculoNaoEncontradoException;
import br.com.gerenciador.veiculos.repository.VeiculoRepository;
import br.com.gerenciador.veiculos.service.VeiculoService;

public class VeiculoServiceTest {

	@Mock
	private VeiculoRepository veiculoRepository;
	@Mock
	private ModelMapper veiculoMapper;
	@Mock
	private VeiculoService veiculoService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		veiculoService = new VeiculoService(veiculoRepository, veiculoMapper);
	}

	@Test
	public void chamarMetodoListarTodosComSucesso() {
		try {

			List<VeiculoDTO> listaVeiculo = criaListaVeiculoDTO();
			when(veiculoService.listar()).thenReturn(listaVeiculo);
			List<VeiculoDTO> listar = veiculoService.listar();
			assertNotNull(listar);

		} catch (VeiculoNaoEncontradoException e) {}

	}
	
	@Test
	public void chamarMetodoListarRetornoVazio() {
		try {
			
			when(veiculoService.listar()).thenThrow(new VeiculoNaoEncontradoException());
			veiculoService.listar();

		} catch (VeiculoNaoEncontradoException e) {
			assertEquals("Veiculo não encontrado no sistema.", e.getMessage());
		}

	}
	@Test
	public void salvarUmVeiculo()  {
		Optional<Veiculo> optional = Optional.of(criarVeiculo());
		VeiculoDTO veiculoDTO = criarVeiculoDTO();
		try {
			when(veiculoRepository.findByChassiOrPlaca(any(), any())).thenReturn(optional);
			when(veiculoService.criarVeiculo(veiculoDTO)).thenReturn(criarVeiculoDTO());
			VeiculoDTO veiculoCriado = veiculoService.criarVeiculo(any());
			assertEquals(veiculoCriado.getName(), "Fiesta");
			assertEquals(veiculoCriado.getColor(), "red");
			assertEquals(veiculoCriado.getId(), 1L);
			
			
		} catch (VeiculoJaRegistradoException e) {
		
		}
	}
	
	
	@Test
	public void tentarSalvarUmVeiculoQueJáExisteNoBanco() {
		Optional<Veiculo> optional = Optional.of(criarVeiculo());
		VeiculoDTO veiculoDTO = criarVeiculoDTO();

		try {
			when(veiculoRepository.findByChassiOrPlaca(anyString(), anyString())).thenReturn(optional);
			when(veiculoService.criarVeiculo(veiculoDTO)).thenReturn(Mockito.isNull());
		} catch (VeiculoJaRegistradoException e) {
			assertEquals("Veículo já registrado no sistema.", e.getMessage());
		}
	}
	
	@Test
	public void buscarUmVeiculoNoBancoDeDados() {
			try {
				when(veiculoService.buscarDetalhesVeiculo(anyLong())).thenReturn(criarVeiculoDTO());
				VeiculoDTO dto = veiculoService.buscarDetalhesVeiculo(anyLong());
				assertNotNull(dto);
				assertEquals("Fiesta", dto.getName());
			} catch (VeiculoNaoEncontradoException e) {}
	}
	
	@Test
	public void veiculoNaoEncontradoNoBancoDeDados() {
		try {
			when(veiculoService.buscarDetalhesVeiculo(anyLong())).thenThrow(new VeiculoNaoEncontradoException());
			veiculoRepository.findById(anyLong());
		} catch (VeiculoNaoEncontradoException e) {
			assertEquals("Veiculo com id 0 não encontrado no sistema", e.getMessage());
		}
	}
	
	
	@Test
	public void atualizarVeiculoNoBancoDeDado() {
		
	}
	
	

	private VeiculoDTO criarVeiculoDTO() {
		VeiculoDTO veiculoDTO1 = new VeiculoDTO( "784snc651das2", "Fiesta", "Ford", 2015, "red", Status.ACTIVATED,
				"ABC-4657");
		return veiculoDTO1;
	}

	private Veiculo criarVeiculo() {
		Veiculo veiculo1 = new Veiculo(1L, "784sdc651das2", "Fiesta", "Ford", 2015, "red", Status.ACTIVATED,
				"ABC-4657");
		return veiculo1;
	}
		

	private List<VeiculoDTO> criaListaVeiculoDTO() {
		List<VeiculoDTO> lista = new ArrayList<VeiculoDTO>();

		VeiculoDTO veiculoDTO1 = new VeiculoDTO(1L, "784sdc651das2", "Fiesta", "Ford", 2015, "red", Status.ACTIVATED,
				"ABC-4657");
		VeiculoDTO veiculoDTO2 = new VeiculoDTO(2L, "28h791asdwe874", "Ka", "Ford", 2016, "green", Status.ACTIVATED,
				"ABC-7890");
		VeiculoDTO veiculoDTO3 = new VeiculoDTO(3L, "d4qwda6s5da4dqw", "Mondeo", "Ford", 1998, "black",
				Status.ACTIVATED, "ABC-1478");
		VeiculoDTO veiculoDTO4 = new VeiculoDTO(4L, "28h79fsadfi98", "Focus", "Ford", 2018, "blue", Status.ACTIVATED,
				"ABC-1234");

		lista.add(veiculoDTO1);
		lista.add(veiculoDTO2);
		lista.add(veiculoDTO3);
		lista.add(veiculoDTO4);
		return lista;
	}
	

}
