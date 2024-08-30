package javafx.poov.modelo;

public class Pessoa {
    private Long codigo; 
    private String nome; 
    private String cpf; 
    private String nascimento; 
    private Situacao situacao = Situacao.ATIVO;

    public Pessoa(String nome, String cpf, String nascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
    }

    public Pessoa(Long codigo, String nome, String cpf, String nascimento) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "codigo=" + codigo + "\nnome=" + nome + "\ncpf=" + cpf + "\nnascimento=" + nascimento
                + "\nsituacao=" + situacao;
    } 
    
    
}
