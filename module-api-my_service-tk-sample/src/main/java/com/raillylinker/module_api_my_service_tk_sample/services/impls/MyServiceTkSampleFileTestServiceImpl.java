package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleFileTestController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleFileTestService;
import com.raillylinker.module_common.util_components.AwsS3UtilComponent;
import com.raillylinker.module_common.util_components.CustomUtil;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipOutputStream;

@Service
public class MyServiceTkSampleFileTestServiceImpl implements MyServiceTkSampleFileTestService {
    public MyServiceTkSampleFileTestServiceImpl(
            @Value("${spring.profiles.active:default}")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String activeProfile,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CustomUtil customUtil,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AwsS3UtilComponent awsS3UtilComponent
    ) {
        this.activeProfile = activeProfile;
        this.customUtil = customUtil;
        this.awsS3UtilComponent = awsS3UtilComponent;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final String activeProfile;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final CustomUtil customUtil;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final AwsS3UtilComponent awsS3UtilComponent;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private static final Logger classLogger = LoggerFactory.getLogger(MyServiceTkSampleFileTestServiceImpl.class);


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleFileTestController.UploadToServerTestOutputVo uploadToServerTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleFileTestController.UploadToServerTestInputVo inputVo
    ) throws IOException {
        Path saveDirectoryPath = Paths.get("./by_product_files/test").toAbsolutePath().normalize();
        Files.createDirectories(saveDirectoryPath);

        String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.multipartFile().getOriginalFilename()));
        int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

        String fileNameWithOutExtension;
        String fileExtension;

        if (fileExtensionSplitIdx == -1) {
            fileNameWithOutExtension = multiPartFileNameString;
            fileExtension = "";
        } else {
            fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
            fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
        }

        String savedFileName = String.format("%s(%s).%s",
                fileNameWithOutExtension,
                LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")),
                fileExtension);

        inputVo.multipartFile().transferTo(saveDirectoryPath.resolve(savedFileName).normalize().toFile());
        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleFileTestController.UploadToServerTestOutputVo(
                "http://127.0.0.1:8080/my-service/tk/sample/file-test/download-from-server/" + savedFileName);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> fileDownloadTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    ) {
        // 프로젝트 루트 경로 (프로젝트 settings.gradle 이 있는 경로)
        String projectRootAbsolutePathString = new File("").getAbsolutePath();

        // 파일 절대 경로 및 파일명 (프로젝트 루트 경로에 있는 by_product_files/test 폴더를 기준으로 함)
        Path serverFilePathObject = Paths.get(projectRootAbsolutePathString, "by_product_files", "test", fileName);

        try {
            if (Files.isDirectory(serverFilePathObject)) {
                // 파일이 디렉토리일때
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "1");
                return null;
            }

            if (Files.notExists(serverFilePathObject)) {
                // 파일이 없을 때
                httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                httpServletResponse.setHeader("api-result-code", "1");
                return null;
            }

            httpServletResponse.setStatus(HttpStatus.OK.value());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename(fileName, StandardCharsets.UTF_8)
                            .build()
            );
            headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(serverFilePathObject));

            return new ResponseEntity<>(
                    new InputStreamResource(Files.newInputStream(serverFilePathObject)),
                    headers,
                    HttpStatus.OK
            );

        } catch (IOException e) {
            throw new RuntimeException("Error during file handling", e);
        }
    }


    ////
    @Override
    public void filesToZipTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        // 프로젝트 루트 경로 (프로젝트 settings.gradle 이 있는 경로)
        String projectRootAbsolutePathString = new File("").getAbsolutePath();

        // 파일 경로 리스트
        List<String> filePathList = Arrays.asList(
                projectRootAbsolutePathString + "/module-api-my_service-tk-sample/src/main/resources/static/files_to_zip_test/1.txt",
                projectRootAbsolutePathString + "/module-api-my_service-tk-sample/src/main/resources/static/files_to_zip_test/2.xlsx",
                projectRootAbsolutePathString + "/module-api-my_service-tk-sample/src/main/resources/static/files_to_zip_test/3.png",
                projectRootAbsolutePathString + "/module-api-my_service-tk-sample/src/main/resources/static/files_to_zip_test/4.mp4"
        );

        // 파일 저장 디렉토리 경로
        Path saveDirectoryPath = Paths.get("./by_product_files/test").toAbsolutePath().normalize();

        // 파일 저장 디렉토리 생성
        try {
            Files.createDirectories(saveDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException("Error creating directories", e);
        }

        // 확장자 포함 파일명 생성
        String fileTargetPathString = saveDirectoryPath.resolve(
                "zipped_" + LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")) + ".zip"
        ).normalize().toString();

        // 압축 파일 생성
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileTargetPathString);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            for (String filePath : filePathList) {
                File file = new File(filePath);
                if (file.exists()) {
                    customUtil.addToZip(file, file.getName(), zipOutputStream);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error creating zip file", e);
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    public void folderToZipTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        // 프로젝트 루트 경로 (프로젝트 settings.gradle 이 있는 경로)
        String projectRootAbsolutePathString = new File("").getAbsolutePath();

        // 압축 대상 디렉토리
        File sourceDir = new File(projectRootAbsolutePathString + "/module-api-my_service-tk-sample/src/main/resources/static/files_to_zip_test");

        // 파일 저장 디렉토리 경로
        Path saveDirectoryPath = Paths.get("./by_product_files/test").toAbsolutePath().normalize();

        // 파일 저장 디렉토리 생성
        try {
            Files.createDirectories(saveDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException("Error creating directories", e);
        }

        // 확장자 포함 파일명 생성
        String fileTargetPathString = saveDirectoryPath.resolve(
                "zipped_" + LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")) + ".zip"
        ).normalize().toString();

        // 압축 파일 생성
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileTargetPathString);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            // customUtil.compressDirectoryToZip 메서드가 자바에서도 동작하도록 구현되어야 합니다.
            customUtil.compressDirectoryToZip(sourceDir, sourceDir.getName(), zipOutputStream);

        } catch (IOException e) {
            throw new RuntimeException("Error creating zip file", e);
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    public void unzipTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        // 프로젝트 루트 경로 (프로젝트 settings.gradle 이 있는 경로)
        String projectRootAbsolutePathString = new File("").getAbsolutePath();
        String filePathString = projectRootAbsolutePathString + "/module-api-my_service-tk-sample/src/main/resources/static/unzip_test/test.zip";

        // 파일 저장 디렉토리 경로
        Path saveDirectoryPath = Paths.get("./by_product_files/test").toAbsolutePath().normalize();

        // 파일 저장 디렉토리 생성
        try {
            Files.createDirectories(saveDirectoryPath);
        } catch (IOException e) {
            throw new RuntimeException("Error creating directories", e);
        }

        // 요청 시간을 문자열로
        String timeString = LocalDateTime.now().atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"));

        // 확장자 포함 파일명 생성
        String saveFileName = "unzipped_" + timeString + "/";

        Path fileTargetPath = saveDirectoryPath.resolve(saveFileName).normalize();

        // customUtil.unzipFile 메서드가 자바에서도 동작하도록 구현되어야 합니다.
        customUtil.unzipFile(filePathString, fileTargetPath);

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> forClientSideImageTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer delayTimeSecond
    ) throws InterruptedException {
        if (delayTimeSecond < 0) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }

        // 지연 시간 적용
        Thread.sleep(delayTimeSecond * 1000L);

        // 파일 로딩
        Resource file = new ClassPathResource("static/for_client_side_image_test/client_image_test.jpg");

        httpServletResponse.setStatus(HttpStatus.OK.value());

        // 파일 다운로드 응답 반환
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment")
                        .filename("client_image_test.jpg", StandardCharsets.UTF_8)
                        .build().toString())
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(file);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleFileTestController.AwsS3UploadTestOutputVo awsS3UploadTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleFileTestController.AwsS3UploadTestInputVo inputVo
    ) throws IOException {
        // 원본 파일명(with suffix)
        String multiPartFileNameString = StringUtils.cleanPath(Objects.requireNonNull(inputVo.multipartFile().getOriginalFilename()));

        // 파일 확장자 구분 위치
        int fileExtensionSplitIdx = multiPartFileNameString.lastIndexOf('.');

        // 확장자가 없는 파일명
        String fileNameWithOutExtension;
        // 확장자
        String fileExtension;

        if (fileExtensionSplitIdx == -1) {
            fileNameWithOutExtension = multiPartFileNameString;
            fileExtension = "";
        } else {
            fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
            fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);
        }

        String savedFileName = fileNameWithOutExtension + "(" +
                LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")) + ")." + fileExtension;

        String uploadedFileFullUrl = awsS3UtilComponent.upload(
                inputVo.multipartFile(),
                savedFileName,
                activeProfile.equals("prod80") ? "test-prod/test" : "test-dev/test"
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());

        return new MyServiceTkSampleFileTestController.AwsS3UploadTestOutputVo(uploadedFileFullUrl);

    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleFileTestController.GetFileContentToStringTestOutputVo getFileContentToStringTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String uploadFileName
    ) throws IOException {
        httpServletResponse.setStatus(HttpStatus.OK.value());

        String fileContent = awsS3UtilComponent.getTextFileString(
                activeProfile.equals("prod80") ? "petlogon-contract-prod/test" : "petlogon-contract-dev/test",
                uploadFileName
        );

        return new MyServiceTkSampleFileTestController.GetFileContentToStringTestOutputVo(fileContent);
    }


    ////
    @Override
    public void deleteAwsS3FileTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String deleteFileName
    ) {
        // AWS 파일 삭제
        awsS3UtilComponent.delete(
                activeProfile.equals("prod80") ? "petlogon-contract-prod/test" : "petlogon-contract-dev/test",
                deleteFileName
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }
}