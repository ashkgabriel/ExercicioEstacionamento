
package Model.Estacionamento;

import java.io.Serializable;

public enum TipoVeiculoEnum implements Serializable{
    
    MOTOCICLETA(1,2,20),
    CARRO_PASSEIO(2,4,40),
    CAMINHOES(4,8,80);
    
    public double quinzeMinutos, umaHora, vinteQuatroHoras;
    
    TipoVeiculoEnum(double valor15min, double valor1Hora, double valor24Horas){
        quinzeMinutos=valor15min;
        umaHora=valor1Hora;
        vinteQuatroHoras=valor24Horas;
    }  
    
}
