package javafx.poov.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.poov.modelo.dao.AplicacaoDAO;
import javafx.poov.modelo.dao.ConexaoFactory;
import javafx.poov.modelo.dao.PessoaDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.poov.modelo.Aplicar;
import javafx.poov.modelo.Pessoa;
import javafx.poov.modelo.Vacina;
import javafx.poov.modelo.dao.DAOFactory;
import javafx.poov.modelo.dao.VacinaDAO;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PrimaryController implements Initializable{

    @FXML
    private TableView<Vacina> vacinaTableView;

    @FXML
    private TableColumn<Vacina, Long> codigoVacina;

    @FXML
    private TableColumn<Vacina, String> nomeVacina;

    @FXML
    private TableColumn<Vacina, String> descricaoVacina;

    @FXML
    private TextField inputCodigoVacina;

    @FXML
    private TextField inputNomeVacina;

    @FXML
    private TextArea inputDescricaoVacina;

    @FXML
    private Button botaoPesquisaVacina;

    @FXML
    private Button botaoNova;

    @FXML
    private Button botaoEditar;

    @FXML
    private Button botaoRemover;

    @FXML
    private TextField inputCodigoPessoa;

    @FXML
    private TextField inputNomePessoa;

    @FXML
    private TextField inputCPFPessoa;

    @FXML
    private DatePicker inputAPartir;

    @FXML
    private DatePicker inputAte;

    @FXML
    private Button botaoPesquisaPessoa;

    @FXML
    private Button botaoFechar;

    @FXML
    private Button botaoAplicar;

    @FXML
    private TableView<Pessoa> pessoaTableView;

    @FXML
    private TableColumn<Pessoa, Long> codigoPessoa;

    @FXML
    private TableColumn<Pessoa, String> nomePessoa;

    @FXML
    private TableColumn<Pessoa, String> cpfPessoa;

    @FXML
    private TableColumn<Pessoa, String> nascimentoPessoa;

    private Stage stageVacina;

    private SecondaryController controllerVacina;

    private Vacina vacina;
    private Pessoa pessoa;


    List<Vacina> vacinas = null;
    List<Pessoa> pessoas = null;

    public PrimaryController() {
        ConexaoFactory.getConexao();
    }

    @FXML
    void switchToEditar(ActionEvent event) {
        if(vacina == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione uma vacina para editar!");
            alert.showAndWait();
            return;
        }
        controllerVacina.setVacina(vacina);
        controllerVacina.setEditar(true);
        stageVacina.setResizable(false);
        stageVacina.showAndWait();
        vacinaTableView.getItems().clear();
        pesquisarVacina(event);
    }

    @FXML
    void switchToCriar(ActionEvent event) {
        controllerVacina.setEditar(false);
        stageVacina.showAndWait();
        pesquisarVacina(event);
    }

    @FXML
    void aplicarVacina(ActionEvent event) {

        Aplicar aplicacao = new Aplicar(LocalDate.now(), pessoa, vacina);
        if (vacina == null || pessoa == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("Selecione uma vacina e uma pessoa!");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Aplicar Vacina");
            alert.setHeaderText("Aplicar Vacina");
            alert.setContentText("Tem certeza que deseja aplicar a vacina nessa pessoa?");
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")) {
                DAOFactory daoFactory = new DAOFactory();

                try {
                    daoFactory.abrirConexao();
                    AplicacaoDAO aplicacaoDAO = daoFactory.criarAplicacaoDAO();

                    if (aplicacaoDAO.gravar(aplicacao)) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Vacina aplicada com sucesso!");
                        alert.showAndWait();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Erro aplicar vacina.");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    DAOFactory.mostrarSQLException(e);
                } finally {
                    daoFactory.fecharConexao();
                }
            }
        }

        

    }


    protected List<Vacina> buscarTodasVacinas() {
        DAOFactory daoFactory = new DAOFactory();
        try {
            daoFactory.abrirConexao();
            VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
            List<Vacina> vacinas = vacinaDAO.buscarTodas();

            //for (Vacina vacina : vacinas) {
                //System.out.println(vacina);
            //}
            return vacinas;
        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }
        
        return null;

    }

    protected List<Pessoa> buscarTodasPessoas() {
        DAOFactory daoFactory = new DAOFactory();
        try {
            daoFactory.abrirConexao();
            PessoaDAO pessoaDAO = daoFactory.criarPessoaDAO();
            List<Pessoa> pessoas = pessoaDAO.buscarTodas();

            //for (Pessoa pessoa : pessoas) {
                //System.out.println(pessoa);
            //}
            return pessoas;
        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            daoFactory.fecharConexao();
        }
        
        return null;

    }

    @FXML
    void excluiVacina(ActionEvent event) {

        if(vacina == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Selecione uma vacina para remover.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remover Vacina");
        alert.setHeaderText("Remover Vacina");
        alert.setContentText("Deseja mesmo remover a vacina?");
        alert.showAndWait();
        if (alert.getResult().getText().equals("OK")) {
            DAOFactory daoFactory = new DAOFactory();
            try {
                daoFactory.abrirConexao();
                VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();
                vacinaDAO.remover(vacina);
                vacinaTableView.getItems().remove(vacina);
            } catch (SQLException e) {
                DAOFactory.mostrarSQLException(e);
            } finally {
                daoFactory.fecharConexao();

            }
        }

    }

    @FXML
    void pesquisarVacina(ActionEvent event) {

        DAOFactory daoFactory = new DAOFactory();

        try {

            daoFactory.abrirConexao();
            VacinaDAO vacinaDAO = daoFactory.criarVacinaDAO();

            if (inputCodigoVacina.getText().isEmpty()
                    && inputNomeVacina.getText().isEmpty()
                    && inputDescricaoVacina.getText().isEmpty()) {
                vacinaTableView.getItems().clear();
                List<Vacina> vacinas = buscarTodasVacinas();
                vacinaTableView.getItems().addAll(vacinas);
            }

            String codigo = inputCodigoVacina.getText();
            String nome = inputNomeVacina.getText();
            String descricao = inputDescricaoVacina.getText();

            List<Vacina> vacinas = vacinaDAO.buscar(codigo, nome, descricao);

            //System.out.print(vacinas);

            vacinaTableView.getItems().clear();
            vacinaTableView.getItems().addAll(vacinas);

            

        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            vacina = null;
            controllerVacina.setVacina(null);
            daoFactory.fecharConexao();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/poov/Vacina.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stageVacina = new Stage();
            stageVacina.setScene(scene);
            controllerVacina = loader.getController();

            inputAPartir.setConverter(new StringConverter<LocalDate>() {
                String pattern = "dd/MM/yyyy";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    inputAPartir.setPromptText(pattern.toLowerCase());
                }

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            });

            inputAte.setConverter(new StringConverter<LocalDate>() {
                String pattern = "dd/MM/yyyy";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

                {
                    inputAte.setPromptText(pattern.toLowerCase());
                }

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            });

            inputCodigoPessoa.setTextFormatter(
                    new TextFormatter<>(change -> change.getControlNewText().matches("[0-9]*") ? change : null));
            inputCodigoVacina.setTextFormatter(
                    new TextFormatter<>(change -> change.getControlNewText().matches("[0-9]*") ? change : null));
            vacinas = buscarTodasVacinas();
            pessoas = buscarTodasPessoas();

            codigoVacina.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
            nomeVacina.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
            descricaoVacina.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));
            vacinaTableView.setPlaceholder(new Label("Não existem Vacinas para serem exibidas."));

            vacinaTableView.getItems().addAll(vacinas);
            vacinaTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!vacinaTableView.getSelectionModel().isEmpty())) {
                    Vacina selectedVacina = vacinaTableView.getSelectionModel().getSelectedItem();
                    controllerVacina.setVacina(selectedVacina);
                    vacina = selectedVacina;
                    //System.out.println(vacina.toString());
                }
            });

            inputCodigoPessoa.setTextFormatter(
                    new TextFormatter<>(change -> change.getControlNewText().matches("[0-9]*") ? change : null));
            inputCodigoVacina.setTextFormatter(
                    new TextFormatter<>(change -> change.getControlNewText().matches("[0-9]*") ? change : null));
            
            codigoPessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, Long>("codigo"));
            nomePessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));
            cpfPessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("cpf"));
            nascimentoPessoa.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nascimento"));
            pessoaTableView.setPlaceholder(new Label("Não existem Pessoas para serem exibidas."));

            nascimentoPessoa.setCellFactory(column -> {
                return new TableCell<Pessoa, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                        } else {
                            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate date = LocalDate.parse(item, inputFormat);
                            String formattedDate = outputFormat.format(date);

                            setText(formattedDate);
                        }
                    }
                };
            });

            pessoaTableView.getItems().addAll(pessoas);
            pessoaTableView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!pessoaTableView.getSelectionModel().isEmpty())) {
                    Pessoa selectedPessoa = pessoaTableView.getSelectionModel().getSelectedItem();
                    pessoa = selectedPessoa;
                    //System.out.println(pessoa.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void verificarDatas() {
        if (inputAPartir == null) {
            inputAPartir.getEditor().clear();
        }
        if (inputAte == null) {
            inputAte.getEditor().clear();
        }
    }

    @FXML
    void pesquisarPessoa(ActionEvent event) {

        DAOFactory daoFactory = new DAOFactory();

        try {

            daoFactory.abrirConexao();
            PessoaDAO pessoaDAO = daoFactory.criarPessoaDAO();

            if (inputCodigoPessoa.getText().isEmpty()
                    && inputNomePessoa.getText().isEmpty()
                    && inputCPFPessoa.getText().isEmpty()
                    && inputAPartir.getValue() == null
                    && inputAte.getValue() == null) {
                pessoaTableView.getItems().clear();
                List<Pessoa> pessoas = buscarTodasPessoas();
                pessoaTableView.getItems().addAll(pessoas);
            } 
            String codigo = "";
            String nome = "";
            String cpf = "";
            String dataNascimento = "";
            String ateData = "";
            if (!inputCodigoPessoa.getText().isBlank()) {
                codigo = inputCodigoPessoa.getText();
            }
            if (!inputNomePessoa.getText().isBlank()) {
                nome = inputNomePessoa.getText();
            }
            if (!inputCPFPessoa.getText().isBlank()) {
                cpf = inputCPFPessoa.getText();
            }
            if (inputAPartir.getValue() != null) {
                dataNascimento = inputAPartir.getValue().toString();
            }
            if (inputAte.getValue() != null) {
                ateData = inputAte.getValue().toString();
            }
            List<Pessoa> pessoas = pessoaDAO.buscar(codigo, nome, cpf, dataNascimento, ateData);
            pessoaTableView.getItems().clear();
            pessoaTableView.getItems().addAll(pessoas);
            

        } catch (SQLException e) {
            DAOFactory.mostrarSQLException(e);
        } finally {
            pessoa = null;
            daoFactory.fecharConexao();
        }

    }


}
