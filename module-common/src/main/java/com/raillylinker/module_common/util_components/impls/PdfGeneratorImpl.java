package com.raillylinker.module_common.util_components.impls;

import com.lowagie.text.Image;
import com.raillylinker.module_common.util_components.PdfGenerator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// [HTML String 을 기반으로 PDF 파일을 생성하는 유틸]
// https://flyingsaucerproject.github.io/flyingsaucer/r8/guide/users-guide-R8.html
// PDF 로 변환할 HTML 작성시 XHTML 1.0(strict), CSS 2.1 (@page 의 size 는 가능) 를 엄격히 지켜야 합니다.
@Component
public class PdfGeneratorImpl implements PdfGenerator {
    // (HTML String 을 PDF 로 변환)
    @Override
    public byte[] createPdfByteArrayFromHtmlString(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String htmlString, // PDF 로 변환할 HTML String (ex : <!DOCTYPE html> <html> ....)
            // 폰트 파일 맵 (키 : html 의 @font-face src 에 입력한 파일명, 값 : html 의 @font-face src 에 url('주소') 이런 형식으로 치환될 값)
            /*
                 map value ex : {"NanumGothicFile.ttf" : "http://127.0.0.1:8080/test.ttf"}
                 html ex :
                 @font-face {
                     font-family: NanumGothic;
                     src: "NanumGothicFile.ttf";
                     -fs-pdf-font-embed: embed;
                     -fs-pdf-font-encoding: Identity-H;
                 }
             */
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HashMap<String, String> resourceFontFileNameMap,
            // 이미지 파일 맵 (키 : html 의 img src 에 입력한 파일명, 값 : 로컬에 저장된 이미지 파일 full 경로)
            /*
                 map value ex : {"html_to_pdf_sample.jpg" : "C:\Dev\test.jpg"}
                 html ex :
                 <img src="html_to_pdf_sample.jpg" />
             */
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HashMap<String, String> savedImgFilePathMap
    ) {
        String newHtmlString = htmlString;

        // PDF 변환 객체
        ITextRenderer renderer = new ITextRenderer();

        for (var fontFilePathKv : resourceFontFileNameMap.entrySet()) {
            /*
                htmlString 에서,

                @font-face {
            		font-family: NanumGothic;
            		src: fileName.ttf;
            		-fs-pdf-font-embed: embed;
            		-fs-pdf-font-encoding: Identity-H;
            	}

                이것을

                @font-face {
            		font-family: NanumGothic;
            		src: url('resourceFontFileNameMap['fileName.ttf']');
            		-fs-pdf-font-embed: embed;
            		-fs-pdf-font-encoding: Identity-H;
            	}

                이렇게 합성하는 로직
             */
            String originalFontFileName = fontFilePathKv.getKey();
            String mappedFontFileName = fontFilePathKv.getValue();

            String patternStr = "@font-face\\s*\\{([^}]*src:\\s*)([^;]*);";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(newHtmlString);

            StringBuilder sb = new StringBuilder();
            while (matcher.find()) {
                String srcPrefix = matcher.group(1);
                String srcValue = matcher.group(2);
                String modifiedSrcValue = srcValue.replace("\"" + originalFontFileName + "\"", "url('" + mappedFontFileName + "')");
                matcher.appendReplacement(sb, "@font-face { " + srcPrefix + modifiedSrcValue + ";");
            }
            matcher.appendTail(sb);
            newHtmlString = sb.toString();
        }

        // HTML 내 img src 경로의 이미지 적용
        ReplacedElementFactory replacedElementFactory = renderer.getSharedContext().getReplacedElementFactory();
        renderer.getSharedContext().setReplacedElementFactory(new ReplacedElementFactory() {
            @Override
            public ReplacedElement createReplacedElement(
                    LayoutContext layoutContext,
                    BlockBox blockBox,
                    UserAgentCallback userAgentCallback,
                    int cssWidth,
                    int cssHeight
            ) {
                // HTML element 가 비어있으면 null 을 반환
                Element element = blockBox.getElement();
                if (element == null) {
                    return null;
                }

                String nodeName = element.getNodeName(); // HTML Tag 이름(ex : "img")
                String srcPath = element.getAttribute("src"); // HTML 태그의 src 속성에 적힌 값(ex : "html_to_pdf_sample.jpg")

                if ("img".equals(nodeName) && srcPath.startsWith("classpath:")) {
                    // img 태그에, <img src="classpath:/static/for_c6_n6_html_to_pdf_sample/html_to_pdf_sample.jpg"/> 처럼 내부 파일 참고시
                    try {
                        ITextFSImage fsImage = new ITextFSImage(Image.getInstance(
                                Files.readAllBytes(Path.of(new ClassPathResource(srcPath.replace("classpath:", "")).getURI()))
                        ));

                        // css의 높이, 너비가 설정되어있으면 적용
                        if (cssWidth != -1 || cssHeight != -1) {
                            fsImage.scale(cssWidth, cssHeight);
                        }

                        return new ITextImageElement(fsImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Error loading image from classpath", e);
                    }
                } else if ("img".equals(nodeName) && !srcPath.startsWith("http") && !srcPath.startsWith("classpath:")) {
                    // img 태그에,http 이미지, 혹은 classpath:/static/for_c6_n6_html_to_pdf_sample/html_to_pdf_sample.jpg 이렇게 서버 내 이미지 파일이 아닌 경우
                    // 여기선 html_to_pdf_sample.jpg 같이 파일명을 가정
                    String filePath = savedImgFilePathMap.get(srcPath);
                    if (filePath != null) {
                        ITextFSImage fsImage;
                        try {
                            fsImage = new ITextFSImage(
                                    Image.getInstance(Files.readAllBytes(Paths.get(filePath)))
                            );
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        // css의 높이, 너비가 설정되어있으면 적용
                        if (cssWidth != -1 || cssHeight != -1) {
                            fsImage.scale(cssWidth, cssHeight);
                        }

                        return new ITextImageElement(fsImage);
                    }
                }

                // 해당사항 없는 태그는 그대로 반환
                return replacedElementFactory.createReplacedElement(
                        layoutContext,
                        blockBox,
                        userAgentCallback,
                        cssWidth,
                        cssHeight
                );
            }

            @Override
            public void remove(Element e) {
                replacedElementFactory.remove(e);
            }

            @Override
            public void setFormSubmissionListener(FormSubmissionListener listener) {
                replacedElementFactory.setFormSubmissionListener(listener);
            }

            @Override
            public void reset() {
                replacedElementFactory.reset();
            }
        });

        renderer.setDocumentFromString(newHtmlString); // HTML String 세팅
        renderer.layout(); // PDF 데이터 생성

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        renderer.createPDF(byteArrayOutputStream); // PDF 파일 생성
        return byteArrayOutputStream.toByteArray();
    }
}
