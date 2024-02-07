package com.avelios.hospital.server.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient extends BaseEntity {
    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "age")
    @NotNull
    private Integer age;

    @Column(name = "sex")
    @NotEmpty
    private String sex;

    @Column(name = "address")
    @NotEmpty
    private String address;

    @Column(name = "phone")
    @NotEmpty
    private String phone;

    @Column(name = "diagnosis")
    @NotEmpty
    private String diagnosis;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HospitalPatient> hospitals = new HashSet<>();
}
