package com.my.erp.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.my.erp.domain.exception.CargoNaoEncontradoException;
import com.my.erp.domain.exception.EntidadeEmUsoException;
import com.my.erp.domain.exception.NegocioException;
import com.my.erp.domain.model.Cargo;
import com.my.erp.domain.model.Loja;
import com.my.erp.domain.repository.CargoRepository;


@Service
public class CargoService {
	
	@Autowired
	private LojaService lojaService;

	@Autowired
	private CargoRepository cargoRepository;

	private static final String msg_cargo_em_uso = "Cargo de codigo identificador %d está em uso";
	private static final String msg_cargo_inativo = "Cargo de codigo identificador %d está inativo";

	@Transactional
	public Cargo salvar(Cargo cargo) {
		Long lojaId = cargo.getLoja().getId();
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		lojaService.verificarAtivo(loja);
		cargo.setLoja(loja);
		return cargoRepository.save(cargo);
	}
	
	@Transactional
	public Cargo salvar(Cargo cargo, Long lojaId) {
		Loja loja = lojaService.buscarOuFalhar(lojaId);
		lojaService.verificarAtivo(loja);
		cargo.setLoja(loja);
		return cargoRepository.save(cargo);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			cargoRepository.deleteById(id);
			cargoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CargoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(msg_cargo_em_uso, id));
		}
	}

	public Cargo buscarOuFalhar(Long id) {
		return cargoRepository.findById(id).orElseThrow(() -> new CargoNaoEncontradoException(id));
	}
	
	public Cargo buscarOuFalhar(Long lojaId, Long cargoId) {
		return cargoRepository.findById(lojaId, cargoId).orElseThrow(() -> new CargoNaoEncontradoException(lojaId,cargoId));
	}
	
	public void verificarAtivo(Cargo cargo) {
		if(!cargo.getAtivo()) {
			throw new NegocioException(String.format(msg_cargo_inativo, cargo.getId()));
		}
	}
	
	@Transactional
	public void ativar(Long id) {
		Cargo cargoAtual = buscarOuFalhar(id);
		cargoAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long id) {
		Cargo cargoAtual = buscarOuFalhar(id);
		cargoAtual.inativar();
	}
	
	@Transactional
	public void ativar(Long lojaId, Long cargoId) {
		Cargo cargoAtual = buscarOuFalhar(lojaId, cargoId);
		cargoAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long lojaId, Long cargoId) {
		Cargo cargoAtual = buscarOuFalhar(lojaId, cargoId);
		cargoAtual.inativar();
	}

}
