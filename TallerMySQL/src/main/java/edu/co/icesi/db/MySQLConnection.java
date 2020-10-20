package edu.co.icesi.db;

import edu.co.icesi.model.Actor;
import edu.co.icesi.model.Genero;
import edu.co.icesi.model.Pelicula;

import java.sql.*;
import java.util.ArrayList;

public class MySQLConnection {

    private Connection connection;

    public MySQLConnection(){

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hollywoodmovies","root","");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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

    public boolean lookAtNotRepeatGenero(String genero){
        boolean t = false;
        ArrayList<Genero> output = getAllGeneros();

        for(int i = 0; i < output.size() && !t;i++){
            if(output.get(i).getNombre().equalsIgnoreCase(genero)){
                t = true;
            }

        }
        return t;
    }

    public void agregarPelicula(Pelicula pelicula){

    }

    public void agregarActor(Actor actor){

    }

}
