package com.knowget.knowgetbackend.domain.successCase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knowget.knowgetbackend.domain.successCase.dto.SuccessCaseResponseDTO;
import com.knowget.knowgetbackend.domain.successCase.repository.SuccessCaseRepository;
import com.knowget.knowgetbackend.global.entity.SuccessCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuccessCaseServiceImpl implements SuccessCaseService {

	private final SuccessCaseRepository successCaseRepository;

	// 특정 caseId에 해당하는 SuccessCase 조회 - findById
	@Override
	@Transactional(readOnly = true)
	public SuccessCaseResponseDTO getSuccessCase(Integer caseId) {
		Optional<SuccessCase> successCase = successCaseRepository.findById(caseId);
		return successCase.map(SuccessCaseResponseDTO::new).orElse(null);
		//caseId에 해당하는 successcase를 반환!
	}

	// 전체 SuccessCase 목록 조회 - findAll
	@Override
	@Transactional(readOnly = true)
	public List<SuccessCaseResponseDTO> getAllSuccessCases() {
		return successCaseRepository.findAll().stream().map(SuccessCaseResponseDTO::new).toList();
		//모든 successcase를 반환!
	}
}
