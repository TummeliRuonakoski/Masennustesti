package application;
	
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Masennustesti  extends Application{ 
	
	
	Font kirjasinlajiIso = new Font(18);
	Font kirjasinlaji = new Font(16);
	
	Label ohje = new Label("Valitse kuvaavin vaihtoehto");
	ToggleGroup painikeryhmä;
	RadioButton radioPainike1;
	RadioButton radioPainike2;
	RadioButton radioPainike3;
	RadioButton radioPainike4;
	Label ilmoitusteksti = new Label();
	VBox otsikkoPystylaatikko = new VBox(15); 
	VBox radiopainikePystylaatikko = new VBox(15);
	Label kysymysTeksti = new Label();
	
	Button laskePisteet = new Button ("Laske pisteet");
	Button seuraavaKysymys = new Button ("Seuraava kysymys");
	Button edellinenKysymys = new Button ("Edellinen kysymys");
	HBox painikkeet = new HBox(15); 
	
	List<MasennustestiKysymys> kysymysLista;
	int tamanHetkinenKysymys = 0; 
	
	public List<MasennustestiKysymys> lueKysymyksetTiedostosta() {
		int rivilaskuri = 1;
		int kysymysNro = 0;
		MasennustestiKysymys testi;
		List <MasennustestiKysymys> kysymysLista = new LinkedList<MasennustestiKysymys>();
		
		String polku = "/Users/tummeliruonakoski/documents/Koodauskurssi/masennustesti.txt";
		polku = polku.replace("\\", "/");
		
		FileReader tiedostoLukija; 
		try {
			String kysymysTeksti = "";
			String vaihtoehto1 = "";
			String vaihtoehto2 = "";
			String vaihtoehto3 = "";
			String vaihtoehto4 = "";
			
			tiedostoLukija = new FileReader(polku);
			BufferedReader puskuroivaLukija = new BufferedReader(tiedostoLukija);
			String tiedostonRivi;
			tiedostonRivi = puskuroivaLukija.readLine();
			while (tiedostonRivi != null) {
				switch (rivilaskuri%6){ 
					case 1: 
						kysymysTeksti = tiedostonRivi;
						rivilaskuri++;
						break;
					
					case 2: 
						vaihtoehto1 = tiedostonRivi;
						rivilaskuri++;
						break;
					case 3: 
						vaihtoehto2 = tiedostonRivi;
						rivilaskuri++;
						break;
					case 4: 
						vaihtoehto3 = tiedostonRivi;
						rivilaskuri++;
						break;
					case 5: 
						vaihtoehto4 = tiedostonRivi;
						rivilaskuri++;
						break;
					default:
						testi = new MasennustestiKysymys(
								kysymysNro, 
								kysymysTeksti, 
								vaihtoehto1, 
								vaihtoehto2, 
								vaihtoehto3, 
								vaihtoehto4
								);
						kysymysLista.add(testi);
						rivilaskuri = 1;
						kysymysNro++;
						break;
				}
				tiedostonRivi = puskuroivaLukija.readLine();	
				}
			puskuroivaLukija.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Tiedostoa ei löydy");
		} catch (IOException e) {
			e.printStackTrace();
		}
				return kysymysLista;
			
			
		}
	
	public void taytaKysymyksenTiedot(MasennustestiKysymys kysymys, VBox pystylaatikko) {
		
		kysymysTeksti.setText(kysymys.kysymysTeksti);
		otsikkoPystylaatikko.getChildren().clear();
		otsikkoPystylaatikko.getChildren().addAll(kysymysTeksti, ohje); 
		
		painikeryhmä = new ToggleGroup();
		radioPainike1 = new RadioButton();
		radioPainike2 = new RadioButton();
		radioPainike3 = new RadioButton();
		radioPainike4 = new RadioButton();
		
		radioPainike1.setText(kysymys.vaihtoehdot[0]);
		radioPainike2.setText(kysymys.vaihtoehdot[1]);
		radioPainike3.setText(kysymys.vaihtoehdot[2]);
		radioPainike4.setText(kysymys.vaihtoehdot[3]);
		
		radioPainike1.setToggleGroup(painikeryhmä);
		radioPainike2.setToggleGroup(painikeryhmä);
		radioPainike3.setToggleGroup(painikeryhmä);
		radioPainike4.setToggleGroup(painikeryhmä);
		
		painikeryhmä.selectedToggleProperty().addListener(new ChangeListener<Toggle>()  
        { 

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {

				MasennustestiKysymys kysymys = kysymysLista.get(tamanHetkinenKysymys);
	//			System.out.println("Radiopainike valittu");

				if (painikeryhmä.getSelectedToggle() == radioPainike1) {
					kysymys.asetaVastaus(0);
				} else if (painikeryhmä.getSelectedToggle() == radioPainike2) {
					kysymys.asetaVastaus(1);
				} else if (painikeryhmä.getSelectedToggle() == radioPainike3) {
					kysymys.asetaVastaus(2);
				} else {
					kysymys.asetaVastaus(3);
				}
				laskePisteet.setDisable(!kaikkiKysymyksetVastattu());
			} 
        });
		
	if (kysymys.vastattu == false) {
		radioPainike1.setSelected(false);
		radioPainike2.setSelected(false);
		radioPainike3.setSelected(false);
		radioPainike4.setSelected(false);
	} else {
		switch (kysymys.annettuVastaus) {
		case 0:
			radioPainike1.setSelected(true);
			break;
		case 1:
			radioPainike1.setSelected(true);
			break;
		case 2:
			radioPainike1.setSelected(true);
			break;
		case 3:
			radioPainike1.setSelected(true);
			break;
		}
	}
	
	if (tamanHetkinenKysymys == kysymysLista.size() -1) {
		ilmoitusteksti.setText("Viimeinen kysymys");
	}
	
	pystylaatikko.getChildren().clear();
	pystylaatikko.getChildren().addAll(radioPainike1,radioPainike2, radioPainike3, radioPainike4, ilmoitusteksti);
	
	radioPainike1.setFont(kirjasinlaji);
	radioPainike2.setFont(kirjasinlaji);
	radioPainike3.setFont(kirjasinlaji);
	radioPainike4.setFont(kirjasinlaji);
	
	edellinenKysymys.setFont(kirjasinlaji);
	seuraavaKysymys.setFont(kirjasinlaji);
	laskePisteet.setFont(kirjasinlaji);
	
	}

	public boolean kaikkiKysymyksetVastattu() { 
		boolean vastaamatonLöydetty = false;
		for (MasennustestiKysymys kysymys : kysymysLista) { 
			if (kysymys.vastattu == false) {
				vastaamatonLöydetty = true;
			}
		}
		return !vastaamatonLöydetty;
	}
	
	public void valitseNayttavatPainikkeet() {
		if (tamanHetkinenKysymys == 0) {
			edellinenKysymys.setDisable(true);
		} else {
			edellinenKysymys.setDisable(false);
		}
		
		if (tamanHetkinenKysymys == kysymysLista.size() -1) {
			seuraavaKysymys.setDisable(true);
		} else {
			seuraavaKysymys.setDisable(false);
		}
		
		if (kaikkiKysymyksetVastattu()) {
			laskePisteet.setDisable(false);
		} else {
			laskePisteet.setDisable(true);
		}
	}
	
	@Override
	public void start(Stage nayttamo) throws Exception {
		
		
		nayttamo.setTitle("Masennustesti");
		this.kysymysLista = lueKysymyksetTiedostosta();
		
		
		BorderPane paataso = new BorderPane();
		paataso.setPadding(new Insets(10, 0, 20, 20));
		Scene kulissi = new Scene(paataso, 800, 600);
		
		taytaKysymyksenTiedot(this.kysymysLista.get(tamanHetkinenKysymys),radiopainikePystylaatikko);
		
		laskePisteet.setOnAction(e -> {
			laskePisteet();
		});
		seuraavaKysymys.setOnAction(e-> {
			siirrySeuraavaan();
		});
		edellinenKysymys.setOnAction(e -> {
			siirryEdelliseen();
		});
		
		painikkeet.getChildren().addAll(edellinenKysymys, seuraavaKysymys, laskePisteet);
		
		valitseNayttavatPainikkeet();
		ohje.setFont(kirjasinlajiIso);
		kysymysTeksti.setFont(kirjasinlajiIso);
		ilmoitusteksti.setFont(kirjasinlaji);
		otsikkoPystylaatikko.setPadding(new Insets(10,0,30,0));
		
		paataso.setTop(otsikkoPystylaatikko);
		paataso.setCenter(radiopainikePystylaatikko);
		
		paataso.setBottom(painikkeet);
		nayttamo.setScene(kulissi);
		nayttamo.show();
	
	}
		
		public void siirrySeuraavaan() {

			ilmoitusteksti.setText("");
		//	System.out.println("size: " + kysymysLista.size());

			if (tamanHetkinenKysymys < kysymysLista.size() -1) {
				tamanHetkinenKysymys++;
			}
			taytaKysymyksenTiedot(this.kysymysLista.get(tamanHetkinenKysymys), radiopainikePystylaatikko);
			valitseNayttavatPainikkeet();

		}

		public void siirryEdelliseen() {

			ilmoitusteksti.setText("");

			if (tamanHetkinenKysymys > 0) {
				tamanHetkinenKysymys--;
			}
			taytaKysymyksenTiedot(this.kysymysLista.get(tamanHetkinenKysymys), radiopainikePystylaatikko);
			valitseNayttavatPainikkeet();
			
		}
		
		public void laskePisteet() {
			int pistemaara = 0;
			for (MasennustestiKysymys kysymys : kysymysLista) {
				if (kysymys.annaVastaus() == Integer.MAX_VALUE) {
					
				} else {
					pistemaara += kysymys.annaVastaus();
				}
			}
			
		List<Label> rivit = new LinkedList<Label>();
		rivit.add(new Label("Sait " + pistemaara + " pistettä."));
		
		
		String masennustyyppi = "";
		if(pistemaara <13) {masennustyyppi = "ei ole masennusta";
		} else if(pistemaara >=13 && pistemaara <=18) {masennustyyppi = "on lievä masennus";
		} else if(pistemaara >=19 && pistemaara <=29) {masennustyyppi = "on kohtalainen tai keskivaikea masennus";
		} else { masennustyyppi = "on vaikea masennus";
		}
		
		rivit.add(new Label("Sinulla " + masennustyyppi));
		rivit.add(new Label(""));
		rivit.add(new Label("Normaali (0-12 pistettä)"));
		rivit.add(new Label("Lievä masenus (13-18 pistettä)"));
		rivit.add(new Label("Kohtalainen tai keskivaikea masenus (19-29 pistettä)"));
		rivit.add(new Label("Vaikea masenus (30 pistettä ja yli)"));
		
		for (Label rivi : rivit) {
			rivi.setFont(kirjasinlaji);
		}
		MasennustestiTulos.naytaTulosIkkuna(kirjasinlajiIso, kirjasinlaji, rivit);
		}

		
		public static void main(String[] args) {
			launch(args);
		}
}
