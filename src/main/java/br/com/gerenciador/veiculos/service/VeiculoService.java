package br.com.gerenciador.veiculos.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gerenciador.veiculos.dto.VeiculoDTO;
import br.com.gerenciador.veiculos.entity.Veiculo;
import br.com.gerenciador.veiculos.enums.Status;
import br.com.gerenciador.veiculos.exception.VeiculoJaRegistradoException;
import br.com.gerenciador.veiculos.exception.VeiculoNaoEncontradoException;
import br.com.gerenciador.veiculos.repository.VeiculoRepository;

@Service
public class VeiculoService {
	@Autowired
	private VeiculoRepository veiculoRepository;
	@Autowired
	private ModelMapper veiculoMapper;

	public List<Veiculo> listar() {
		return veiculoRepository.findAll();
	}

	public VeiculoDTO criarVeiculo(VeiculoDTO veiculoDTO) throws VeiculoJaRegistradoException {
		validarSeVeiculoJaExiste(veiculoDTO);
		Veiculo veiculo = veiculoMapper.map(veiculoDTO,Veiculo.class);
		Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
		return veiculoMapper.map(veiculoSalvo,VeiculoDTO.class);
	}

	private void validarSeVeiculoJaExiste(VeiculoDTO veiculoDTO) throws VeiculoJaRegistradoException {
		Optional<Veiculo> veiculo = veiculoRepository.findByChassiOrPlaca(veiculoDTO.getChassi(),veiculoDTO.getPlaca());
		if (veiculo.isPresent()) {
			throw new VeiculoJaRegistradoException(veiculo.get().toString());
		}		
	}

	public VeiculoDTO buscarDetalhesVeiculo(Long id) throws VeiculoNaoEncontradoException {
		Optional<Veiculo> veiculo = veiculoRepository.findById(id);
		if (veiculo.isEmpty()) {
			throw new VeiculoNaoEncontradoException(id);
		}	
		return veiculoMapper.map(veiculo.get(),VeiculoDTO.class);
	}

	public VeiculoDTO atualizarStatusVeiculo(Long id, VeiculoDTO veiculoDTO) throws VeiculoNaoEncontradoException {
		VeiculoDTO DTO = buscarDetalhesVeiculo(id);
		validarStatusRequiscao(veiculoDTO);
		DTO.setStatus(veiculoDTO.getStatus());
		Veiculo veiculo = veiculoMapper.map(DTO,Veiculo.class);
		Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
		return veiculoMapper.map(veiculoSalvo,VeiculoDTO.class);
	}

	private void validarStatusRequiscao(VeiculoDTO veiculoDTO) {
		
		
	}

	public VeiculoDTO delecaoVeiculo(Long id) throws VeiculoNaoEncontradoException {
		VeiculoDTO DTO = buscarDetalhesVeiculo(id);
		DTO.setStatus(Status.DEACTIVATED);
		Veiculo veiculo = veiculoMapper.map(DTO,Veiculo.class);
		Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
		return veiculoMapper.map(veiculoSalvo,VeiculoDTO.class);
	}

}
