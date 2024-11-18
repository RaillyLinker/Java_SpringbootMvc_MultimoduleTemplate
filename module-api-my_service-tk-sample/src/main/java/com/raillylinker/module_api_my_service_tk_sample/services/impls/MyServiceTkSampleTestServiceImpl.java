package com.raillylinker.module_api_my_service_tk_sample.services.impls;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleTestController;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleTestService;
import com.raillylinker.module_common.util_components.*;
import com.raillylinker.module_kafka.kafka_components.producers.Kafka1MainProducer;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.fontbox.ttf.TTFParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MyServiceTkSampleTestServiceImpl implements MyServiceTkSampleTestService {
    public MyServiceTkSampleTestServiceImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ExcelFileUtil excelFileUtil,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CustomUtil customUtil,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CryptoUtil cryptoUtil,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PdfGenerator pdfGenerator,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            EmailSender emailSender,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            NaverSmsSenderComponent naverSmsSenderComponent,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Kafka1MainProducer kafka1MainProducer,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ServerProperties serverProperties,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ResourceLoader resourceLoader
    ) {
        this.excelFileUtil = excelFileUtil;
        this.customUtil = customUtil;
        this.cryptoUtil = cryptoUtil;
        this.pdfGenerator = pdfGenerator;
        this.emailSender = emailSender;
        this.naverSmsSenderComponent = naverSmsSenderComponent;
        this.kafka1MainProducer = kafka1MainProducer;
        this.serverProperties = serverProperties;
        this.resourceLoader = resourceLoader;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ExcelFileUtil excelFileUtil;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final CustomUtil customUtil;

    // 암복호화 유틸
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final CryptoUtil cryptoUtil;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final PdfGenerator pdfGenerator;

    // 이메일 발송 유틸
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final EmailSender emailSender;

    // 네이버 메시지 발송 유틸
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final NaverSmsSenderComponent naverSmsSenderComponent;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Kafka1MainProducer kafka1MainProducer;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ServerProperties serverProperties;
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final ResourceLoader resourceLoader;

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());


    // ---------------------------------------------------------------------------------------------
    // <공개 메소드 공간>
    @Override
    public void sendEmailTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.SendEmailTestInputVo inputVo
    ) throws Exception {
        emailSender.sendMessageMail(
                inputVo.senderName(),
                inputVo.receiverEmailAddressList().toArray(String[]::new),
                inputVo.carbonCopyEmailAddressList() != null ?
                        inputVo.carbonCopyEmailAddressList().toArray(String[]::new) : null,
                inputVo.subject(),
                inputVo.message(),
                null,
                inputVo.multipartFileList()
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    public void sendHtmlEmailTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.SendHtmlEmailTestInputVo inputVo
    ) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("message", inputVo.message());

        Map<String, ClassPathResource> attachments = new HashMap<>();
        attachments.put("html_email_sample_css", new ClassPathResource("static/send_html_email_test/html_email_sample.css"));
        attachments.put("image_sample", new ClassPathResource("static/send_html_email_test/image_sample.jpg"));

        emailSender.sendThymeLeafHtmlMail(
                inputVo.senderName(),
                inputVo.receiverEmailAddressList().toArray(String[]::new),
                inputVo.carbonCopyEmailAddressList() != null ?
                        inputVo.carbonCopyEmailAddressList().toArray(String[]::new) : null,
                inputVo.subject(),
                "send_html_email_test/html_email_sample",
                model,
                null,
                attachments,
                null,
                inputVo.multipartFileList()
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    public void naverSmsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.NaverSmsSampleInputVo inputVo
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String[] phoneNumberSplit = inputVo.phoneNumber().split("\\)");

        // 국가 코드 (ex : 82)
        String countryCode = phoneNumberSplit[0];

        // 전화번호 (ex : "01000000000")
        String phoneNumber = phoneNumberSplit[1]
                .replace("-", "")
                .replace(" ", "");

        // SMS 전송
        boolean sendSmsResult = naverSmsSenderComponent.sendSms(
                new NaverSmsSenderComponent.SendSmsInputVo(
                        "SMS",
                        countryCode,
                        phoneNumber,
                        inputVo.smsMessage()
                )
        );

        if (!sendSmsResult) {
            throw new RuntimeException("SMS 전송 실패");
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    public void naverAlimTalkSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.NaverAlimTalkSampleInputVo inputVo
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String[] phoneNumberSplit = inputVo.phoneNumber().split("\\)");

        // 국가 코드 (ex : 82)
        String countryCode = phoneNumberSplit[0];

        // 전화번호 (ex : "01000000000")
        String phoneNumber = phoneNumberSplit[1]
                .replace("-", "")
                .replace(" ", "");

        // 알림톡 전송
        naverSmsSenderComponent.sendAlimTalk(
                new NaverSmsSenderComponent.SendAlimTalkInputVo(
                        inputVo.plusFriendId(),
                        inputVo.templateCode(),
                        new ArrayList<>(List.of(
                                new NaverSmsSenderComponent.SendAlimTalkInputVo.MessageVo(
                                        countryCode,
                                        phoneNumber,
                                        null,
                                        inputVo.message(),
                                        null,
                                        null,
                                        null,
                                        null,
                                        true,
                                        new NaverSmsSenderComponent.SendAlimTalkInputVo.FailOverConfigVo(
                                                null,
                                                null,
                                                null,
                                                "카카오 실패시의 SMS 발송 메시지입니다."
                                        )
                                )
                        ))
                )
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleTestController.ReadExcelFileSampleOutputVo readExcelFileSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.ReadExcelFileSampleInputVo inputVo
    ) throws Exception {
        List<List<String>> excelData;

        try (InputStream fileInputStream = inputVo.excelFile().getInputStream()) {
            excelData = excelFileUtil.readExcel(
                    fileInputStream,
                    inputVo.sheetIdx(),
                    inputVo.rowRangeStartIdx(),
                    inputVo.rowRangeEndIdx(),
                    inputVo.columnRangeIdxList(),
                    inputVo.minColumnLength()
            );
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleTestController.ReadExcelFileSampleOutputVo(
                excelData != null ? excelData.size() : 0,
                excelData != null ? excelData.toString() : "null"
        );
    }


    ////
    @Override
    public void writeExcelFileSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        // 파일 저장 디렉토리 경로
        String saveDirectoryPathString = "./by_product_files/test";
        Path saveDirectoryPath = Paths.get(saveDirectoryPathString).toAbsolutePath().normalize();
        // 파일 저장 디렉토리 생성
        Files.createDirectories(saveDirectoryPath);

        // 확장자 포함 파일명 생성
        String saveFileName = "temp_" + LocalDateTime.now().atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")) + ".xlsx";

        // 파일 저장 경로와 파일명(with index) 을 합친 path 객체
        Path fileTargetPath = saveDirectoryPath.resolve(saveFileName).normalize();
        File file = fileTargetPath.toFile();

        Map<String, List<List<String>>> inputExcelSheetDataMap = new HashMap<>();
        inputExcelSheetDataMap.put("testSheet1", List.of(
                List.of("1-1", "1-2", "1-3"),
                List.of("2-1", "2-2", "2-3"),
                List.of("3-1", "3-2", "3-3")
        ));
        inputExcelSheetDataMap.put("testSheet2", List.of(
                List.of("1-1", "1-2"),
                List.of("2-1", "2-2")
        ));

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            excelFileUtil.writeExcel(fileOutputStream, inputExcelSheetDataMap);
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> htmlToPdfSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException {
        // thymeLeaf 엔진으로 파싱한 HTML String 가져오기
        String htmlString = customUtil.parseHtmlFileToHtmlString(
                "html_to_pdf_sample/html_to_pdf_sample", // thymeLeaf Html 이름 (ModelAndView 의 사용과 동일)
                Map.of("title", "PDF 변환 테스트")
        );

        HashMap<String, String> savedFontFileNameMap = new HashMap<>();
        HashMap<String, byte[]> savedImgFilePathMap = new HashMap<>();

        // htmlString 을 PDF 로 변환하여 저장
        savedFontFileNameMap.put("NanumGothicFile.ttf", "http://127.0.0.1:" + serverProperties.getPort() + "/html_to_pdf_sample/NanumGothic.ttf");
        savedFontFileNameMap.put("NanumMyeongjo.ttf", "http://127.0.0.1:" + serverProperties.getPort() + "/html_to_pdf_sample/NanumMyeongjo.ttf");

        InputStream inputStream = resourceLoader.getResource("classpath:static/html_to_pdf_sample/html_to_pdf_sample.jpg").getInputStream();
        byte[] imageBytes = inputStream.readAllBytes();
        savedImgFilePathMap.put("html_to_pdf_sample.jpg", imageBytes);

        byte[] pdfByteArray = pdfGenerator.createPdfByteArrayFromHtmlString2(htmlString, savedFontFileNameMap, savedImgFilePathMap);

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"result(" +
                        LocalDateTime.now().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z")) + ").pdf\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(new InputStreamResource(new java.io.ByteArrayInputStream(pdfByteArray)));
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> multipartHtmlToPdfSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.MultipartHtmlToPdfSampleInputVo inputVo,
            @Nullable @org.jetbrains.annotations.Nullable
            String controllerBasicMapping
    ) throws IOException {
        HashMap<String, String> savedFontFileNameMap = new HashMap<>();
        ArrayList<File> savedImgFileList = new ArrayList<>();
        HashMap<String, String> savedImgFilePathMap = new HashMap<>();

        try {
            if (inputVo.fontFiles() != null) {
                for (MultipartFile fontFile : inputVo.fontFiles()) {
                    // 파일 저장 기본 디렉토리 경로
                    Path saveDirectoryPath = Paths.get("./by_product_files/uploads/fonts").toAbsolutePath().normalize();

                    // 파일 저장 기본 디렉토리 생성
                    Files.createDirectories(saveDirectoryPath);

                    // 원본 파일명(with suffix)
                    String multiPartFileNameString = fontFile.getOriginalFilename();

                    // 파일 확장자 구분 위치
                    int fileExtensionSplitIdx = Objects.requireNonNull(multiPartFileNameString).lastIndexOf('.');

                    String fileNameWithOutExtension;
                    String fileExtension;
                    String ttfName;

                    if (fileExtensionSplitIdx == -1) {
                        httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                        httpServletResponse.setHeader("api-result-code", "1");
                        return null;
                    } else {
                        fileNameWithOutExtension = multiPartFileNameString.substring(0, fileExtensionSplitIdx);
                        fileExtension = multiPartFileNameString.substring(fileExtensionSplitIdx + 1);

                        if (!fileExtension.equals("ttf")) {
                            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
                            httpServletResponse.setHeader("api-result-code", "1");
                            return null;
                        }

                        try (InputStream fontInputStream = fontFile.getInputStream()) {
                            TTFParser ttfParser = new TTFParser();
                            ttfName = ttfParser.parseEmbedded(fontInputStream).getName();
                        }

                        String fontFileUrl = "http://127.0.0.1:" + serverProperties.getPort()
                                + (controllerBasicMapping != null ? controllerBasicMapping : "")
                                + "/by_product_files/uploads/fonts/" + ttfName + "." + fileExtension;

                        savedFontFileNameMap.put(fileNameWithOutExtension + "." + fileExtension, fontFileUrl);
                    }

                    Path fontFilePath = saveDirectoryPath.resolve(ttfName + "." + fileExtension).normalize();

                    if (!Files.exists(fontFilePath)) {
                        fontFile.transferTo(fontFilePath);
                    }
                }
            }

            if (inputVo.imgFiles() != null) {
                for (MultipartFile imgFile : inputVo.imgFiles()) {
                    String multiPartFileNameString = imgFile.getOriginalFilename();
                    int fileExtensionSplitIdx = Objects.requireNonNull(multiPartFileNameString).lastIndexOf('.');

                    String fileExtension = (fileExtensionSplitIdx == -1) ? "" :
                            multiPartFileNameString.substring(fileExtensionSplitIdx + 1);

                    File tempFile = Files.createTempFile(null, "." + fileExtension).toFile();
                    imgFile.transferTo(tempFile);

                    savedImgFileList.add(tempFile);
                    savedImgFilePathMap.put(multiPartFileNameString, tempFile.toString());
                }
            }

            byte[] pdfByteArray = pdfGenerator.createPdfByteArrayFromHtmlString(
                    new String(inputVo.htmlFile().getBytes(), StandardCharsets.UTF_8),
                    savedFontFileNameMap,
                    savedImgFilePathMap);

            httpServletResponse.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=result("
                            + LocalDateTime.now().atZone(ZoneId.systemDefault())
                            .format(DateTimeFormatter.ofPattern("yyyy_MM_dd_'T'_HH_mm_ss_SSS_z"))
                            + ").pdf")
                    .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                    .body(new InputStreamResource(new ByteArrayInputStream(pdfByteArray)));

        } finally {
            for (File imgFile : savedImgFileList) {
                boolean result = imgFile.delete();
                System.out.println("delete " + imgFile + " : " + result);
            }
        }
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ResponseEntity<Resource> downloadFontFile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    ) {
        // 프로젝트 루트 경로 (프로젝트 settings.gradle 이 있는 경로)
        String projectRootAbsolutePathString = new File("").getAbsolutePath();

        // 파일 절대 경로 및 파일명 (프로젝트 루트 경로에 있는 by_product_files/test 폴더를 기준으로 함)
        Path serverFilePathObject = Paths.get(projectRootAbsolutePathString + "/by_product_files/uploads/fonts/" + fileName);

        if (Files.isDirectory(serverFilePathObject)) {
            // 파일이 디렉토리일때
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        } else if (Files.notExists(serverFilePathObject)) {
            // 파일이 없을 때
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            httpServletResponse.setHeader("api-result-code", "1");
            return null;
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.valueOf(ContentDisposition.attachment().filename(fileName, StandardCharsets.UTF_8).build()))
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(serverFilePathObject))
                    .body(new InputStreamResource(Files.newInputStream(serverFilePathObject)));
        } catch (IOException e) {
            // Handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    ////
    @Override
    public void sendKafkaTopicMessageTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.SendKafkaTopicMessageTestInputVo inputVo
    ) {
        // kafkaProducer1 에 토픽 메세지 발행
        kafka1MainProducer.sendMessageToTestTopic1(
                new Kafka1MainProducer.SendMessageToTestTopic1InputVo(inputVo.message(), 1)
        );

        httpServletResponse.setStatus(HttpStatus.OK.value());
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleTestController.ProcessBuilderTestOutputVo processBuilderTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Nullable @org.jetbrains.annotations.Nullable
            String javaEnvironmentPath
    ) throws IOException, InterruptedException {
        String javaEnv = (javaEnvironmentPath != null) ? javaEnvironmentPath : "java";

        // JAR 파일 실행 명령어 설정
        ProcessBuilder javaJarPb = new ProcessBuilder(javaEnv, "-jar", "./external_files/files_for_api_test/JarExample/Counter.jar");
        javaJarPb.directory(new File(".")); // 현재 작업 디렉토리 설정

        // 프로세스 시작
        Process javaJarProcess = javaJarPb.start();

        long result;
        try (InputStream inputStream = javaJarProcess.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            result = Long.parseLong(reader.readLine());
            int exitCode = javaJarProcess.waitFor();
            System.out.println("Exit Code: " + exitCode);
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleTestController.ProcessBuilderTestOutputVo(result);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleTestController.CheckFontFileInnerNameOutputVo checkFontFileInnerName(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.CheckFontFileInnerNameInputVo inputVo
    ) throws IOException {
        String fontName;
        try (InputStream fontInputStream = inputVo.fontFile().getInputStream()) {
            TTFParser parser = new TTFParser();
            fontName = parser.parseEmbedded(fontInputStream).getName();
        }

        httpServletResponse.setStatus(HttpStatus.OK.value());
        return new MyServiceTkSampleTestController.CheckFontFileInnerNameOutputVo(fontName);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleTestController.Aes256EncryptTestOutputVo aes256EncryptTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String plainText,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.Aes256EncryptTestCryptoAlgEnum alg,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String initializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptionKey
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        String encryptedText = cryptoUtil.encryptAES256(plainText, alg.alg, initializationVector, encryptionKey);
        return new MyServiceTkSampleTestController.Aes256EncryptTestOutputVo(encryptedText);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public MyServiceTkSampleTestController.Aes256DecryptTestOutputVo aes256DecryptTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptedText,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.Aes256DecryptTestCryptoAlgEnum alg,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String initializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String encryptionKey
    ) {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        String decryptedText = cryptoUtil.decryptAES256(encryptedText, alg.alg, initializationVector, encryptionKey);
        return new MyServiceTkSampleTestController.Aes256DecryptTestOutputVo(decryptedText);
    }


    ////
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public String jsoupTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean fix
    ) {
        String htmlString = """
                    <!DOCTYPE HTML>
                    <head>
                        <meta content="text/html; charset=UTF-8" http-equiv="Content-Type"/>
                        <meta charset='UTF-8'/>
                        <title>Test</title>
                    </head>
                    <body>
                    <div>
                        <div>
                            <div class="fix">
                                수정 전
                            </div>
                        </div>

                        <div>
                            <div class="fix">
                                <h1>수정 전</h1>
                            </div>
                        </div>
                    </div>

                    </body>
                    </html>
                """;

        if (fix) {
            // Jsoup 라이브러리를 이용한 HTML 파싱
            Document doc = Jsoup.parse(htmlString);
            // 클래스가 "fix"인 모든 요소를 선택합니다.
            Elements buyerSignElements = doc.select(".fix");
            for (Element buyerSignElement : buyerSignElements) {
                // 선택된 요소 내의 모든 자식 요소를 삭제합니다.
                buyerSignElement.children().remove();
                buyerSignElement.text("");
                // 선택된 요소에 태그를 추가합니다.
                buyerSignElement.append("<span>수정 완료</span>");
            }

            httpServletResponse.setStatus(HttpStatus.OK.value());
            return doc.html();
        } else {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return htmlString;
        }
    }
}