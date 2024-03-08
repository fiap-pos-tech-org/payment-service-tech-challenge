package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.EnderecoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.EnderecoDTO;

public class EnderecoHelper {

    private EnderecoHelper() {
    }

    public static EnderecoDTO criaEnderecoDTO() {
        return new EnderecoDTO(1L, "Avenida", "Brasil", 1500, "Centro",
                "Uberlândia", "MG");
    }

    public static EnderecoResponse criaEnderecoResponse() {
        var enderecoDTO = criaEnderecoDTO();
        return new EnderecoResponse(enderecoDTO.id(), enderecoDTO.logradouro(), enderecoDTO.rua(), enderecoDTO.numero(),
                enderecoDTO.bairro(), enderecoDTO.cidade(), enderecoDTO.estado());
    }

}
