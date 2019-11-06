package view;

import java.text.SimpleDateFormat;
import java.util.Date;

import control.ControlFun;
import entity.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class FuncionaroBoundary implements BoundaryContent, EventHandler<ActionEvent> {
	
	public static ControlFun controlFun = new ControlFun();
	
	private GridPane panGrid;
	private BorderPane painelPrincipal;
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnExcluir = new Button("Excluir");
	
	private TextField txtNome = new TextField();
	private TextField txtCPF = new TextField();
	private TextField txtTelefone = new TextField();
	private TextField txtNasc = new TextField();
	private TextField txtSalario = new TextField();
	private TextField txtLogin = new TextField();
	private TextField txtSenha = new TextField();
	
	ObservableList<String> cargo = 
			FXCollections.observableArrayList("Atendente", "Assistente de Estoque ", "Gerente");
	ComboBox<String> cmbCargo = new ComboBox<>();
	
	private TableView table = new TableView();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public FuncionaroBoundary() {
		
		panGrid = new GridPane();
		painelPrincipal = new BorderPane();
		painelPrincipal.setStyle("-fx-padding: 10px");
		
		panGrid.add(new Label("Nome"), 0, 0);
		panGrid.add(txtNome, 1, 0);
		
		panGrid.add(new Label("CPF"), 0, 1);
		panGrid.add(txtCPF, 1, 1);
		
		panGrid.add(new Label("Telefone"), 0, 2);
		panGrid.add(txtTelefone, 1, 2);
		
		panGrid.add(new Label("Dt Nasc."), 3, 2);
		panGrid.add(txtNasc, 4, 2);
		
		panGrid.add(new Label("Cargo"),0,4);
		panGrid.add(cmbCargo, 1, 4);
		
		panGrid.add(new Label("Salario(R$)"), 3, 4);
		panGrid.add(txtSalario, 4, 4);
		
		panGrid.add(new Label("Login"), 0, 5);
		panGrid.add(txtLogin, 1,5);
		
		panGrid.add(new Label("Senha"), 3, 5);
		panGrid.add(txtSenha, 4, 5);
		
		panGrid.add(new Label("    "), 2, 0);
		
		cmbCargo.setEditable(false);
		cmbCargo.setItems(cargo);
	
		
		panGrid.add(btnAdicionar, 0,6);
		panGrid.add(btnPesquisar, 1, 6);
		panGrid.add(btnExcluir, 3, 6);
		
		btnAdicionar.addEventHandler(ActionEvent.ANY, this);
		btnPesquisar.addEventHandler(ActionEvent.ANY, this);
		
			
		panGrid.setHgap(10);
		panGrid.setVgap(10);
		
		painelPrincipal.setTop(panGrid);
		painelPrincipal.setCenter(table);
		
		addTableColumns();
		
		
	}
	private void addTableColumns() {
		TableColumn<Funcionario,String> columnNome = new TableColumn<>("Nome");
		columnNome.setCellValueFactory(
				new PropertyValueFactory<Funcionario, String>("nome"));
		
		TableColumn<Funcionario,String> columnCargo = new TableColumn<>("Cargo");
		columnCargo.setCellValueFactory(
				new PropertyValueFactory<Funcionario, String>("cargo"));
		
		TableColumn<Funcionario,String> columnCPF = new TableColumn<>("CPF");
		columnCPF.setCellValueFactory(
				new PropertyValueFactory<Funcionario, String>("CPF"));
		
		TableColumn<Funcionario, Long> columnTel = new TableColumn<>("Telefone");
		columnTel.setCellValueFactory(
				new PropertyValueFactory<Funcionario, Long>("telefone"));
		
		TableColumn<Funcionario,Double> columnSalario = new TableColumn<>("Salario");
		columnSalario.setCellValueFactory(
				new PropertyValueFactory<Funcionario, Double>("salario"));
		
		table.getColumns().addAll(columnNome,columnCargo,columnCPF,columnTel,columnSalario);
		table.setItems(controlFun.getLista());
	}
	@Override
	public void handle(ActionEvent event) {
		if(event.getTarget()==btnAdicionar) {
			controlFun.adicionar(boundaryParaEntidade());
		} else if (event.getTarget()==btnPesquisar) {
			String cpf = txtCPF.getText();
			Funcionario f = controlFun.pesquisarPorCPF(cpf);
			entidadeParaBoundary(f);
		}
		
	}
	
	private void entidadeParaBoundary(Funcionario f) {
		if (f != null) {
			txtCPF.setText(f.getCPF());
			txtNome.setText(f.getNome());
			txtTelefone.setText(String.valueOf(f.getTelefone()));
			txtLogin.setText(f.getLogin());
			txtSenha.setText(f.getSenha());
			String strData = sdf.format(f.getNascimento());
			txtNasc.setText(strData);
			txtSalario.setText(String.valueOf(f.getSalario()));
			
		}
		
	}
	
	//mover da tela para a entidade
	private Funcionario boundaryParaEntidade() {
		Funcionario f = new Funcionario();
		try {
			f.setCPF(txtCPF.getText());
			f.setNome(txtNome.getText());
			f.setTelefone(Long.parseLong(txtTelefone.getText()));
			f.setLogin(txtLogin.getText());
			f.setSenha(txtSenha.getText());
			Date d = sdf.parse(txtNasc.getText());
			f.setNascimento(d);
			f.setCargo(cmbCargo.getValue());
			f.setSalario(Double.parseDouble(txtSalario.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
	public Pane generateForm() { 
		return painelPrincipal;
	}
}

