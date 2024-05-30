package com.knowget.knowgetbackend.domain.successCase.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.knowget.knowgetbackend.domain.successCase.dto.SuccessCaseResponseDTO;
import com.knowget.knowgetbackend.domain.successCase.service.SuccessCaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/success-case")
@RequiredArgsConstructor
public class SuccessCaseController {

	private final SuccessCaseService successCaseService;

	// 특정 caseId에 해당하는 SuccessCase 조회
	@GetMapping("/{caseId}")
	public ResponseEntity<SuccessCaseResponseDTO> getSuccessCase(@PathVariable Integer caseId) {
		SuccessCaseResponseDTO successCase = successCaseService.getSuccessCase(caseId);
		return ResponseEntity.ok(successCase);
	}

	// 전체 SuccessCase 목록 조회
	@GetMapping
	public ResponseEntity<List<SuccessCaseResponseDTO>> getAllSuccessCases() {
		List<SuccessCaseResponseDTO> successCases = successCaseService.getAllSuccessCases();
		return ResponseEntity.ok(successCases);
	}
}
