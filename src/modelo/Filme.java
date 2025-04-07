package modelo;

/**
 * Representa um filme cadastrado no sistema da locadora.
 */
public class Filme {
    private int id;
    private String titulo;
    private String categoria;
    private int ano;

    /**
     * Construtor padrão. Útil para frameworks, leitura de dados ou uso genérico.
     */
    public Filme() {
    }

    /**
     * Construtor completo com ID (usado para carregamento de dados do banco).
     *
     * @param id        ID do filme
     * @param titulo    Título do filme
     * @param categoria Categoria do filme (ex: Ação, Comédia)
     * @param ano       Ano de lançamento
     */
    public Filme(int id, String titulo, String categoria, int ano) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.ano = ano;
    }

    /**
     * Construtor sem ID (usado para novos cadastros).
     *
     * @param titulo    Título do filme
     * @param categoria Categoria do filme
     * @param ano       Ano de lançamento
     */
    public Filme(String titulo, String categoria, int ano) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.ano = ano;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * Retorna uma representação legível do filme.
     */
    @Override
    public String toString() {
        return "Filme #" + id + " - " + titulo + " (" + ano + ")";
    }
}
