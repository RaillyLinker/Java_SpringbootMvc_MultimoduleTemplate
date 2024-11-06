package com.raillylinker.module_jpa.const_objects;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


// [프로젝트 전역 상수 모음]
// 아래 변수들은 절대 런타임에 변경되어서는 안됩니다.
// 왜냐면, 서버 복제와 같은 Scale out 기법을 사용시 메모리에 저장되는 상태변수가 존재하면 에러가 날 것이기 때문입니다.
// 꼭 메모리에 저장을 해야한다면 Redis, Kafka 등을 사용해 결합성을 낮추는 방향으로 설계하세요.
public final class ModuleConst {
    // 기본 생성자를 private로 설정하여 인스턴스 생성을 방지
    private ModuleConst() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // !!!본인의 패키지명 작성!!!
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public static final String PACKAGE_NAME = "com.raillylinker.module_jpa.const_objects";
}