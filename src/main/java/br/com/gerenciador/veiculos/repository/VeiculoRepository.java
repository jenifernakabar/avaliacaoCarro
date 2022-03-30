package br.com.gerenciador.veiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.gerenciador.veiculos.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {

	Optional<Veiculo> findByChassiOrPlaca(String chassi, String placa);

}
