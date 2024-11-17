package com.raillylinker.module_api_my_service_tk_sample.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raillylinker.module_api_my_service_tk_sample.services.MyServiceTkSampleMapCoordinateCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Google 맵과 비교하여 정상 동작 테스트를 하면 됩니다.
@Tag(name = "/my-service/tk/sample/map-coordinate-calculation APIs", description = "지도/좌표 계산에 대한 API 컨트롤러")
@Controller
@RequestMapping("/my-service/tk/sample/map-coordinate-calculation")
public class MyServiceTkSampleMapCoordinateCalculationController {
    public MyServiceTkSampleMapCoordinateCalculationController(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            MyServiceTkSampleMapCoordinateCalculationService service
    ) {
        this.service = service;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final MyServiceTkSampleMapCoordinateCalculationService service;


    // ---------------------------------------------------------------------------------------------
    // <매핑 함수 공간>
    @Operation(
            summary = "테스트용 기본 좌표 리스트를 DB에 저장",
            description = "DB 내에 기존 좌표 데이터들을 모두 날려버리고, 테스트용 기본 좌표 리스트를 DB에 저장합니다.\n\n"
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
            path = "/default-coordinate",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void insertDefaultCoordinateDataToDatabase(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.insertDefaultCoordinateDataToDatabase(httpServletResponse);
    }


    ////
    @Operation(
            summary = "두 좌표 사이의 거리를 반환(하버사인 공식)",
            description = "하버사인 공식을 사용하여 두 좌표 사이의 거리를 meter 단위로 반환하는 API\n\n"
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
            path = "/distance-meter-between-two-coordinate-harversine",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public GetDistanceMeterBetweenTwoCoordinateOutputVo getDistanceMeterBetweenTwoCoordinate(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "latitude1", description = "위도1", example = "37.675683")
            @RequestParam("latitude1")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude1,
            @Parameter(name = "longitude1", description = "경도1", example = "126.761259")
            @RequestParam("longitude1")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude1,
            @Parameter(name = "latitude2", description = "위도2", example = "37.676563")
            @RequestParam("latitude2")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude2,
            @Parameter(name = "longitude2", description = "경도2", example = "126.764777")
            @RequestParam("longitude2")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude2
    ) {
        return service.getDistanceMeterBetweenTwoCoordinate(httpServletResponse, latitude1, longitude1, latitude2, longitude2);
    }

    public record GetDistanceMeterBetweenTwoCoordinateOutputVo(
            @Schema(description = "좌표간 거리 (Meter)", requiredMode = Schema.RequiredMode.REQUIRED, example = "325.42")
            @JsonProperty("distanceMeter")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double distanceMeter
    ) {
    }


    ////
    @Operation(
            summary = "두 좌표 사이의 거리를 반환(Vincenty 공식)",
            description = "Vincenty 공식을 사용하여 두 좌표 사이의 거리를 meter 단위로 반환하는 API\n\n"
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
            path = "/distance-meter-between-two-coordinate-vincenty",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public GetDistanceMeterBetweenTwoCoordinateVincentyOutputVo getDistanceMeterBetweenTwoCoordinateVincenty(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "latitude1", description = "위도1", example = "37.675683")
            @RequestParam("latitude1")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude1,
            @Parameter(name = "longitude1", description = "경도1", example = "126.761259")
            @RequestParam("longitude1")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude1,
            @Parameter(name = "latitude2", description = "위도2", example = "37.676563")
            @RequestParam("latitude2")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude2,
            @Parameter(name = "longitude2", description = "경도2", example = "126.764777")
            @RequestParam("longitude2")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude2
    ) {
        return service.getDistanceMeterBetweenTwoCoordinateVincenty(httpServletResponse, latitude1, longitude1, latitude2, longitude2);
    }

    public record GetDistanceMeterBetweenTwoCoordinateVincentyOutputVo(
            @Schema(description = "좌표간 거리 (Meter)", requiredMode = Schema.RequiredMode.REQUIRED, example = "325.42")
            @JsonProperty("distanceMeter")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double distanceMeter
    ) {
    }


    ////
    @Operation(
            summary = "좌표들 사이의 중심 좌표를 반환",
            description = "함수를 사용하여 좌표들 사이의 중심 좌표를 반환하는 API\n\n"
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
            path = "/for-center-coordinate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public ReturnCenterCoordinateOutputVo returnCenterCoordinate(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            ReturnCenterCoordinateInputVo inputVo
    ) {
        return service.returnCenterCoordinate(httpServletResponse, inputVo);
    }

    public record ReturnCenterCoordinateInputVo(
            @Schema(description = "좌표 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("coordinateList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull Coordinate> coordinateList
    ) {
        public record Coordinate(
                @Schema(description = "중심좌표 위도", requiredMode = Schema.RequiredMode.REQUIRED, example = "37.676563")
                @JsonProperty("centerLatitude")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double centerLatitude,
                @Schema(description = "중심좌표 경도", requiredMode = Schema.RequiredMode.REQUIRED, example = "126.761259")
                @JsonProperty("centerLongitude")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double centerLongitude
        ) {
        }
    }

    public record ReturnCenterCoordinateOutputVo(
            @Schema(description = "중심좌표 위도", requiredMode = Schema.RequiredMode.REQUIRED, example = "37.676563")
            @JsonProperty("centerLatitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double centerLatitude,
            @Schema(description = "중심좌표 경도", requiredMode = Schema.RequiredMode.REQUIRED, example = "126.761259")
            @JsonProperty("centerLongitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double centerLongitude
    ) {
    }


    ////
    @Operation(
            summary = "DB 의 좌표 테이블에 좌표 정보를 저장",
            description = "DB 의 좌표 테이블에 좌표 정보를 저장하는 API\n\n"
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
            path = "/test-map/coordinate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public InsertCoordinateDataToDatabaseOutputVo insertCoordinateDataToDatabase(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @RequestBody
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            InsertCoordinateDataToDatabaseInputVo inputVo
    ) {
        return service.insertCoordinateDataToDatabase(httpServletResponse, inputVo);
    }

    public record InsertCoordinateDataToDatabaseInputVo(
            @Schema(description = "좌표 위도", requiredMode = Schema.RequiredMode.REQUIRED, example = "37.676563")
            @JsonProperty("latitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double latitude,

            @Schema(description = "좌표 경도", requiredMode = Schema.RequiredMode.REQUIRED, example = "126.761259")
            @JsonProperty("longitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double longitude
    ) {
    }

    public record InsertCoordinateDataToDatabaseOutputVo(
            @Schema(description = "DB 좌표 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("coordinateList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull Coordinate> coordinateList,

            @Schema(description = "좌표 리스트들의 중심좌표", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("centerCoordinate")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Coordinate centerCoordinate
    ) {
        public record Coordinate(
                @Schema(description = "중심좌표 위도", requiredMode = Schema.RequiredMode.REQUIRED, example = "37.676563")
                @JsonProperty("centerLatitude")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double centerLatitude,

                @Schema(description = "중심좌표 경도", requiredMode = Schema.RequiredMode.REQUIRED, example = "126.761259")
                @JsonProperty("centerLongitude")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double centerLongitude
        ) {
        }
    }


    ////
    @Operation(
            summary = "DB 의 좌표 테이블의 모든 데이터 삭제",
            description = "DB 의 좌표 테이블의 모든 데이터 삭제 API\n\n"
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
            path = "/test-map/coordinate/all",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.ALL_VALUE
    )
    @ResponseBody
    public void deleteAllCoordinateDataFromDatabase(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse
    ) {
        service.deleteAllCoordinateDataFromDatabase(httpServletResponse);
    }


    ////
    @Operation(
            summary = "DB 에 저장된 좌표들을 SQL 을 사용하여, 기준 좌표의 N Km 내의 결과만 필터",
            description = "기준 좌표를 입력하면 DB 에 저장된 좌표들과의 거리를 SQL 로 계산하여 N Km 내의 결과만 필터링 하여 리스트로 반환하는 API\n\n"
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
            path = "/test-map/coordinate-in-round",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectCoordinateDataRowsInRadiusKiloMeterSampleOutputVo selectCoordinateDataRowsInRadiusKiloMeterSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "anchorLatitude", description = "기준 좌표 위도", example = "37.675683")
            @RequestParam("anchorLatitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double anchorLatitude,
            @Parameter(name = "anchorLongitude", description = "기준 좌표 경도", example = "126.761259")
            @RequestParam("anchorLongitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double anchorLongitude,
            @Parameter(name = "radiusKiloMeter", description = "기준 좌표를 중심으로 몇 Km 안까지의 결과를 가져올지", example = "12.54")
            @RequestParam("radiusKiloMeter")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double radiusKiloMeter
    ) {
        return service.selectCoordinateDataRowsInRadiusKiloMeterSample(httpServletResponse, anchorLatitude, anchorLongitude, radiusKiloMeter);
    }

    public record SelectCoordinateDataRowsInRadiusKiloMeterSampleOutputVo(
            @Schema(description = "결과 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("resultList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull CoordinateCalcResult> resultList
    ) {
        public record CoordinateCalcResult(
                @Schema(description = "고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "좌표 위도", requiredMode = Schema.RequiredMode.REQUIRED, example = "37.676563")
                @JsonProperty("latitude")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double latitude,

                @Schema(description = "좌표 경도", requiredMode = Schema.RequiredMode.REQUIRED, example = "126.761259")
                @JsonProperty("longitude")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double longitude,

                @Schema(description = "기준 좌표와의 거리 (Km)", requiredMode = Schema.RequiredMode.REQUIRED, example = "15.214")
                @JsonProperty("distanceMeter")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double distanceKiloMeter
        ) {
        }
    }


    ////
    @Operation(
            summary = "DB 에 저장된 좌표들을 SQL 을 사용하여, 북서 좌표에서 남동 좌표까지의 사각 영역 안에 들어오는 좌표들만 필터링하여 반환",
            description = "북, 서, 남, 동 좌표를 입력하면 DB 에 저장된 좌표들 중 좌표 사각 영역 안에 들어오는 좌표를 필터링 하여 리스트로 반환하는 API\n\n"
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
            path = "/test-map/coordinate-in-box",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @Nullable
    @org.jetbrains.annotations.Nullable
    public SelectCoordinateDataRowsInCoordinateBoxSampleOutputVo selectCoordinateDataRowsInCoordinateBoxSample(
            @Parameter(hidden = true)
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            HttpServletResponse httpServletResponse,
            @Parameter(name = "northLatitude", description = "북위도", example = "37.771848")
            @RequestParam("northLatitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double northLatitude,
            @Parameter(name = "eastLongitude", description = "동경도", example = "127.433549")
            @RequestParam("eastLongitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double eastLongitude,
            @Parameter(name = "southLatitude", description = "남위도", example = "37.245683")
            @RequestParam("southLatitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double southLatitude,
            @Parameter(name = "westLongitude", description = "서경도", example = "126.587602")
            @RequestParam("westLongitude")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Double westLongitude
    ) {
        return service.selectCoordinateDataRowsInCoordinateBoxSample(httpServletResponse, northLatitude, eastLongitude, southLatitude, westLongitude);
    }

    public record SelectCoordinateDataRowsInCoordinateBoxSampleOutputVo(
            @Schema(description = "결과 좌표 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @JsonProperty("resultList")
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull CoordinateCalcResult> resultList
    ) {
        public record CoordinateCalcResult(
                @Schema(description = "고유번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
                @JsonProperty("uid")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Long uid,

                @Schema(description = "좌표 위도", requiredMode = Schema.RequiredMode.REQUIRED, example = "37.676563")
                @JsonProperty("latitude")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double latitude,

                @Schema(description = "좌표 경도", requiredMode = Schema.RequiredMode.REQUIRED, example = "126.761259")
                @JsonProperty("longitude")
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                Double longitude
        ) {
        }
    }
}