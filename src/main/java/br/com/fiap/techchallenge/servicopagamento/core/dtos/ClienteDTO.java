package br.com.fiap.techchallenge.servicopagamento.core.dtos;

public record ClienteDTO(Long id, String nome, String cpf, String email) {

    public ClienteDTO(String nome, String cpf, String email) {
        this(null, nome, cpf, email);
    }

    public ClienteDTO(Long id) {
        this(id, null, null, null);
    }

    public ClienteDTO(String nome) {
        this(null, nome, null, null);
    }
}
