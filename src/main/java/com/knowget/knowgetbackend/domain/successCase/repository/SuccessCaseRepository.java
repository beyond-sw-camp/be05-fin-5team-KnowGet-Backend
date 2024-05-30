package com.knowget.knowgetbackend.domain.successCase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.knowget.knowgetbackend.global.entity.SuccessCase;

public interface SuccessCaseRepository extends JpaRepository<SuccessCase, Integer> {
}
