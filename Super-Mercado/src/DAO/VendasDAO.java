package DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Connection.ConnectionFactory;
import Model.Clientes;
import Model.Vendas;

public class VendasDAO {

    private Connection connection;
    private List<Vendas> vendas;

    // Construtor
    public VendasDAO() {

        this.connection = ConnectionFactory.getConnection();
        System.out.println("Conexão 'VendasDAO' estabelecida.");
    }

    /* Métodos aqui */

    public void criarTabelaVendas() {
        String sqlCriarTabelaVendas = "CREATE TABLE IF NOT EXISTS vendas_mercado (CODIGO_BARRAS VARCHAR(5) PRIMARY KEY NOT NULL, CPF VARCHAR(255), DESCONTO_APLICADO VARCHAR(255), QUANTIDADE VARCHAR(255), TOTAL VARCHAR(255), HORARIO VARCHAR(255), DIA VARCHAR(255))";

        try (Statement stmt = this.connection.createStatement()) {

            /* Ejetor de código SQL */
            stmt.execute(sqlCriarTabelaVendas);
            System.out.println("Tabela 'vendas_mercado' criada com sucesso.");
        } catch (SQLException e) {

            throw new RuntimeException("Erro ao criar a tabela 'vendas_mercado'.", e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    public void realizarVenda(String codigoBarras, String cliente, String quantidade, String descontoAplicado,
            String total) {
        PreparedStatement stmtInserir = null;
        PreparedStatement stmsDeletar = null;

        // Instanciando vendas
        vendas = new ArrayList<>();

        String sqlCadastrarVenda = "INSERT INTO vendas_mercado (codigoBarras, cliente, quantidade, descontoAplicado, total) VALUES(?, ?, ?, ?, ?)";
        String deletarItemVendido = "DELETE FROM vendas_mercado WHERE codigoBarras = ?";
        try {
            stmtInserir = this.connection.prepareStatement(sqlCadastrarVenda);

            /* Ejetor SQL */
            stmtInserir.setString(1, codigoBarras);
            stmtInserir.setString(2, cliente);
            stmtInserir.setString(3, quantidade);
            stmtInserir.setString(4, descontoAplicado);
            stmtInserir.setString(5, total);


            int linhasAfetadas = stmtInserir.executeUpdate();
            System.out.println(
                    linhasAfetadas > 0 ? "Venda cadastrada com sucesso." : "Não foi possível cadastrar a venda.");
        } catch (SQLException e) {
            System.out.println("Não foi possível realizar a venda.");
            e.printStackTrace();
        }finally{
            try {
                ConnectionFactory.closeConnection(connection, stmtInserir);
            } catch (Exception e) {
                System.out.println("Não foi possível fechar as conexões em realizarVenda.");
            }
        }

    }


}