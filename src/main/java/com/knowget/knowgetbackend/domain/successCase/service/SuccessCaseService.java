package com.knowget.knowgetbackend.domain.successCase.service;

import java.util.List;

import com.knowget.knowgetbackend.domain.successCase.dto.SuccessCaseResponseDTO;

public interface SuccessCaseService {

	// 특정 caseId에 해당하는 SuccessCase 조회
	SuccessCaseResponseDTO getSuccessCase(Integer caseId);

	// 전체 SuccessCase 목록 조회
	List<SuccessCaseResponseDTO> getAllSuccessCases();
}
