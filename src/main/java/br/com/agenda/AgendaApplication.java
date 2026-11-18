
package br.com.agenda;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class AgendaApplication extends Application {

    @Override
    public void start(Stage stage) {

        Label titulo = new Label("AGENDA INTELIGENTE");
        titulo.setStyle(
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitulo = new Label(
                "Organize seus compromissos em um só lugar."
        );

        VBox conteudo = new VBox(15, titulo, subtitulo);
        conteudo.setAlignment(Pos.CENTER);

        BorderPane layout = new BorderPane();
        layout.setCenter(conteudo);
        layout.setPadding(new Insets(30));

        Scene cena = new Scene(layout, 900, 600);

        stage.setTitle("Agenda Inteligente");
        stage.setScene(cena);
        stage.setMinWidth(700);
        stage.setMinHeight(450);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}