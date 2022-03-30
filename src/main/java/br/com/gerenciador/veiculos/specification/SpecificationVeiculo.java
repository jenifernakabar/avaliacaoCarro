package br.com.gerenciador.veiculos.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.gerenciador.veiculos.entity.Veiculo;

public class SpecificationVeiculo {
	
	public static Specification<Veiculo> name(String name) {
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}
	
	public static Specification<Veiculo> manufacturer(String manufacturer) {
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("manufacturer"),"%" + manufacturer + "%");
	}
	
	public static Specification<Veiculo> year(int year) {
		return (root, criteriaQuery, criteriaBuilder) -> 
			criteriaBuilder.equal(root.get("year"), year);
	}
	

}
