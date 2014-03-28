
package sk.fiit.tomas.chovanak.dbs.gui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DAOfactory;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DAOmanager;
import sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS.DaoCommand;
import sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS.ClientDAO;
import sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS.ProduktDAO;
import sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS.ZamestnanecZmluvaDAO;

@SuppressWarnings("rawtypes")
public class MainViewController {
	
	private static int counter = 0;

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
	private static ComboBox<String> cmbObjemEur;
	

	
	@FXML
	private static Button btnZobrazZamestnanec;
	
	@FXML
	private static TextField txtZamestnanec;
	
	@FXML
	private static TextField txtPrveMeno;
	
	@FXML
	private static TextField txtPriezvisko;
	
	@FXML
	private static TextField txtDatumNastupu;
	
	@FXML
	private static TextField txtDatumUkoncenia;
	
	@FXML
	private static TextField txtZamestnanecNovy;
	
	@FXML
	private static TextField txtPrveMenoNovy;
	
	@FXML
	private static TextField txtPriezviskoNovy;
	
	@FXML
	private static TextField txtDatumNastupuNovy;
	
	@FXML
	private static TextField txtDatumUkonceniaNovy;
	
	
	@FXML
	private static ComboBox<String> cmbZamestnanec2;
	
	@FXML
	private static Button btnZobraz2;
	
	@FXML
	private static Button btnDelete;
	
	
	@FXML
	private static Button btnStatistika1;
	
	@FXML
	private static ComboBox<String> cmbZiskOd;
	
	@FXML
	private static ComboBox<String> cmbZiskDo;
	
	@FXML
	private static ComboBox<String> cmbProdukt2;
	
	
	@FXML
	private static Button btnStatistika2;
	
	
	
	@FXML
	private static ComboBox<String> cmbZamestnanec3;
	
	@FXML
	private static Button btnStatistika3;
	
	
	
	@FXML
	private static ComboBox<String> cmbZamestnanec4;
	
	@FXML
	private static ComboBox<String> cmbProdukt3;
	
	@FXML
	private static Button btnVyhladaj;
	
	
	
	
	@FXML
	private static TextArea txtAreaStav;
	
	@FXML
	private static TextArea txtArea;
		
	public static final String undefined = "null";
	
	
	/**
	 * STATICKA METODA - potrebujem ju na obidenie bugu - v comboboxe java fx sa neda nastavit preddefinovana null hodnota, 
	 * tuto metodu volam z Mainu po tom ako sa inicializuju objekty z FXML suboru
	 */
	/*public static void initComboBoxes() {
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
	}*/
	
	
	/**
	 * pri injekcii FXML objektov inicializuje comboboxy hodnotami z databazy
	 */
	public void initialize(){
		DAOfactory daoFactory = new DAOfactory();
		DAOmanager daoManager = daoFactory.createDAOmanager();
		daoManager.OpenTransactionAndClose(new DaoCommand(){

			@Override
			public Object execute(DAOmanager manager) {
				ObservableList<String> itemsProdukt = manager.getProduktDAO().getAllProductsNames();
				cmbProdukt.setItems(itemsProdukt);
				cmbProdukt2.setItems(itemsProdukt);
				cmbProdukt3.setItems(itemsProdukt);
				
				ObservableList<String> itemsKlient = manager.getClientDAO().getAllClientNames();
				cmbKlient.setItems(itemsKlient);
				
				ObservableList<String> itemsPredajca = manager.getZamestnanecZmluvaDAO().getAllNames();
				cmbPredajca.setItems(itemsPredajca);
				cmbZamestnanec2.setItems(itemsPredajca);
				cmbZamestnanec3.setItems(itemsPredajca);
				cmbZamestnanec4.setItems(itemsPredajca);
				
				return null;
			}
			
		});
		
	}
	
/*********************************** OUTPUT CONTROL METHODS *******************************************************/
	
	
	public static void zobrazZamestnanec(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
					
				final String id_zamestnanec = txtZamestnanec.getText();
				final String prve_meno = txtPrveMeno.getText();
				final String priezvisko = txtPriezvisko.getText();
				final String datum_nastupu = txtDatumNastupu.getText();
				final String datum_ukoncenia = txtDatumUkoncenia.getText();
					
				
				System.out.println(id_zamestnanec);
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
	

	public static void zobrazZamestnanec2(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
				
				final String id_zamestnanec = ZamestnanecZmluvaDAO.convertNameToId(cmbZamestnanec2.getValue());
				System.out.println("id_zamestnanec" + id_zamestnanec);
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
						output(manager.getZamestnanecZmluvaDAO().getZamestnanec(id_zamestnanec));
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
				final String id_zamestnanec = txtZamestnanec.getText();
			
				if (id_zamestnanec == undefined ){
					outputState("\n\n!!!! UPOZORNENIE : AKTUALIZACIA NEUSPESNA - ID musi byt vyplnene \n\n");
					return null;
				}
				
				final String id_zamestnanecNovy = txtZamestnanecNovy.getText();
				final String prve_menoNovy = txtPrveMenoNovy.getText();
				final String priezviskoNovy = txtPriezviskoNovy.getText();
				final String datum_nastupuNovy = txtDatumNastupuNovy.getText();
				final String datum_ukonceniaNovy = txtDatumUkonceniaNovy.getText();
				
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
	
	public static void deleteEmployee(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
				
				final String id_zamestnanec = ZamestnanecZmluvaDAO.convertNameToId(cmbZamestnanec2.getValue());
				
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
						manager.getZamestnanecZmluvaDAO().delete(id_zamestnanec);
						return null;
					}
				
				});
				
