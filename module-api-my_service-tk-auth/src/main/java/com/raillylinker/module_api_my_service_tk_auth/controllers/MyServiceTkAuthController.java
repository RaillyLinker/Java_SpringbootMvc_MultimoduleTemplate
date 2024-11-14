package com.raillylinker.module_api_my_service_tk_auth.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_auth.services.MyServiceTkAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// API 의 로그인/비로그인 시의 결과를 달리하고 싶으면, 차라리 API 를 2개로 나누는 것이 더 좋습니다.
// SpringSecurity 의 도움을 받지 않는다면 복잡한 인증 관련 코드를 적용하여 로그인 여부를 확인해야 하므로, API 코드가 지저분해지기 때문이죠.
@Tag(name = "/my-service/tk/auth APIs", description = "my-service token 인증/인가 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/auth")
public class MyServiceTkAuthController {
    public MyServiceTkAuthController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkAuthService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkAuthService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "비 로그인 접속 테스트",
            description = "비 로그인 접속 테스트용 API\n\n"
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
            path = {"/for-no-logged-in"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    @ResponseBody
    public String noLoggedInAccessTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.noLoggedInAccessTest(httpServletResponse);
    }


    ////
    @Operation(
            summary = "로그인 진입 테스트 <>",
            description = "로그인 되어 있어야 진입 가능\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/for-logged-in"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public String loggedInAccessTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.loggedInAccessTest(httpServletResponse, authorization);
    }


