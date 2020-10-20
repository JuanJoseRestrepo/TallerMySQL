package edu.co.icesi.controller;

import edu.co.icesi.db.MySQLConnection;
import edu.co.icesi.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.util.Callback;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


import java.util.Optional;

public class windowController {

    @FXML
    private Button botonDeAgregar; //Agregar Pelicula
    @FXML
    private Button botonDeAgregarActores; //Agregar actores
    @FXML
    private Button botonDeAgregarGeneros; //Agregar genero
    @FXML
    private TextArea informacionAuxiliar;
    @FXML
    private TextArea informacionPelicula;
    @FXML
    private TextArea informacionActor;
    @FXML
    private TextArea informacionGenero;
    @FXML
    private Label TituloAuxiliar;

    private MySQLConnection connection;

    public windowController(){
        connection = new MySQLConnection();
        connection.crearTablaPelicula();
        connection.crearTablaActor();
        connection.crearTablaGenero();
        connection.crearTablaPivote();
    }


    //Acciones
    public void agregarGenero(ActionEvent e){
        Dialog<Genero> dialog = new Dialog<>();
        dialog.setTitle("Genero de Peliculas");
        dialog.setHeaderText("Por favor, agregue un genero para una pelicula");
        dialog.setResizable(false);
        Label label1 = new Label("Nombre del genero: ");
        TextField text1 = new TextField();
        GridPane grid = new GridPane();
        grid.add(label1,1,1);
        grid.add(text1,2,1);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Genero>() {
            public Genero call(ButtonType b) {

                if(b == buttonTypeOk) {
                    if(!text1.getText().isEmpty()) {
                        Genero m = new Genero(-1,text1.getText());

                        return m;

                    }
                }

                return null;
            }
        });

        Optional<Genero> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            if(!connection.lookAtNotRepeatGenero(m1.get().getNombre())){
                connection.agregarGenero(m1.get());
            }else{
                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Ya hay un genero igual");
                gameOver.showAndWait();
            }

        }

    }



    //GETTERS AND SETTERS
    public Button getBotonDeAgregar() {
        return botonDeAgregar;
    }

    public void setBotonDeAgregar(Button botonDeAgregar) {
        this.botonDeAgregar = botonDeAgregar;
    }

    public Button getBotonDeAgregarActores() {
        return botonDeAgregarActores;
    }

    public void setBotonDeAgregarActores(Button botonDeAgregarActores) {
        this.botonDeAgregarActores = botonDeAgregarActores;
    }

    public Button getBotonDeAgregarGeneros() {
        return botonDeAgregarGeneros;
    }

    public void setBotonDeAgregarGeneros(Button botonDeAgregarGeneros) {
        this.botonDeAgregarGeneros = botonDeAgregarGeneros;
    }

    public TextArea getInformacionAuxiliar() {
        return informacionAuxiliar;
    }

    public void setInformacionAuxiliar(TextArea informacionAuxiliar) {
        this.informacionAuxiliar = informacionAuxiliar;
    }

    public TextArea getInformacionPelicula() {
        return informacionPelicula;
    }

    public void setInformacionPelicula(TextArea informacionPelicula) {
        this.informacionPelicula = informacionPelicula;
    }

    public TextArea getInformacionActor() {
        return informacionActor;
    }

    public void setInformacionActor(TextArea informacionActor) {
        this.informacionActor = informacionActor;
    }

    public TextArea getInformacionGenero() {
        return informacionGenero;
    }

    public void setInformacionGenero(TextArea informacionGenero) {
        this.informacionGenero = informacionGenero;
    }

    public Label getTituloAuxiliar() {
        return TituloAuxiliar;
    }

    public void setTituloAuxiliar(Label tituloAuxiliar) {
        TituloAuxiliar = tituloAuxiliar;
    }
}