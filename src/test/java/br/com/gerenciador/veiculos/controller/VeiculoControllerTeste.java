package br.com.gerenciador.veiculos.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.gerenciador.veiculos.dto.VeiculoDTO;
import br.com.gerenciador.veiculos.exception.VeiculoJaRegistradoException;
import br.com.gerenciador.veiculos.exception.VeiculoNaoEncontradoException;
import br.com.gerenciador.veiculos.repository.VeiculoRepository;
import br.com.gerenciador.veiculos.service.VeiculoService;

public class VeiculoControllerTeste {
	
	private static final String URL = "http://localhost:8080/api/veiculos";
	
	private MockMvc mockMvc;
	
	@Mock
	private VeiculoService veiculoService;
	
	@Mock
	private VeiculoRepository veiculoRepository;
	
	@InjectMocks
	private VeiculoController veiculoController;

	@SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(veiculoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }
	
	@Test
	void cadastrarUmVeiculoNovoComSucesso() throws Exception {
		VeiculoDTO veiculoBD = new VeiculoDTO();
				
				 when(veiculoService.criarVeiculo(any())).thenReturn(any());

				 mockMvc.perform(post(URL)
			                .contentType(MediaType.APPLICATION_JSON)
							.content(asJsonString(veiculoBD)))
			                .andExpect(status().isCreated());
				 
    }
	
	 @Test
	    void tentarCadastrarUmVeiculoQueJaExisteNoBancoDeDados() throws Exception {
	        VeiculoDTO v = new VeiculoDTO();
		 	when(veiculoService.criarVeiculo(any())).thenThrow(new VeiculoJaRegistradoException());
		 	
	        mockMvc.perform(post(URL)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(v)))
	                .andExpect(status().isBadRequest());
	               
	        Mockito.verify(veiculoRepository,times(0)).save(Mockito.any());
	    }
	
	@Test
	void quandoChamarApiBuscarPorIdRetornarUmVeiculoEStatusOK() throws Exception {
		
		 when(veiculoService.buscarDetalhesVeiculo(any())).thenReturn(any());
		 
		 MockHttpServletRequestBuilder builder =
	              MockMvcRequestBuilders.get(URL + "/1")
	                                    .contentType(MediaType.APPLICATION_JSON)
	                                    .accept(MediaType.APPLICATION_JSON)
	                                    .characterEncoding("UTF-8");

		 mockMvc.perform(builder)
         .andExpect(status().isOk());	
	}
	@Test
	void quandoChamarApiBuscarPorIdNaoRetornarUmVeiculoEStatusNotFound() throws Exception {
		
		when(veiculoService.buscarDetalhesVeiculo(any())).thenThrow(new VeiculoNaoEncontradoException());
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL+"/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void listarTodosOsVeiculosComSucesso() throws Exception {
		
		when(veiculoService.listar()).thenReturn(new ArrayList<VeiculoDTO>());
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	void erroAolistarTodosOsVeiculos() throws Exception {
		
		when(veiculoService.listar()).thenThrow(new VeiculoNaoEncontradoException());
		
		mockMvc.perform(MockMvcRequestBuilders.get(URL).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void atualizarVeiculoComSucesso() throws Exception {
		
		VeiculoDTO veiculoDTO = new VeiculoDTO();
		
		when(veiculoService.atualizarVeiculo(any(), any())).thenReturn(veiculoDTO);
		
		 MockHttpServletRequestBuilder builder =
	              MockMvcRequestBuilders.put(URL + "/1")
	                                    .contentType(MediaType.APPLICATION_JSON)
	                                    .accept(MediaType.APPLICATION_JSON)
	                                    .content(asJsonString(new VeiculoDTO()))
	                                    .characterEncoding("UTF-8");
		 
		 mockMvc.perform(builder)
         		.andExpect(status().isOk());
		
	}
	
	@Test
	void erroAoatualizarVeiculoVeiculoNaoEncontrado() throws Exception {
		
		when(veiculoService.atualizarVeiculo(any(), any())).thenThrow(new VeiculoNaoEncontradoException());
		
		 MockHttpServletRequestBuilder builder =
	              MockMvcRequestBuilders.put(URL + "/1")
	                                    .contentType(MediaType.APPLICATION_JSON)
	                                    .accept(MediaType.APPLICATION_JSON)
	                                    .content(asJsonString(new VeiculoDTO()))
	                                    .characterEncoding("UTF-8");
		 
		 mockMvc.perform(builder)
         		.andExpect(status().isNotFound());
		
	}
	
	
	
	
	public static String asJsonString(Object veiculo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());

            return objectMapper.writeValueAsString(veiculo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }}
	
