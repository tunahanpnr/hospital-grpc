package com.avelios.hospital.server.exception;

import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.transaction.TransactionSystemException;

@GrpcAdvice
public class GrpcExceptionAdvice {
    @GrpcExceptionHandler
    public Status IllegalArgumentException(IllegalArgumentException e) {
        return Status.INVALID_ARGUMENT.withDescription("Given argument is not exists!").withCause(e);
    }

    @GrpcExceptionHandler(TransactionSystemException.class)
    public Status handleTransactionSystemException(TransactionSystemException e) {
        return Status.INVALID_ARGUMENT.withDescription("Some fields are missing or not correct!").withCause(e);
    }

    @GrpcExceptionHandler(ResourceNotFoundException.class)
    public Status handleResourceNotFoundException(ResourceNotFoundException e) {
        return Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e);
    }

}
