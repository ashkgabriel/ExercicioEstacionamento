
package Controller;

import Model.DAO.*;
import Model.Estacionamento.*;
import View.TelaPrincipal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Controlador {
    
    private List<ContaVeiculo> listaVeiculos;
    private Thread t0,t1;
    private PersistenciaDados DAO;
    
    public Controlador(){
        listaVeiculos=new ArrayList<ContaVeiculo>();  
        DAO=new PersistenciaDados();
    }
    
    public void addContaVeiculo(String nome, String placa, TipoVeiculoEnum tipo)throws Exception{
        Veiculo auxVeiculo = new Veiculo(nome, placa, tipo);
        ContaVeiculo auxConta = new ContaVeiculo(Calendar.getInstance().getTimeInMillis(), auxVeiculo);
        listaVeiculos.add(auxConta);
        ContaDAO inserir = new ContaDAO();
        inserir.inserir(auxConta);
    }
    
    public String[][] listaVeiculosCadastrados() throws Exception{
        
        String[][] aux=new String[listaVeiculos.size()][6];
        ContaVeiculo conta;
        Date dataAux;
        for(int i=0;i<listaVeiculos.size();i++){
            conta=(ContaVeiculo) listaVeiculos.get(i);
            aux[i][0]=conta.getVeiculo().getNome();
            aux[i][1]=conta.getVeiculo().getPlaca();
            aux[i][2]=conta.getVeiculo().getTipo().toString();
            dataAux=new Date(conta.getInicio());
            aux[i][3]= dataAux.toString();
            dataAux=new Date(conta.getFim());
            aux[i][4]= dataAux.toString();
            aux[i][5]=conta.getStatus().toString();
        }
        return aux;
        
    }
    
    public void finalizarConta(String placaVeiculo,MetricaCalculoEnum metrica) throws Exception{
        ContaVeiculo aux = new ContaVeiculo(0, null);
        for(int i = 0; i < listaVeiculos.size(); i++){
            if(placaVeiculo.equals(listaVeiculos.get(i).getVeiculo().getPlaca())){
                aux = listaVeiculos.get(i);
            }
        }
        //Finaliza a conta, utilizando a metrica de calculo recebida como paramentro.
        // Se a metrica for AUTOMATICO, o sistema deverá verificar a opção mais barata e utiliza-la
        calculaValor(placaVeiculo, metrica);
        // Altera o status para fechado e salva o registro.
        //Se valor da conta for zero retorna um erro.
        aux.setStatus(StatusConta.FECHADO);
        if(calculaValor(placaVeiculo, metrica) == "0"){
            throw new Exception("Valor igual a zero");
        }
        //Se não for possivel registra no BD, salve um backup local da listaVeiculos;
        //Utilize o objeto DAO
       DAO.criarNovoRegistro(aux);
       if(!DAO.criarNovoRegistro(aux)){
            DAO.salvarBackupLocal(listaVeiculos);
       }
        
        
    }
    
    public String calculaPermanencia(String placa){
        ContaVeiculo aux;
        long inicio = 0;
        for(int i = 0; i < listaVeiculos.size(); i++){
            aux = listaVeiculos.get(i);
            if(placa.equals(aux.getVeiculo().getPlaca())){
                inicio = aux.getInicio()/1000/60/60;
            }
        }
        return (Calendar.getInstance().getTimeInMillis()/1000/60/60) - inicio + "";
    }
    public String calculaValor(String placaVeiculo,MetricaCalculoEnum metrica){
        ContaVeiculo aux = new ContaVeiculo(0, null);
        for(int i = 0; i < listaVeiculos.size(); i++){
            if(placaVeiculo.equals(listaVeiculos.get(i).getVeiculo().getPlaca())){
                aux = listaVeiculos.get(i);
            }
        } 
        if(metrica.equals(MetricaCalculoEnum.UM_QUARTO_HORA)){
            aux.setCalculo(new Calculo15Minutos());
            aux.setValor(aux.valorConta(Calendar.getInstance().getTimeInMillis()));
            return aux.valorConta(Calendar.getInstance().getTimeInMillis()) + "";
        }
        if(metrica.equals(MetricaCalculoEnum.DIARIA)){
            aux.setCalculo(new CalculoDiaria());
            aux.setValor(aux.valorConta(Calendar.getInstance().getTimeInMillis()));
            return aux.valorConta(Calendar.getInstance().getTimeInMillis()) + "";
        }
        if(metrica.equals(MetricaCalculoEnum.HORA)){
            aux.setCalculo(new CalculoHorista());
            aux.setValor(aux.valorConta(Calendar.getInstance().getTimeInMillis()));
            return aux.valorConta(Calendar.getInstance().getTimeInMillis()) + "";
        }
        if(metrica.equals(MetricaCalculoEnum.AUTOMATICO)){
            if(Integer.parseInt(calculaPermanencia(placaVeiculo)) < 1){
                aux.setCalculo(new Calculo15Minutos());
                aux.setValor(aux.valorConta(Calendar.getInstance().getTimeInMillis()));
                return aux.valorConta(Calendar.getInstance().getTimeInMillis()) + "";
            }
            if(Integer.parseInt(calculaPermanencia(placaVeiculo)) > 1 && Integer.parseInt(calculaPermanencia(placaVeiculo)) < 12){
                aux.setCalculo(new CalculoHorista());
                aux.setValor(aux.valorConta(Calendar.getInstance().getTimeInMillis()));
                return aux.valorConta(Calendar.getInstance().getTimeInMillis()) + "";
            }
            else{
                aux.setCalculo(new CalculoDiaria());
                aux.setValor(aux.valorConta(Calendar.getInstance().getTimeInMillis()));
                return aux.valorConta(Calendar.getInstance().getTimeInMillis()) + "";
            }
        }
      return "0"; 
    }
}
