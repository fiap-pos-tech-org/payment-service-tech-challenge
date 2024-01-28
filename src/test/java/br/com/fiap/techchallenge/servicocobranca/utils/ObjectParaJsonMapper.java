package br.com.fiap.techchallenge.servicocobranca.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class ObjectParaJsonMapper {

    private ObjectParaJsonMapper() {
    }

    public static String converte(Object obj) throws JsonProcessingException {
        return JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndAddModules()
                .build()
                .writeValueAsString(obj);
    }
}
