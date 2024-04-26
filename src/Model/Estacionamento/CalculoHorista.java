package Model.Estacionamento;

import java.io.Serializable;

public class CalculoHorista implements CalculoValor, Serializable{
    
    @Override
    public double calcular(long periodo, Veiculo veiculo) {
        //Periodo de tempo padrão em milisegundo para o calculo do valor = 60min * 60s * 1000mS
        float tempoPadrao= 60*60*1000;
        double valorDiaria=veiculo.getTipo().umaHora;
        
        return valorDiaria * (periodo / tempoPadrao);
    }
    
}
