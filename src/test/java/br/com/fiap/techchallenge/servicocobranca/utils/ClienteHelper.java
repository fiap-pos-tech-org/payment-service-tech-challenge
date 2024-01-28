package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.ClienteRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.ClienteDTO;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class ClienteHelper {

    private ClienteHelper() {
    }

    public static ClienteRequest criarClienteRequest() {
        var clienteRequest = new ClienteRequest();
        clienteRequest.setNome("Cliente Teste");
        clienteRequest.setCpf(gerarCPF());
        clienteRequest.setEmail(gerarEmail());
        return clienteRequest;
    }

    private static String gerarEmail() {
        var random = new Random();
        return String.format("clienteteste%s@gmail.com", random.nextInt(100000));
    }

    private static String gerarCPF() {
        var cpf = new int[11];
        var random = new Random();

        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        cpf[9] = calcularDigitoVerificador(cpf, 10);
        cpf[10] = calcularDigitoVerificador(cpf, 11);

        return Arrays.stream(cpf)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(""));
    }

    private static int calcularDigitoVerificador(int[] cpf, int multiplicador) {
        int soma = 0;
        for (int i = 0; i < multiplicador - 1; i++) {
            soma += cpf[i] * (multiplicador - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : (11 - resto);
    }

    public static ClienteDTO criaClienteDTO() {
        return new ClienteDTO(1L, "cliente1", "56312729036", "cliente1@email.com");
    }

    public static ClienteResponse criaClienteResponse() {
        return new ClienteResponse(1L, "cliente1", "56312729036", "cliente1@email.com");
    }

    public static ClienteRequest criaClienteRequest() {
        return new ClienteRequest("cliente1", "56312729036", "cliente1@email.com");
    }

}
