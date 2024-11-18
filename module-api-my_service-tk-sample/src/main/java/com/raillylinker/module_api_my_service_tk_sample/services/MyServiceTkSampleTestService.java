package com.raillylinker.module_api_my_service_tk_sample.services;

import com.raillylinker.module_api_my_service_tk_sample.controllers.MyServiceTkSampleTestController;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MyServiceTkSampleTestService {
    // (이메일 발송 테스트)
    void sendEmailTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.SendEmailTestInputVo inputVo
    ) throws Exception;


    ////
    // (HTML 이메일 발송 테스트)
    void sendHtmlEmailTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.SendHtmlEmailTestInputVo inputVo
    ) throws Exception;


    ////
    // (Naver API SMS 발송 샘플)
    void naverSmsSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.NaverSmsSampleInputVo inputVo
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException;


    ////
    // (Naver API AlimTalk 발송 샘플)
    void naverAlimTalkSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.NaverAlimTalkSampleInputVo inputVo
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException;


    ////
    // (액셀 파일을 받아서 해석 후 데이터 반환)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleTestController.ReadExcelFileSampleOutputVo readExcelFileSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.ReadExcelFileSampleInputVo inputVo
    ) throws Exception;


    ////
    // (액셀 파일 쓰기)
    void writeExcelFileSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException;


    ////
    // (HTML 을 기반으로 PDF 를 생성)
    @Nullable
    @org.jetbrains.annotations.Nullable
    ResponseEntity<Resource> htmlToPdfSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) throws IOException;


    ////
    // (입력받은 HTML 을 기반으로 PDF 를 생성 후 반환)
    @Nullable
    @org.jetbrains.annotations.Nullable
    ResponseEntity<Resource> multipartHtmlToPdfSample(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.MultipartHtmlToPdfSampleInputVo inputVo,
            @Nullable @org.jetbrains.annotations.Nullable
            String controllerBasicMapping
    ) throws IOException;


    ////
    // (by_product_files/uploads/fonts 폴더에서 파일 다운받기)
    @Nullable @org.jetbrains.annotations.Nullable
    ResponseEntity<Resource> downloadFontFile(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    );


    ////
    // (Kafka 토픽 메세지 발행 테스트)
    void sendKafkaTopicMessageTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.SendKafkaTopicMessageTestInputVo inputVo
    );


    ////
    // (ProcessBuilder 샘플)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleTestController.ProcessBuilderTestOutputVo processBuilderTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Nullable @org.jetbrains.annotations.Nullable
            String javaEnvironmentPath
    ) throws IOException, InterruptedException;


    ////
    // (입력받은 폰트 파일의 내부 이름을 반환)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleTestController.CheckFontFileInnerNameOutputVo checkFontFileInnerName(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleTestController.CheckFontFileInnerNameInputVo inputVo
    ) throws IOException;


    ////
    // (AES256 암호화 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleTestController.Aes256EncryptTestOutputVo aes256EncryptTest(
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
    );


    ////
    // (AES256 복호화 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    MyServiceTkSampleTestController.Aes256DecryptTestOutputVo aes256DecryptTest(
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
    );


    ////
    // (Jsoup 태그 조작 테스트)
    @Nullable
    @org.jetbrains.annotations.Nullable
    String jsoupTest(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean fix
    );
}