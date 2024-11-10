package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories;

import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_TotalAuthMember;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory_Repository extends JpaRepository<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory, Long> {
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Optional<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> findByTokenTypeAndAccessTokenAndLogoutDateAndRowDeleteDateStr(
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
    List<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> findAllByTotalAuthMemberAndLogoutDateAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember,
            LocalDateTime logoutDate,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    List<Db1_RaillyLinkerCompany_TotalAuthLogInTokenHistory> findAllByTotalAuthMemberAndAccessTokenExpireWhenAfterAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            LocalDateTime accessTokenExpireWhenAfter,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );
}