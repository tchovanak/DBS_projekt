package sk.fiit.tomas.chovanak.dbs.gui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DAOfactory;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DAOmanager;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DaoCommand;

public class InsertViewController {

	/*********** SECTION WITH FXML INJECTED OBJECTS **********************************/
	
	@FXML
	private static Button btnPredaj;
	
	@FXML
	private static ComboBox<String> cmbProdukt;
	
	@FXML
	private static ComboBox<String> cmbKlient;
	
	@FXML
	private static ComboBox<String> cmbPredajca;
	
	@FXML
	private static ComboBox<String> cmbDatum;
	
	@FXML
	private static TextArea txtAreaStav;

	
	/*********************************** OUTPUT CONTROL METHODS *******************************************************/

	public static void insertNewSell(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
				
				final String datum = cmbDatum.getValue();
				final String id_produkt = cmbProdukt.getValue();
				final String id_klient = cmbKlient.getValue();
				final String id_predajca = cmbPredajca.getValue();
				
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
						manager.getPredajProduktuDAO().insertNewPredaj(datum,id_produkt,id_klient,id_predajca);
						return null;
					}
				
				});
				
				return null;
			}	
		});
	}

	public static void outputState(final String string) {
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				txtAreaStav.clear();
				txtAreaStav.appendText(string);
				return null;
			}
		});
		
	}
	
}
