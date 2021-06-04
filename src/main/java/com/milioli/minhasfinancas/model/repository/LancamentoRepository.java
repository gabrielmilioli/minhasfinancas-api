package com.milioli.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milioli.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
