package com.avelios.hospital.server.mapper;

import com.avelios.hospital.server.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {
    com.avelios.grpc.Patient mapToGrpc(Patient patient);

    Patient mapToEntity(com.avelios.grpc.Patient patient);

    List<com.avelios.grpc.Patient> mapPatientsToGrpc(Set<Patient> patients);
    List<com.avelios.grpc.Patient> mapPatientsToGrpc(List<Patient> patients);
}
