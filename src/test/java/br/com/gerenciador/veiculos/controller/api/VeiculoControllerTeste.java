package br.com.gerenciador.veiculos.controller.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.gerenciador.veiculos.controller.VeiculoController;
import br.com.gerenciador.veiculos.dto.VeiculoDTO;
import br.com.gerenciador.veiculos.enums.Status;
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
	void quandoChamarApiCadastrarRetornarSucesso() throws Exception {
		VeiculoDTO veiculoBD = new VeiculoDTO();
				
				 when(veiculoService.criarVeiculo(veiculoBD)).thenReturn(veiculoBD);

				 mockMvc.perform(post(URL)
			                .contentType(MediaType.APPLICATION_JSON)
			                .content(asJsonString(veiculoBD)))
			                .andExpect(status().isCreated());
				 
    }
	
	 @Test
	    void quandoChamarApiCadastrarComValoresInvalidosRetornarErro() throws Exception {
		 	VeiculoDTO carro = new VeiculoDTO();
	        
		 	when(veiculoService.criarVeiculo(carro)).thenThrow(new HttpMessageNotReadableException(null));
		 	
	        mockMvc.perform(post(URL)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(carro)))
	                .andExpect(status().isBadRequest());
	               
	        Mockito.verify(veiculoRepository,times(0)).save(Mockito.any());
	    }
	
	@Test
	void quandoChamarApiBuscarPorIdRetornarUmVeiculoEStatusOK() throws Exception {
		VeiculoDTO veiculoBD = new VeiculoDTO(1L, "932sdtw028dft235dfr","Palio", "Fiat", 2010, "azul", Status.ACTIVATED, "DCF-5248" );
		
		 when(veiculoService.buscarDetalhesVeiculo(1L)).thenReturn(veiculoBD);
		 
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
		VeiculoDTO veiculoBD = new VeiculoDTO();
		
		Optional<VeiculoDTO> retorno = Optional.empty();
		
		when(veiculoService.buscarDetalhesVeiculo(1L)).thenReturn(veiculoBD);

		
		mockMvc.perform(MockMvcRequestBuilders.get(URL+"/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	public static String asJsonString(Object produto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());

            return objectMapper.writeValueAsString(produto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }}
	
