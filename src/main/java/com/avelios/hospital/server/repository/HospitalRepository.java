package com.avelios.hospital.server.repository;

import com.avelios.hospital.server.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query(value = "SELECT h.* FROM hospital AS h INNER JOIN " +
            "(SELECT * FROM hospital_patient WHERE patient_id = :patient_id) AS h_p ON h.id = h_p.hospital_id",
            nativeQuery = true)
    Set<Hospital> findHospitalsOfPatient(@Param("patient_id") Long patientId);
}
