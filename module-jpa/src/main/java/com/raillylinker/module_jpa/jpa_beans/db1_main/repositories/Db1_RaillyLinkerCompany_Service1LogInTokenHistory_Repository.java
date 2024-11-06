package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories;

import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_Service1LogInTokenHistory;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_Service1MemberData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface Db1_RaillyLinkerCompany_Service1LogInTokenHistory_Repository extends JpaRepository<Db1_RaillyLinkerCompany_Service1LogInTokenHistory, Long> {
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Optional<Db1_RaillyLinkerCompany_Service1LogInTokenHistory> findByTokenTypeAndAccessTokenAndLogoutDateAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String tokenType,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String accessToken,
            LocalDateTime logoutDate,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    List<Db1_RaillyLinkerCompany_Service1LogInTokenHistory> findAllByService1MemberDataAndLogoutDateAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_Service1MemberData service1MemberData,
            LocalDateTime logoutDate,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    List<Db1_RaillyLinkerCompany_Service1LogInTokenHistory> findAllByService1MemberDataAndAccessTokenExpireWhenAfterAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_Service1MemberData service1MemberData,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime accessTokenExpireWhenAfter,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );
}