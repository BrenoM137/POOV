package javafx.poov.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.poov.modelo.Vacina;
import javafx.scene.control.Alert;

public class VacinaDAO {

    private final Connection conexao;

    public VacinaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void gravar(Vacina vacina) throws SQLException {

        String sql = "INSERT INTO vacina(nome, descricao, situacao) VALUES (?, ?, 'ATIVO');";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, vacina.getNome());
        pstmt.setString(2, vacina.getDescricao());

        if (pstmt.executeUpdate() == 1) {
            System.out.println("Insercao da vacina feita com sucesso");
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                vacina.setCodigo(rs.getLong(1));
            } else {
                System.out.println("Erro ao obter o codigo gerado pelo BD para a vacina");
            }
            rs.close();
        } else {
            System.out.println("Erro ao inserir a vacina");
        }
        pstmt.close();

    }

    public List<Vacina> buscar(String codigo, String nome, String descricao) throws SQLException {
        Vacina v = null;
        List<Vacina> vacinas = new ArrayList<>();
        int cont = 1;
        String sql = "SELECT * FROM vacina WHERE";

        if (!codigo.isEmpty()) {
            sql += " codigo = ? AND";
        }
        if (!nome.isEmpty()) {
            sql += " LOWER(nome) LIKE ? AND";
        }
        if (!descricao.isEmpty()) {
            sql += " LOWER(descricao) LIKE ? AND";
        }

        sql += " situacao = 'ATIVO'";

        System.out.println(nome);
        
        PreparedStatement pstmt = conexao.prepareStatement(sql);

        if (!codigo.isEmpty()) {
            pstmt.setLong(cont, Long.parseLong(codigo));
            cont++;
        }
        if (!nome.isEmpty()) {
            pstmt.setString(cont,  "%" + nome.toLowerCase() + "%");
            cont++;
        }
        if (!descricao.isEmpty()) {
            pstmt.setString(cont, "%" + descricao.toLowerCase() + "%");
            cont++;
        }

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
           
            v = new Vacina(rs.getLong(1), rs.getString(2), rs.getString(3));
            vacinas.add(v);
        }
        if (vacinas.size() == 0) {  
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Nao foi encontrada vacina/as");
            alert.showAndWait();
        }
        rs.close();
        pstmt.close();

        return vacinas;
    }

    public List<Vacina> buscarTodas() throws SQLException {
        Vacina v;
        List<Vacina> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM vacina WHERE situacao = 'ATIVO';";
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            v = new Vacina(rs.getLong(1), rs.getString(2), rs.getString(3));
            vacinas.add(v);
        }
        rs.close();
        stmt.close();
        return vacinas;
    }

    public boolean remover(Vacina vacina) throws SQLException {
        boolean retorno = false;
        String sqlUpdate = "UPDATE vacina SET situacao = 'INATIVO' WHERE codigo = ?;";
        PreparedStatement pstmtUpd = conexao.prepareStatement(sqlUpdate);

        pstmtUpd.setLong(1, vacina.getCodigo());

        int resultado = pstmtUpd.executeUpdate();

        if (resultado == 1) {
            System.out.println("Remocao da vacina executada com sucesso");
            retorno = true;
        } else {
            System.out.println("Erro removendo a vacina com codigo: " + vacina.getCodigo());
        }

        pstmtUpd.close();

        return retorno;
    }

    public boolean atualizar(Vacina vacina) throws SQLException {
        boolean retorno = false;
        String sqlUpdate = "UPDATE vacina SET nome = ?, descricao = ?, situacao = ? WHERE codigo = ?;";
        PreparedStatement pstmtUpd = conexao.prepareStatement(sqlUpdate);
        pstmtUpd.setString(1, vacina.getNome());
        pstmtUpd.setString(2, vacina.getDescricao());
        pstmtUpd.setString(3, vacina.getSituacao().toString());
        pstmtUpd.setLong(4, vacina.getCodigo());

        int resultado = pstmtUpd.executeUpdate();
        if (resultado == 1) {
            System.out.println("Alteracao da vacina executada com sucesso");
            retorno = true;
        } else {
            System.out.println("Erro alterando a vacina com codigo: " + vacina.getCodigo());
        }
        pstmtUpd.close();

        return retorno;
    }

}
