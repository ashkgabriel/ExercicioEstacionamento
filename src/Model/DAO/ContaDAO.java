package Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Estacionamento.*;
import java.util.Calendar;

public class ContaDAO {
    
    private Connection connection;
    VeiculoDAO veiculo = new VeiculoDAO();
    Calendar c;
    String[] i, f;
    ContaVeiculo conta = new ContaVeiculo();
    
    public ContaDAO() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            String DATABASE_URL = "jdbc:derby://localhost:1527/bdestacionamento";
            String usuario = "APP";
            String senha = "123";
            this.connection = DriverManager.getConnection(DATABASE_URL, usuario, senha);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<ContaVeiculo> listar() {
        String sql = "SELECT * FROM CONTA_VEICULO2";
        List<ContaVeiculo> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                conta = new ContaVeiculo(1, veiculo.buscar(resultado.getString("placa")));
                conta.setStatus(StatusConta.valueOf(resultado.getString("status")));
                conta.setValor(resultado.getDouble("valor"));
                i = resultado.getString("inicio").split("-");
                c.set(Integer.parseInt(i[0]), Integer.parseInt(i[1]), Integer.parseInt(i[2]));
                conta.setInicio(c.getTimeInMillis());
                f = resultado.getString("fim").split("-");
                c.set(Integer.parseInt(f[0]), Integer.parseInt(f[1]), Integer.parseInt(f[2]));
                conta.setFim(c.getTimeInMillis());
                retorno.add(conta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public boolean inserir(ContaVeiculo conta) {
        String sql = "INSERT INTO CONTA_VEICULO2( placa_veiculo, data_entrada, data_saida, status_conta) VALUES(?,?,?,?)";
        try {
            String year =  "" + conta.getInicio()/31556952000L + "-";
            String month = "" + conta.getInicio()/2629746000L + "-";
            String day = "" + conta.getInicio()/86400000L + "";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, conta.getVeiculo().getPlaca());
            stmt.setLong(2, conta.getInicio());
            year =  "" + conta.getFim()/31556952000L + "-";
            month = "" + conta.getFim()/2629746000L + "-";
            day = "" + conta.getFim()/86400000L + "";
            stmt.setLong(3, conta.getFim());
            stmt.setString(4, conta.getStatus().toString());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET inicio=?, fim=?, status=?, valor+? WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            String year =  "" + conta.getInicio()/31556952000L + "-";
            String month = "" + conta.getInicio()/2629746000L + "-";
            String day = "" + conta.getInicio()/86400000L + "";
            stmt.setString(1, conta.getVeiculo().getPlaca());
            stmt.setString(2, year+month+day);
            year =  "" + conta.getFim()/31556952000L + "-";
            month = "" + conta.getFim()/2629746000L + "-";
            day = "" + conta.getFim()/86400000L + "";
            stmt.setString(3, year+month+day);
            stmt.setString(4, conta.getStatus().toString());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(String id) {
        String sql = "DELETE FROM conta WHERE id=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
