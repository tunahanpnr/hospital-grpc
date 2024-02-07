#!/bin/bash

# Script to run createHospital
echo "Running create_hospitals script..."
./create_hospitals.sh

# Script to run createPatient and assign to hospitals
echo "Running create_patients script..."
./create_patients.sh

# Script to run createPatient and assign to hospitals
echo "Running assign_patients_to_hospitals script..."
./assign_patients_to_hospitals.sh

echo "All scripts executed successfully."
