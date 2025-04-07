package modelo;

import java.sql.Date;

/**
 * Representa um empréstimo de filme feito por um cliente na locadora.
 */
public class Emprestimo {
    private int id;
    private int idFilme;
    private int idCliente;
    private Date dataEmprestimo;
    private Date dataDevolucao;

    /**
     * Construtor padrão. Útil para frameworks ou leitura de dados.
     */
    public Emprestimo() {
    }

    /**
     * Construtor completo com ID.
     *
     * @param id             ID do empréstimo
     * @param idFilme        ID do filme emprestado
     * @param idCliente      ID do cliente que fez o empréstimo
     * @param dataEmprestimo Data do empréstimo
     * @param dataDevolucao  Data da devolução prevista
     */
    public Emprestimo(int id, int idFilme, int idCliente, Date dataEmprestimo, Date dataDevolucao) {
        this.id = id;
        this.idFilme = idFilme;
        this.idCliente = idCliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    /**
     * Construtor sem ID, usado para novos registros.
     *
     * @param idFilme        ID do filme emprestado
     * @param idCliente      ID do cliente que fez o empréstimo
     * @param dataEmprestimo Data do empréstimo
     * @param dataDevolucao  Data da devolução prevista
     */
    public Emprestimo(int idFilme, int idCliente, Date dataEmprestimo, Date dataDevolucao) {
        this.idFilme = idFilme;
        this.idCliente = idCliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    /**
     * Retorna uma representação simplificada do empréstimo.
     */
    @Override
    public String toString() {
        return "Empréstimo #" + id + " - Cliente: " + idCliente + ", Filme: " + idFilme;
    }
}
