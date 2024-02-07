package com.avelios.hospital.server.repository;

import com.avelios.hospital.server.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT p.* FROM patient AS p INNER JOIN " +
            "(SELECT * FROM hospital_patient WHERE hospital_id = :hospital_id) AS h_p ON p.id = h_p.patient_id",
            nativeQuery = true)
    Set<Patient> findPatientsOfHospital(@Param("hospital_id") Long hospitalId);
}
