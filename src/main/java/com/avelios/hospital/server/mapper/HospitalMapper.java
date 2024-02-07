package com.avelios.hospital.server.mapper;

import com.avelios.hospital.server.entity.Hospital;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HospitalMapper {
    Hospital mapToEntity(com.avelios.grpc.Hospital hospital);
    com.avelios.grpc.Hospital mapToGrpc(Hospital hospital);
    List<com.avelios.grpc.Hospital> mapHospitalsToGrpc(Set<com.avelios.hospital.server.entity.Hospital> hospitals);
    List<com.avelios.grpc.Hospital> mapHospitalsToGrpc(List<com.avelios.hospital.server.entity.Hospital> hospitals);
}
