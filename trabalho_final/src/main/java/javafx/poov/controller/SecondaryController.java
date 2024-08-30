package javafx.poov.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.poov.modelo.Vacina;
import javafx.poov.modelo.dao.DAOFactory;
import javafx.poov.modelo.dao.VacinaDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SecondaryController {


    @FXML
    private Button botaoCancelar;

    @FXML
    private Label labelVacina;

    @FXML
    private TextField inputCodigo;

    @FXML
    private Button botaoVacina;

    @FXML
    private TextArea inputDescricao;

    @FXML
    private TextField inputNome;

    private Boolean editVacina = null;

    private Vacina vacina;

    @FXML
    void cancelaVacina(ActionEvent event) throws IOException {
        ((Button)event.getSource()).getScene().getWindow().hide();
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Boolean setEditar(Boolean editar) {
        if(editar) {
            editVacina = true;
            labelVacina.setText("Editar Vacina");
            botaoVacina.setText("Editar");
            inputCodigo.setText(String.valueOf(vacina.getCodigo()));
            inputNome.setText(vacina.getNome());
            inputDescricao.setText(vacina.getDescricao());
        } else {
            editVacina = false;
            labelVacina.setText("Nova Vacina");
            botaoVacina.setText("Criar");
            inputCodigo.clear();
            inputNome.clear();
            inputDescricao.clear();
        }
        return editar;
    }

    private Boolean checaCampo() {
        return 
        (inputNome.getText().isEmpty() == false) &&
        (inputDescricao.getText().isEmpty() == false);
    }

    @FXML
    void criaVacina(ActionEvent event) {
        if (editVacina != true) {
            if(checaCampo()) {
                vacina = new Vacina(inputNome.getText(), inputDescricao.getText());

                DAOFactory daoFactory = new DAOFactory();
                try {
                    daoFactory.abrirConexao();
                    VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                    
                    vacinaDAO.gravar(vacina);
                    ((Button)event.getSource()).getScene().getWindow().hide();
    
                } catch (SQLException e) {
                    DAOFactory.mostrarSQLException(e);
                } finally {
                    daoFactory.fecharConexao();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
            }
        } else {
            if(checaCampo()) {
                vacina = new Vacina(Long.parseLong(inputCodigo.getText()),inputNome.getText(), inputDescricao.getText());

                DAOFactory daoFactory = new DAOFactory();
                try {
                    daoFactory.abrirConexao();
                    VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                    
                    vacinaDAO.atualizar(vacina);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Vacina atualizada com sucesso!");
                    alert.showAndWait();
                    ((Button)event.getSource()).getScene().getWindow().hide();
                    
                } catch (SQLException e) {
                    DAOFactory.mostrarSQLException(e);
                } finally {
                    
                    daoFactory.fecharConexao();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
            }
        }
        
    }


}