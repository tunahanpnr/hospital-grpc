#!/bin/bash

# gRPC server address
SERVER_ADDRESS="localhost:9090"

# gRPC service and methods
PATIENT_SERVICE_NAME="PatientService"
PATIENT_METHOD_NAME="CreatePatient"


# Loop to send 10 createPatient requests with mock data
for ((i=1; i<=10; i++)); do
  PATIENT_NAME="Patient_$i"
  PATIENT_AGE=$((i * 5))
  PATIENT_SEX="Male"
  PATIENT_ADDRESS="Address_$i"
  PATIENT_PHONE="Phone_$i"
  PATIENT_DIAGNOSIS="Diagnosis_$i"

  # Create a JSON payload for the createPatient request
  PATIENT_JSON_PAYLOAD="{
    \"patient\": {
      \"name\": \"$PATIENT_NAME\",
      \"age\": $PATIENT_AGE,
      \"sex\": \"$PATIENT_SEX\",
      \"address\": \"$PATIENT_ADDRESS\",
      \"phone\": \"$PATIENT_PHONE\",
      \"diagnosis\": \"$PATIENT_DIAGNOSIS\"
    }
  }"

  # Use grpcurl to send the createPatient request
  grpcurl -plaintext -d "$PATIENT_JSON_PAYLOAD" $SERVER_ADDRESS $PATIENT_SERVICE_NAME/$PATIENT_METHOD_NAME
done
