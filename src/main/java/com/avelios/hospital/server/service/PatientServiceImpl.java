package com.avelios.hospital.server.service;

import com.avelios.grpc.*;
import com.avelios.hospital.server.exception.ResourceNotFoundException;
import com.avelios.hospital.server.entity.Patient;
import com.avelios.hospital.server.mapper.PatientMapper;
import com.avelios.hospital.server.repository.PatientRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
@RequiredArgsConstructor
public class PatientServiceImpl extends PatientServiceGrpc.PatientServiceImplBase {
    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    @Override
    public void createPatient(CreatePatientRequest request, StreamObserver<CreatePatientResponse> responseObserver) {
        patientRepository.save(patientMapper.mapToEntity(request.getPatient()));

        responseObserver.onNext(CreatePatientResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPatient(GetPatientRequest request, StreamObserver<GetPatientResponse> responseObserver) {
        Patient patient = patientRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id:" + request.getId() + " not found"));

        responseObserver.onNext(
                GetPatientResponse.newBuilder().setPatient(patientMapper.mapToGrpc(patient)).build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getAllPatients(GetAllPatientsRequest request, StreamObserver<GetAllPatientsResponse> responseObserver) {
        responseObserver.onNext(
                GetAllPatientsResponse.newBuilder()
                        .addAllPatient(patientMapper.mapPatientsToGrpc(patientRepository.findAll()))
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void deletePatient(DeletePatientRequest request, StreamObserver<DeletePatientResponse> responseObserver) {
        patientRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id:" + request.getId() + " not found"));

        patientRepository.deleteById(request.getId());

        responseObserver.onNext(DeletePatientResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updatePatient(UpdatePatientRequest request, StreamObserver<UpdatePatientResponse> responseObserver) {
        patientRepository.findById(request.getPatient().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id:" + request.getPatient().getId() + " not found"));

        patientRepository.save(patientMapper.mapToEntity(request.getPatient()));

        responseObserver.onNext(UpdatePatientResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

}
