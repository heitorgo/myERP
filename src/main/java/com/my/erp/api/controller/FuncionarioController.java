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
import com.my.erp.api.model.funcionario.FuncionarioModel;
import com.my.erp.api.model.funcionario.FuncionarioResumoModel;
import com.my.erp.api.model.input.funcionario.FuncionarioInput;
import com.my.erp.domain.exception.CargoNaoEncontradoException;
import com.my.erp.domain.exception.FuncionarioNaoEncontradoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Funcionario;
import com.my.erp.domain.repository.FuncionarioRepository;
import com.my.erp.domain.service.FuncionarioService;



@RequestMapping("/funcionarios")
@RestController
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private FuncionarioModelAssembler funcionarioModelAssembler;
	
	@Autowired
	private FuncionarioInputDisassembler funcionarioInputDisassembler;

	@GetMapping
	public List<FuncionarioResumoModel> listar() {
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		return funcionarioModelAssembler.toCollectionResumoModel(funcionarios);
	}

	@GetMapping("/{id}")
	public FuncionarioModel buscar(@PathVariable Long id) {
		Funcionario funcionario = funcionarioService.buscarOuFalhar(id);
		return funcionarioModelAssembler.toModel(funcionario);
	}

	@GetMapping("/nome")
	public List<FuncionarioModel> listarPorNome(String nome) {
		List<Funcionario> funcionarios = funcionarioRepository.findAllByNomeContaining(nome);
		if (funcionarios.isEmpty()) {
			throw new FuncionarioNaoEncontradoException("Nenhum funcionario contém este nome");
		}
		return funcionarioModelAssembler.toCollectionModel(funcionarios);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FuncionarioModel adicionar(@RequestBody @Valid FuncionarioInput funcionarioInput) {
		try {
			Funcionario funcionario = funcionarioInputDisassembler.toDomainModel(funcionarioInput);
			funcionario = funcionarioService.salvar(funcionario);
			return funcionarioModelAssembler.toModel(funcionario);
		} catch (CargoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public FuncionarioModel atualizar(@PathVariable Long id, @RequestBody @Valid FuncionarioInput funcionarioInput) {
		try {
			Funcionario funcionarioAtual = funcionarioService.buscarOuFalhar(id);
			funcionarioInputDisassembler.copyToDomainObject(funcionarioInput, funcionarioAtual);
			funcionarioAtual = funcionarioService.salvar(funcionarioAtual);
			return funcionarioModelAssembler.toModel(funcionarioAtual);
		} catch (CargoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		funcionarioService.excluir(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		funcionarioService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		funcionarioService.inativar(id);
	}

}
