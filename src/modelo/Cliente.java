package modelo;

/**
 * Representa um cliente da locadora de filmes.
 */
public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String email;

    /**
     * Construtor padrão. Útil para frameworks, serialização ou leitura de dados.
     */
    public Cliente() {
    }

    /**
     * Construtor completo com ID.
     * @param id ID do cliente
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param email Email do cliente
     */
    public Cliente(int id, String nome, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    /**
     * Construtor sem ID. Usado para novos cadastros.
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param email Email do cliente
     */
    public Cliente(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    /**
     * Retorna o nome do cliente para exibição.
     */
    @Override
    public String toString() {
        return nome;
    }
}
