package com.avelios.hospital.server.repository;

import com.avelios.hospital.server.entity.HospitalPatient;
import com.avelios.hospital.server.entity.HospitalPatientId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalPatientRepository extends JpaRepository<HospitalPatient, HospitalPatientId> {

}
