
package Model.Estacionamento;

import java.io.Serializable;

public class ContaVeiculo implements Serializable{
    private CalculoValor calculo;
    private Veiculo veiculo;
    private long inicio=0;
    private long fim=0;
    private StatusConta status; 
    private double valor;
    private int id;
    
    public ContaVeiculo(long inicio, Veiculo veiculo){
        this.inicio=inicio;
        this.veiculo=veiculo;
        status= StatusConta.ABERTO;
    }

    public ContaVeiculo() {
    }
    
    
    private double valorConta() {
        return calculo.calcular(fim-inicio, veiculo);
    }
    
    public double valorConta(long dataFinal){
        setFim(dataFinal);
        return valorConta();
    }
    
    public void setCalculo(CalculoValor calculo){
        this.calculo = calculo;
    }
    
    public void setFim(long fim) {
        this.fim = fim;
    }
    public void setStatus(StatusConta status) {
        this.status = status;
    }    
    public StatusConta getStatus() {
        return status;
    }
     public Veiculo getVeiculo() {
        return veiculo;
    }

    public CalculoValor getCalculo() {
        return calculo;
    }

    public long getInicio() {
        return inicio;
    }

    public long getFim() {
        return fim;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public void setInicio(long inicio) {
        this.inicio = inicio;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }
    
    
}
