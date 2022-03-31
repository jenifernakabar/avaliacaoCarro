package br.com.gerenciador.veiculos.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.gerenciador.veiculos.enums.Status;

public class VeiculoDTO {

	private Long id;

	private String chassi;
	
	private String name;
	
	private String manufacturer;
	
	private int year;
	
	private String color;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private String placa;
	
	

	public VeiculoDTO() {
	}

	public VeiculoDTO(Long id, String chassi, String name, String manufacturer, int year, String color, Status type,
			String placa) {
		this.id = id;
		this.chassi = chassi;
		this.name = name;
		this.manufacturer = manufacturer;
		this.year = year;
		this.color = color;
		this.status = type;
		this.placa = placa;
	}
	
	public VeiculoDTO( String chassi, String name, String manufacturer, int year, String color, Status type,
			String placa) {
		this.chassi = chassi;
		this.name = name;
		this.manufacturer = manufacturer;
		this.year = year;
		this.color = color;
		this.status = type;
		this.placa = placa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}


	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "VeiculoDTO [chassi=" + chassi + ", placa=" + placa + "]";
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
