package Treino_POO;

public class Computador {
    private String nome;
    private String processador;
    private float memoria;
    private String placadevideo;
    private String sistemaoperacional;
    
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public void setProcessador(String processador)
    {
        this.processador = processador;
    }
    
    public void setMemoria(float memoria)
    {
        this.memoria = memoria;
    }    
        
    public void setPlacadeVideo(String placa)
    {
        this.placadevideo = placa;
    }

    public void setSO(String SO)
    {
        this.sistemaoperacional = SO;
    }
    
    public String toString()
    {
        return("\n-> Nome: "+
                this.nome+"\n -> Processador: "+
                this.processador+"\n -> Memoria: "+
                this.memoria+"\n -> Placa de Video: "+
                this.placadevideo+"\n -> Sistema Operacional: "+
                this.sistemaoperacional);
    }
}
