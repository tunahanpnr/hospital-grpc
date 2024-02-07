package com.avelios.hospital.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hospital extends BaseEntity {

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "address")
    @NotEmpty
    private String address;

    @Column(name = "phone")
    @NotEmpty
    private String phone;

    @Column(name = "doctor_count")
    @Min(1)
    private Integer doctorCount;

    @Column(name = "nurse_count")
    @Min(1)
    private Integer nurseCount;

    @Column(name = "outpatient_capacity")
    @Min(1)
    private Integer outpatientCapacity;

    @Column(name = "inpatient_capacity")
    @Min(1)
    private Integer inpatientCapacity;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HospitalPatient> patients = new HashSet<>();
}
