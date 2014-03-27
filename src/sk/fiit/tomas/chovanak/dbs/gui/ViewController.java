package sk.fiit.tomas.chovanak.dbs.gui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DAOfactory;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DAOmanager;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DaoCommand;

public class ViewController {
	
	
	
	
	/**
	 * Executor na spustanie background threadu
	 */
	ExecutorService exec = Executors.newCachedThreadPool();

	 

	/*********** SECTION WITH FXML INJECTED OBJECTS **********************************/
	
	@FXML
	private static Button btnPredaj;
	
	@FXML
	private static Button btnZobrazZamestnanec;
	
	@FXML
	private static ComboBox<String> cmbProdukt;
	
	@FXML
	private static ComboBox<String> cmbKlient;
	
	@FXML
	private static ComboBox<String> cmbPredajca;
	
	@FXML
	private static ComboBox<String> cmbDatum;
	
	@FXML
	private static ComboBox<String> cmbZamestnanec;
	
	@FXML
	private static ComboBox<String> cmbPrveMeno;
	
	@FXML
	private static ComboBox<String> cmbPriezvisko;
	
	@FXML
	private static ComboBox<String> cmbDatumNastupu;
	
	@FXML
	private static ComboBox<String> cmbDatumUkoncenia;
	
	@FXML
	private static ComboBox<String> cmbZamestnanecNovy;
	
	@FXML
	private static ComboBox<String> cmbPrveMenoNovy;
	
	@FXML
	private static ComboBox<String> cmbPriezviskoNovy;
	
	@FXML
	private static ComboBox<String> cmbDatumNastupuNovy;
	
	@FXML
	private static ComboBox<String> cmbDatumUkonceniaNovy;
	
	@FXML
	private static TextArea txtAreaStav;
	
	@FXML
	private static TextArea txtArea;
		
	public static final String undefined = "undefined";
	
	/**
	 * STATICKA METODA - potrebujem ju na obidenie bugu - v comboboxe java fx sa neda nastavit preddefinovana null hodnota, 
	 * tuto metodu volam z Mainu po tom ako sa inicializuju objekty z FXML suboru
	 */
	public static void initComboBoxes() {
		cmbPrveMeno.setValue(undefined);
		cmbPriezvisko.setValue(undefined);
		cmbZamestnanec.setValue(undefined);
		cmbDatumNastupu.setValue(undefined);
		cmbDatumUkoncenia.setValue(undefined);	
		
		cmbPrveMenoNovy.setValue(undefined);
		cmbPriezviskoNovy.setValue(undefined);
		cmbZamestnanecNovy.setValue(undefined);
		cmbDatumNastupuNovy.setValue(undefined);
		cmbDatumUkonceniaNovy.setValue(undefined);	
	}


	/*********************************** OUTPUT CONTROL METHODS *******************************************************/
	
	public static void zobrazZamestnanec(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
					
				final String id_zamestnanec = cmbZamestnanec.getValue();
				final String prve_meno = cmbPrveMeno.getValue();
				final String priezvisko = cmbPriezvisko.getValue();
				final String datum_nastupu = cmbDatumNastupu.getValue();
				final String datum_ukoncenia = cmbDatumUkoncenia.getValue();
					
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
						output(manager.getZamestnanecZmluvaDAO().getZamestnanec(id_zamestnanec,prve_meno,priezvisko,datum_nastupu,datum_ukoncenia));
						return null;
					}
				
				});
				
				return null;
			}
		});
	}
	
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
	
	public static void updateDataEmployee(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
				
				// STARE UDAJE 
				final String id_zamestnanec = cmbZamestnanec.getValue();
			
				if (id_zamestnanec == undefined ){
					ViewController.outputState("\n\n!!!! UPOZORNENIE : AKTUALIZACIA NEUSPESNA - ID musi byt vyplnene \n\n");
					return null;
				}
				
				final String id_zamestnanecNovy = cmbZamestnanecNovy.getValue();
				final String prve_menoNovy = cmbPrveMenoNovy.getValue();
				final String priezviskoNovy = cmbPriezviskoNovy.getValue();
				final String datum_nastupuNovy = cmbDatumNastupuNovy.getValue();
				final String datum_ukonceniaNovy = cmbDatumUkonceniaNovy.getValue();
				
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
						manager.getZamestnanecZmluvaDAO().update(id_zamestnanec,id_zamestnanecNovy,
								prve_menoNovy,priezviskoNovy,datum_nastupuNovy,datum_ukonceniaNovy);
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
	
	public static void output(final String string) {
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				txtArea.clear();
				txtArea.appendText(string);
				txtArea.appendText("\n\n");
				return null;
			}
		});
		
	}
	
	
	
	
}
