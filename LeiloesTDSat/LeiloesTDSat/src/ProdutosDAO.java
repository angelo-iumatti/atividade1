/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){        
        conn = new conectaDAO().getConnection();          
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){        
        return listagem;
    }
    
 public boolean salvar(ProdutosDTO produto) {

        String sql = "INSERT INTO produto (nome, valor, status) VALUES (?,?,?)";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return false;
        } finally {
            conectaDAO.closeConnection(conn, stmt);
        }
    }  

    public void venderProduto(ProdutosDTO produto) {

        String sql = "INSERT INTO produto (status) VALUE (?)";
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getStatus());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            conectaDAO.closeConnection(conn, stmt);
        }
    }  

    //Fazer a consulta entre as duas tabelas usando composição
    public List<ProdutosDTO> listarProdutosVendidos() {
        String sql = "SELECT * FROM lista_estoque;";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<ProdutosDTO> produtos = new ArrayList<>();

        try {
            //adicionando os dados no banco na Lista
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                //setar os dois objetos de consulta nome e status
                ProdutosDTO produto = new ProdutosDTO();
                produto.setNome(rs.getString("pdesc"));
                produto.setStatus(false);

                produtos.add(produto);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            conectaDAO.closeConnection(conn, stmt, rs);
        }
        return produtos;
    }
        
}

