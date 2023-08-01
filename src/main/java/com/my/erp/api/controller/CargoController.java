package com.my.erp.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.my.erp.api.assembler.cargo.CargoInputDisassembler;
import com.my.erp.api.assembler.cargo.CargoModelAssembler;
import com.my.erp.api.model.cargo.CargoModel;
import com.my.erp.api.model.input.cargo.CargoInput;
import com.my.erp.domain.exception.CargoNaoEncontradoException;
import com.my.erp.domain.exception.LojaNaoEncontradaException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.repository.CargoRepository;
import com.my.erp.domain.service.CargoService;

@RequestMapping("/cargos")
@RestController
public class CargoController {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private CargoModelAssembler cargoModelAssembler;
	
	@Autowired
	private CargoInputDisassembler cargoInputDisassembler;

	@GetMapping
	public List<CargoModel> listar() {
		List<Cargo> cargos = cargoRepository.findAll();
		return cargoModelAssembler.toCollectionModel(cargos);
	}

	@GetMapping("/{id}")
	public CargoModel buscar(@PathVariable Long id) {
		Cargo cargo = cargoService.buscarOuFalhar(id);
		return cargoModelAssembler.toModel(cargo);
	}

	@GetMapping("/titulo")
	public List<CargoModel> listarPorTitulo(String titulo) {
		List<Cargo> cargos = cargoRepository.findAllByTituloContaining(titulo);
		if (cargos.isEmpty()) {
			throw new CargoNaoEncontradoException(String.format("Nenhum cargo contém o titulo %s", titulo));
		}
		return cargoModelAssembler.toCollectionModel(cargos);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CargoModel adicionar(@RequestBody @Valid CargoInput cargoInput) {
		try {
			Cargo cargo = cargoInputDisassembler.toDomainModel(cargoInput);
			cargo = cargoService.salvar(cargo);
			return cargoModelAssembler.toModel(cargo);
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@PutMapping("/{id}")
	public CargoModel atualizar(@PathVariable Long id, @RequestBody @Valid CargoInput cargoInput) {
		try {
			Cargo cargoAtual = cargoService.buscarOuFalhar(id);
			cargoInputDisassembler.copyToDomainObject(cargoInput, cargoAtual);
			cargoAtual = cargoService.salvar(cargoAtual);
			return cargoModelAssembler.toModel(cargoAtual);
		} catch (LojaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cargoService.excluir(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		cargoService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		cargoService.inativar(id);
	}

}
