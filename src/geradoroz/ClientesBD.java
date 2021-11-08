/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geradoroz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alcimar
 */
public class ClientesBD {

    private Connection conn;

    public boolean conectar() {
        try {
            String url = "jdbc:sqlite:base/baseCerttificado.db";
            this.conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public boolean desconectar() {
        try {
            if (this.conn.isClosed() == false) {
                this.conn.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }
    public Statement criarStatement() {
        try {
            return this.conn.createStatement();
        } catch (SQLException e) {
            return null;
        }
    }

    public PreparedStatement criarPreparedStatement(String sql) {
        try {
            return this.conn.prepareStatement(sql);
        } catch (SQLException e) {
            return null;
        }
    }

    public Connection getConexao() {
        return this.conn;
    }
}
