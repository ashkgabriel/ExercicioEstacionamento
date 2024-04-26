
package Model.Estacionamento;

import java.io.Serializable;

public class Calculo15Minutos implements CalculoValor , Serializable{

    @Override
    public double calcular(long periodo, Veiculo veiculo) {
        //Periodo de tempo padrão em milisegundo para o calculo do valor = 15min * 60s * 1000mS
        float tempoPadrao= 15*60*1000;
        double valorDiaria=veiculo.getTipo().quinzeMinutos;
        
        return valorDiaria * (periodo / tempoPadrao);
    }
    
}
