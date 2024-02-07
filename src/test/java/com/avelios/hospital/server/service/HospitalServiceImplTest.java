package com.avelios.hospital.server.service;

import com.avelios.grpc.CreateHospitalRequest;
import com.avelios.grpc.CreateHospitalResponse;
import com.avelios.grpc.GetHospitalRequest;
import com.avelios.grpc.GetHospitalResponse;
import com.avelios.hospital.server.exception.ResourceNotFoundException;
import com.avelios.hospital.server.entity.Hospital;
import com.avelios.hospital.server.mapper.HospitalMapper;
import com.avelios.hospital.server.repository.HospitalRepository;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;


@SpringBootTest
@SpringJUnitConfig(classes = { HospitalServiceImplTestConfiguration.class })
public class HospitalServiceImplTest {

    @Autowired
    private HospitalServiceImpl hospitalService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalMapper hospitalMapper;

    @Test
    void testCreateHospitalWithSuccess() throws Exception {
        com.avelios.grpc.Hospital hospital = getMockHospitalGrpcData();

        CreateHospitalRequest request = CreateHospitalRequest.newBuilder().setHospital(hospital).build();

        StreamRecorder<CreateHospitalResponse> responseObserver = StreamRecorder.create();
        hospitalService.createHospital(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }

        assertNull(responseObserver.getError());
        List<CreateHospitalResponse> results = responseObserver.getValues();
        assertEquals(1, results.size());
        CreateHospitalResponse response = results.get(0);
        assertEquals(CreateHospitalResponse.newBuilder()
                .setSuccess(true)
                .build(), response);
    }

    @Test
    void testCreateHospitalWithMissingField() {
        com.avelios.grpc.Hospital hospital = getMockHospitalGrpcData();

        CreateHospitalRequest request = CreateHospitalRequest.newBuilder().setHospital(hospital).build();

        when(hospitalRepository.save(any())).thenThrow(new IllegalArgumentException());

        StreamRecorder<CreateHospitalResponse> responseObserver = StreamRecorder.create();
        assertThrows(IllegalArgumentException.class, () -> hospitalService.createHospital(request, responseObserver));

        reset(hospitalRepository);
    }

    @Test
    void testGetHospital() {
        Hospital hospital = getMockHospitalData();

        com.avelios.grpc.Hospital hospitalGrpc = getMockHospitalGrpcData();

        when(hospitalRepository.findById(any())).thenReturn(Optional.of(hospital));
        when(hospitalMapper.mapToGrpc(any())).thenReturn(hospitalGrpc);

        GetHospitalRequest request = GetHospitalRequest.newBuilder().setId(1).build();
        StreamRecorder<GetHospitalResponse> responseObserver = StreamRecorder.create();

        hospitalService.getHospital(request, responseObserver);

        assertNull(responseObserver.getError());
        List<GetHospitalResponse> results = responseObserver.getValues();
        assertEquals(1, results.size());
        GetHospitalResponse response = results.get(0);
        assertEquals(GetHospitalResponse.newBuilder()
                .setHospital(hospitalGrpc)
                .build(), response);

        reset(hospitalRepository, hospitalMapper);
    }

    @Test
    void testGetHospitalWithResourceNotFoundError() {
        when(hospitalRepository.findById(any())).thenThrow(new ResourceNotFoundException("Patient with id:" + 1 + " not found"));

        GetHospitalRequest request = GetHospitalRequest.newBuilder().setId(1).build();
        StreamRecorder<GetHospitalResponse> responseObserver = StreamRecorder.create();

        assertThrows(ResourceNotFoundException.class, () -> hospitalService.getHospital(request, responseObserver));

        reset(hospitalRepository);
    }

    //TODO implement other tests

    private Hospital getMockHospitalData() {
        Hospital hospital = new Hospital();
        hospital.setId(1);
        hospital.setName("name");
        hospital.setAddress("address");
        hospital.setPhone("phone");
        hospital.setDoctorCount(10);
        hospital.setNurseCount(10);
        hospital.setOutpatientCapacity(10);
        hospital.setInpatientCapacity(10);

        return hospital;
    }

    private com.avelios.grpc.Hospital getMockHospitalGrpcData() {
        return com.avelios.grpc.Hospital.newBuilder()
                .setId(1)
                .setName("name")
                .setAddress("surname")
                .setPhone("phone")
                .setDoctorCount(10)
                .setNurseCount(10)
                .setOutpatientCapacity(10)
                .setInpatientCapacity(10)
                .build();
    }
}

