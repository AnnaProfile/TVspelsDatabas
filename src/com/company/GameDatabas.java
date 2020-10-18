package com.company;

import java.sql.*;

public class GameDatabas {
    Connection conn = null;


    public GameDatabas() {
        connectToSQLiteDb();

        getAllGamesFromKonsol(2);
        System.out.println("-----------------------");
       /* getAllGamesFromKonsol(2);
        System.out.println("-----------------------");
        getAllGamesFromKonsol(3);
        System.out.println("-----------------------");
        getAllGamesFromKonsol(4);*/


       // postNewGame("Halo 4",2,6);
       // postNewGenre("Platformer");
        //postNewKonsol("Nintendo Switch", "2017-03-03");
    }


    void connectToSQLiteDb() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:TVspelsDatabas.db");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void postNewKonsol(String konsol, String Rdate) {

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO konsoler(name,releaseDate) VALUES(?, ?)");
            stmt.setString(1, konsol);
            stmt.setString(2, Rdate);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public void postNewGenre(String name) {

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO genre(name) VALUES(?)");
            stmt.setString(1, name);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void postNewGame(String name, Integer konsol, Integer genre) {

        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO games(name,konsol,genre) VALUES(?, ?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, konsol);
            stmt.setInt(3, genre);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public void getAllGamesFromKonsol(int konsolID) {
        // "SELECT * FROM persons WHERE persons.age > 15"
        try {

            // dynamic query not preventing SQL-injection
//            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM games WHERE games.konsol == konsolID);

            // dynamic query preventing SQL-injection
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM games INNER JOIN genre ON games.genre = genre.id WHERE games.konsol == ?");
            stmt.setInt(1, konsolID);


            ResultSet resultSet = stmt.executeQuery();
            // resultSet is an iterator (another type of array/list)

           PreparedStatement stmt2 = conn.prepareStatement("SELECT nameKonsol FROM konsoler WHERE konsoler.id == ?");
            stmt2.setInt(1, konsolID);
            ResultSet resultSet2 = stmt2.executeQuery();

            String KonsolName=resultSet2.getString("nameKonsol");

            System.out.printf("Konsol: %s \n", KonsolName);


            while(resultSet.next()) {
               // int gameId = resultSet.getInt("id");
                String gameName = resultSet.getString("name");
                String gameGenre = resultSet.getString("nameGenre");
                //int gameKonsol = resultSet.getInt("konsol");

                System.out.printf("game: %s, genre: %s \n",gameName, gameGenre);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
