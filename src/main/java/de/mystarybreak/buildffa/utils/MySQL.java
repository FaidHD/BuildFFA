package de.mystarybreak.buildffa.utils;

import de.mystarybreak.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.*;

public class MySQL {

    private BuildFFA plugin;

    private String HOST;
    private String PORT;
    private String DATABASE;
    private String USER;
    private String PASSWORD;

    @Getter private Connection con;

    public MySQL(String host, String port, String database, String user, String password, BuildFFA plugin) {
        this.HOST = host;
        this.PORT = port;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;

        this.plugin = plugin;

        connect();
    }

    private void connect() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE + "?autoReconnect=true", this.USER, this.PASSWORD);
            Bukkit.getConsoleSender().sendMessage(plugin.getData().getPrefix() + "Die Verbindung zur MySQL wurde hergestellt!");
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(plugin.getData().getPrefix() + "Die Verbindung zur MySQL ist fehlgeschlagen! Fehler: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (con != null) {
                con.close();
                Bukkit.getConsoleSender().sendMessage(plugin.getData().getPrefix() + "Die Verbindung zur MySQL wurde Erfolgreich beendet!");
            }
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(plugin.getData().getPrefix() + "Fehler beim beenden der Verbindung zur MySQL! Fehler: " + e.getMessage());
        }
    }

    public void update(String qry) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return rs;
    }
}