package br.com.gerenciador.veiculos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private VeiculoRepository veiculoRepository;
	private ModelMapper veiculoMapper;
	
	@Autowired
	public VeiculoService(VeiculoRepository veiculoRepository, ModelMapper veiculoMapper) {
		this.veiculoRepository = veiculoRepository;
		this.veiculoMapper = veiculoMapper;
	}
	

	public List<VeiculoDTO> listar() throws VeiculoNaoEncontradoException {
		// Se for necessario retornar todos os veiculos que o status for diferente de
		// Deactivated
		// .filter(v -> !v.getStatus().equals(Status.DEACTIVATED))
		// Percorre a Stream e converte cada um dos elementos para um DTO
//		 List<VeiculoDTO> collect = veiculoRepository.findAll().stream().map(this::toVeiculoDTO).collect(Collectors.toList());
		 List<VeiculoDTO> collect = veiculoRepository.findByOrderByManufacturerAscNameAscYearAsc().stream().map(this::toVeiculoDTO).collect(Collectors.toList());
		 if (collect.isEmpty()) {
			throw new VeiculoNaoEncontradoException();
		}
		 return collect;
	}

	public VeiculoDTO criarVeiculo(VeiculoDTO veiculoDTO) throws VeiculoJaRegistradoException {
		validarSeVeiculoJaExiste(veiculoDTO);
		Veiculo veiculo = veiculoMapper.map(veiculoDTO, Veiculo.class);
		Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
		return veiculoMapper.map(veiculoSalvo, VeiculoDTO.class);
	}

	public void validarSeVeiculoJaExiste(VeiculoDTO veiculoDTO) throws VeiculoJaRegistradoException {
		Optional<Veiculo> veiculo = veiculoRepository.findByChassiOrPlaca(veiculoDTO.getChassi(),
				veiculoDTO.getPlaca());
		if (veiculo.isPresent()) {
			throw new VeiculoJaRegistradoException();
		}
	}

	public VeiculoDTO buscarDetalhesVeiculo(Long id) throws VeiculoNaoEncontradoException {
		Optional<Veiculo> veiculo = veiculoRepository.findById(id);
		if (veiculo.isEmpty()) {
			throw new VeiculoNaoEncontradoException(id);
		}
		return veiculoMapper.map(veiculo.get(), VeiculoDTO.class);
	}

	public VeiculoDTO atualizarVeiculo(Long id, VeiculoDTO veiculoDTO) throws VeiculoNaoEncontradoException {
		VeiculoDTO DTO = buscarDetalhesVeiculo(id);
		veiculoDTO.setId(DTO.getId());
		Veiculo veiculo = veiculoMapper.map(veiculoDTO, Veiculo.class);
		Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
		return veiculoMapper.map(veiculoSalvo, VeiculoDTO.class);
	}

	public VeiculoDTO atualizarStatusVeiculo(Long id, VeiculoDTO veiculoDTO) throws VeiculoNaoEncontradoException {
		VeiculoDTO DTO = buscarDetalhesVeiculo(id);
		DTO.setStatus(veiculoDTO.getStatus());
		Veiculo veiculo = veiculoMapper.map(DTO, Veiculo.class);
		Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
		return veiculoMapper.map(veiculoSalvo, VeiculoDTO.class);
	}

	public VeiculoDTO delecaoVeiculo(Long id) throws VeiculoNaoEncontradoException {
		VeiculoDTO DTO = buscarDetalhesVeiculo(id);
		DTO.setStatus(Status.DEACTIVATED);
		Veiculo veiculo = veiculoMapper.map(DTO, Veiculo.class);
		Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
		return veiculoMapper.map(veiculoSalvo, VeiculoDTO.class);
	}

	private VeiculoDTO toVeiculoDTO(Veiculo veiculo) {
		return veiculoMapper.map(veiculo, VeiculoDTO.class);
	}

	public List<VeiculoDTO> listadinamica(String fabricante, String name, int ano) throws VeiculoNaoEncontradoException {
		List<VeiculoDTO> collect = veiculoRepository
				.findAll()
				.stream()
				.map(this::toVeiculoDTO)
				.collect(Collectors.toList());

		if (!name.isBlank()) {
			collect.removeIf(v -> !v.getName().equalsIgnoreCase(name));
		}
		if (!fabricante.isBlank()) {
			collect.removeIf(v -> !v.getManufacturer().equalsIgnoreCase(fabricante));
		}
		if (ano != 0) {
			collect.removeIf(v -> v.getYear() != ano);
		}

		if (collect.isEmpty()) {
			throw new VeiculoNaoEncontradoException();
		}
		return collect;

//		tentativa de usar specification
//		return veiculoRepository.findAll(Specification
//				.where(
//						SpecificationVeiculo.name(name)
//						.or(SpecificationVeiculo.manufacturer(fabricante))
//						.or(SpecificationVeiculo.year(Integer.valueOf(ano))))
//				);

	}

	

}
