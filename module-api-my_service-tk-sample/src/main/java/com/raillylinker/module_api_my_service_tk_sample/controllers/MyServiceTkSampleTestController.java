package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleTestService;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Tag(name = "/my-service/tk/sample/test APIs", description = "테스트 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/test")
public class MyServiceTkSampleTestController {
    public MyServiceTkSampleTestController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleTestService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "이메일 발송 테스트",
            description = "이메일 발송 테스트\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/send-email",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void sendEmailTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendEmailTestInputVo inputVo
    ) throws Exception {
        service.sendEmailTest(httpServletResponse, inputVo);
    }

    public record SendEmailTestInputVo(
            @Schema(description = "수신자 이메일 배열", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"test1@gmail.com\"]")
            @JsonProperty("receiverEmailAddressList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> receiverEmailAddressList,

            @Schema(description = "참조자 이메일 배열", example = "[\"test2@gmail.com\"]")
            @JsonProperty("carbonCopyEmailAddressList")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> carbonCopyEmailAddressList,

            @Schema(description = "발신자명", requiredMode = Schema.RequiredMode.REQUIRED, example = "Railly Linker")
            @JsonProperty("senderName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String senderName,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 이메일")
            @JsonProperty("subject")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String subject,

            @Schema(description = "메세지", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 이메일을 송신했습니다.")
            @JsonProperty("message")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message,

            @Schema(description = "첨부 파일 리스트")
            @JsonProperty("multipartFileList")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull MultipartFile> multipartFileList
    ) {
    }


    ////
    @Operation(
            summary = "HTML 이메일 발송 테스트",
            description = "HTML 로 이루어진 이메일 발송 테스트\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/send-html-email",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void sendHtmlEmailTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendHtmlEmailTestInputVo inputVo
    ) throws Exception {
        service.sendHtmlEmailTest(httpServletResponse, inputVo);
    }

    public record SendHtmlEmailTestInputVo(
            @Schema(description = "수신자 이메일 배열", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"test1@gmail.com\"]")
            @JsonProperty("receiverEmailAddressList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> receiverEmailAddressList,

            @Schema(description = "참조자 이메일 배열", example = "[\"test2@gmail.com\"]")
            @JsonProperty("carbonCopyEmailAddressList")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> carbonCopyEmailAddressList,

            @Schema(description = "발신자명", requiredMode = Schema.RequiredMode.REQUIRED, example = "Railly Linker")
            @JsonProperty("senderName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String senderName,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 이메일")
            @JsonProperty("subject")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String subject,

            @Schema(description = "메세지", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 이메일을 송신했습니다.")
            @JsonProperty("message")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message,

            @Schema(description = "첨부 파일 리스트")
            @JsonProperty("multipartFileList")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull MultipartFile> multipartFileList
    ) {
    }


    ////
    @Operation(
            summary = "Naver API SMS 발송 샘플",
            description = "Naver API 를 사용한 SMS 발송 샘플\n\n" +
                    "Service 에서 사용하는 Naver SMS 발송 유틸 내의 개인정보를 변경해야 사용 가능\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/naver-sms-sample",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void naverSmsSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            NaverSmsSampleInputVo inputVo
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        service.naverSmsSample(httpServletResponse, inputVo);
    }

    public record NaverSmsSampleInputVo(
            @Schema(description = "SMS 수신측 휴대전화 번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)010-1111-1111")
            @JsonProperty("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,

            @Schema(description = "SMS 메세지", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 메세지 발송입니다.")
            @JsonProperty("smsMessage")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String smsMessage
    ) {
    }


    ////
    @Operation(
            summary = "Naver API AlimTalk 발송 샘플",
            description = "Naver API 를 사용한 AlimTalk 발송 샘플\n\n" +
                    "Service 에서 사용하는 Naver AlimTalk 발송 유틸 내의 개인정보를 변경해야 사용 가능\n\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정상 동작")
    })
    @PostMapping(
            path = "/naver-alim-talk-sample",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void naverAlimTalkSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            NaverAlimTalkSampleInputVo inputVo
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        service.naverAlimTalkSample(httpServletResponse, inputVo);
    }

    public record NaverAlimTalkSampleInputVo(
            @Schema(description = "카카오톡 채널명 ((구)플러스친구 아이디)", requiredMode = Schema.RequiredMode.REQUIRED, example = "@test")
            @JsonProperty("plusFriendId")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String plusFriendId,

            @Schema(description = "템플릿 코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "AAA1111")
            @JsonProperty("templateCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String templateCode,

            @Schema(description = "SMS 수신측 휴대전화 번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)010-1111-1111")
            @JsonProperty("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,

            @Schema(description = "메세지(템플릿에 등록한 문장과 동일해야 됩니다.)", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 메세지 발송입니다.")
            @JsonProperty("message")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message
    ) {
    }


    ////
    @Operation(
            summary = "액셀 파일을 받아서 해석 후 데이터 반환",
            description = "액셀 파일을 받아서 해석 후 데이터 반환\n\n"
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
            path = "/read-excel",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ReadExcelFileSampleOutputVo readExcelFileSample(
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ReadExcelFileSampleInputVo inputVo,
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws Exception {
        return service.readExcelFileSample(httpServletResponse, inputVo);
    }

    public record ReadExcelFileSampleInputVo(
            @Schema(description = "가져오려는 시트 인덱스 (0부터 시작)", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
            @JsonProperty("sheetIdx")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer sheetIdx,

            @Schema(description = "가져올 행 범위 시작 인덱스 (0부터 시작)", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
            @JsonProperty("rowRangeStartIdx")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer rowRangeStartIdx,

            @Schema(description = "가져올 행 범위 끝 인덱스 null 이라면 전부 (0부터 시작)", example = "10")
            @JsonProperty("rowRangeEndIdx")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer rowRangeEndIdx,

            @Schema(description = "가져올 열 범위 인덱스 리스트 null 이라면 전부 (0부터 시작)", example = "[0, 1, 2]")
            @JsonProperty("columnRangeIdxList")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull Integer> columnRangeIdxList,

            @Schema(description = "결과 컬럼의 최소 길이 (길이를 넘으면 그대로, 미만이라면 \"\" 로 채움)", example = "5")
            @JsonProperty("minColumnLength")
            @Nullable @org.jetbrains.annotations.Nullable
            Integer minColumnLength,

            @Schema(description = "액셀 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("excelFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile excelFile
    ) {
    }

    public record ReadExcelFileSampleOutputVo(
            @Schema(description = "행 카운트", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("rowCount")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer rowCount,

            @Schema(description = "분석한 객체를 toString 으로 표현한 데이터 String", requiredMode = Schema.RequiredMode.REQUIRED, example = "[[\"데이터1\", \"데이터2\"]]")
            @JsonProperty("dataString")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String dataString
    ) {
    }


    ////
    @Operation(
            summary = "액셀 파일 쓰기",
            description = "받은 데이터를 기반으로 액셀 파일을 만들어 by_product_files/test 폴더에 저장\n\n"
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
            path = "/write-excel",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void writeExcelFileSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        service.writeExcelFileSample(httpServletResponse);
    }


    ////
    @Operation(
            summary = "HTML 을 기반으로 PDF 를 생성",
            description = "준비된 HTML 1.0(strict), CSS 2.1 을 기반으로 PDF 를 생성\n\n"
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
            path = "/html-to-pdf",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> htmlToPdfSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        return service.htmlToPdfSample(httpServletResponse);
    }


    ////
    @Operation(
            summary = "입력받은 HTML 을 기반으로 PDF 를 생성 후 반환",
            description = "입력받은 HTML 1.0(strict), CSS 2.1 을 기반으로 PDF 를 생성 후 반환\n\n" +
                    "HTML 이 엄격한 규격을 요구받으므로 그것을 확인하며 변환하는 과정에 사용하라고 제공되는 api 입니다.\n\n"
    )
    @ApiResponses(
            value = {
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
                                                    "1 : fontFiles 에 ttf 가 아닌 폰트 파일이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = "/multipart-html-to-pdf",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> multipartHtmlToPdfSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartHtmlToPdfSampleInputVo inputVo
    ) throws IOException {
        String controllerBasicMapping = null;
        for (RequestMapping requestMappingAnnotation : this.getClass().getAnnotationsByType(RequestMapping.class)) {
            String[] paths = requestMappingAnnotation.value();
            if (paths.length > 0) {
                controllerBasicMapping = paths[0];
                break;
            }
        }
        return service.multipartHtmlToPdfSample(httpServletResponse, inputVo, controllerBasicMapping);
    }

    public record MultipartHtmlToPdfSampleInputVo(
            @Schema(description = "업로드 HTML 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("htmlFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile htmlFile,

            @Schema(description = "TTF 폰트 파일 리스트")
            @JsonProperty("fontFiles")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull MultipartFile> fontFiles,

            @Schema(description = "이미지 파일 리스트")
            @JsonProperty("imgFiles")
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull MultipartFile> imgFiles
    ) {
    }


    ////
    @Operation(
            summary = "by_product_files/uploads/fonts 폴더에서 파일 다운받기",
            description = "by_product_files/uploads/fonts 경로의 파일을 다운로드\n\n"
    )
    @ApiResponses({
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
            path = "/by_product_files/uploads/fonts/{fileName}",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> downloadFontFile(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "fileName", description = "by_product_files/test 폴더 안의 파일명", example = "sample.txt")
            @PathVariable("fileName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    ) {
        return service.downloadFontFile(httpServletResponse, fileName);
    }


    ////
    @Operation(
            summary = "Kafka 토픽 메세지 발행 테스트",
            description = "Kafka 토픽 메세지를 발행합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/kafka-produce-test",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void sendKafkaTopicMessageTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendKafkaTopicMessageTestInputVo inputVo
    ) {
        service.sendKafkaTopicMessageTest(httpServletResponse, inputVo);
    }

    public record SendKafkaTopicMessageTestInputVo(
            @Schema(description = "메세지", requiredMode = Schema.RequiredMode.REQUIRED, example = "testMessage")
            @JsonProperty("message")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String message
    ) {
    }


    ////
    @Operation(
            summary = "ProcessBuilder 샘플",
            description = "ProcessBuilder 를 이용하여 준비된 jar 파일을 실행시킵니다.\n\n" +
                    "jar 파일은 3초간 while 문으로 int 변수에 ++ 를 한 후 그 결과를 반환합니다.\n\n"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @GetMapping(
            path = "/process-builder-test",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ProcessBuilderTestOutputVo processBuilderTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(
                    name = "java 실행 파일 경로",
                    description = "java 명령어 실행 파일의 경로를 넣어줍니다. 환경변수 등록시 null",
                    example = "C:\\Users\\raill\\.jdks\\openjdk-21.0.2\\bin"
            )
            @RequestParam("javaEnvironmentPath")
            @Nullable @org.jetbrains.annotations.Nullable
            String javaEnvironmentPath
    ) throws IOException, InterruptedException {
        return service.processBuilderTest(httpServletResponse, javaEnvironmentPath);
    }

    public record ProcessBuilderTestOutputVo(
            @Schema(description = "jar 실행 결과", requiredMode = Schema.RequiredMode.REQUIRED, example = "3333")
            @JsonProperty("jarResult")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long jarResult
    ) {
    }


    ////
    @Operation(
            summary = "입력받은 폰트 파일의 내부 이름을 반환",
            description = "입력받은 폰트 파일의 내부 이름을 반환\n\n"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "정상 동작"
            )
    })
    @PostMapping(
            path = "/font-file-inner-name",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public CheckFontFileInnerNameOutputVo checkFontFileInnerName(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CheckFontFileInnerNameInputVo inputVo
    ) throws IOException {
        return service.checkFontFileInnerName(httpServletResponse, inputVo);
    }

    public record CheckFontFileInnerNameInputVo(
            @Schema(description = "업로드 폰트 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("fontFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile fontFile
    ) {
    }

    public record CheckFontFileInnerNameOutputVo(
            @Schema(description = "폰트 파일의 내부 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "NanumGothic")
            @JsonProperty("innerName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String innerName
    ) {
    }


    ////
    @Operation(
            summary = "AES256 암호화 테스트",
            description = "입력받은 텍스트를 암호화 하여 반환합니다.\n\n"
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
            path = "/aes256-encrypt",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public Aes256EncryptTestOutputVo aes256EncryptTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "plainText", description = "암호화 하려는 평문", example = "testString")
            @RequestParam("plainText")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String plainText,
            @Parameter(name = "alg", description = "암호화 알고리즘", example = "AES_CBC_PKCS5")
            @RequestParam("alg")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Aes256EncryptTestCryptoAlgEnum alg,
            @Parameter(name = "initializationVector", description = "초기화 벡터 16byte = 16char", example = "1q2w3e4r5t6y7u8i")
            @RequestParam("initializationVector")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String initializationVector,
            @Parameter(name = "encryptionKey", description = "암호화 키 32byte = 32char", example = "1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y")
            @RequestParam("encryptionKey")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptionKey
    ) {
        return service.aes256EncryptTest(httpServletResponse, plainText, alg, initializationVector, encryptionKey);
    }

    public enum Aes256EncryptTestCryptoAlgEnum {
        AES_CBC_PKCS5("AES/CBC/PKCS5Padding");

        Aes256EncryptTestCryptoAlgEnum(String alg) {
            this.alg = alg;
        }

        public final String alg;
    }

    public record Aes256EncryptTestOutputVo(
            @Schema(description = "암호화된 결과물", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("cryptoResult")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String cryptoResult
    ) {
    }


    ////
    @Operation(
            summary = "AES256 복호화 테스트",
            description = "입력받은 텍스트를 복호화 하여 반환합니다.\n\n"
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
            path = "/aes256-decrypt",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public Aes256DecryptTestOutputVo aes256DecryptTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "encryptedText", description = "복호화 하려는 암호문", example = "DwH1WCA3Bzqf6xq+udBI1Q==")
            @RequestParam("encryptedText")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptedText,
            @Parameter(name = "alg", description = "암호화 알고리즘", example = "AES_CBC_PKCS5")
            @RequestParam("alg")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Aes256DecryptTestCryptoAlgEnum alg,
            @Parameter(name = "initializationVector", description = "초기화 벡터 16byte = 16char", example = "1q2w3e4r5t6y7u8i")
            @RequestParam("initializationVector")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String initializationVector,
            @Parameter(name = "encryptionKey", description = "암호화 키 32byte = 32char", example = "1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y")
            @RequestParam("encryptionKey")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptionKey
    ) {
        return service.aes256DecryptTest(httpServletResponse, encryptedText, alg, initializationVector, encryptionKey);
    }

    public enum Aes256DecryptTestCryptoAlgEnum {
        AES_CBC_PKCS5("AES/CBC/PKCS5Padding");

        Aes256DecryptTestCryptoAlgEnum(String alg) {
            this.alg = alg;
        }

        public final String alg;
    }

    public record Aes256DecryptTestOutputVo(
            @Schema(description = "복호화된 결과물", requiredMode = Schema.RequiredMode.REQUIRED, example = "testString")
            @JsonProperty("cryptoResult")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String cryptoResult
    ) {
    }


    ////
    @Operation(
            summary = "Jsoup 태그 조작 테스트",
            description = "Jsoup 을 이용하여, HTML 태그를 조작하여 반환합니다.\n\n"
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
            path = "/jsoup-test",
            consumes = MediaType.ALL_VALUE,
            produces = "text/html;charset=utf-8"
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String jsoupTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "fix", description = "변환 여부", example = "true")
            @RequestParam("fix")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean fix
    ) {
        return service.jsoupTest(httpServletResponse, fix);
    }
}