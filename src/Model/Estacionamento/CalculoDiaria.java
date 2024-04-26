
package Model.Estacionamento;

import java.io.Serializable;
import java.util.Calendar;

public class CalculoDiaria implements CalculoValor, Serializable{
    
     @Override
    public double calcular(long periodo, Veiculo veiculo) {
        //Periodo de tempo padrão em milisegundo para o calculo do valor =24h * 60min * 60s * 1000mS
        float tempoPadrao= 24*60*60*1000;
        double valorDiaria=veiculo.getTipo().vinteQuatroHoras;
        
        return valorDiaria * (periodo / tempoPadrao);
    }
    
}
