package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleFileTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "/my-service/tk/sample/file-test APIs", description = "파일을 다루는 테스트 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/file-test")
public class MyServiceTkSampleFileTestController {
    public MyServiceTkSampleFileTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleFileTestService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleFileTestService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "by_product_files/test 폴더로 파일 업로드",
            description = "multipart File 을 하나 업로드하여 서버의 by_product_files/test 폴더에 저장\n\n"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "정상 동작")})
    @PostMapping(path = "/upload-to-server", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public UploadToServerTestOutputVo uploadToServerTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            UploadToServerTestInputVo inputVo) throws IOException {
        return service.uploadToServerTest(httpServletResponse, inputVo);
    }

    public record UploadToServerTestInputVo(
            @Schema(description = "업로드 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("multipartFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile multipartFile) {
    }

    public record UploadToServerTestOutputVo(
            @Schema(description = "파일 다운로드 경로",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "http://127.0.0.1:8080/service1/tk/v1/file-test/download-from-server/file.txt"
            )
            @JsonProperty("fileDownloadFullUrl")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileDownloadFullUrl) {
    }


    ////
    @Operation(
            summary = "by_product_files/test 폴더에서 파일 다운받기",
            description = "업로드 API 를 사용하여 by_product_files/test 로 업로드한 파일을 다운로드\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            ),
            @ApiResponse(
                    responseCode = "204",
                    content = @Content(),
                    description = "Response Body 가 없습니다.\n\n" +
                            "Response Headers 를 확인하세요.",
                    headers = {
                            @Header(
                                    name = "api-result-code",
                                    description = "(Response Code 반환 원인) - Required\n\n" +
                                            "1 : fileName 에 해당하는 파일이 존재하지 않습니다.\n\n",
                                    schema = @Schema(type = "string")
                            )
                    }
            )
    })
    @GetMapping(
            path = "/download-from-server/{fileName}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> fileDownloadTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "fileName", description = "by_product_files/test 폴더 안의 파일명", example = "sample.txt") @PathVariable("fileName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    ) {
        return service.fileDownloadTest(httpServletResponse, fileName);
    }


    ////
    @Operation(
            summary = "파일 리스트 zip 압축 테스트",
            description = "파일들을 zip 타입으로 압축하여 by_product_files/test 폴더에 저장\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @PostMapping(
            path = "/zip-files",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void filesToZipTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.filesToZipTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "폴더 zip 압축 테스트",
            description = "폴더를 통째로 zip 타입으로 압축하여 by_product_files/test 폴더에 저장\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @PostMapping(
            path = "/zip-folder",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void folderToZipTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.folderToZipTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "zip 압축 파일 해제 테스트",
            description = "zip 압축 파일을 해제하여 by_product_files/test 폴더에 저장\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @PostMapping(
            path = "/unzip-file",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void unzipTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        service.unzipTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "클라이언트 이미지 표시 테스트용 API",
            description = "서버에서 이미지를 반환합니다. 클라이언트에서의 이미지 표시 시 PlaceHolder, Error 처리에 대응 할 수 있습니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/client-image-test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> forClientSideImageTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "delayTimeSecond", description = "이미지 파일 반환 대기 시간(0 은 바로, 음수는 에러 발생)", example = "0")
            @RequestParam("delayTimeSecond")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer delayTimeSecond
    ) throws InterruptedException {
        return service.forClientSideImageTest(httpServletResponse, delayTimeSecond);
    }


    ////
    @Operation(
            summary = "AWS S3 로 파일 업로드",
            description = "multipart File 을 하나 업로드하여 AWS S3 에 저장\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @PostMapping(
            path = "/upload-to-s3",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public AwsS3UploadTestOutputVo awsS3UploadTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AwsS3UploadTestInputVo inputVo
    ) throws IOException {
        return service.awsS3UploadTest(httpServletResponse, inputVo);
    }

    public record AwsS3UploadTestInputVo(
            @Schema(description = "업로드 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("multipartFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile multipartFile
    ) {
    }

    public record AwsS3UploadTestOutputVo(
            @Schema(description = "파일 다운로드 경로", requiredMode = Schema.RequiredMode.REQUIRED, example = "http://127.0.0.1:8080/service1/tk/v1/file-test/download-from-server/file.txt")
            @JsonProperty("fileDownloadFullUrl")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileDownloadFullUrl
    ) {
    }


    ////
    @Operation(
            summary = "AWS S3 파일의 내용을 String 으로 가져오기",
            description = "AWS S3 파일의 내용을 String 으로 가져옵니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @GetMapping(
            path = "/read-from-s3",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public GetFileContentToStringTestOutputVo getFileContentToStringTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "uploadFileName", description = "업로드한 파일 이름", example = "file.txt")
            @RequestParam("uploadFileName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String uploadFileName
    ) throws IOException {
        return service.getFileContentToStringTest(httpServletResponse, uploadFileName);
    }

    public record GetFileContentToStringTestOutputVo(
            @Schema(description = "읽은 파일 내용", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("fileContent")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileContent
    ) {
    }


    ////
    @Operation(
            summary = "AWS S3 파일을 삭제하기",
            description = "AWS S3 파일을 삭제합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    )
            }
    )
    @DeleteMapping(
            path = "/delete-from-s3",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public void deleteAwsS3FileTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "deleteFileName", description = "삭제할 파일 이름", example = "file.txt")
            @RequestParam("deleteFileName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String deleteFileName
    ) {
        service.deleteAwsS3FileTest(httpServletResponse, deleteFileName);
    }
}