package it.polito.tdp.metroparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class Controller {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> boxArrivo;

    @FXML
    private ComboBox<Fermata> boxPartenza;

    @FXML
    private TextArea txtResult;


    @FXML
    void handleCalcola(ActionEvent event) {

    	Fermata p = this.boxPartenza.getValue();
    	Fermata a = this.boxArrivo.getValue();
    	
    	if(p!=null && a!=null && !p.equals(a)) {
    		List<Fermata> percorso = model.percorso(p, a);
    		txtResult.setText("Percorso tra "+p.getNome()+" e "+a.getNome()+":\n\n");
    		for(Fermata f: percorso) {
    			txtResult.appendText(f.getNome()+"\n");
    		}
    	}else {
    		txtResult.appendText("Selezionare due stazioni diverse tra loto.\n");
    	}
    }

    @FXML
    void handleCrea(ActionEvent event) {
    	
    	this.model.creaGrafo();
    	
    	if(this.model.isGrafoLoaded()) {
    		txtResult.setText("Grafo correttamente importato.");
    	}

    }

    @FXML
    void initialize() {
        assert boxArrivo != null : "fx:id=\"boxArrivo\" was not injected: check your FXML file 'Metro.fxml'.";
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'Metro.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Metro.fxml'.";

    }

	public void setModel(Model m) {
		// TODO Auto-generated method stub
		this.model = m;
		List<Fermata> fermate = this.model.getAllFermate();
		boxPartenza.getItems().setAll(fermate);
		boxArrivo.getItems().setAll(fermate);
		
	}

}
