package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories;

import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_TotalAuthMember;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login_Repository extends JpaRepository<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login, Long> {
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Optional<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login> findByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Byte oauth2TypeCode,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String snsId,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean existsByOauth2TypeCodeAndOauth2IdAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Byte oauth2TypeCode,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String snsId,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    List<Db1_RaillyLinkerCompany_TotalAuthMemberOauth2Login> findAllByTotalAuthMemberAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean existsByTotalAuthMemberAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_TotalAuthMember totalAuthMember,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );
}