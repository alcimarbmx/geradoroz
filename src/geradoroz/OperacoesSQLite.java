/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geradoroz;

import conexao.ConexaoSQLite;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alcimar
 */
public class OperacoesSQLite {

    /**
     * @param args the command line arguments
     */
    ConexaoSQLite conn = new ConexaoSQLite();
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    public boolean CadastrarCliente(Clientes cliente) {
        String sql = "INSERT INTO certificado(nome, telefone, email, razao_social, cnpj_cpf, data_nasc, data_certificado) VALUES (?,?,?,?,?,?,?);";
        PreparedStatement ps = conn.criarPreparedStatement(sql);
        try {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getCidade());

            int res = ps.executeUpdate();
            if (res == 1) {
                System.out.println("Query inserida.");
                return true;
            } else {
                System.out.println("Query nao inserida.");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean AtualizarCadastro(Clientes cliente) {
       
        String sql = "UPDATE tblCertificado SET nome = ?,  "
                + "telefone = ?, email = ?, razao_social = ?, dataCertificado = ? WHERE cnpj_cpf = ?;";
        PreparedStatement ps = conn.criarPreparedStatement(sql);
        try {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getCidade());
            
            int res = ps.executeUpdate();
            if (res == 1) {
                System.out.println("Query inserida.");
                return true;
            } else {
                System.out.println("Query nao inserida.");

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ResultSet Pesquisar(String pesquisa) {
        String query = "SELECT * FROM certificado WHERE nome LIKE %?%%?%";

        try {
            pstmt = conn.criarPreparedStatement(query);
            pstmt.setString(1, pesquisa);
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(OperacoesSQLite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean Deletar(String cnpj_cpf) {
        String query = "DELETE FROM certificado WHERE cnpj_cpf = ?";
        boolean i = false;
        try {
            pstmt = conn.criarPreparedStatement(query);
            pstmt.setString(1, cnpj_cpf);
            i = pstmt.execute();
            return i;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
        return i;
    }

//    public static void main(String[] args) {
//        // TODO code application logic here
//        ConexaoSQLite conn = new ConexaoSQLite();
//        /*
//        CriarBancoSQLite criarBancoSQLite = new CriarBancoSQLite(conn);
//        conn.conectar();
//        Pessoa p = new Pessoa();
//        p.setId(2);
//        p.setNome("Lulu");
//        p.setIdade(21);
//        String sql = "INSERT INTO tbl_pessoa(id, nome, idade) VALUES (?,?,?);";
//        PreparedStatement ps = conn.criarPreparedStatement(sql);
//        try {
//            ps.setInt(1, p.getId());
//            ps.setString(2, p.getNome());
//            ps.setInt(3, p.getIdade());
//            int res = ps.executeUpdate();
//            if (res == 1) {
//                System.out.println("Query inserida.");
//            } else {
//                System.out.println("Query nao inserida.");
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            if (ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(LembreteCertificado.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            conn.desconectar();
//
//        }
//        criarBancoSQLite.criartabelaPessoa();
//        conn.desconectar();
//         */
// /*
//        ResultSet rs = null;
//        Statement stmt = null;
//        conn.conectar();
//
//        String query = "SELECT * FROM tbl_pessoa";
//
//        stmt = conn.criarStatement();
//        try {
//            rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                System.out.print(rs.getInt("id"));
//                System.out.print(rs.getString("nome"));
//                System.out.print(rs.getInt("idade"));
//                System.out.print('\n');
//            }
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(LembreteCertificado.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//         */
//        ResultSet rs = null;
//        PreparedStatement pstmt = null;
//        //conn.desconectar();
//        conn.conectar();
//
//        String query = "SELECT * FROM tbl_pessoa WHERE id = ?;";
//
//        try {
//            pstmt = conn.criarPreparedStatement(query);
//            pstmt.setInt(1, 10);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                System.out.print(rs.getInt("id"));
//                System.out.print(rs.getString("nome"));
//                System.out.print(rs.getInt("idade"));
//                System.out.print('\n');
//            }
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        } finally {
//            try {
//                rs.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(OperacoesSQLite.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}
