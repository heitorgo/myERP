package com.my.erp.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.FuncionarioNaoEncontradoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Funcionario;
import com.my.erp.domain.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private CargoService cargoService;

	private static final String msg_funcionario_em_uso = "O funcionario de codigo identificador %d está em uso";
	private static final String msg_funcionario_inativo = "O funcionario de codigo identificador %d está inativo";
	private static final String msg_funcionario_nao_encontrado_loja = "O funcionario de codigo identificador %d não foi encontrado para loja de código %d";

	@Transactional
	public Funcionario salvar(Funcionario funcionario) {
		Long cargoId = funcionario.getCargo().getId();
		Cargo cargo = cargoService.buscarOuFalhar(cargoId);
		cargoService.verificarAtivo(cargo);
		funcionario.setCargo(cargo);
		return funcionarioRepository.save(funcionario);
	}
	
	@Transactional
	public Funcionario salvar(Funcionario funcionario, Long lojaId, Long cargoId) {
		Cargo cargo = cargoService.buscarOuFalhar(lojaId, cargoId);
		cargoService.verificarAtivo(cargo);
		funcionario.setCargo(cargo);
		return funcionarioRepository.save(funcionario);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			funcionarioRepository.deleteById(id);
			funcionarioRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new FuncionarioNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_funcionario_em_uso, id));
		}
	}

	public Funcionario buscarOuFalhar(Long id) {
		return funcionarioRepository.findById(id).orElseThrow(() -> new FuncionarioNaoEncontradoException(id));
	}
	
	public Funcionario buscarOuFalhar(Long cargoId, Long funcionarioId) {
		return funcionarioRepository.findById(cargoId, funcionarioId).orElseThrow(() -> new FuncionarioNaoEncontradoException(funcionarioId, cargoId));
	}
	
	public Funcionario buscarLojaOuFalhar(Long lojaId, Long funcionarioId) {
		return funcionarioRepository.findByLojaId(lojaId, funcionarioId).orElseThrow(() -> new FuncionarioNaoEncontradoException(
				String.format(msg_funcionario_nao_encontrado_loja, funcionarioId, lojaId)));
	}
	
	public void verificarAtivo(Funcionario funcionario) {
		if(funcionario.getAtivo() != true) {
			throw new NegocioException(String.format(msg_funcionario_inativo, funcionario.getId()));
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Funcionario funcionarioAtual = buscarOuFalhar(id);
		funcionarioAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long id) {
		Funcionario funcionarioAtual = buscarOuFalhar(id);
		funcionarioAtual.inativar();
	}
	
	@Transactional
	public void ativar(Long cargoId, Long funcionarioId) {
		Funcionario funcionarioAtual = buscarOuFalhar(cargoId, funcionarioId);
		funcionarioAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long cargoId, Long funcionarioId) {
		Funcionario funcionarioAtual = buscarOuFalhar(cargoId, funcionarioId);
		funcionarioAtual.inativar();
	}

}
