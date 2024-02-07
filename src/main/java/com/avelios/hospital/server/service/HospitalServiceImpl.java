package com.avelios.hospital.server.service;

import com.avelios.grpc.*;
import com.avelios.hospital.server.exception.ResourceNotFoundException;
import com.avelios.hospital.server.entity.Hospital;
import com.avelios.hospital.server.entity.HospitalPatient;
import com.avelios.hospital.server.entity.HospitalPatientId;
import com.avelios.hospital.server.entity.Patient;
import com.avelios.hospital.server.mapper.HospitalMapper;
import com.avelios.hospital.server.mapper.PatientMapper;
import com.avelios.hospital.server.repository.HospitalPatientRepository;
import com.avelios.hospital.server.repository.HospitalRepository;
import com.avelios.hospital.server.repository.PatientRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
@RequiredArgsConstructor
public class HospitalServiceImpl extends HospitalServiceGrpc.HospitalServiceImplBase {
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final HospitalPatientRepository hospitalPatientRepository;

    private final HospitalMapper hospitalMapper;
    private final PatientMapper patientMapper;

    @Override
    public void createHospital(CreateHospitalRequest request, StreamObserver<CreateHospitalResponse> responseObserver) {
        hospitalRepository.save(hospitalMapper.mapToEntity(request.getHospital()));

        responseObserver.onNext(CreateHospitalResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getHospital(GetHospitalRequest request, StreamObserver<GetHospitalResponse> responseObserver) {
        Hospital hospital = hospitalRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Hospital with id:" + request.getId() + " not found"));

        responseObserver.onNext(
                GetHospitalResponse.newBuilder().setHospital(hospitalMapper.mapToGrpc(hospital)).build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void getAllHospitals(GetAllHospitalsRequest request, StreamObserver<GetAllHospitalsResponse> responseObserver) {
        responseObserver.onNext(
                GetAllHospitalsResponse.newBuilder()
                        .addAllHospitals(hospitalMapper.mapHospitalsToGrpc(hospitalRepository.findAll()))
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void deleteHospital(DeleteHospitalRequest request, StreamObserver<DeleteHospitalResponse> responseObserver) {
        hospitalRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Hospital with id:" + request.getId() + " not found"));

        hospitalRepository.deleteById(request.getId());

        responseObserver.onNext(DeleteHospitalResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateHospital(UpdateHospitalRequest request, StreamObserver<UpdateHospitalResponse> responseObserver) {
        hospitalRepository.findById(request.getHospital().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Hospital with id:" + request.getHospital().getId() + " not found"));

        hospitalRepository.save(hospitalMapper.mapToEntity(request.getHospital()));

        responseObserver.onNext(UpdateHospitalResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void registerPatient(RegisterPatientRequest request, StreamObserver<RegisterPatientResponse> responseObserver) {
        Long patientId = request.getPatientId();
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id:" + patientId + " not found"));

        Long hospitalId = request.getHospitalId();
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(
                () -> new ResourceNotFoundException("Hospital with id:" + hospitalId + " not found"));

        HospitalPatient hospitalPatient = new HospitalPatient();
        hospitalPatient.setId(new HospitalPatientId());
        hospitalPatient.setPatient(patient);
        hospitalPatient.setHospital(hospital);
        hospitalPatientRepository.save(hospitalPatient);

        responseObserver.onNext(RegisterPatientResponse.newBuilder().setSuccess(true).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPatientsOfHospital(GetPatientsOfHospitalRequest request, StreamObserver<GetPatientsOfHospitalResponse> responseObserver) {
        Long hospitalId = request.getId();
        hospitalRepository.findById(hospitalId).orElseThrow(
                () -> new ResourceNotFoundException("Hospital with id:" + hospitalId + " not found"));

        responseObserver.onNext(
                GetPatientsOfHospitalResponse
                        .newBuilder()
                        .addAllPatients(
                                patientMapper.mapPatientsToGrpc(patientRepository.findPatientsOfHospital(hospitalId)))
                        .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getHospitalsOfPatient(GetHospitalsOfPatientRequest request, StreamObserver<GetHospitalsOfPatientResponse> responseObserver) {
        Long patientId = request.getId();
        patientRepository.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("Patient with id:" + patientId + " not found"));

        responseObserver.onNext(
                GetHospitalsOfPatientResponse
                        .newBuilder()
                        .addAllHospitals(
                                hospitalMapper.mapHospitalsToGrpc(hospitalRepository.findHospitalsOfPatient(patientId)))
                        .build());
        responseObserver.onCompleted();
    }
}
