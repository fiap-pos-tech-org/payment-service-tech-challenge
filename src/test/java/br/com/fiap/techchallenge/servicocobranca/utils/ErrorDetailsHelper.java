package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.handlers.ErrorDetails;

public class ErrorDetailsHelper {

    private ErrorDetailsHelper() {
    }

    public static ErrorDetails criarErrorDetails(int httpStatus) {
        return new ErrorDetails.Builder()
                .status(httpStatus)
                .message("alguma exception")
                .timestamp(System.currentTimeMillis())
                .build();
    }

}
