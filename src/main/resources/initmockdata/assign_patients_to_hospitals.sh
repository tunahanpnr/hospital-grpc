#!/bin/bash

# gRPC server address
SERVER_ADDRESS="localhost:9090"

# gRPC service and method
SERVICE_NAME="HospitalService"
METHOD_NAME="RegisterPatient"

# Loop to send 10 CreateHospital requests with mock data
for ((i=1; i<=10; i++)); do
  for ((j=52; j<=61; j++)); do
      grpcurl -plaintext -d "{
                                 \"hospitalId\": $i,
                                 \"patientId\": $j
                             }" $SERVER_ADDRESS $SERVICE_NAME/$METHOD_NAME

      echo "Patient $j registered to the Hospital $i"

  done
done
