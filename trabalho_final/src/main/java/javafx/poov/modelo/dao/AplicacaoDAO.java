package javafx.poov.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.poov.modelo.Aplicar;
import javafx.poov.modelo.Pessoa;
import javafx.poov.modelo.Vacina;

public class AplicacaoDAO {
    private final Connection conexao;

    public AplicacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean gravar(Aplicar aplicacao) throws SQLException {

        Pessoa pessoa = aplicacao.getPessoa();
        Vacina vacina = aplicacao.getVacina();
        Date dataAplicacao = Date.valueOf(aplicacao.getData());

        Long codigoPessoa = pessoa.getCodigo();
        Long codigoVacina = vacina.getCodigo();
        
        String sql = "INSERT INTO aplicacao(data, codigo_pessoa, codigo_vacina, situacao) VALUES (?, ?, ?, 'ATIVO');";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        pstmt.setDate(1, dataAplicacao);
        pstmt.setLong(2, codigoPessoa);
        pstmt.setLong(3, codigoVacina);

        if (pstmt.executeUpdate() == 1) {
            System.out.println("Insercao da aplicacao com sucesso");
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                aplicacao.setCodigo(rs.getLong(1));  
            } else {
                System.out.println("Erro ao obter o codigo gerado pelo BD para a aplicacao");
            }
            rs.close();
            return true;
        } else {
            System.out.println("Erro ao inserir a aplicacao");
            
        }
        pstmt.close();
        return false;
    }

    
}