    ////
    @Operation(
            summary = "ADMIN 권한 진입 테스트 <'ADMIN'>",
            description = "ADMIN 권한이 있어야 진입 가능\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "인가되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/for-admin"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_ADMIN'))")
    @ResponseBody
    public String adminAccessTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.adminAccessTest(httpServletResponse, authorization);
    }


    ////
    @Operation(
            summary = "Developer 권한 진입 테스트 <'ADMIN' or 'Developer'>",
            description = "Developer 권한이 있어야 진입 가능\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "인가되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/for-developer"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE}
    )
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_DEVELOPER') or hasRole('ROLE_ADMIN'))")
    @ResponseBody
    public String developerAccessTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.developerAccessTest(httpServletResponse, authorization);
    }


    ////
    @Operation(
            summary = "특정 회원의 발행된 Access 토큰 만료 처리",
            description = "특정 회원의 발행된 Access 토큰 만료 처리를 하여 Reissue 로 재검증을 하도록 만듭니다.\n\n" +
                    "해당 회원의 권한 변경, 계정 정지 처리 등으로 인해 발행된 토큰을 회수해야 할 때 사용하세요.\n\n" +
                    "단순히 만료만 시키는 것이므로 치명적인 기능을 가지진 않았지만 비밀번호를 입력해야만 동작하도록 설계하였습니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 비밀키가 다릅니다.\n\n" +
                                                    "2 : 존재하지 않는 회원 고유번호입니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PatchMapping(
            path = {"/expire-access-token/{memberUid}"},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @ResponseBody
    public void doExpireAccessToken(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @PathVariable("memberUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long memberUid,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            DoExpireAccessTokenInputVo inputVo
    ) {
        service.doExpireAccessToken(httpServletResponse, memberUid, inputVo);
    }

    public record DoExpireAccessTokenInputVo(
            @Schema(
                    description = "API 비밀키",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "aadke234!@"
            )
            @JsonProperty("apiSecret")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String apiSecret
    ) {
    }


    ////
    @Operation(
            summary = "계정 비밀번호 로그인",
            description = "계정 아이디 + 비밀번호를 사용하는 로그인 요청\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 입력한 id 로 가입된 회원 정보가 없습니다.\n\n" +
                                                    "2 : 입력한 password 가 일치하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/login-with-password"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public LoginOutputVo loginWithPassword(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LoginWithPasswordInputVo inputVo
    ) {
        return service.loginWithPassword(httpServletResponse, inputVo);
    }

    public record LoginWithPasswordInputVo(
            @Schema(
                    description = "로그인 타입 (0 : 아이디, 1 : 이메일, 2 : 전화번호)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("loginTypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer loginTypeCode,

            @Schema(
                    description = "아이디 값 (0 : 홍길동, 1 : test@gmail.com, 2 : 82)000-0000-0000)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "test@gmail.com"
            )
            @JsonProperty("id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id,

            @Schema(
                    description = "사용할 비밀번호",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "kkdli!!"
            )
            @JsonProperty("password")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String password
    ) {
    }

    public record LoginOutputVo(
            @Schema(description = "로그인 성공 정보 (이 변수가 Null 이 아니라면 lockedOutputList 가 Null 입니다.)")
            @JsonProperty("loggedInOutput")
            LoggedInOutput loggedInOutput,

            @Schema(description = "계정 잠김 정보 리스트 (이 변수가 Null 이 아니라면 loggedInOutput 이 Null 입니다. 최신순 정렬)")
            @JsonProperty("lockedOutputList")
            List<LockedOutput> lockedOutputList
    ) {
        @Schema(description = "계정 잠김 정보")
        public record LockedOutput(
                @Schema(description = "멤버 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("memberUid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long memberUid,

                @Schema(
                        description = "계정 정지 시작 시간",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        example = "2024_05_02_T_15_14_49_552_KST"
                )
                @JsonProperty("lockStart")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String lockStart,

                @Schema(
                        description = "계정 정지 만료 시간 (이 시간이 지나기 전까지 계정 정지 상태, null 이라면 무기한 정지)",
                        required = false,
                        example = "2024_05_02_T_15_14_49_552_KST"
                )
                @JsonProperty("lockBefore")
                String lockBefore,

                @Schema(description = "계정 정지 이유 코드(0 : 기타, 1 : 휴면계정, 2 : 패널티)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("lockReasonCode")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer lockReasonCode,

                @Schema(
                        description = "계정 정지 이유 상세(시스템 악용 패널티, 1년 이상 미접속 휴면계정 등...)",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        example = "시스템 악용 패널티"
                )
                @JsonProperty("lockReason")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String lockReason
        ) {
        }

        @Schema(description = "로그인 성공 정보")
        public record LoggedInOutput(
                @Schema(description = "멤버 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("memberUid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long memberUid,

                @Schema(description = "인증 토큰 타입", requiredMode = Schema.RequiredMode.REQUIRED, example = "Bearer")
                @JsonProperty("tokenType")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String tokenType,

                @Schema(description = "엑세스 토큰", requiredMode = Schema.RequiredMode.REQUIRED, example = "kljlkjkfsdlwejoe")
                @JsonProperty("accessToken")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String accessToken,

                @Schema(description = "리프레시 토큰", requiredMode = Schema.RequiredMode.REQUIRED, example = "cxfdsfpweiijewkrlerw")
                @JsonProperty("refreshToken")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String refreshToken,

                @Schema(
                        description = "엑세스 토큰 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        example = "2024_05_02_T_15_14_49_552_KST"
                )
                @JsonProperty("accessTokenExpireWhen")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String accessTokenExpireWhen,

                @Schema(
                        description = "리프레시 토큰 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        example = "2024_05_02_T_15_14_49_552_KST"
                )
                @JsonProperty("refreshTokenExpireWhen")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String refreshTokenExpireWhen
        ) {
        }
    }


    ////
    @Operation(
            summary = "OAuth2 Code 로 OAuth2 AccessToken 발급",
            description = "OAuth2 Code 를 사용하여 얻은 OAuth2 AccessToken 발급\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 유효하지 않은 OAuth2 인증 정보입니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = {"/oauth2-access-token"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public GetOAuth2AccessTokenOutputVo getOAuth2AccessToken(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(
                    name = "oauth2TypeCode",
                    description = "OAuth2 종류 코드 (1 : GOOGLE, 2 : NAVER, 3 : KAKAO)",
                    example = "1"
            )
            @RequestParam("oauth2TypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,
            @Parameter(name = "oauth2Code", description = "OAuth2 인증으로 받은 OAuth2 Code", example = "asdfeqwer1234")
            @RequestParam("oauth2Code")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2Code
    ) {
        return service.getOAuth2AccessToken(httpServletResponse, oauth2TypeCode, oauth2Code);
    }

    public record GetOAuth2AccessTokenOutputVo(
            @Schema(
                    description = "Code 로 발급받은 SNS AccessToken Type",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "Bearer"
            )
            @JsonProperty("oAuth2AccessTokenType")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oAuth2AccessTokenType,

            @Schema(
                    description = "Code 로 발급받은 SNS AccessToken",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "abcd1234!@#$"
            )
            @JsonProperty("oAuth2AccessToken")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oAuth2AccessToken
    ) {
    }


    ////
    @Operation(
            summary = "OAuth2 로그인 (Access Token)",
            description = "OAuth2 Access Token 으로 로그인 요청\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 유효하지 않은 OAuth2 Access Token 입니다.\n\n" +
                                                    "2 : 가입 된 회원 정보가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/login-with-oauth2-access-token"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public LoginOutputVo loginWithOAuth2AccessToken(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LoginWithOAuth2AccessTokenInputVo inputVo
    ) throws IOException {
        return service.loginWithOAuth2AccessToken(httpServletResponse, inputVo);
    }

    public record LoginWithOAuth2AccessTokenInputVo(
            @Schema(
                    description = "OAuth2 종류 코드 (1 : GOOGLE, 2 : NAVER, 3 : KAKAO)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("oauth2TypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,

            @Schema(
                    description = "OAuth2 인증으로 받은 TokenType + AccessToken",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "Bearer asdfeqwer1234"
            )
            @JsonProperty("oauth2AccessToken")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2AccessToken
    ) {
    }


    ////
    @Operation(
            summary = "OAuth2 로그인 (ID Token)",
            description = "OAuth2 ID Token 으로 로그인 요청\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 유효하지 않은 OAuth2 Access Token 입니다.\n\n" +
                                                    "2 : 가입 된 회원 정보가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/login-with-oauth2-id-token"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public LoginOutputVo loginWithOAuth2IdToken(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LoginWithOAuth2IdTokenInputVo inputVo
    ) {
        return service.loginWithOAuth2IdToken(httpServletResponse, inputVo);
    }

    public record LoginWithOAuth2IdTokenInputVo(
            @Schema(
                    description = "OAuth2 종류 코드 (4 : Apple)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("oauth2TypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,

            @Schema(
                    description = "OAuth2 인증으로 받은 ID Token",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "asdfeqwer1234"
            )
            @JsonProperty("oauth2IdToken")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2IdToken
    ) {
    }


    ////
    @Operation(
            summary = "로그아웃 처리 <>",
            description = "로그아웃 처리\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @DeleteMapping(
            path = {"/logout"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @ResponseBody
    public void logout(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        service.logout(authorization, httpServletResponse);
    }


    ////
    @Operation(
            summary = "토큰 재발급 <>",
            description = "엑세스 토큰 및 리프레시 토큰 재발행\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 유효하지 않은 Refresh Token 입니다.\n\n" +
                                                    "2 : Refresh Token 이 만료되었습니다.\n\n" +
                                                    "3 : 올바르지 않은 Access Token 입니다.\n\n" +
                                                    "4 : 탈퇴된 회원입니다.\n\n" +
                                                    "5 : 로그아웃 처리된 Access Token 입니다.(갱신 불가)\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/reissue"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public LoginOutputVo reissueJwt(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ReissueJwtInputVo inputVo
    ) {
        return service.reissueJwt(authorization, inputVo, httpServletResponse);
    }

    public record ReissueJwtInputVo(
            @Schema(description = "리프레시 토큰 (토큰 타입을 앞에 붙이기)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Bearer 1sdfsadfsdafsdafsdafd")
            @JsonProperty("refreshToken")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String refreshToken
    ) {
    }


    ////
    @Operation(
            summary = "멤버의 현재 발행된 모든 토큰 비활성화 (= 모든 기기에서 로그아웃) <>",
            description = "멤버의 현재 발행된 모든 토큰을 비활성화 (= 모든 기기에서 로그아웃) 하는 API\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @DeleteMapping(
            path = {"/all-authorization-token"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void deleteAllJwtOfAMember(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        service.deleteAllJwtOfAMember(authorization, httpServletResponse);
    }


    ////
    @Operation(
            summary = "회원 정보 가져오기 <>",
            description = "회원 정보 반환\n\n바뀔 가능성이 없는 회원 정보는 로그인시 반환되며,\n\n이 API 에서는 변경 가능성이 있는 회원 정보를 반환합니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/member-info"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public GetMemberInfoOutputVo getMemberInfo(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.getMemberInfo(httpServletResponse, authorization);
    }

    public record GetMemberInfoOutputVo(
            @Schema(description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "hongGilDong")
            @JsonProperty("accountId")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accountId,

            @Schema(
                    description = "권한 리스트 (관리자 : ROLE_ADMIN, 개발자 : ROLE_DEVELOPER)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "[\"ROLE_ADMIN\", \"ROLE_DEVELOPER\"]"
            )
            @JsonProperty("roleList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> roleList,

            @Schema(description = "내가 등록한 OAuth2 정보 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myOAuth2List")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull OAuth2Info> myOAuth2List,

            @Schema(description = "내가 등록한 Profile 정보 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myProfileList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull ProfileInfo> myProfileList,

            @Schema(description = "내가 등록한 이메일 정보 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myEmailList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull EmailInfo> myEmailList,

            @Schema(description = "내가 등록한 전화번호 정보 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myPhoneNumberList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull PhoneNumberInfo> myPhoneNumberList,

            @Schema(
                    description = "계정 로그인 비밀번호 설정 Null 여부 (OAuth2 만으로 회원가입한 경우는 비밀번호가 없으므로 true)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "true"
            )
            @JsonProperty("authPasswordIsNull")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean authPasswordIsNull
    ) {
        @Schema(description = "OAuth2 정보")
        public record OAuth2Info(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(
                        description = "OAuth2 (1 : Google, 2 : Naver, 3 : Kakao, 4 : Apple)",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        example = "1"
                )
                @JsonProperty("oauth2TypeCode")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer oauth2TypeCode,

                @Schema(description = "oAuth2 고유값 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "asdf1234")
                @JsonProperty("oauth2Id")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String oauth2Id
        ) {
        }

        @Schema(description = "Profile 정보")
        public record ProfileInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "프로필 이미지 Full URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://profile-image.com/1.jpg")
                @JsonProperty("imageFullUrl")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String imageFullUrl,

                @Schema(description = "대표 프로필 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
                @JsonProperty("isFront")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Boolean isFront
        ) {
        }

        @Schema(description = "이메일 정보")
        public record EmailInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "이메일 주소", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
                @JsonProperty("emailAddress")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String emailAddress,

                @Schema(description = "대표 이메일 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
                @JsonProperty("isFront")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Boolean isFront
        ) {
        }

        @Schema(description = "전화번호 정보")
        public record PhoneNumberInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "전화번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)010-6222-6461")
                @JsonProperty("phoneNumber")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String phoneNumber,

                @Schema(description = "대표 전화번호 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
                @JsonProperty("isFront")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Boolean isFront
        ) {
        }
    }


    ////
    @Operation(
            summary = "아이디 중복 검사",
            description = "아이디 중복 여부 반환\n\n"
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
            path = {"/id-duplicate-check"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public CheckIdDuplicateOutputVo checkIdDuplicate(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "id", description = "중복 검사 아이디", example = "hongGilDong")
            @RequestParam("id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    ) {
        return service.checkIdDuplicate(httpServletResponse, id);
    }

    public record CheckIdDuplicateOutputVo(
            @Schema(description = "중복여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
            @JsonProperty("duplicated")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean duplicated
    ) {
    }


    ////
    @Operation(
            summary = "아이디 수정하기 <>",
            description = "아이디 수정하기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 동일한 아이디를 사용하는 회원이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PatchMapping(
            path = {"/my/profile/id"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void updateId(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "id", description = "아이디", example = "mrHong")
            @RequestParam("id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id
    ) {
        service.updateId(httpServletResponse, authorization, id);
    }


    ////
    @Operation(
            summary = "테스트 회원 회원가입",
            description = "테스트용으로, 입력받은 정보를 가지고 회원가입 처리\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : API 비밀키가 다릅니다.\n\n" +
                                                    "2 : 이미 동일한 아이디로 가입된 회원이 존재합니다.\n\n" +
                                                    "3 : 이미 동일한 이메일로 가입된 회원이 존재합니다.\n\n" +
                                                    "4 : 이미 동일한 전화번호로 가입된 회원이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/join-the-membership-for-test"},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @ResponseBody
    public void joinTheMembershipForTest(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JoinTheMembershipForTestInputVo inputVo
    ) {
        service.joinTheMembershipForTest(httpServletResponse, inputVo);
    }

    public record JoinTheMembershipForTestInputVo(
            @Schema(
                    description = "API 비밀키",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "aadke234!@"
            )
            @JsonProperty("apiSecret")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String apiSecret,

            @Schema(
                    description = "아이디 - 이메일",
                    example = "test@gmail.com"
            )
            @JsonProperty("email")
            String email,

            @Schema(
                    description = "아이디 - 전화번호(국가번호 + 전화번호)",
                    example = "82)010-0000-0000"
            )
            @JsonProperty("phoneNumber")
            String phoneNumber,

            @Schema(
                    description = "계정 아이디",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "hongGilDong"
            )
            @JsonProperty("id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id,

            @Schema(
                    description = "사용할 비밀번호",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "kkdli!!"
            )
            @JsonProperty("password")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String password,

            @Schema(description = "프로필 이미지 파일")
            @JsonProperty("profileImageFile")
            MultipartFile profileImageFile
    ) {
    }


    ////
    @Operation(
            summary = "이메일 회원가입 본인 인증 이메일 발송",
            description = "이메일 회원가입시 본인 이메일 확인 메일 발송\n\n" +
                    "발송 후 10분 후 만료됨\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 동일한 이메일을 사용하는 회원이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/join-the-membership-email-verification"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public SendEmailVerificationForJoinOutputVo sendEmailVerificationForJoin(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendEmailVerificationForJoinInputVo inputVo
    ) {
        return service.sendEmailVerificationForJoin(httpServletResponse, inputVo);
    }

    public record SendEmailVerificationForJoinInputVo(
            @Schema(description = "수신 이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
            @JsonProperty("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email
    ) {
    }

    public record SendEmailVerificationForJoinOutputVo(
            @Schema(
                    description = "검증 고유값",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(
                    description = "검증 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @JsonProperty("verificationExpireWhen")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationExpireWhen
    ) {
    }


    ////
    @Operation(
            summary = "이메일 회원가입 본인 확인 이메일에서 받은 코드 검증하기",
            description = "이메일 회원가입시 본인 이메일에 보내진 코드를 입력하여 일치 결과 확인\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이메일 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 이메일 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = {"/join-the-membership-email-verification-check"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void checkEmailVerificationForJoin(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "verificationUid", description = "검증 고유값", example = "1")
            @RequestParam("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Parameter(name = "email", description = "확인 이메일", example = "test@gmail.com")
            @RequestParam("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,
            @Parameter(name = "verificationCode", description = "확인 이메일에 전송된 코드", example = "123456")
            @RequestParam("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        service.checkEmailVerificationForJoin(httpServletResponse, verificationUid, email, verificationCode);
    }


    ////
    @Operation(
            summary = "이메일 회원가입",
            description = "이메일 회원가입 처리\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이메일 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 이메일 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n" +
                                                    "4 : 이미 동일한 이메일로 가입된 회원이 존재합니다.\n\n" +
                                                    "5 : 이미 동일한 아이디로 가입된 회원이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/join-the-membership-with-email"},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @ResponseBody
    public void joinTheMembershipWithEmail(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JoinTheMembershipWithEmailInputVo inputVo
    ) {
        service.joinTheMembershipWithEmail(httpServletResponse, inputVo);
    }

    public record JoinTheMembershipWithEmailInputVo(
            @Schema(
                    description = "아이디 - 이메일",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "test@gmail.com"
            )
            @JsonProperty("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,

            @Schema(
                    description = "검증 고유값",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(
                    description = "사용할 비밀번호",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "kkdli!!"
            )
            @JsonProperty("password")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String password,

            @Schema(
                    description = "계정 아이디",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "hongGilDong"
            )
            @JsonProperty("id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id,

            @Schema(
                    description = "이메일 검증에 사용한 코드",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "123456"
            )
            @JsonProperty("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode,

            @Schema(description = "프로필 이미지 파일")
            @JsonProperty("profileImageFile")
            MultipartFile profileImageFile
    ) {
    }


    ////
    @Operation(
            summary = "전화번호 회원가입 본인 인증 문자 발송",
            description = "전화번호 회원가입시 본인 전화번호 확인 문자 발송\n\n" +
                    "발송 후 10분 후 만료됩니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이미 동일한 전화번호로 가입된 회원이 존재합니다.\n\n" +
                                                    "2 : 설명2\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/join-the-membership-phone-number-verification"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public SendPhoneVerificationForJoinOutputVo sendPhoneVerificationForJoin(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendPhoneVerificationForJoinInputVo inputVo
    ) {
        return service.sendPhoneVerificationForJoin(httpServletResponse, inputVo);
    }

    public record SendPhoneVerificationForJoinInputVo(
            @Schema(description = "인증 문자 수신 전화번호(국가번호 + 전화번호)", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)010-0000-0000")
            @JsonProperty("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber
    ) {
    }

    public record SendPhoneVerificationForJoinOutputVo(
            @Schema(
                    description = "검증 고유값",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(
                    description = "검증 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @JsonProperty("verificationExpireWhen")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationExpireWhen
    ) {
    }


    ////
    @Operation(
            summary = "전화번호 회원가입 본인 확인 문자에서 받은 코드 검증하기",
            description = "전화번호 회원가입시 본인 전화번호에 보내진 코드를 입력하여 일치 결과 확인\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 전화번호 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 전화번호 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = {"/join-the-membership-phone-number-verification-check"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void checkPhoneVerificationForJoin(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "verificationUid", description = "검증 고유값", example = "1")
            @RequestParam("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Parameter(name = "phoneNumber", description = "인증 문자 수신 전화번호(국가번호 + 전화번호)", example = "82)010-0000-0000")
            @RequestParam("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Parameter(name = "verificationCode", description = "확인 문자에 전송된 코드", example = "123456")
            @RequestParam("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        service.checkPhoneVerificationForJoin(httpServletResponse, verificationUid, phoneNumber, verificationCode);
    }


    ////
    @Operation(
            summary = "전화번호 회원가입",
            description = "전화번호 회원가입 처리\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 전화번호 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 전화번호 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n" +
                                                    "4 : 이미 동일한 전화번호로 가입된 회원이 존재합니다.\n\n" +
                                                    "5 : 이미 동일한 아이디로 가입된 회원이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/join-the-membership-with-phone-number"},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @ResponseBody
    public void joinTheMembershipWithPhoneNumber(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JoinTheMembershipWithPhoneNumberInputVo inputVo
    ) {
        service.joinTheMembershipWithPhoneNumber(httpServletResponse, inputVo);
    }

    public record JoinTheMembershipWithPhoneNumberInputVo(
            @Schema(
                    description = "아이디 - 전화번호(국가번호 + 전화번호)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "82)010-0000-0000"
            )
            @JsonProperty("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,

            @Schema(
                    description = "검증 고유값",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(
                    description = "사용할 비밀번호",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "kkdli!!"
            )
            @JsonProperty("password")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String password,

            @Schema(
                    description = "계정 아이디",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "hongGilDong"
            )
            @JsonProperty("id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id,

            @Schema(
                    description = "문자 검증에 사용한 코드",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "123456"
            )
            @JsonProperty("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode,

            @Schema(description = "프로필 이미지 파일")
            @JsonProperty("profileImageFile")
            MultipartFile profileImageFile
    ) {
    }


    ////
    @Operation(
            summary = "OAuth2 AccessToken 으로 회원가입 검증",
            description = "OAuth2 AccessToken 으로 회원가입 검증\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : Oauth2 AccessToken 이 유효하지 않습니다.\n\n" +
                                                    "2 : 이미 동일한 Oauth2 AccessToken 으로 가입된 회원이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/join-the-membership-oauth2-access-token-verification"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public CheckOauth2AccessTokenVerificationForJoinOutputVo checkOauth2AccessTokenVerificationForJoin(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CheckOauth2AccessTokenVerificationForJoinInputVo inputVo
    ) {
        return service.checkOauth2AccessTokenVerificationForJoin(httpServletResponse, inputVo);
    }

    public record CheckOauth2AccessTokenVerificationForJoinInputVo(
            @Schema(
                    description = "OAuth2 종류 코드 (1 : GOOGLE, 2 : NAVER, 3 : KAKAO)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("oauth2TypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,

            @Schema(
                    description = "OAuth2 인증으로 받은 OAuth2 TokenType + AccessToken",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "Bearer asdfeqwer1234"
            )
            @JsonProperty("oauth2AccessToken")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2AccessToken
    ) {
    }

    public record CheckOauth2AccessTokenVerificationForJoinOutputVo(
            @Schema(
                    description = "검증 고유값",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(
                    description = "OAuth2 가입시 검증에 사용할 코드",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "123456"
            )
            @JsonProperty("oauth2VerificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2VerificationCode,

            @Schema(
                    description = "가입에 사용할 OAuth2 고유 아이디",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "abcd1234"
            )
            @JsonProperty("oauth2Id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2Id,

            @Schema(
                    description = "검증 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @JsonProperty("expireWhen")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String expireWhen
    ) {
    }


    ////
    @Operation(
            summary = "OAuth2 IdToken 으로 회원가입 검증",
            description = "OAuth2 IdToken 으로 회원가입 검증\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : OAuth2 IdToken 이 유효하지 않습니다.\n\n" +
                                                    "2 : 이미 동일한 OAuth2 IdToken 으로 가입된 회원이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/join-the-membership-oauth2-id-token-verification"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public CheckOauth2IdTokenVerificationForJoinOutputVo checkOauth2IdTokenVerificationForJoin(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CheckOauth2IdTokenVerificationForJoinInputVo inputVo
    ) {
        return service.checkOauth2IdTokenVerificationForJoin(httpServletResponse, inputVo);
    }

    public record CheckOauth2IdTokenVerificationForJoinInputVo(
            @Schema(
                    description = "OAuth2 종류 코드 (4 : Apple)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("oauth2TypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,

            @Schema(
                    description = "OAuth2 인증으로 받은 OAuth2 IdToken",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "asdfeqwer1234"
            )
            @JsonProperty("oauth2IdToken")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2IdToken
    ) {
    }

    public record CheckOauth2IdTokenVerificationForJoinOutputVo(
            @Schema(
                    description = "검증 고유값",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(
                    description = "OAuth2 가입시 검증에 사용할 코드",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "123456"
            )
            @JsonProperty("oauth2VerificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2VerificationCode,

            @Schema(
                    description = "가입에 사용할 OAuth2 고유 아이디",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "abcd1234"
            )
            @JsonProperty("oauth2Id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2Id,

            @Schema(
                    description = "검증 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "2024_05_02_T_15_14_49_552_KST"
            )
            @JsonProperty("expireWhen")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String expireWhen
    ) {
    }


    ////
    @Operation(
            summary = "OAuth2 회원가입",
            description = "OAuth2 회원가입 처리\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : OAuth2 검증 요청을 보낸적이 없습니다.\n\n" +
                                                    "2 : OAuth2 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n" +
                                                    "4 : 이미 동일한 OAuth2 정보로 가입된 회원이 존재합니다.\n\n" +
                                                    "5 : 이미 동일한 아이디로 가입된 회원이 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = {"/join-the-membership-with-oauth2"},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @ResponseBody
    public void joinTheMembershipWithOauth2(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JoinTheMembershipWithOauth2InputVo inputVo
    ) {
        service.joinTheMembershipWithOauth2(httpServletResponse, inputVo);
    }

    public record JoinTheMembershipWithOauth2InputVo(
            @Schema(
                    description = "검증 고유값",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(
                    description = "가입에 사용할 OAuth2 고유 아이디",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "abcd1234"
            )
            @JsonProperty("oauth2Id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2Id,

            @Schema(
                    description = "OAuth2 종류 코드 (1 : Google, 2 : Naver, 3 : Kakao, 4 : Apple)",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "1"
            )
            @JsonProperty("oauth2TypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,

            @Schema(
                    description = "계정 아이디",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "hongGilDong"
            )
            @JsonProperty("id")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String id,

            @Schema(
                    description = "oauth2Id 검증에 사용한 코드",
                    requiredMode = Schema.RequiredMode.REQUIRED,
                    example = "123456"
            )
            @JsonProperty("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode,

            @Schema(description = "프로필 이미지 파일")
            @JsonProperty("profileImageFile")
            MultipartFile profileImageFile
    ) {
    }


    ////
    @Operation(
            summary = "계정 비밀번호 변경 <>",
            description = "계정 비밀번호 변경\n\n" +
                    "변경 완료된 후, 기존 모든 인증/인가 토큰이 비활성화(로그아웃) 됩니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 기존 비밀번호가 일치하지 않습니다.\n\n" +
                                                    "2 : 비밀번호를 null 로 만들려고 할 때, 이외에 로그인할 수단이 없으므로 비밀번호 제거가 불가능합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PutMapping(
            path = {"/change-account-password"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void updateAccountPassword(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            UpdateAccountPasswordInputVo inputVo
    ) {
        service.updateAccountPassword(httpServletResponse, authorization, inputVo);
    }

    public record UpdateAccountPasswordInputVo(
            @Schema(
                    description = "기존 계정 로그인용 비밀번호(기존 비밀번호가 없다면 null)",
                    example = "kkdli!!"
            )
            @JsonProperty("oldPassword")
            String oldPassword,

            @Schema(
                    description = "새 계정 로그인용 비밀번호(비밀번호를 없애려면 null)",
                    example = "fddsd##"
            )
            @JsonProperty("newPassword")
            String newPassword
    ) {
    }


    ////
    @Operation(
            summary = "이메일 비밀번호 찾기 본인 인증 이메일 발송",
            description = "이메일 비밀번호 찾기 본인 이메일 확인 메일 발송\n\n" +
                    "발송 후 10분 후 만료됨\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 해당 이메일로 가입된 회원 정보가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/find-password-email-verification"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public SendEmailVerificationForFindPasswordOutputVo sendEmailVerificationForFindPassword(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendEmailVerificationForFindPasswordInputVo inputVo
    ) {
        return service.sendEmailVerificationForFindPassword(httpServletResponse, inputVo);
    }

    public record SendEmailVerificationForFindPasswordInputVo(
            @Schema(description = "수신 이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
            @JsonProperty("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email
    ) {
    }

    public record SendEmailVerificationForFindPasswordOutputVo(
            @Schema(description = "검증 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(description = "검증 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
            @JsonProperty("verificationExpireWhen")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationExpireWhen
    ) {
    }


    ////
    @Operation(
            summary = "이메일 비밀번호 찾기 본인 확인 이메일에서 받은 코드 검증하기",
            description = "이메일 비밀번호 찾기 시 본인 이메일에 보내진 코드를 입력하여 일치 결과 확인\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이메일 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 이메일 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = {"/find-password-email-verification-check"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void checkEmailVerificationForFindPassword(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "verificationUid", description = "검증 고유값", example = "1")
            @RequestParam("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Parameter(name = "email", description = "확인 이메일", example = "test@gmail.com")
            @RequestParam("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,
            @Parameter(name = "verificationCode", description = "확인 이메일에 전송된 코드", example = "123456")
            @RequestParam("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        service.checkEmailVerificationForFindPassword(httpServletResponse, verificationUid, email, verificationCode);
    }


    ////
    @Operation(
            summary = "이메일 비밀번호 찾기 완료",
            description = "계정 비밀번호를 랜덤 값으로 변경 후 인증한 이메일로 발송\n\n" +
                    "변경 완료된 후, 기존 모든 인증/인가 토큰이 비활성화(로그아웃) 됩니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이메일 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 이메일 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n" +
                                                    "4 : 해당 이메일로 가입한 회원 정보가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/find-password-with-email"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @ResponseBody
    public void findPasswordWithEmail(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            FindPasswordWithEmailInputVo inputVo
    ) {
        service.findPasswordWithEmail(httpServletResponse, inputVo);
    }

    public record FindPasswordWithEmailInputVo(
            @Schema(description = "비밀번호를 찾을 계정 이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
            @JsonProperty("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,

            @Schema(description = "검증 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(description = "이메일 검증에 사용한 코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
            @JsonProperty("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
    }


    ////
    @Operation(
            summary = "전화번호 비밀번호 찾기 본인 인증 문자 발송",
            description = "전화번호 비밀번호 찾기 본인 전화번호 확인 문자 발송\n\n발송 후 10분 후 만료됨\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 해당 전화번호로 가입된 회원 정보가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/find-password-phone-number-verification"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public SendPhoneVerificationForFindPasswordOutputVo sendPhoneVerificationForFindPassword(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendPhoneVerificationForFindPasswordInputVo inputVo
    ) {
        return service.sendPhoneVerificationForFindPassword(httpServletResponse, inputVo);
    }

    public record SendPhoneVerificationForFindPasswordInputVo(
            @Schema(description = "수신 전화번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)000-0000-0000")
            @JsonProperty("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber
    ) {
    }

    public record SendPhoneVerificationForFindPasswordOutputVo(
            @Schema(description = "검증 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(description = "검증 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
            @JsonProperty("verificationExpireWhen")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationExpireWhen
    ) {
    }


    ////
    @Operation(
            summary = "전화번호 비밀번호 찾기 본인 확인 문자에서 받은 코드 검증하기",
            description = "전화번호 비밀번호 찾기 시 본인 전와번호에 보내진 코드를 입력하여 일치 결과 확인\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 전화번호 검증 요청을 보낸적이 없습니다.\n\n" +
                                                    "2 : 전화번호 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @GetMapping(
            path = {"/find-password-phone-number-verification-check"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public void checkPhoneVerificationForFindPassword(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "verificationUid", description = "검증 고유값", example = "1")
            @RequestParam("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Parameter(name = "phoneNumber", description = "수신 전화번호", example = "82)000-0000-0000")
            @RequestParam("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Parameter(name = "verificationCode", description = "확인 이메일에 전송된 코드", example = "123456")
            @RequestParam("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        service.checkPhoneVerificationForFindPassword(httpServletResponse, verificationUid, phoneNumber, verificationCode);
    }


    ////
    @Operation(
            summary = "전화번호 비밀번호 찾기 완료",
            description = "계정 비밀번호를 랜덤 값으로 변경 후 인증한 전화번호로 발송\n\n변경 완료된 후, 기존 모든 인증/인가 토큰이 비활성화 됩니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 전화번호 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 전화번호 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n" +
                                                    "4 : 해당 전화번호로 가입된 회원 정보가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    )
            }
    )
    @PostMapping(
            path = {"/find-password-with-phone-number"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @ResponseBody
    public void findPasswordWithPhoneNumber(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            FindPasswordWithPhoneNumberInputVo inputVo
    ) {
        service.findPasswordWithPhoneNumber(httpServletResponse, inputVo);
    }

    public record FindPasswordWithPhoneNumberInputVo(
            @Schema(description = "비밀번호를 찾을 계정 전화번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)000-0000-0000")
            @JsonProperty("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,

            @Schema(description = "검증 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(description = "검증에 사용한 코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
            @JsonProperty("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
    }


    ////
    @Operation(
            summary = "내 이메일 리스트 가져오기 <>",
            description = "내 이메일 리스트 가져오기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/my-email-addresses"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public GetMyEmailListOutputVo getMyEmailList(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.getMyEmailList(httpServletResponse, authorization);
    }

    public record GetMyEmailListOutputVo(
            @Schema(description = "내가 등록한 이메일 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("emailInfoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull EmailInfo> emailInfoList
    ) {

        @Schema(description = "이메일 정보")
        public record EmailInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "이메일 주소", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
                @JsonProperty("emailAddress")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String emailAddress,

                @Schema(description = "대표 이메일 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
                @JsonProperty("isFront")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Boolean isFront
        ) {
        }
    }

    ////
    @Operation(
            summary = "내 전화번호 리스트 가져오기 <>",
            description = "내 전화번호 리스트 가져오기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/my-phone-numbers"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public GetMyPhoneNumberListOutputVo getMyPhoneNumberList(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.getMyPhoneNumberList(httpServletResponse, authorization);
    }

    public record GetMyPhoneNumberListOutputVo(
            @Schema(description = "내가 등록한 전화번호 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("phoneInfoList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull PhoneInfo> phoneInfoList
    ) {

        @Schema(description = "전화번호 정보")
        public record PhoneInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "전화번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)010-6222-6461")
                @JsonProperty("phoneNumber")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String phoneNumber,

                @Schema(description = "대표 전화번호 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
                @JsonProperty("isFront")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Boolean isFront
        ) {
        }
    }


    ////
    @Operation(
            summary = "내 OAuth2 로그인 리스트 가져오기 <>",
            description = "내 OAuth2 로그인 리스트 가져오기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/my-oauth2-list"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public GetMyOauth2ListOutputVo getMyOauth2List(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.getMyOauth2List(httpServletResponse, authorization);
    }

    public record GetMyOauth2ListOutputVo(
            @Schema(description = "내가 등록한 OAuth2 정보 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myOAuth2List")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull OAuth2Info> myOAuth2List
    ) {

        @Schema(description = "OAuth2 정보")
        public record OAuth2Info(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(
                        description = "OAuth2 (1 : Google, 2 : Naver, 3 : Kakao, 4 : Apple)",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        example = "1"
                )
                @JsonProperty("oauth2Type")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Integer oauth2Type,

                @Schema(description = "oAuth2 고유값 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "asdf1234")
                @JsonProperty("oauth2Id")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String oauth2Id
        ) {
        }
    }


    ////
    @Operation(
            summary = "이메일 추가하기 본인 인증 이메일 발송 <>",
            description = "이메일 추가하기 본인 이메일 확인 메일 발송\n\n" +
                    "발송 후 10분 후 만료됨\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이미 사용중인 이메일입니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = {"/add-email-verification"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public SendEmailVerificationForAddNewEmailOutputVo sendEmailVerificationForAddNewEmail(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendEmailVerificationForAddNewEmailInputVo inputVo
    ) {
        return service.sendEmailVerificationForAddNewEmail(httpServletResponse, inputVo, authorization);
    }

    public record SendEmailVerificationForAddNewEmailInputVo(
            @Schema(description = "수신 이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
            @JsonProperty("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email
    ) {
    }

    public record SendEmailVerificationForAddNewEmailOutputVo(
            @Schema(description = "검증 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(description = "검증 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
            @JsonProperty("verificationExpireWhen")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationExpireWhen
    ) {
    }


    ////
    @Operation(
            summary = "이메일 추가하기 본인 확인 이메일에서 받은 코드 검증하기 <>",
            description = "이메일 추가하기 본인 이메일에 보내진 코드를 입력하여 일치 결과 확인\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이메일 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 이메일 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/add-email-verification-check"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void checkEmailVerificationForAddNewEmail(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "verificationUid", description = "검증 고유값", example = "1")
            @RequestParam("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Parameter(name = "email", description = "확인 이메일", example = "test@gmail.com")
            @RequestParam("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,
            @Parameter(name = "verificationCode", description = "확인 이메일에 전송된 코드", example = "123456")
            @RequestParam("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        service.checkEmailVerificationForAddNewEmail(httpServletResponse, verificationUid, email, verificationCode, authorization);
    }


    ////
    @Operation(
            summary = "이메일 추가하기 <>",
            description = "내 계정에 이메일 추가\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이메일 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 이메일 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n" +
                                                    "4 : 해당 이메일로 가입된 회원 정보가 존재합니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = {"/my-new-email"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public AddNewEmailOutputVo addNewEmail(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AddNewEmailInputVo inputVo
    ) {
        return service.addNewEmail(httpServletResponse, inputVo, authorization);
    }

    public record AddNewEmailInputVo(
            @Schema(description = "추가할 이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
            @JsonProperty("email")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String email,

            @Schema(description = "검증 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(description = "이메일 검증에 사용한 코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
            @JsonProperty("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode,

            @Schema(description = "대표 이메일 설정 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("frontEmail")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean frontEmail
    ) {
    }

    public record AddNewEmailOutputVo(
            @Schema(description = "이메일의 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("emailUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long emailUid
    ) {
    }


    ////
    @Operation(
            summary = "내 이메일 제거하기 <>",
            description = "내 계정에서 이메일 제거\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : emailUid 의 정보가 존재하지 않습니다.\n\n" +
                                                    "2 : 제거할 수 없습니다. (이외에 로그인할 방법이 없습니다.)\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @DeleteMapping(
            path = {"/my-email/{emailUid}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void deleteMyEmail(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "emailUid", description = "이메일의 고유값", example = "1")
            @PathVariable("emailUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long emailUid
    ) {
        service.deleteMyEmail(httpServletResponse, emailUid, authorization);
    }


    ////
    @Operation(
            summary = "전화번호 추가하기 본인 인증 문자 발송 <>",
            description = "전화번호 추가하기 본인 전화번호 확인 문자 발송\n\n" +
                    "발송 후 10분 후 만료됨\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이미 사용중인 전화번호입니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = {"/add-phone-number-verification"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public SendPhoneVerificationForAddNewPhoneNumberOutputVo sendPhoneVerificationForAddNewPhoneNumber(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            SendPhoneVerificationForAddNewPhoneNumberInputVo inputVo
    ) {
        return service.sendPhoneVerificationForAddNewPhoneNumber(httpServletResponse, inputVo, authorization);
    }

    public record SendPhoneVerificationForAddNewPhoneNumberInputVo(
            @Schema(description = "수신 전화번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)000-0000-0000")
            @JsonProperty("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber
    ) {
    }

    public record SendPhoneVerificationForAddNewPhoneNumberOutputVo(
            @Schema(description = "검증 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(description = "검증 만료 시간(yyyy_MM_dd_'T'_HH_mm_ss_SSS_z)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024_05_02_T_15_14_49_552_KST")
            @JsonProperty("verificationExpireWhen")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationExpireWhen
    ) {
    }


    ////
    @Operation(
            summary = "전화번호 추가하기 본인 확인 문자에서 받은 코드 검증하기 <>",
            description = "전화번호 추가하기 본인 전화번호에 보내진 코드를 입력하여 일치 결과 확인\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 전화번호 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 전화번호 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/add-phone-number-verification-check"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void checkPhoneVerificationForAddNewPhoneNumber(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "verificationUid", description = "검증 고유값", example = "1")
            @RequestParam("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,
            @Parameter(name = "phoneNumber", description = "수신 전화번호", example = "82)000-0000-0000")
            @RequestParam("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,
            @Parameter(name = "verificationCode", description = "확인 문자에 전송된 코드", example = "123456")
            @RequestParam("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode
    ) {
        service.checkPhoneVerificationForAddNewPhoneNumber(httpServletResponse, verificationUid, phoneNumber, verificationCode, authorization);
    }


    ////
    @Operation(
            summary = "전화번호 추가하기 <>",
            description = "내 계정에 전화번호 추가\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 이메일 검증 요청을 보낸 적이 없습니다.\n\n" +
                                                    "2 : 이메일 검증 요청이 만료되었습니다.\n\n" +
                                                    "3 : verificationCode 가 일치하지 않습니다.\n\n" +
                                                    "4 : 이미 사용중인 전화번호입니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = {"/my-new-phone-number"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public AddNewPhoneNumberOutputVo addNewPhoneNumber(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AddNewPhoneNumberInputVo inputVo
    ) {
        return service.addNewPhoneNumber(httpServletResponse, inputVo, authorization);
    }

    public record AddNewPhoneNumberInputVo(
            @Schema(description = "추가할 전화번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)000-0000-0000")
            @JsonProperty("phoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String phoneNumber,

            @Schema(description = "검증 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("verificationUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long verificationUid,

            @Schema(description = "문자 검증에 사용한 코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
            @JsonProperty("verificationCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String verificationCode,

            @Schema(description = "대표 전화번호 설정 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @JsonProperty("frontPhoneNumber")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean frontPhoneNumber
    ) {
    }

    public record AddNewPhoneNumberOutputVo(
            @Schema(description = "전화번호의 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("phoneUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long phoneUid
    ) {
    }


    ////
    @Operation(
            summary = "내 전화번호 제거하기 <>",
            description = "내 계정에서 전화번호 제거\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : phoneUid 의 정보가 없습니다.\n\n" +
                                                    "2 : 제거할 수 없습니다. (이외에 로그인할 방법이 없습니다.)\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @DeleteMapping(
            path = {"/my-phone-number/{phoneUid}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void deleteMyPhoneNumber(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "phoneUid", description = "전화번호의 고유값", example = "1")
            @PathVariable("phoneUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long phoneUid
    ) {
        service.deleteMyPhoneNumber(httpServletResponse, phoneUid, authorization);
    }


    ////
    @Operation(
            summary = "OAuth2 추가하기 (Access Token) <>",
            description = "내 계정에 OAuth2 Access Token 으로 인증 추가\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : OAuth2 AccessToken 이 유효하지 않습니다.\n\n" +
                                                    "2 : 이미 사용중인 OAuth2 인증 정보입니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = {"/my-new-oauth2-token"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void addNewOauth2WithAccessToken(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AddNewOauth2WithAccessTokenInputVo inputVo
    ) {
        service.addNewOauth2WithAccessToken(httpServletResponse, inputVo, authorization);
    }

    public record AddNewOauth2WithAccessTokenInputVo(
            @Schema(description = "OAuth2 종류 코드 (1 : GOOGLE, 2 : NAVER, 3 : KAKAO)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("oauth2TypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,

            @Schema(description = "OAuth2 인증으로 받은 oauth2 TokenType + AccessToken", requiredMode = Schema.RequiredMode.REQUIRED, example = "Bearer asdfeqwer1234")
            @JsonProperty("oauth2AccessToken")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2AccessToken
    ) {
    }


    ////
    @Operation(
            summary = "OAuth2 추가하기 (Id Token) <>",
            description = "내 계정에 OAuth2 Id Token 으로 인증 추가\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : OAuth2 Id Token 이 유효하지 않습니다.\n\n" +
                                                    "2 : 이미 사용중인 OAuth2 인증 정보입니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = {"/my-new-oauth2-id-token"},
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void addNewOauth2WithIdToken(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AddNewOauth2WithIdTokenInputVo inputVo
    ) {
        service.addNewOauth2WithIdToken(httpServletResponse, inputVo, authorization);
    }

    public record AddNewOauth2WithIdTokenInputVo(
            @Schema(description = "OAuth2 종류 코드 (4 : Apple)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("oauth2TypeCode")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Integer oauth2TypeCode,

            @Schema(description = "OAuth2 인증으로 받은 oauth2 IdToken", requiredMode = Schema.RequiredMode.REQUIRED, example = "asdfeqwer1234")
            @JsonProperty("oauth2IdToken")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String oauth2IdToken
    ) {
    }


    ////
    @Operation(
            summary = "내 OAuth2 제거하기 <>",
            description = "내 계정에서 OAuth2 제거\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : emailUid 의 정보가 존재하지 않습니다.\n\n" +
                                                    "2 : 제거할 수 없습니다. (이외에 로그인할 방법이 없습니다.)\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @DeleteMapping(
            path = {"/my-oauth2/{oAuth2Uid}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void deleteMyOauth2(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "oAuth2Uid", description = "OAuth2 고유값", example = "1")
            @PathVariable("oAuth2Uid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long oAuth2Uid
    ) {
        service.deleteMyOauth2(httpServletResponse, oAuth2Uid, authorization);
    }


    ////
    @Operation(
            summary = "회원탈퇴 <>",
            description = "회원탈퇴 요청\n\n탈퇴 완료 후 모든 토큰이 비활성화 됩니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @DeleteMapping(
            path = {"/withdrawal"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void withdrawalMembership(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        service.withdrawalMembership(httpServletResponse, authorization);
    }


    ////
    @Operation(
            summary = "내 Profile 이미지 정보 리스트 가져오기 <>",
            description = "내 Profile 이미지 정보 리스트 가져오기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/my-profile-list"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public GetMyProfileListOutputVo getMyProfileList(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.getMyProfileList(httpServletResponse, authorization);
    }

    public record GetMyProfileListOutputVo(
            @Schema(description = "내가 등록한 Profile 이미지 정보 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myProfileList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull ProfileInfo> myProfileList
    ) {
        @Schema(description = "Profile 정보")
        public record ProfileInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "프로필 이미지 Full URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://profile-image.com/1.jpg")
                @JsonProperty("imageFullUrl")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String imageFullUrl,

                @Schema(description = "대표 프로필 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
                @JsonProperty("isFront")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Boolean isFront
        ) {
        }
    }


    ////
    @Operation(
            summary = "내 대표 Profile 이미지 정보 가져오기 <>",
            description = "내 대표 Profile 이미지 정보 가져오기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/my-front-profile"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public GetMyFrontProfileOutputVo getMyFrontProfile(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.getMyFrontProfile(httpServletResponse, authorization);
    }

    public record GetMyFrontProfileOutputVo(
            @Schema(description = "내 대표 Profile 이미지 정보", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myFrontProfileInfo")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ProfileInfo myFrontProfileInfo
    ) {
        @Schema(description = "Profile 정보")
        public record ProfileInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "프로필 이미지 Full URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://profile-image.com/1.jpg")
                @JsonProperty("imageFullUrl")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String imageFullUrl
        ) {
        }
    }


    ////
    @Operation(
            summary = "내 대표 프로필 설정하기 <>",
            description = "내가 등록한 프로필들 중 대표 프로필 설정하기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : profileUid 가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PatchMapping(
            path = {"/my-front-profile"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void setMyFrontProfile(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "profileUid", description = "대표 프로필로 설정할 프로필의 고유값(null 이라면 대표 프로필 해제)", example = "1")
            @RequestParam("profileUid")
            Long profileUid
    ) {
        service.setMyFrontProfile(httpServletResponse, authorization, profileUid);
    }


    ////
    @Operation(
            summary = "내 프로필 삭제 <>",
            description = "내가 등록한 프로필들 중 하나를 삭제합니다.\n\n" +
                    "대표 프로필을 삭제했다면, 대표 프로필 설정이 Null 로 변경됩니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : profileUid 의 정보가 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @DeleteMapping(
            path = {"/my-profile/{profileUid}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void deleteMyProfile(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "profileUid", description = "프로필의 고유값", example = "1")
            @PathVariable("profileUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long profileUid
    ) {
        service.deleteMyProfile(authorization, httpServletResponse, profileUid);
    }


    ////
    @Operation(
            summary = "내 프로필 이미지 추가 <>",
            description = "내 프로필 이미지 추가\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PostMapping(
            path = {"/my-profile"},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public AddNewProfileOutputVo addNewProfile(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @ModelAttribute
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            AddNewProfileInputVo inputVo
    ) {
        return service.addNewProfile(httpServletResponse, authorization, inputVo);
    }

    public record AddNewProfileInputVo(
            @Schema(description = "프로필 이미지 파일", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("profileImageFile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MultipartFile profileImageFile,

            @Schema(description = "대표 이미지로 설정할 것인지 여부", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("frontProfile")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Boolean frontProfile
    ) {
    }

    public record AddNewProfileOutputVo(
            @Schema(description = "프로필의 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("profileUid")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long profileUid,

            @Schema(description = "업로드한 프로필 이미지 파일 Full URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
            @JsonProperty("profileImageFullUrl")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String profileImageFullUrl
    ) {
    }


    ////
    @Operation(
            summary = "by_product_files/member/profile 폴더에서 파일 다운받기",
            description = "프로필 이미지를 by_product_files/member/profile 위치에 저장했을 때 파일을 가져오기 위한 API 로,\n\n" +
                    "AWS 나 다른 Storage 서비스를 사용해도 좋습니다.\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 파일이 존재하지 않습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/member-profile/{fileName}"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE}
    )
    @ResponseBody
    public ResponseEntity<Resource> downloadProfileFile(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "fileName", description = "by_product_files/member/profile 폴더 안의 파일명", example = "test.jpg")
            @PathVariable("fileName")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String fileName
    ) {
        return service.downloadProfileFile(httpServletResponse, fileName);
    }


    ////
    @Operation(
            summary = "내 대표 이메일 정보 가져오기 <>",
            description = "내 대표 이메일 정보 가져오기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/my-front-email"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public GetMyFrontEmailOutputVo getMyFrontEmail(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.getMyFrontEmail(httpServletResponse, authorization);
    }

    public record GetMyFrontEmailOutputVo(
            @Schema(description = "내 대표 이메일 정보", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myFrontEmailInfo")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            EmailInfo myFrontEmailInfo
    ) {
        @Schema(description = "이메일 정보")
        public record EmailInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "이메일 주소", requiredMode = Schema.RequiredMode.REQUIRED, example = "test@gmail.com")
                @JsonProperty("emailAddress")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String emailAddress
        ) {
        }
    }


    ////
    @Operation(
            summary = "내 대표 이메일 설정하기 <>",
            description = "내가 등록한 이메일들 중 대표 이메일 설정하기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 선택한 emailUid 가 없습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PatchMapping(
            path = {"/my-front-email"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void setMyFrontEmail(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "emailUid", description = "대표 이메일로 설정할 이메일의 고유값(null 이라면 대표 이메일 해제)", example = "1")
            @RequestParam("emailUid")
            Long emailUid
    ) {
        service.setMyFrontEmail(httpServletResponse, authorization, emailUid);
    }


    ////
    @Operation(
            summary = "내 대표 전화번호 정보 가져오기 <>",
            description = "내 대표 전화번호 정보 가져오기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @GetMapping(
            path = {"/my-front-phone-number"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public GetMyFrontPhoneNumberOutputVo getMyFrontPhoneNumber(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization
    ) {
        return service.getMyFrontPhoneNumber(httpServletResponse, authorization);
    }

    public record GetMyFrontPhoneNumberOutputVo(
            @Schema(description = "내 대표 전화번호 정보", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("myFrontPhoneNumberInfo")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PhoneNumberInfo myFrontPhoneNumberInfo
    ) {
        @Schema(description = "전화번호 정보")
        public record PhoneNumberInfo(
                @Schema(description = "행 고유값", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "전화번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "82)010-6222-6461")
                @JsonProperty("phoneNumber")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String phoneNumber
        ) {
        }
    }


    ////
    @Operation(
            summary = "내 대표 전화번호 설정하기 <>",
            description = "내가 등록한 전화번호들 중 대표 전화번호 설정하기\n\n"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "정상 동작"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = {@Content},
                            description = "Response Body 가 없습니다.\n\n" +
                                    "Response Headers 를 확인하세요.",
                            headers = {
                                    @Header(
                                            name = "api-result-code",
                                            description = "(Response Code 반환 원인) - Required\n\n" +
                                                    "1 : 선택한 phoneNumberUid 가 없습니다.\n\n",
                                            schema = @Schema(type = "string")
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증되지 않은 접근입니다.",
                            content = {@Content}
                    )
            }
    )
    @PatchMapping(
            path = {"/my-front-phone-number"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.ALL_VALUE}
    )
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void setMyFrontPhoneNumber(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(hidden = true)
            @RequestHeader("Authorization")
            String authorization,
            @Parameter(name = "phoneNumberUid", description = "대표 전화번호로 설정할 전화번호의 고유값(null 이라면 대표 전화번호 해제)", example = "1")
            @RequestParam("phoneNumberUid")
            Long phoneNumberUid
    ) {
        service.setMyFrontPhoneNumber(httpServletResponse, authorization, phoneNumberUid);
    }


    ////
    @Operation(
            summary = "Redis Key-Value 모두 조회 테스트",
            description = "Redis1_Service1ForceExpireAuthorizationSet 에 저장된 모든 Key-Value 를 조회합니다.\n\n"
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
            path = {"/service1-force-expire-authorization-set"},
            consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public SelectAllRedisKeyValueSampleOutputVo selectAllRedisKeyValueSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        return service.selectAllRedisKeyValueSample(httpServletResponse);
    }

    public record SelectAllRedisKeyValueSampleOutputVo(
            @Schema(description = "Key-Value 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("keyValueList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull KeyValueVo> keyValueList
    ) {
        @Schema(description = "Key-Value 객체")
        public record KeyValueVo(
                @Schema(description = "Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "testing")
                @JsonProperty("key")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String key,

                @Schema(description = "데이터 만료시간(밀리 초, -1 이라면 무한정)", requiredMode = Schema.RequiredMode.REQUIRED, example = "12000")
                @JsonProperty("expirationMs")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long expirationMs
        ) {
        }
    }
}
