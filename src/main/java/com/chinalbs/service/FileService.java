package com.chinalbs.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	/**
	 * 上传头像图片
	 * 
	 * @param multipartFile
	 * @return
	 */
	public String saveImage(MultipartFile[] multipartFile);


}
