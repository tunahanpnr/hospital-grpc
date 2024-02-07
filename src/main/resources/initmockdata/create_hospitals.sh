#!/bin/bash

# gRPC server address
SERVER_ADDRESS="localhost:9090"

# gRPC service and method
SERVICE_NAME="HospitalService"
METHOD_NAME="CreateHospital"

# Loop to send 10 CreateHospital requests with mock data
for ((i=1; i<=10; i++)); do
  HOSPITAL_NAME="Hospital_$i"
  HOSPITAL_ADDRESS="Address_$i"
  HOSPITAL_PHONE="Phone_$i"
  DOCTOR_COUNT=$((i * 2))
  NURSE_COUNT=$((i * 3))
  OUTPATIENT_CAPACITY=$((i * 5))
  INPATIENT_CAPACITY=$((i * 10))

  # Create a JSON payload for the request
  JSON_PAYLOAD="{
                    \"hospital\": {
                        \"address\": \"$HOSPITAL_ADDRESS\",
                        \"doctor_count\": $DOCTOR_COUNT,
                        \"inpatient_capacity\": $INPATIENT_CAPACITY,
                        \"name\": \"$HOSPITAL_NAME\",
                        \"nurse_count\": $NURSE_COUNT,
                        \"outpatient_capacity\": $OUTPATIENT_CAPACITY,
                        \"phone\": \"$HOSPITAL_PHONE\"
                    }
                }"

  # Use grpcurl to send the request
  grpcurl -plaintext -d "$JSON_PAYLOAD" $SERVER_ADDRESS $SERVICE_NAME/$METHOD_NAME

  echo "Sent CreateHospital request $i"
done
