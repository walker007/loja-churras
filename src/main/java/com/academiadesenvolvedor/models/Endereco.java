package com.academiadesenvolvedor.models;

import com.academiadesenvolvedor.exception.NotFoundException;
import com.academiadesenvolvedor.persistence.DbConnection;
import lombok.*;

import java.sql.*;


@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Endereco {
    private int id;
    @NonNull
    private String rua;
    @NonNull
    private String bairro;
    @NonNull
    private String cep;
    @NonNull
    private String cidade;
    @NonNull
    private String uf;

    public String toString(){
        return "Rua: " + this.getRua() + "\n"+
                "Bairro: " + this.getBairro() + "\n"+
                "Cidade: " + this.getCidade() + "\n"+
                "UF: " + this.getUf() + "\n"+
                "CEP: " + this.getCep() ;

    }

    public Endereco createEndereco(int cliente_id){
        String sql = "INSERT INTO enderecos(cliente_id, rua, bairro,cep,cidade,uf) VALUES (?,?,?,?,?,?)";
        Connection conn = DbConnection.getConnection();
        try{
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setInt(1,cliente_id);
            stm.setString(2, this.getRua());
            stm.setString(3,this.getBairro());
            stm.setString(4,this.getCep());
            stm.setString(5, this.getCidade());
            stm.setString(6, this.getUf());

            int rowsCreated = stm.executeUpdate();
            ResultSet generated = stm.getGeneratedKeys();
            if(generated.next()){
                int id = generated.getInt(1);
                this.setId(id);
            }
            conn.commit();

            return this;

        } catch (SQLException e) {
            DbConnection.rollbackChanges(conn);
            throw new RuntimeException(e);
        }finally {
            DbConnection.closeConnection(conn);
        }
    }

    public static Endereco getEnderecoByClienteId(int cliente_id){
        Connection conn = DbConnection.getConnection();
        String sql = "SELECT * FROM enderecos WHERE cliente_id = ? ";
        try{
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1,cliente_id);
            ResultSet resultSet = stm.executeQuery();

            if(!resultSet.next()){
                throw new NotFoundException("Endereço não encontrado");
            }

            return convertEndereco(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbConnection.closeConnection(conn);
        }
    }

    private static Endereco convertEndereco(ResultSet resultSet) throws SQLException {

        return new Endereco(
                resultSet.getInt("id"),
                resultSet.getString("rua"),
                resultSet.getString("bairro"),
                resultSet.getString("cep"),
                resultSet.getString("cidade"),
                resultSet.getString("uf")
        );
    }
}
