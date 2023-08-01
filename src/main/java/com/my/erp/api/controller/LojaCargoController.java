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
import com.my.erp.api.model.cargo.LojaCargoModel;
import com.my.erp.api.model.input.cargo.LojaCargoInput;
import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.repository.CargoRepository;
import com.my.erp.domain.service.CargoService;
import com.my.erp.domain.service.LojaService;

@RestController
@RequestMapping(value = "/lojas/{lojaId}/cargos")
public class LojaCargoController {
	
	@Autowired
	private LojaService lojaService;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private CargoModelAssembler cargoModelAssembler;
	
	@Autowired
	private CargoInputDisassembler cargoInputDisassembler;
	
	@GetMapping
	public List<LojaCargoModel> listar(@PathVariable Long lojaId){
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		List<Cargo> cargos = cargoRepository.findByLoja(loja);
		return cargoModelAssembler.toCollectionLojaModel(cargos); 
	}
	
	@GetMapping("/{cargoId}")
	public LojaCargoModel buscar(@PathVariable Long lojaId, @PathVariable Long cargoId) {
		Cargo cargo = cargoService.buscarOuFalhar(lojaId, cargoId);
		return cargoModelAssembler.toLojaModel(cargo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LojaCargoModel salvar(@PathVariable Long lojaId, @RequestBody @Valid LojaCargoInput lojaCargoInput) {
		Cargo cargo = cargoInputDisassembler.toDomainModel(lojaCargoInput);
		cargo = cargoService.salvar(cargo, lojaId);
		return cargoModelAssembler.toLojaModel(cargo);
	}
	
	@PutMapping("/{cargoId}")
	public LojaCargoModel atualizar(@PathVariable Long lojaId, @PathVariable Long cargoId, @RequestBody @Valid LojaCargoInput lojaCargoInput) {
		Cargo cargoAtual = cargoService.buscarOuFalhar(lojaId, cargoId);
		cargoInputDisassembler.copyToDomainObject(lojaCargoInput, cargoAtual);
		cargoAtual = cargoService.salvar(cargoAtual);
		return cargoModelAssembler.toLojaModel(cargoAtual);
	}
	
	@PutMapping("/{cargoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long lojaId, @PathVariable Long cargoId) {
		cargoService.ativar(lojaId, cargoId);
	}
	
	@DeleteMapping("/{cargoId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long lojaId, @PathVariable Long cargoId) {
		cargoService.inativar(lojaId, cargoId);
	}

}
