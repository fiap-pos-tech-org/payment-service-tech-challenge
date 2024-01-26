package br.com.fiap.techchallenge.servicocobranca.adapters.gateways;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.handlers.ErrorDetails;
import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Objects;

public abstract class GatewayBase {

    protected <T> T newCall(Request request, Class<T> clazz) {
        var mapper = getJsonMapper();

        try {
            var response = getHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
                var errorDetails = mapper.readValue(response.body().byteStream(),
                        ErrorDetails.class);
                throw new NotFoundException(Objects.nonNull(errorDetails.message()) ? errorDetails.message() : response.message());
            }

            return mapper.readValue(response.body().byteStream(), clazz);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected JsonMapper getJsonMapper() {
        return JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndAddModules()
                .build();
    }

    protected abstract OkHttpClient getHttpClient();

}
