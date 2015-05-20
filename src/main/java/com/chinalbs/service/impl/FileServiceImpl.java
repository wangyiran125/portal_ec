package com.chinalbs.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chinalbs.service.FileService;
import com.chinalbs.utils.ImageUtils;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService {

	private static final String DEST_EXTENSION = "jpg";

	@Value("${ImageUploadPath}")
	private String uploadPath;
	@Value("${ImageMaxSize}")
	private Integer ImageMaxSize;
	
	@Value("${LicenseImageWidth}")
	private Integer licenseImageWidth;
	@Value("${LicenseImageHeight}")
	private Integer licenseImageHeight;
	

	@Override
	public String saveImage(MultipartFile[] multipartFile){
		String webPath = null;
		if (multipartFile == null || multipartFile.length == 0) {
			return null;
		}
		  try {
			for (MultipartFile multiFile : multipartFile) {
				if (multiFile.getSize() > ImageMaxSize) {
					continue;
				}
				String uuid = UUID.randomUUID().toString();
				String sourcePath = uploadPath
						+ File.separator
						+ "src_"
						+ uuid
						+ "."
						+ FilenameUtils.getExtension(multiFile
								.getOriginalFilename());
//				webPath = uuid + "." + DEST_EXTENSION;
				webPath=File.separator
                    + "src_"
                    + uuid
                    + "."
                    + FilenameUtils.getExtension(multiFile
                            .getOriginalFilename());
				String storePath = uploadPath + File.separator + uuid + "." + DEST_EXTENSION;;

				File tempFile = new File(System.getProperty("java.io.tmpdir")
						+ File.separator + "upload_" + UUID.randomUUID()
						+ ".tmp");
				if (!tempFile.getParentFile().exists()) {
					tempFile.getParentFile().mkdirs();
				}
			
				  multiFile.transferTo(tempFile);
				proccessImage(tempFile, sourcePath, storePath, licenseImageWidth,
				    licenseImageHeight, true);
			}
		  } catch (IllegalStateException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }

		return webPath;
	}

	private void proccessImage(File tempFile, String sourcePath,
			String resizedPath, Integer width, Integer height,
			boolean moveSource) {
		String tempPath = System.getProperty("java.io.tmpdir");
		File resizedFile = new File(tempPath + File.pathSeparator + "upload_"
				+ UUID.randomUUID() + "." + DEST_EXTENSION);
		ImageUtils.zoom(tempFile, resizedFile, width, height);

		File destFile = new File(resizedPath);
		try {
			if (moveSource) {
				File destSrcFile = new File(sourcePath);
				FileUtils.moveFile(tempFile, destSrcFile);
			}
			FileUtils.moveFile(resizedFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
