package javafx.poov.modelo;

import java.time.LocalDate;

public class FiltraPessoa {
    private Long codigo; 
    private String nome; 
    private String cpf; 
    private LocalDate dataDe; 
    private LocalDate dataAte; 
    private Situacao situacao; 

    public FiltraPessoa(Long codigo, String nome, String cpf, LocalDate dataDe, LocalDate dataAte) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.dataDe = dataDe;
        this.dataAte = dataAte;
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

    public LocalDate getDataDe() {
        return dataDe;
    }

    public void setDataDe(LocalDate dataDe) {
        this.dataDe = dataDe;
    }

    public LocalDate getDataAte() {
        return dataAte;
    }

    public void setDataAte(LocalDate dataAte) {
        this.dataAte = dataAte;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    
    
}
