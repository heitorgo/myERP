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

import com.my.erp.api.assembler.funcionario.FuncionarioInputDisassembler;
import com.my.erp.api.assembler.funcionario.FuncionarioModelAssembler;
import com.my.erp.api.model.funcionario.CargoFuncionarioModel;
import com.my.erp.api.model.input.funcionario.CargoFuncionarioInput;
import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Funcionario;
import com.my.erp.domain.repository.FuncionarioRepository;
import com.my.erp.domain.service.CargoService;
import com.my.erp.domain.service.FuncionarioService;



@RestController
@RequestMapping(value = "/lojas/{lojaId}/cargos/{cargoId}/funcionarios")
public class CargoFuncionarioController {
	
	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private FuncionarioModelAssembler funcionarioModelAssembler;
	
	@Autowired
	private FuncionarioInputDisassembler funcionarioInputDisassembler;
	
	@GetMapping
	public List<CargoFuncionarioModel> listar(@PathVariable Long lojaId, @PathVariable Long cargoId){
		Cargo cargo = cargoService.buscarOuFalhar(lojaId, cargoId);
		List<Funcionario> funcionarios = funcionarioRepository.findByCargo(cargo);
		return funcionarioModelAssembler.toCollectionCargoModel(funcionarios); 
	}
	
	@GetMapping("/{funcionarioId}")
	public CargoFuncionarioModel buscar(@PathVariable Long lojaId, @PathVariable Long cargoId, @PathVariable Long funcionarioId) {
		Cargo cargo = cargoService.buscarOuFalhar(lojaId, cargoId);
		Funcionario funcionario = funcionarioService.buscarOuFalhar(cargo.getId(), funcionarioId);
		return funcionarioModelAssembler.toCargoModel(funcionario);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CargoFuncionarioModel salvar(@PathVariable Long lojaId, @PathVariable Long cargoId, @RequestBody @Valid CargoFuncionarioInput cargoFuncionarioInput) {
		Funcionario funcionario = funcionarioInputDisassembler.toDomainModel(cargoFuncionarioInput);
		funcionario = funcionarioService.salvar(funcionario, lojaId, cargoId);
		return funcionarioModelAssembler.toCargoModel(funcionario);
	}
	
	@PutMapping("/{funcionarioId}")
	public CargoFuncionarioModel atualizar(@PathVariable Long lojaId, @PathVariable Long cargoId, @PathVariable Long funcionarioId, @RequestBody @Valid CargoFuncionarioInput cargoFuncionarioInput) {
		Cargo cargo = cargoService.buscarOuFalhar(lojaId, cargoId);
		Funcionario funcionarioAtual = funcionarioService.buscarOuFalhar(cargo.getId(), funcionarioId);
		funcionarioInputDisassembler.copyToDomainObject(cargoFuncionarioInput, funcionarioAtual);
		funcionarioAtual = funcionarioService.salvar(funcionarioAtual);
		return funcionarioModelAssembler.toCargoModel(funcionarioAtual);
	}
	
	@PutMapping("/{funcionarioId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long lojaId, @PathVariable Long cargoId, @PathVariable Long funcionarioId) {
		Cargo cargo = cargoService.buscarOuFalhar(lojaId, cargoId);
		funcionarioService.ativar(cargo.getId(), funcionarioId);
	}
	
	@DeleteMapping("/{funcionarioId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long lojaId, @PathVariable Long cargoId, @PathVariable Long funcionarioId) {
		Cargo cargo = cargoService.buscarOuFalhar(lojaId, cargoId);
		funcionarioService.inativar(cargo.getId(), funcionarioId);
	}

}
