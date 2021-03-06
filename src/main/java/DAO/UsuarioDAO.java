/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import BD.Conexao;
import java.util.List;
import Objetos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author felip
 */
public class UsuarioDAO {
    public List<Usuario> read() {
        Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<>();
        
        
        try {
            stmt = con.prepareStatement("SELECT * FROM tbl_usuarios");
            rs = stmt. executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setTipo(rs.getString("tipo"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Falha ao obter dados" + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
        return usuarios;
    }

    public void create(Usuario u) {
       Connection con = Conexao.getConnection();
       PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO tbl_usuarios (nome, login, senha, tipo) VALUES ( ?, ?, ?, ?)");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getLogin());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getTipo());
            
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Cadastrado com Sucesso! ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Falha ao obter dados " + e);
        } finally {
            Conexao.closeConnection(con, stmt);
        }
    }

    public void update(Usuario u) {
         Connection con = Conexao.getConnection();
         PreparedStatement stmt = null;
         
         try {
            stmt = con.prepareStatement("UPDATE tbl_usuarios SET nome = ?, login = ?, senha = ?, tipo = ? WHERE id = ? ");
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getLogin());
            stmt.setString(3, u.getSenha());
            stmt.setString(4, u.getTipo());
            stmt.setInt(5, u.getId());
            
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Atualizado com sucesso! ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Falha ao atualizar " + e);
        }finally {
             Conexao.closeConnection(con, stmt);
         }
    }
   
    public void delete(Usuario u) {
         Connection con = Conexao.getConnection();
         PreparedStatement stmt = null;
         
         try {
            stmt = con.prepareStatement("DELETE FROM tbl_usuarios WHERE id = ?");
            stmt.setInt(1,u.getId());
            
            stmt.execute();
            JOptionPane.showMessageDialog(null,"Removido com sucesso! ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Falha ao deletar " + e);
        } finally {
             Conexao.closeConnection(con, stmt);
    }
  }
    
   public boolean checaLogin(String login, String senha) {
       Connection con = Conexao.getConnection();
       PreparedStatement stmt = null;
       ResultSet rs = null;
       boolean logado = false;
       
       try {
           stmt = con.prepareStatement("Select * FROM tbl_usuarios WHERE login = ? and senha = ?");
           stmt.setString(1,login);
           stmt.setString(2, senha);
           rs = stmt.executeQuery();
           if (rs.next()) {
               logado = true;
           }
       } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,"Usu??rio n??o encontrado");
       } finally {
           Conexao.closeConnection(con, stmt, rs);
       }
       return logado;
   }
   
   public Usuario dadosUsuario (String login, String senha) {
       Connection con = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario u = new Usuario();
        
         try {
            stmt = con.prepareStatement("SELECT * FROM tbl_usuarios WHERE login = ? AND senha = ?");
            stmt.setString(1, login);
            stmt.setString(2, senha);
            rs = stmt. executeQuery();
          
            while (rs.next()) {
                
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setTipo(rs.getString("tipo"));
                
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Falha ao obter dados" + e);
        } finally {
            Conexao.closeConnection(con, stmt, rs);
        }
         return u;
   }
}
