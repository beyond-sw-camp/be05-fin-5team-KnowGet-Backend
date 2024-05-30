package com.knowget.knowgetbackend.domain.successCase.controller;

import org.springframework.web.bind.annotation.RestController;

import com.knowget.knowgetbackend.domain.successCase.service.SuccessCaseServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SuccessCaseController {

	private final SuccessCaseServiceImpl successCaseServiceImpl;

}
