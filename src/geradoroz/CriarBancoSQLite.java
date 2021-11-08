/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geradoroz;

import conexao.ConexaoSQLite;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alcimar
 */
public class CriarBancoSQLite {

    private final ConexaoSQLite conexaoSQLite;

    public CriarBancoSQLite(ConexaoSQLite pConexaoSQLite) {
        this.conexaoSQLite = pConexaoSQLite;
    }

    public void criartabelaCertificado() {
        String sql = "CREATE TABLE IF NOT EXISTS certificado"
                + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome VARCHAR(255),"
                + "endereco VARCHAR(255),"
                + "cidade VARCHAR(255) UNIQUE,"
                + "telefone VARCHAR(255),"
                + "dataos DATETIME"
                + ");";
        boolean conectou = false;
        try {
            conectou = this.conexaoSQLite.conectar();
            Statement stmt = this.conexaoSQLite.criarStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conectou) {
                this.conexaoSQLite.desconectar();
            }
        }

    }
}
