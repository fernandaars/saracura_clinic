import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class SQLiteConnection {

	private SQLiteConnection() {
		throw new AssertionError("Esta classe nao deve ser instanciada.");
	}

	private static Connection connect() {
		String url = "jdbc:sqlite:saracura.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	// Retorna todas as entradas de uma tabela informada
	public static ResultSet selectAll(String table) {
		String sql = "SELECT * FROM " + table;
		ResultSet rs = null;
		
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rs;
	}
	
	// Retorna todas as consultas marcadas para um médico informado
	// TODO: Generalizar para qualquer recurso
	public static ResultSet selectConsultas(int idMedico) {
		String sql = "SELECT C.* FROM CONSULTAS C, MEDICOS_CONSULTAS A " + 
				"WHERE A.ID_MEDICO = ? " + 
				"AND A.ID_CONSULTA = C.ID";
		ResultSet rs = null;
				
		try {
			Connection conn = connect();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idMedico);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return rs;
	}
	
	// Insere entrada na tabela de médicos
	public static int insertMedico(Medico m) throws SQLException {
		String sql = "INSERT INTO MEDICOS(NOME, HORA_INICIO, DURAC_JORNADA, DURAC_CONSULTA, DIA_ATEND1, "
				+ "DIA_ATEND2, " + "DIA_ATEND3, " + "ESPECIALIDADE) " + "VALUES(?,?,?,?,?,?,?,?)";
		
		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {			
			pstmt.setString(1, m.getNome());
			pstmt.setInt(2, m.getHoraInicioDisponibilidade());
			pstmt.setInt(3, m.getDuracaoJornada());
			pstmt.setInt(4, m.getIntervaloConsulta());
			pstmt.setInt(5, m.getDiasAtendimento()[0].getValue());
			pstmt.setInt(6, m.getDiasAtendimento()[1].getValue());
			pstmt.setInt(7, m.getDiasAtendimento()[2].getValue());
			pstmt.setInt(8, m.getEspecialidade().getID());
			pstmt.executeUpdate();
			
			return pstmt.getGeneratedKeys().getInt(1);
		} catch (SQLException ex) {
			throw ex;
		}
	}
	
	// Insere entrada na tabela de especialidades
	public static int insertEspecialidade(Especialidade e) throws SQLException {
		String sql = "INSERT INTO ESPECIALIDADES(DESCRICAO) VALUES(?)";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, e.toString());
			pstmt.executeUpdate();
			
			return pstmt.getGeneratedKeys().getInt(1);
		} catch (SQLException ex) {
			throw ex;
		}
	}
	
	// Insere entrada na tabela de consultas, e relaciona a médico com o qual a consulta está marcada
	// TODO: Generalizar para qualquer recurso
	public static int insertConsulta(Consulta c, Recurso r) throws SQLException {
		String sql = "INSERT INTO CONSULTAS(NOME_CLIENTE, TEL_CLIENTE, HORARIO, "
			+ "MATRICULA_CONVENIO, TIPO_PAGAMENTO, AUTORIZADA) VALUES(?,?,?,?,?,?)";
		int idConsulta;

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, c.getNomeCliente());
			pstmt.setString(2, c.getTelefoneCliente());
			pstmt.setString(3, c.getHorario().toString());
			pstmt.setInt(4, (c.isAtendimentoConvenio()) ? c.getMatriculaConvenio() : java.sql.Types.INTEGER);
			pstmt.setInt(5, (c.isAtendimentoConvenio()) ? 0 : c.getTipoPagamento() );
			pstmt.setInt(6, (c.isAutorizada()) ? 1 : 0);
			pstmt.executeUpdate();
			
			idConsulta = pstmt.getGeneratedKeys().getInt(1);
		} catch (SQLException ex) {
			throw ex;
		}
		
		sql = "INSERT INTO MEDICOS_CONSULTAS(ID_MEDICO, ID_CONSULTA) VALUES(?,?)";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, r.getID());
			pstmt.setInt(2, idConsulta);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		}
		
		return idConsulta;
	}
}