package view;

import java.text.SimpleDateFormat;
import java.util.Date;

import control.EdicaoControl;
import entity.Edicao;
import entity.Titulo;
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

public class EdicaoBoundary implements BoundaryContent, EventHandler<ActionEvent> {

	public static EdicaoControl controlEd = new EdicaoControl(); 
	//criando uma variavel do tipo Titulo para alimentar a combo
	ObservableList<Titulo> titulos = TituloBoundary.controlTi.getLista(); 
	private ComboBox<Titulo> comboTitulo = new ComboBox<>();

	private GridPane panGrid;
	private BorderPane painelPrincipal;
	
	private TextField txtEdicao= new TextField();
	private TextField txtLan�amento = new TextField();
	private TextField txtValorCompra = new TextField();
	private TextField txtValorVenda = new TextField();
	private TextField txtDescricao = new TextField();
	
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnPesquisar = new Button("Pesquisar");
	private Button btnExcluir = new Button("Excluir");
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private TableView table = new TableView();
	
	public EdicaoBoundary() { 
		panGrid = new GridPane();
		painelPrincipal = new BorderPane();
		painelPrincipal.setStyle("-fx-padding: 10px");
		panGrid.add(comboTitulo, 0, 0);
		
		panGrid.add(new Label("Edicao"), 2, 0);
		panGrid.add(txtEdicao, 3, 0);
		
		panGrid.add(new Label("Lancamento"), 0, 1);
		panGrid.add(txtLan�amento, 1, 1);
		
	/*	panGrid.add(new Label("Valor Compra"), 0, 2);
		panGrid.add(txtValorCompra, 1, 2);
		
		panGrid.add(new Label("Valor Venda"), 2, 2);
		panGrid.add(txtValorVenda, 3, 2);
	*/	
		panGrid.add(new Label("Descricao"), 0, 3);
		panGrid.add(txtDescricao, 1, 3);
		
		panGrid.add(btnAdicionar, 0, 4);
		panGrid.add(btnPesquisar, 1, 4);
		panGrid.add(btnExcluir, 2, 4);
		
		panGrid.setHgap(10);
		panGrid.setVgap(10);
		
		// deixar a comboBox nao editavel 
		comboTitulo.setEditable(false);
		
		// adicionar a lista de titulos na combo
		comboTitulo.setItems(titulos);
		
		btnAdicionar.addEventHandler(ActionEvent.ANY, this);
		btnPesquisar.addEventHandler(ActionEvent.ANY, this);
		
		painelPrincipal.setTop(panGrid);
		painelPrincipal.setCenter(table);
		
		addTableColumns();
	}
	//criar as tabelas
	private void addTableColumns() {
		TableColumn<Edicao, Titulo> columnTitulo = new TableColumn<>("Titulo");
		columnTitulo.setCellValueFactory(
				new PropertyValueFactory<Edicao, Titulo>("titulo"));
		
		TableColumn<Edicao, Integer> columnEdicao = new TableColumn<>("Edicao");
		columnEdicao.setCellValueFactory(
				new PropertyValueFactory<Edicao,Integer>("edicao"));
		
		TableColumn<Edicao, Date> columnLancamento = new TableColumn<>("Lancamento");
		columnLancamento.setCellValueFactory(
				new PropertyValueFactory<Edicao,Date>("lan�amento"));
		
	/*	TableColumn<Edicao, Double> columnValorV = new TableColumn<>("ValorVenda");
		columnValorV.setCellValueFactory(
				new PropertyValueFactory<Edicao,Double>("valorVenda"));
		*/
		
		table.getColumns().addAll(columnTitulo, columnEdicao,columnLancamento);
		table.setItems(controlEd.getLista());
		
		
	}
	public Pane generateForm() { 
		return painelPrincipal;
	}
	
	// acao ao clicar um botao
	@Override
	public void handle(ActionEvent event) {
		if (event.getTarget() == btnAdicionar) { 
			controlEd.adicionar(boundaryParaEntidade());
		} else if (event.getTarget() == btnPesquisar) {
			int edicao = Integer.parseInt(txtEdicao.getText());
			String titulo = comboTitulo.getValue().getTitulo();
			controlEd.pesquisarPorTipo(titulo,edicao);			
			//entidadeParaBoundary(t);
		}
		
	}
	//mover da entidade para a tela
	private void entidadeParaBoundary(Edicao e) {
		if (e != null) {
			txtEdicao.setText(String.valueOf(e.getEdicao()));
			String strData = sdf.format(e.getLan�amento());
			txtLan�amento.setText(strData);
			//	txtValorCompra.setText(String.valueOf(e.getValorCompra()));
			//	txtValorVenda.setText(String.valueOf(e.getValorVenda()));
			txtDescricao.setText(e.getDescricao());
			
			
		}
		
	}
	
	//mover da tela para a entidade
	private Edicao boundaryParaEntidade() {
		Edicao e = new Edicao();
		try {
			e.setEdicao(Integer.parseInt(txtEdicao.getText()));
			Date d = sdf.parse(txtLan�amento.getText());
			e.setLan�amento(d);
			//	e.setValorCompra(Double.parseDouble(txtValorCompra.getText()));
			//	e.setValorVenda(Double.parseDouble(txtValorVenda.getText()));
			e.setDescricao(txtDescricao.getText());
			e.setTitulo(comboTitulo.getValue());
		} catch (Exception f) {
			f.printStackTrace();
		}
		return e;
	}
}
