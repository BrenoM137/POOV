package javafx.poov.modelo;

import java.time.LocalDate;

public class Aplicar {

    private Long codigo; 
    private LocalDate data; 
    private Pessoa pessoa; 
    private Vacina vacina; 
    private Situacao situacao;

    public Aplicar(LocalDate data, Pessoa pessoa, Vacina vacina) {
        this.data = data;
        this.pessoa = pessoa;
        this.vacina = vacina;
    }

    public Aplicar(Long codigo, LocalDate data, Pessoa pessoa, Vacina vacina) {
        this.codigo = codigo;
        this.data = data;
        this.pessoa = pessoa;
        this.vacina = vacina;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    } 

}
