package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests;

public class ClienteRequest {

    private String nome;
    private String cpf;
    private String email;

    public ClienteRequest() {
    }

    public ClienteRequest(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
