package com.academiadesenvolvedor.models;

import com.academiadesenvolvedor.exception.NotFoundException;
import com.academiadesenvolvedor.persistence.DbConnection;
import com.academiadesenvolvedor.utils.Telefone;
import lombok.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Cliente {

    private int id;
    @NonNull
    private String nome;
    @NonNull
    private Telefone telefone;
    @NonNull
    private Endereco endereco;
    @NonNull
    private String cpf;
    @NonNull
    private boolean ativo;
    private List<Pedido> pedidos;
    @NonNull
    private double credito;

    public String toString(){
        return "Nome: " + this.getNome() + "\n"+
                "id: " + this.getId() + "\n"+
                "CPF: " + this.getCpf() + "\n" +
                "Telefone: " + this.getTelefone() + "\n"+
                "Situação: " + this.isAtivo() + "\n" +
                "Cédito dispoível: " + this.getCredito() + "\n"+
                "---- \n"+
                this.getEndereco();
    }

    public Cliente createCliente(){
        Connection conn = DbConnection.getConnection();
        String sql = "INSERT INTO clientes (nome, cpf, telefone, ativo,credito) VALUES (?, ?, ?, ?,?)";

        try{
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, this.getNome());
            stm.setString(2, this.getCpf());
            stm.setString(3, this.getTelefone().toString());
            stm.setBoolean(4, this.isAtivo());
            stm.setDouble(5, this.getCredito());

           int affectedRows =  stm.executeUpdate();
           ResultSet generated = stm.getGeneratedKeys();
          if(generated.next()){
              int id = generated.getInt(1);
              this.setId(id);
          }
           conn.commit();

           return this;

        }catch (SQLException e){
            DbConnection.rollbackChanges(conn);
            throw new RuntimeException(e);
        }finally {
            DbConnection.closeConnection(conn);
        }

    }

    public Cliente updateCliente(){
        Connection conn = DbConnection.getConnection();
        String sql = "UPDATE clientes SET ativo = ?, credito = ? WHERE id = ?";

        try{
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setBoolean(1,this.isAtivo());
            stm.setDouble(2, this.getCredito());
            stm.setInt(3,this.getId());

            int affectedRows = stm.executeUpdate();
            conn.commit();
            return  this;
        } catch (SQLException e) {
            DbConnection.rollbackChanges(conn);
            throw new RuntimeException(e);
        }finally {
            DbConnection.closeConnection(conn);
        }
    }

    public static Cliente findCliente(int id){
        Cliente cliente;
        Connection conn = DbConnection.getConnection();
        String sql = "SELECT * FROM clientes WHERE id = ? ";

        try{
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1,id);
            ResultSet result = stm.executeQuery();


          if (!result.next()){
             throw new NotFoundException("Cliente id {"+id+"} não encontrado");
          }

            cliente = convertClient(result);

            return cliente;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbConnection.closeConnection(conn);
        }

    }

    private static Cliente convertClient(ResultSet result) throws SQLException {
        String[] telefone = result.getString("telefone")
                .replace("+","")
                .replace("(","")
                .replace(")" , "")
                .split(" ");
        try{
            Endereco endereco = Endereco.getEnderecoByClienteId(result.getInt("id"));
            return  new Cliente(
                    result.getInt("id"),
                    result.getString("nome"),
                    new Telefone(telefone[0],telefone[1],telefone[2]),
                    endereco,
                    result.getString("cpf"),
                    result.getBoolean("ativo"),
                    new ArrayList<>(),
                    result.getDouble("credito")
            );
        }catch (NotFoundException e){
            System.out.println(e.getMessage());

            return new Cliente(
                    result.getInt("id"),
                    result.getString("nome"),
                    new Telefone(telefone[0],telefone[1],telefone[2]),
                    new Endereco(),
                    result.getString("cpf"),
                    result.getBoolean("ativo"),
                    new ArrayList<>(),
                    result.getDouble("credito")
            );
        }

    }
    public static List<Cliente> searchCliente(String where){
        Connection conn = DbConnection.getConnection();
        String sql = "SELECT * FROM clientes " + where;
        List<Cliente> results = new ArrayList<Cliente>();

        try{
            Statement stm = conn.createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            while (resultSet.next()){
                results.add(convertClient(resultSet));
            }

            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbConnection.closeConnection(conn);
        }
    }
}
