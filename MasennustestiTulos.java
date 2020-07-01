package application;

import javafx.scene.control.Label;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MasennustestiTulos {
	
	public static void naytaTulosIkkuna(Font kirjasinIso, Font kirjasinLaji, List<javafx.scene.control.Label> rivit) {
		
		Stage nayttamo = new Stage();
		nayttamo.setTitle("Testin tulos");
		
		BorderPane paataso = new BorderPane();
		paataso.setPadding(new Insets(10,0,20,20));
		
		Scene kulissi = new Scene(paataso, 600, 400);
		
		VBox ikkunarivit = new VBox(15);
		ikkunarivit.getChildren().addAll(rivit);
		
		for (Label rivi : rivit) {
			rivi.setFont(kirjasinLaji);
		}
		
		paataso.setCenter(ikkunarivit);
		
		Button sulje = new Button("Sulje");
		paataso.setBottom(sulje);
		
		sulje.setOnAction(e -> {
			nayttamo.close();
		});
		
		nayttamo.setScene(kulissi);
		nayttamo.show();
	}

}
