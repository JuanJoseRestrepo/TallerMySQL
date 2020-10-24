package edu.co.icesi.db;

import edu.co.icesi.model.Actor;
import edu.co.icesi.model.Genero;
import edu.co.icesi.model.Pelicula;

import java.sql.*;
import java.util.ArrayList;

public class MySQLConnection {

    private Connection connection;
    private static MySQLConnection instance = null;

    public MySQLConnection(){

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hollywoodmovies","root","");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static synchronized MySQLConnection getInstance() {

        if(instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }


    public void closeDB(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void crearTablaGenero(){
        try {
            Statement statemnt = connection.createStatement();
            statemnt.execute("CREATE TABLE IF NOT EXISTS generos(id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(100))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void crearTablaActor(){
        try {
            Statement statemnt = connection.createStatement();
            statemnt.execute("CREATE TABLE IF NOT EXISTS actores(id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(100),apellido VARCHAR(100), edad INT )");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void crearTablaPelicula(){
        try {
            Statement statemnt = connection.createStatement();
            statemnt.execute("CREATE TABLE IF NOT EXISTS peliculas(id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(100), generoID INT)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void crearTablaPivote(){
        try {
            Statement statemnt = connection.createStatement();
            statemnt.execute("CREATE TABLE IF NOT EXISTS actores_peliculas(id INT PRIMARY KEY AUTO_INCREMENT, peliculasID INT, actoresID INT, FOREIGN KEY (peliculasID) REFERENCES peliculas(id), FOREIGN KEY(actoresID) REFERENCES actores(id))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Genero> getAllGeneros(){
        ArrayList<Genero> output = new ArrayList<Genero>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM generos";

            ResultSet resultados = statement.executeQuery(sql);

            while(resultados.next()){
                int id = resultados.getInt(resultados.findColumn("id"));
                String nombre = resultados.getString(resultados.findColumn("nombre"));
                output.add(new Genero(id,nombre));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public Pelicula getPelicula(int id){
        Pelicula pe = null;
        ArrayList<Pelicula> output = getAllPeliculas();

        for(int i = 0; i < output.size();i++){

            if(output.get(i).getId() == id){
                pe = output.get(i);
            }

        }

        return pe;

    }

    public Genero getGenero(int id){
        Genero ge = null;
        ArrayList<Genero> output = getAllGeneros();

        for(int i = 0; i < output.size();i++){

            if(output.get(i).getId() == id){
                ge = output.get(i);
            }

        }

        return ge;
    }

    public Actor getActor(int id){
        Actor ge = null;
        ArrayList<Actor> output = getAllActor();

        for(int i = 0; i < output.size();i++){

            if(output.get(i).getId() == id){
                ge = output.get(i);
            }

        }

        return ge;
    }


    public ArrayList<Actor> actoresXPeliculas(int peliculadID){

        ArrayList<Actor> output = new ArrayList<Actor>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT actores.id, actores.nombre, actores.apellido,actores.edad FROM (actores INNER JOIN actores_peliculas ON actores.id = actores_peliculas.actoresID) INNER JOIN peliculas ON actores_peliculas.peliculasID = peliculas.id WHERE peliculas.id="+ peliculadID;
            ResultSet resultados = statement.executeQuery(sql);

            while(resultados.next()){
                int id = resultados.getInt(resultados.findColumn("id"));
                String nombre = resultados.getString(resultados.findColumn("nombre"));
                String apellido = resultados.getString(resultados.findColumn("apellido"));
                int edad = resultados.getInt(resultados.findColumn("edad"));
                output.add(new Actor(id,nombre,apellido,edad));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;

    }

    public ArrayList<Pelicula> peliculasPorActores(int actorID){
        ArrayList<Pelicula> output = new ArrayList<Pelicula>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT peliculas.id, peliculas.nombre, peliculas.generoID FROM (peliculas INNER JOIN actores_peliculas ON peliculas.id = actores_peliculas.peliculasID) INNER  JOIN actores ON actores_peliculas.actoresID = actores.id WHERE actores.id="+ actorID;
            ResultSet resultados = statement.executeQuery(sql);

            while(resultados.next()){
                int id = resultados.getInt(resultados.findColumn("id"));
                String nombre = resultados.getString(resultados.findColumn("nombre"));
                int generoID = resultados.getInt(resultados.findColumn("generoID"));
                output.add(new Pelicula(id,nombre,generoID));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public ArrayList<Pelicula> getMoviesByGenre(int generoID1){
        ArrayList<Pelicula> output = new ArrayList<Pelicula>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT peliculas.id, peliculas.nombre FROM peliculas INNER JOIN generos ON peliculas.generoID = generos.id WHERE generos.id="+ generoID1;
            ResultSet resultados = statement.executeQuery(sql);

            while(resultados.next()){
                int id = resultados.getInt(resultados.findColumn("id"));
                String nombre = resultados.getString(resultados.findColumn("nombre"));
                output.add(new Pelicula(id,nombre,generoID1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public ArrayList<Pelicula> getAllPeliculas(){
        ArrayList<Pelicula> output = new ArrayList<Pelicula>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM peliculas";
            ResultSet resultados = statement.executeQuery(sql);

            while(resultados.next()){
                int id = resultados.getInt(resultados.findColumn("id"));
                String nombre = resultados.getString(resultados.findColumn("nombre"));
                int generoID = resultados.getInt(resultados.findColumn("generoID"));
                output.add(new Pelicula(id,nombre,generoID));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public ArrayList<Actor> getAllActor(){
        ArrayList<Actor> output = new ArrayList<Actor>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM actores";
            ResultSet resultados = statement.executeQuery(sql);

            while(resultados.next()){
                int id = resultados.getInt(resultados.findColumn("id"));
                String nombre = resultados.getString(resultados.findColumn("nombre"));
                String apellido = resultados.getString(resultados.findColumn("apellido"));
                int edad = resultados.getInt(resultados.findColumn("edad"));
                output.add(new Actor(id,nombre,apellido,edad));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public boolean lookThatFoundActor(int id){
        boolean t = false;
        ArrayList<Actor> output = getAllActor();

        if(output.size() != 0){
            for(int i = 0; i < output.size() && !t;i++){
                if(output.get(i).getId() == id){
                    t = true;
                }
            }
        }
        return t;
    }

    public boolean lookThatFoundPelicula(int id){
        boolean t = false;
        ArrayList<Pelicula> output = getAllPeliculas();

        if(output.size() != 0){
            for(int i = 0; i < output.size() && !t;i++){
                if(output.get(i).getId() == id){
                    t = true;
                }
            }
        }
        return t;
    }

    public boolean lookThatFoundGenero(int id){
        boolean t = false;
        ArrayList<Genero> output = getAllGeneros();

        if(output.size() != 0){
            for(int i = 0; i < output.size() && !t;i++){
                if(output.get(i).getId() == id){
                    t = true;
                }
            }
        }
        return t;
    }


    public boolean lookAtNotRepeatPeliculas(String nombre){
        boolean t = false;
        ArrayList<Pelicula> output = getAllPeliculas();

        if(output.size() != 0){

            for(int i = 0; i < output.size() && !t;i++){
                if(output.get(i).getNombre().equalsIgnoreCase(nombre)){
                    t = true;
                }

            }

        }

        return t;
    }

    public boolean lookAtNotRepeatGenero(String genero){
        boolean t = false;
        ArrayList<Genero> output = getAllGeneros();
        if(output.size() != 0){
            for(int i = 0; i < output.size() && !t;i++){
                if(output.get(i).getNombre().equalsIgnoreCase(genero)){
                    t = true;
                }

            }
        }
        return t;
    }

    public boolean lookAtNotRepeatActor(String nombre , String apellido){
        boolean t = false;
        ArrayList<Actor> output = getAllActor();
        if(output.size() != 0){
            for(int i = 0; i < output.size() && !t;i++){
                if(output.get(i).getNombre().equalsIgnoreCase(nombre) && output.get(i).getApellido().equalsIgnoreCase(apellido)){
                    t = true;
                }
            }
        }
        return t;
    }

    public void agregarGenero(Genero genero){
        try {
            Statement statement = connection.createStatement();
            String sql = ("INSERT INTO generos(nombre) VALUES('$NOMBRE')")
                    .replace("$NOMBRE", genero.getNombre());
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void agregarPelicula(Pelicula pelicula){
        try {
            Statement statement = connection.createStatement();
            String sql = ("INSERT INTO peliculas(nombre,generoID) VALUES('$NOMBRE',NULL)")
                    .replace("$NOMBRE", pelicula.getNombre());
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void agregarActor(Actor actor){
        try {
            Statement statement = connection.createStatement();
            String sql = ("INSERT INTO actores(nombre,apellido,edad) VALUES('$NOMBRE','$APELLIDO',$EDAD)")
                    .replace("$NOMBRE", actor.getNombre())
                    .replace("$APELLIDO", actor.getApellido())
                    .replace("$EDAD", "" + actor.getEdad());
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void vincularPeliculaYActor(int idPelicula,int idActor){

        try {
            Statement statement = connection.createStatement();
            String sql = ("INSERT INTO actores_peliculas(peliculasID,actoresID) VALUES($PELICULASID,$ACTORESID)")
                    .replace("$PELICULASID","" + idPelicula)
                    .replace("$ACTORESID", "" + idActor);
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void vincularPeliculaYGenero(Pelicula pelicula, int id){
        try {
            Statement statement = connection.createStatement();
            String sql = ("UPDATE peliculas SET generoID = $GENEROID  WHERE id = $ID ")
                    .replace("$GENEROID" ,"" + id)
                    .replace("$ID", "" + pelicula.getId());
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void eliminarGenero(int generoID){
        try {
            Statement statement = connection.createStatement();
            ArrayList<Pelicula> output = getMoviesByGenre(generoID);
            for(int i = 0; i < output.size();i++){
                int pelicula = output.get(i).getId();
                eliminarPelicula(pelicula);
            }
            String sql = ("DELETE FROM generos WHERE generos.id = $PELICULASID")
                    .replace("$PELICULASID" ,"" + generoID);
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void eliminarPelicula(int idPelicula){
        try {
            Statement statement = connection.createStatement();
            String sql = ("DELETE FROM actores_peliculas WHERE peliculasID = $PELICULASID")
                    .replace("$PELICULASID" ,"" + idPelicula);
            String sql1 = ("DELETE FROM peliculas WHERE id = $PELICULASID")
                    .replace("$PELICULASID","" + idPelicula);
            statement.execute(sql);
            statement.execute(sql1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void eliminarActor(int idActor){
        try {
            Statement statement = connection.createStatement();
            String sql = ("DELETE FROM actores_peliculas WHERE actoresID = $ACTORID")
                    .replace("$ACTORID" ,"" + idActor);
            String sql1 = ("DELETE FROM actores WHERE id = $ACTORID")
                    .replace("$ACTORID","" + idActor);
            statement.execute(sql);
            statement.execute(sql1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
