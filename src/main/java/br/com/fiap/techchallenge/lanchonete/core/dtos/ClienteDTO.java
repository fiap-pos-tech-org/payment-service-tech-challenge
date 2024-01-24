package br.com.fiap.techchallenge.lanchonete.core.dtos;

import br.com.fiap.techchallenge.lanchonete.core.domain.entities.Cliente;

public record ClienteDTO(Long id, String nome, String cpf, String email) {

    public ClienteDTO(String nome, String cpf, String email) {
        this(null, nome, cpf, email);
    }

    public ClienteDTO(Long id) {
        this(id, null, null, null);
    }

    public ClienteDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getEmail());
    }

    public ClienteDTO(String nome) {
        this(null, nome, null, null);
    }
}
