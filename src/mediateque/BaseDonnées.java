package mediateque;

import com.mysql.jdbc.Connection;
import document.Abonne;
import document.Cd;
import document.Livre;
import mediateque.mediatheque;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class BaseDonnées {

    static final String DB_URL = "jdbc:mysql://localhost:3306/mediatheque?useSSL=false"; // MySQL
    static final String USER = "root";
    static final String PASS = "root";

    private Connection conn;
    private Statement stmt;

    public BaseDonnées() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database");
            stmt = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }


    public void close() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to close database resources", e);
        }
    }
    public void ReadAbonnee() {
        try {
            ResultSet resultats;
            resultats = this.stmt.executeQuery("SELECT id, nom, date_de_naissance FROM user");
            while (resultats.next()) {
                int id = resultats.getInt("id");
                String nom = resultats.getString("nom");
                Date date_de_naissance = resultats.getDate("date_de_naissance");
                Abonne ab = new Abonne(id, nom, date_de_naissance);
                mediatheque.ajouterAbonne(ab);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ReadCd() {
        try {
            ResultSet resultats;
            resultats = this.stmt.executeQuery("SELECT cd.id, cd.id_document, cd.adulte, document.titre " +
                    "FROM cd " +
                    "INNER JOIN document ON cd.id_document = document.id " +
                    "WHERE document.type = 'CD'");
            while (resultats.next()) {
                int id = resultats.getInt("id");
                int id_document = resultats.getInt("id_document");
                Boolean adulte = resultats.getBoolean("adulte");
                String titre = resultats.getString("titre");

                Cd cd = new Cd(id, id_document, titre, adulte);
                mediatheque.ajouterCd(cd);
            }
            resultats.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ReadLivre() {
        try {
            ResultSet resultats;
            resultats = this.stmt.executeQuery("SELECT livre.id, livre.id_document, livre.nombre_de_pages, document.titre " +
                    "FROM livre " +
                    "INNER JOIN document ON livre.id_document = document.id " +
                    "WHERE document.type = 'Livre'");
            while (resultats.next()) {
                int id = resultats.getInt("id");
                int id_document = resultats.getInt("id_document");
                int nombreDePages = resultats.getInt("nombre_de_pages");
                String titre = resultats.getString("titre");

                Livre livre = new Livre(id, id_document, titre, nombreDePages);
                mediatheque.ajouterLivre(livre);
            }
            resultats.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
