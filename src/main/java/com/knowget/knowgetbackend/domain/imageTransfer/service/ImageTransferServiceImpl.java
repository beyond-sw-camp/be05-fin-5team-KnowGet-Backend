package com.knowget.knowgetbackend.domain.imageTransfer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.knowget.knowgetbackend.domain.imageTransfer.repository.ImageTransferRepository;
import com.knowget.knowgetbackend.domain.jobGuide.repository.JobGuideRepository;
import com.knowget.knowgetbackend.global.config.s3.AwsS3Util;
import com.knowget.knowgetbackend.global.entity.Image;
import com.knowget.knowgetbackend.global.entity.JobGuide;
import com.knowget.knowgetbackend.global.exception.ImageNotFoundException;
import com.knowget.knowgetbackend.global.exception.JobGuideNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageTransferServiceImpl implements ImageTransferService {
	private final ImageTransferRepository imageTransferRepository;
	private final JobGuideRepository jobGuideRepository;

	private final AwsS3Util awsS3Util;
	private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB

	// /**
	//  * 이미지 업로드
	//  * @param file
	//  * @param jobGuideId
	//  * @return imageUrl
	//  * @auther 근엽
	//  */
	// @Override
	// @Transactional
	// public String uploadFile(MultipartFile file, Integer jobGuideId) {
	// 	checkFileSize(file);
	//
	// 	JobGuide jobGuide = jobGuideRepository.findById(jobGuideId)
	// 		.orElseThrow(() -> new IllegalArgumentException("해당하는 취업 가이드가 없습니다."));
	//
	// 	Image image = Image.builder()
	// 		.imageUrl(awsS3Util.uploadFile(file))
	// 		.jobGuide(jobGuide)
	// 		.build();
	//
	// 	imageRepository.save(image);
	//
	// 	Image imageUrl = imageRepository.findByJobGuide(jobGuide)
	// 		.orElseThrow(() -> new IllegalArgumentException("해당하는 이미지가 없습니다."));
	//
	// 	return imageUrl.getImageUrl();
	// }

	/**
	 * 이미지 다중 업로드
	 *
	 * @param files
	 * @param guideId
	 * @return imageUrls
	 * @auther 근엽
	 */
	@Override
	@Transactional
	public List<String> uploadFiles(List<MultipartFile> files, Integer guideId) {
		for (MultipartFile file : files) {
			checkFileSize(file);
		}

		List<String> imageUrlList = new ArrayList<>();

		JobGuide jobGuide = jobGuideRepository.findById(guideId)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 취업 가이드가 없습니다."));

		for (MultipartFile file : files) {
			Image image = Image.builder()
				.imageUrl(awsS3Util.uploadFile(file))
				.jobGuide(jobGuide)
				.build();

			imageTransferRepository.save(image);
		}

		for (Image image : imageTransferRepository.findByJobGuide(jobGuide)) {

			imageUrlList.add(image.getImageUrl());
			log.info("이미지 URL : " + image.getImageUrl());
		}

		return imageUrlList;
	}

	/**
	 * 이미지 삭제
	 *
	 * @param imageId
	 * @return
	 * @auther 근엽
	 */
	@Override
	@Transactional
	public String deleteImage(Integer imageId) {

		Image image = imageTransferRepository.findById(imageId)
			.orElseThrow(() -> new ImageNotFoundException("해당하는 이미지가 없습니다."));

		// awsS3Util.deleteFile(image.getImageUrl());
		imageTransferRepository.delete(image);

		return "이미지가 삭제되었습니다.";
	}

	/**
	 * 특정 가이드에 포함된 이미지 URL 반환
	 *
	 * @param guideId 가이드 ID
	 * @return 이미지 URL 리스트
	 * @author Jihwan
	 */
	@Override
	public List<String> getImageUrls(Integer guideId) {
		JobGuide jobGuide = jobGuideRepository.findById(guideId)
			.orElseThrow(() -> new JobGuideNotFoundException("[Error] : 해당하는 취업 가이드가 없습니다."));
		List<Image> images = imageTransferRepository.findByJobGuide(jobGuide);

		List<String> imageUrls = new ArrayList<>();
		if (!images.isEmpty()) {
			for (Image image : images) {
				imageUrls.add(image.getImageUrl());
			}
		}
		return imageUrls;
	}

	/**
	 * 이미지 업데이트
	 *
	 * @param guideId
	 * @param files
	 * @return
	 * @auther 근엽
	 */
	@Override
	public List<String> updateImage(Integer guideId, List<MultipartFile> files) {
		JobGuide jobGuide = jobGuideRepository.findById(guideId)
			.orElseThrow(() -> new JobGuideNotFoundException("[Error] : 해당하는 취업 가이드가 없습니다."));

		for (MultipartFile file : files) {
			checkFileSize(file);
		}

		imageTransferRepository.findByJobGuide(jobGuide).forEach(image -> {
			imageTransferRepository.delete(image);
		});

		List<String> imageUrlList = new ArrayList<>();

		for (MultipartFile file : files) {
			Image image = Image.builder()
				.imageUrl(awsS3Util.uploadFile(file))
				.jobGuide(jobGuide)
				.build();

			imageTransferRepository.save(image);
		}

		for (Image image : imageTransferRepository.findByJobGuide(jobGuide)) {

			imageUrlList.add(image.getImageUrl());
			log.info("이미지 URL : " + image.getImageUrl());
		}

		return imageUrlList;
	}

	/**
	 * 파일 사이즈 체크
	 *
	 * @param file
	 * @auther 근엽
	 */
	private void checkFileSize(MultipartFile file) {
		if (file.getSize() > MAX_FILE_SIZE) {
			throw new MaxUploadSizeExceededException(file.getSize());
		}
	}
}
