package com.avelios.hospital.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HospitalPatientId implements Serializable {
    @Column(name = "hospital_id")
    private long hospitalId;

    @Column(name = "patient_id")
    private long patientId;
}
