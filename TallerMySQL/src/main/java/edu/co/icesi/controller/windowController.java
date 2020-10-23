package edu.co.icesi.controller;

import edu.co.icesi.db.MySQLConnection;
import edu.co.icesi.model.Actor;
import edu.co.icesi.model.Genero;
import edu.co.icesi.model.Pelicula;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class windowController implements Initializable {

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
        connection = MySQLConnection.getInstance();
        connection.crearTablaPelicula();
        connection.crearTablaActor();
        connection.crearTablaGenero();
        connection.crearTablaPivote();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    refreshInformationGenero();
    refreshInformationPeliculas();
    refreshInformationActor();
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

                    }else{
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Dejaste el campo vacio");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<Genero> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            if(!connection.lookAtNotRepeatGenero(m1.get().getNombre())){
                connection.agregarGenero(m1.get());
                refreshInformationGenero();
            }else{
                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Ya hay un genero igual");
                gameOver.showAndWait();
            }

        }

    }

    public void agregarActor(ActionEvent e){

        Dialog<Actor> dialog = new Dialog<Actor>();
        dialog.setTitle("Actor");
        dialog.setHeaderText("Por favor, agregue un Actor");
        dialog.setResizable(false);
        Label label1 = new Label("Nombre del actor: ");
        TextField texto1 = new TextField();
        Label label2 = new Label("Apellido del actor: ");
        TextField texto2 = new TextField();
        Label label3 = new Label("Edad del actor: ");
        TextField texto3 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1,1,1);
        grid.add(texto1,2,1);
        grid.add(label2,1,2);
        grid.add(texto2,2,2);
        grid.add(label3,1,3);
        grid.add(texto3,2,3);

        dialog.getDialogPane().setContent(grid);


        ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Actor>() {
            public Actor call(ButtonType b) {

                if(b == buttonTypeOk) {
                    try {
                        if(!texto1.getText().isEmpty() && !texto2.getText().isEmpty() && !texto3.getText().isEmpty()) {
                            Actor m = new Actor(-1,texto1.getText(),texto2.getText(),Integer.parseInt(texto3.getText()));

                            return m;

                        }else{
                            Alert gameOver = new Alert(AlertType.INFORMATION);
                            gameOver.setTitle("ERROR");
                            gameOver.setHeaderText("Dejaste el campo vacio");
                            gameOver.showAndWait();
                        }
                    }catch (NumberFormatException e){
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Formato de edad invalido");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<Actor> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            if(!connection.lookAtNotRepeatActor(m1.get().getNombre(), m1.get().getApellido())){
                connection.agregarActor(m1.get());
                refreshInformationActor();
            }else{
                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Ya hay un actor igual");
                gameOver.showAndWait();
            }

        }

    }

    public void agregarPelicula(ActionEvent e){
        Dialog<Pelicula> dialog = new Dialog<>();
        dialog.setTitle("Peliculas");
        dialog.setHeaderText("Por favor, agregue una pelicula");
        dialog.setResizable(false);
        Label label1 = new Label("Nombre de la pelicula: ");
        TextField texto1 = new TextField();
        GridPane grid = new GridPane();
        grid.add(label1,1,1);
        grid.add(texto1,2,1);
        dialog.getDialogPane().setContent(grid);


        ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Pelicula>() {
            public Pelicula call(ButtonType b) {

                if(b == buttonTypeOk) {
                    if(!texto1.getText().isEmpty()) {
                        Pelicula m = new Pelicula(-1,texto1.getText(),0);

                        return m;

                    }else{
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Dejaste el espacio vacio");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<Pelicula> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            if(!connection.lookAtNotRepeatPeliculas(m1.get().getNombre())){
                connection.agregarPelicula(m1.get());
                refreshInformationPeliculas();
            }else{
                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Ya hay una pelicula igual");
                gameOver.showAndWait();
            }

        }

    }
    
    public void informationPivotePeliculaPorGenero(ActionEvent e){

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Mostrar informacion Pelicula por un genero");
        dialog.setHeaderText("Por favor, utilice los id mostrados para buscar una pelicula por el id del genero");
        dialog.setResizable(false);
        Label label1 = new Label("id del genero: ");
        TextField texto1 = new TextField();


        GridPane grid = new GridPane();
        grid.add(label1,1,1);
        grid.add(texto1,2,1);

        dialog.getDialogPane().setContent(grid);


        ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            public String call(ButtonType b) {

                if(b == buttonTypeOk) {
                    if(!texto1.getText().isEmpty()) {
                        String m = texto1.getText().toString().trim();

                        return m;

                    }else{
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Dejaste el espacio vacio");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<String> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            try{

                int idGenero = Integer.parseInt(m1.get());
                if(connection.lookThatFoundGenero(idGenero)){
                    informacionAuxiliar.clear();
                    ArrayList<Pelicula> output = connection.getMoviesByGenre(idGenero);
                    Genero gen = connection.getGenero(idGenero);
                    TituloAuxiliar.setText("Peliculas por Genero");
                    informacionAuxiliar.appendText(gen.getNombre() + "\n");
                    for(int i = 0; i < output.size(); i++) {
                        informacionAuxiliar.appendText("" + output.get(i).getId() + "." + output.get(i).getNombre() + " , " + output.get(i).getGeneroID() + "\n");
                    }

                }else{
                    Alert gameOver = new Alert(AlertType.INFORMATION);
                    gameOver.setTitle("ERROR");
                    gameOver.setHeaderText("No existe ninguna pelicula o actor");
                    gameOver.showAndWait();
                    informacionAuxiliar.clear();
                    informacionAuxiliar.appendText("No tiene ninguna pelicula o genero mani" + "\n");
                }

            }catch (NumberFormatException e1){

                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Formato invalido");
                gameOver.showAndWait();

            }

        }



    }

    public void refreshInformationPeliculas(){

        informacionPelicula.clear();
        informacionPelicula.appendText("Pelicula: id, nombre, id del genero" + "\n");
        ArrayList<Pelicula> output = connection.getAllPeliculas();
        if(output.size() != 0){
            for(int i = 0; i < output.size(); i++) {
                informacionPelicula.appendText("" + output.get(i).getId() + "." + output.get(i).getNombre() + " , " + output.get(i).getGeneroID() + "\n");
            }
        }
    }

    public void refreshInformationGenero(){
        informacionGenero.clear();
        informacionGenero.appendText("Genero: id, nombre" + "\n");
        ArrayList<Genero> output = connection.getAllGeneros();
        if(output.size() != 0){
            for(int i = 0; i < output.size(); i++) {
                informacionGenero.appendText("" + output.get(i).getId() + "." +  output.get(i).getNombre() + "\n");
            }
        }
    }

    public void refreshInformationActor(){
        informacionActor.clear();
        informacionActor.appendText("Actor: id, nombre, apellido, edad" + "\n");
        ArrayList<Actor> output = connection.getAllActor();
        if(output.size() != 0){
            for(int i = 0; i < output.size(); i++) {
                informacionActor.appendText("" + output.get(i).getId() + "." +  output.get(i).getNombre() + "  " +output.get(i).getApellido() + ", " +
                         output.get(i).getEdad()   + "\n");
            }
        }
    }

    public void vincularPeliculaYActor(ActionEvent e){


            Dialog<String> dialog = new Dialog<String>();
            dialog.setTitle("Vincular Pelicula y Genero");
            dialog.setHeaderText("Por favor, utilice los id mostrados de las peliculas y actores");
            dialog.setResizable(false);
            Label label1 = new Label("id de la pelicula: ");
            TextField texto1 = new TextField();
            Label label2 = new Label("id del actor: ");
            TextField texto2 = new TextField();

            GridPane grid = new GridPane();
            grid.add(label1, 1, 1);
            grid.add(texto1, 2, 1);
            grid.add(label2, 1, 2);
            grid.add(texto2, 2, 2);


            dialog.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            public String call(ButtonType b) {

                if(b == buttonTypeOk) {
                    if(!texto1.getText().isEmpty() && !texto2.getText().isEmpty()) {
                        String m = texto1.getText().toString().trim() +" "+texto2.getText().toString().trim();
                        System.out.println(m);
                        return m;

                    }else{
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Dejaste el espacio vacio");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<String> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            try{
                String[] numbers = m1.get().split(" ");
                int idPelicula = Integer.parseInt(numbers[0]);
                int idActor = Integer.parseInt(numbers[1]);

                System.out.println(idPelicula + " " + idActor);

                if(connection.lookThatFoundPelicula(idPelicula) && connection.lookThatFoundActor(idActor)){

                    connection.vincularPeliculaYActor(idPelicula,idActor);
                    System.out.println("LETS FUCKING GOOOOOOOOOOOOOOOOOOO");

                }else{
                    Alert gameOver = new Alert(AlertType.INFORMATION);
                    gameOver.setTitle("ERROR");
                    gameOver.setHeaderText("No existe ninguna pelicula o actor");
                    
                    gameOver.showAndWait();
                }

            }catch (NumberFormatException e1){

                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Formato invalido");
                gameOver.showAndWait();

            }

        }


    }

    public void vincularPeliculaYGenero(ActionEvent e){

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Vincular Pelicula y Genero");
        dialog.setHeaderText("Por favor, utilice los id mostrados de las peliculas y genero");
        dialog.setResizable(false);
        Label label1 = new Label("id de la pelicula a seleccionar: ");
        TextField texto1 = new TextField();
        Label label2 = new Label("id del genero a seleccionar: ");
        TextField texto2 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1,1,1);
        grid.add(texto1,2,1);
        grid.add(label2,1,2);
        grid.add(texto2,2,2);

        dialog.getDialogPane().setContent(grid);


        ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            public String call(ButtonType b) {

                if(b == buttonTypeOk) {
                    if(!texto1.getText().isEmpty() && !texto2.getText().isEmpty()) {
                        String m = texto1.getText().toString().trim() +" "+texto2.getText().toString().trim();
                        System.out.println(m);
                        return m;

                    }else{
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Dejaste el espacio vacio");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<String> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            try{
            String[] numbers = m1.get().split(" ");
            int idPelicula = Integer.parseInt(numbers[0]);
            int idGenero = Integer.parseInt(numbers[1]);

            System.out.println(idPelicula + " " + idGenero);

            if(connection.lookThatFoundPelicula(idPelicula) && connection.lookThatFoundGenero(idGenero)){

                Pelicula pe = connection.getPelicula(idPelicula);
                connection.vincularPeliculaYGenero(pe,idGenero);
                refreshInformationPeliculas();

            }else{
                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("No existe ninguna pelicula o genero");
                gameOver.showAndWait();
            }

            }catch (NumberFormatException e1){

                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Formato invalido");
                gameOver.showAndWait();

            }

        }


    }

    public void deleteMovie(ActionEvent e){

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Eliminar una pelicula");
        dialog.setHeaderText("Por favor, utilice los id mostrados para eliminar una pelicula");
        dialog.setResizable(false);
        Label label1 = new Label("id de la pelicula a eliminar: ");
        TextField texto1 = new TextField();


        GridPane grid = new GridPane();
        grid.add(label1,1,1);
        grid.add(texto1,2,1);

        dialog.getDialogPane().setContent(grid);


        ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            public String call(ButtonType b) {

                if(b == buttonTypeOk) {
                    if(!texto1.getText().isEmpty()) {
                        String m = texto1.getText().toString().trim();

                        return m;

                    }else{
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Dejaste el espacio vacio");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<String> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            try{

                int idpelicula = Integer.parseInt(m1.get());
                if(connection.lookThatFoundPelicula(idpelicula)){

                    connection.eliminarPelicula(idpelicula);
                    refreshInformationPeliculas();
                    System.out.println("LETS FUCKING GOOOOOOOOOOOOOOOOOOO");

                }else{
                    Alert gameOver = new Alert(AlertType.INFORMATION);
                    gameOver.setTitle("ERROR");
                    gameOver.setHeaderText("No existe ninguna pelicula o actor");

                    gameOver.showAndWait();
                }

            }catch (NumberFormatException e1){

                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Formato invalido");
                gameOver.showAndWait();

            }

        }


    }

    public void deleteActor(ActionEvent e){

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Eliminar una Actor");
        dialog.setHeaderText("Por favor, utilice los id mostrados para eliminar una Actor");
        dialog.setResizable(false);
        Label label1 = new Label("id del Actor a eliminar: ");
        TextField texto1 = new TextField();


        GridPane grid = new GridPane();
        grid.add(label1,1,1);
        grid.add(texto1,2,1);

        dialog.getDialogPane().setContent(grid);


        ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            public String call(ButtonType b) {

                if(b == buttonTypeOk) {
                    if(!texto1.getText().isEmpty()) {
                        String m = texto1.getText().toString().trim();

                        return m;

                    }else{
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Dejaste el espacio vacio");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<String> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            try{

                int idActor = Integer.parseInt(m1.get());
                if(connection.lookThatFoundActor(idActor)){

                    connection.eliminarActor(idActor);
                    refreshInformationActor();

                    System.out.println("LETS FUCKING GOOOOOOOOOOOOOOOOOOO");

                }else{
                    Alert gameOver = new Alert(AlertType.INFORMATION);
                    gameOver.setTitle("ERROR");
                    gameOver.setHeaderText("No existe ninguna pelicula o actor");
                    gameOver.showAndWait();
                }

            }catch (NumberFormatException e1){

                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Formato invalido");
                gameOver.showAndWait();

            }

        }


    }

    public void deleteGenre(ActionEvent e){

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Eliminar un Genero");
        dialog.setHeaderText("Por favor, utilice los id mostrados para eliminar un Genero");
        dialog.setResizable(false);
        Label label1 = new Label("id del Genero a eliminar: ");
        TextField texto1 = new TextField();


        GridPane grid = new GridPane();
        grid.add(label1,1,1);
        grid.add(texto1,2,1);

        dialog.getDialogPane().setContent(grid);


        ButtonType buttonTypeOk = new ButtonType("Accept", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String>() {
            public String call(ButtonType b) {

                if(b == buttonTypeOk) {
                    if(!texto1.getText().isEmpty()) {
                        String m = texto1.getText().toString().trim();

                        return m;

                    }else{
                        Alert gameOver = new Alert(AlertType.INFORMATION);
                        gameOver.setTitle("ERROR");
                        gameOver.setHeaderText("Dejaste el espacio vacio");
                        gameOver.showAndWait();
                    }
                }

                return null;
            }
        });

        Optional<String> m1 = dialog.showAndWait();

        if(m1.isPresent()){

            try{

                int idGenero = Integer.parseInt(m1.get());
                if(connection.lookThatFoundGenero(idGenero)){

                    connection.eliminarGenero(idGenero);
                    refreshInformationPeliculas();
                    refreshInformationGenero();
                    System.out.println("LETS FUCKING GOOOOOOOOOOOOOOOOOOO");

                }else{
                    Alert gameOver = new Alert(AlertType.INFORMATION);
                    gameOver.setTitle("ERROR");
                    gameOver.setHeaderText("No existe ninguna pelicula o actor");

                    gameOver.showAndWait();
                }

            }catch (NumberFormatException e1){

                Alert gameOver = new Alert(AlertType.INFORMATION);
                gameOver.setTitle("ERROR");
                gameOver.setHeaderText("Formato invalido");
                gameOver.showAndWait();

            }

        }

    }

    //----GETTERS AND SETTERS-----
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
