package br.com.gerenciador.veiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gerenciador.veiculos.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

	Optional<Veiculo> findByChassiOrPlaca(String chassi, String placa);

}
