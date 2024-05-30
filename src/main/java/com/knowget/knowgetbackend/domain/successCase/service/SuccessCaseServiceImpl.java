package com.knowget.knowgetbackend.domain.successCase.service;

import org.springframework.stereotype.Service;

import com.knowget.knowgetbackend.domain.successCase.repository.SuccessCaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuccessCaseServiceImpl implements SuccessCaseService {

	private final SuccessCaseRepository successCaseRepository;

}
