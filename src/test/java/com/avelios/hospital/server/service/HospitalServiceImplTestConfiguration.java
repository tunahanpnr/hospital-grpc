package com.avelios.hospital.server.service;

import com.avelios.hospital.server.mapper.HospitalMapper;
import com.avelios.hospital.server.mapper.PatientMapper;
import com.avelios.hospital.server.repository.HospitalPatientRepository;
import com.avelios.hospital.server.repository.HospitalRepository;
import com.avelios.hospital.server.repository.PatientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class HospitalServiceImplTestConfiguration {

    @Bean
    HospitalRepository hospitalRepository() {
        return mock(HospitalRepository.class);
    }

    @Bean
    PatientRepository patientRepository() {
        return mock(PatientRepository.class);
    }

    @Bean
    HospitalPatientRepository hospitalPatientRepository() {
        return mock(HospitalPatientRepository.class);
    }

    @Bean
    HospitalMapper hospitalMapper() {
        return mock(HospitalMapper.class);
    }

    @Bean
    PatientMapper patientMapper() {
        return mock(PatientMapper.class);
    }

    @Bean
    HospitalServiceImpl hospitalService() {
        return new HospitalServiceImpl(
                hospitalRepository(),
                patientRepository(),
                hospitalPatientRepository(),
                hospitalMapper(),
                patientMapper()
        );
    }

}
