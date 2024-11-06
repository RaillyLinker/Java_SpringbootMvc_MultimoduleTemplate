package com.raillylinker.module_jpa.jpa_beans.db1_main.repositories;

import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_Service1MemberData;
import com.raillylinker.module_jpa.jpa_beans.db1_main.entities.Db1_RaillyLinkerCompany_Service1MemberEmailData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface Db1_RaillyLinkerCompany_Service1MemberEmailData_Repository extends JpaRepository<Db1_RaillyLinkerCompany_Service1MemberEmailData, Long> {
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Optional<Db1_RaillyLinkerCompany_Service1MemberEmailData> findByEmailAddressAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String emailAddress,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean existsByEmailAddressAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String emailAddress,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    List<Db1_RaillyLinkerCompany_Service1MemberEmailData> findAllByService1MemberDataAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_Service1MemberData service1MemberData,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    Boolean existsByService1MemberDataAndRowDeleteDateStr(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Db1_RaillyLinkerCompany_Service1MemberData service1MemberData,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String rowDeleteDateStr
    );
}