				// aktualizacia zoznamu 
				daoManager.OpenTransactionAndClose(new DaoCommand(){
					
					@Override
					public Object execute(DAOmanager manager) {
						ObservableList<String> itemsPredajca = manager.getZamestnanecZmluvaDAO().getAllNames();
						System.out.println(itemsPredajca);
						cmbPredajca.setItems(itemsPredajca);
						cmbZamestnanec2.setItems(itemsPredajca);
						cmbZamestnanec3.setItems(itemsPredajca);
						cmbZamestnanec4.setItems(itemsPredajca);
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
				final String id_produkt = ProduktDAO.convertNameToId(cmbProdukt.getValue());
				final String id_klient = ClientDAO.convertNameToId(cmbKlient.getValue());
				final String id_predajca = ZamestnanecZmluvaDAO.convertNameToId(cmbPredajca.getValue());
				final String objem_eur = cmbObjemEur.getValue();
				
				//System.out.println(id_produkt + ", " + id_klient + ", " + id_predajca);
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
						manager.getPredajProduktuDAO().insertNewPredaj(datum,id_produkt,id_klient,id_predajca,objem_eur);
						return null;
					}
				
				});
				
				return null;
			}	
		});
	}

	
	
	public static void showRegionsStatistics(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
					
				final String ziskOd = cmbZiskOd.getValue();
				final String ziskDo = cmbZiskDo.getValue();
				final String produkt_id = ProduktDAO.convertNameToId(cmbProdukt2.getValue());
				
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
				
						output(manager.getStatisticsDAO().getStatisticsOfRegionsIncome(ziskOd,ziskDo,produkt_id));
						
						return null;
					}
				
				});
				
				return null;
			}
		});
	}
	
	public static void showTopSellersStatistics(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
					
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
				
						output(manager.getStatisticsDAO().getStatisticsOfTopSellers());
						
						return null;
					}
				
				});
				
				return null;
			}
		});
	}
	
	public static void showWithoutPermissionSellsStatistics(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
					
				final String id_zamestnanec = ZamestnanecZmluvaDAO.convertNameToId(cmbZamestnanec3.getValue());
				
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
				
						output(manager.getStatisticsDAO().getStatisticsOfSellsWithoutPermission(id_zamestnanec));
						
						return null;
					}
				
				});
				
				return null;
			}
		});
	}
	
	public static void searchForClient(){
		
		Platform.runLater(new Task(){

			@Override
			protected Object call() throws Exception {
				
				DAOfactory daoFactory = new DAOfactory();
				DAOmanager daoManager =  daoFactory.createDAOmanager();
					
				final String id_zamestnanec = ZamestnanecZmluvaDAO.convertNameToId(cmbZamestnanec4.getValue());
				final String id_produkt = ProduktDAO.convertNameToId(cmbProdukt3.getValue());
				
				daoManager.OpenTransactionAndClose(new DaoCommand(){

					@Override
					public Object execute(DAOmanager manager) {
				
						output(manager.getPredajProduktuDAO().searchForClient(id_zamestnanec,id_produkt));
						
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
				counter++;
				txtAreaStav.appendText("LOG " + counter + " : " + string);
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
