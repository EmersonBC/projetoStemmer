package servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionFactory {

	private static final String DRIVER = "org.postgresql.driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/webservice";
	private static final String USUARIO = "emerson";
	private static final String SENHA = "12345";
	
	public Connection criarConexao(){
		Connection conexao = null;
		
		try {
			Class.forName(DRIVER);
			conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
			
		} catch (Exception e) {
			System.out.println("Erro ao criar conexao com o banco: "+ URL);
			e.printStackTrace();
		}
		
		return conexao;
	}
	
	public void fecharConexao(Connection conexao, PreparedStatement pstmt, ResultSet rs){
		try {
			if (conexao!=null) {
				conexao.close();
			}
			if (pstmt!=null) {
				pstmt.close();
			}
			if (rs!=null) {
				rs.close();
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao fechar conexao com o banco: "+ URL);
		}
	}
}
