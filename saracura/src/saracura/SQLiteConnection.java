package saracura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

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

    // Retorna todas as consultas marcadas para um m�dico informado
    public static ArrayList<Consulta> selectConsultas(int idMedico) throws SQLException {
        ArrayList<Consulta> consultas = new ArrayList<Consulta>();

        String sql = "SELECT C.* FROM CONSULTAS C, MEDICOS_CONSULTAS A "
                + "WHERE A.ID_MEDICO = ? "
                + "AND A.ID_CONSULTA = C.ID";
        ResultSet rs;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idMedico);
        rs = pstmt.executeQuery();

        while (rs.next()) {

            // CONSULTA MARCADA PELO CONVENIO
            if (rs.getInt("TIPO_PAGAMENTO") == 0) {
                Consulta novaConsulta = new Consulta(rs.getString("NOME_CLIENTE"), rs.getString("TEL_CLIENTE"),
                        LocalDateTime.parse(rs.getString("HORARIO")), 999999, rs.getInt("MATRICULA_CONVENIO"));
                consultas.add(novaConsulta);
                novaConsulta.setID(rs.getInt("ID"));
            } // CONSULTA PARTICULAR
            else {
                Consulta novaConsulta = new Consulta(rs.getString("NOME_CLIENTE"), rs.getString("TEL_CLIENTE"),
                        LocalDateTime.parse(rs.getString("HORARIO")), rs.getInt("TIPO_PAGAMENTO"));
                consultas.add(novaConsulta);
                novaConsulta.setID(rs.getInt("ID"));
            }
        }

        return consultas;
    }

    // Retorna todos os exames para um equipamento informado
    public static ArrayList<Consulta> selectExames(int idEquipamento) throws SQLException {
        ArrayList<Consulta> consultas = new ArrayList<Consulta>();

        String sql = "SELECT C.* FROM CONSULTAS C, EQUIPAMENTOS_EXAMES E "
                + "WHERE E.ID_EQUIPAMENTO = ? "
                + "AND E.ID_CONSULTA = C.ID";
        ResultSet rs;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idEquipamento);
        rs = pstmt.executeQuery();

        while (rs.next()) {

            // CONSULTA MARCADA PELO CONVENIO
            if (rs.getInt("TIPO_PAGAMENTO") == 0) {
                Consulta novaConsulta = new Consulta(rs.getString("NOME_CLIENTE"), rs.getString("TEL_CLIENTE"),
                        LocalDateTime.parse(rs.getString("HORARIO")), 999999, rs.getInt("MATRICULA_CONVENIO"));
                consultas.add(novaConsulta);
                novaConsulta.setID(rs.getInt("ID"));
            } // CONSULTA PARTICULAR
            else {
                Consulta novaConsulta = new Consulta(rs.getString("NOME_CLIENTE"),
                        rs.getString("TEL_CLIENTE"),
                        LocalDateTime.parse(rs.getString("HORARIO")),
                        rs.getInt("TIPO_PAGAMENTO"));
                consultas.add(novaConsulta);
                novaConsulta.setID(rs.getInt("ID"));
            }
        }

        return consultas;
    }

    // Procura medicos que atendem uma especialidade
    public static ArrayList<Medico> selectMedicosPorEspecialidade(int idEspecialidade) throws SQLException {
        ArrayList<Medico> medicos = new ArrayList<Medico>();

        String sql = "SELECT * FROM MEDICOS WHERE ESPECIALIDADE = ?";
        ResultSet rs;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idEspecialidade);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            Medico novoMedico = new Medico(rs.getString("NOME"),
                    rs.getInt("HORA_INICIO"),
                    rs.getInt("DIA_ATEND1"),
                    rs.getInt("DIA_ATEND2"),
                    rs.getInt("DIA_ATEND3"),
                    rs.getInt("DURAC_CONSULTA"),
                    idEspecialidade);
            novoMedico.setID(rs.getInt("ID"));
            medicos.add(novoMedico);
        }

        return medicos;
    }

    // Procura equipamentos para algum tipo de exame
    public static ArrayList<Equipamento> selectEquipamentosPorTipoExame(int idTipoExame) throws SQLException {
        ArrayList<Equipamento> equipamentos = new ArrayList<Equipamento>();

        String sql = "SELECT * FROM EQUIPAMENTOS WHERE TIPO_EXAME = ?";
        ResultSet rs;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idTipoExame);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            Equipamento novoEquipamento = new Equipamento(rs.getString("DESCRICAO"), idTipoExame);
            novoEquipamento.setID(rs.getInt("ID"));
            equipamentos.add(novoEquipamento);
        }

        return equipamentos;
    }

    // Insere entrada na tabela de m�dicos
    public static int insertMedico(Medico m) throws SQLException {
        String sql = "INSERT INTO MEDICOS(NOME, HORA_INICIO, DURAC_JORNADA, DURAC_CONSULTA, DIA_ATEND1, "
                + "DIA_ATEND2, DIA_ATEND3, ESPECIALIDADE) VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, m.getNome());
            pstmt.setInt(2, m.getHoraInicioDisponibilidade());
            pstmt.setInt(3, m.getDuracaoJornada());
            pstmt.setInt(4, m.getIntervaloConsulta());
            pstmt.setInt(5, m.getDiasAtendimento()[0].getValue());
            pstmt.setInt(6, m.getDiasAtendimento()[1].getValue());
            pstmt.setInt(7, m.getDiasAtendimento()[2].getValue());
            pstmt.setInt(8, m.getEspecialidade());
            pstmt.executeUpdate();

            return pstmt.getGeneratedKeys().getInt(1);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    // Insere entrada na tabela de especialidades
    public static int insertEspecialidade(String e) throws SQLException {
        String sql = "INSERT INTO ESPECIALIDADES(DESCRICAO) VALUES(?)";

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, e);
        pstmt.executeUpdate();

        return pstmt.getGeneratedKeys().getInt(1);

    }

    // Insere entrada na tabela de consultas, e relaciona a m�dico com o qual a
    // consulta est� marcada
    public static int insertConsulta(Consulta c, Medico m) throws SQLException {
        String sql = "INSERT INTO CONSULTAS(NOME_CLIENTE, TEL_CLIENTE, HORARIO, "
                + "MATRICULA_CONVENIO, TIPO_PAGAMENTO, AUTORIZADA) VALUES(?,?,?,?,?,?)";
        int idConsulta;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, c.getNomeCliente());
        pstmt.setString(2, c.getTelefoneCliente());
        pstmt.setString(3, c.getHorario().toString());
        pstmt.setInt(4, (c.isAtendimentoConvenio()) ? c.getMatriculaConvenio() : java.sql.Types.INTEGER);
        pstmt.setInt(5, (c.isAtendimentoConvenio()) ? 0 : c.getTipoPagamento());
        pstmt.setInt(6, (c.isAutorizada()) ? 1 : 0);
        pstmt.executeUpdate();

        idConsulta = pstmt.getGeneratedKeys().getInt(1);

        sql = "INSERT INTO MEDICOS_CONSULTAS(ID_MEDICO, ID_CONSULTA) VALUES(?,?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, m.getID());
        pstmt.setInt(2, idConsulta);
        pstmt.executeUpdate();

        return idConsulta;
    }

    // Insere entrada na tabela de exames, e relaciona a equipamento com o qual o
    // exame est� marcado
    public static int insertExame(Consulta c, Equipamento eq) throws SQLException {
        String sql = "INSERT INTO CONSULTAS(NOME_CLIENTE, TEL_CLIENTE, HORARIO, "
                + "MATRICULA_CONVENIO, TIPO_PAGAMENTO, AUTORIZADA) VALUES(?,?,?,?,?,?)";
        int idConsulta;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, c.getNomeCliente());
        pstmt.setString(2, c.getTelefoneCliente());
        pstmt.setString(3, c.getHorario().toString());
        pstmt.setInt(4, (c.isAtendimentoConvenio()) ? c.getMatriculaConvenio() : java.sql.Types.INTEGER);
        pstmt.setInt(5, (c.isAtendimentoConvenio()) ? 0 : c.getTipoPagamento());
        pstmt.setInt(6, (c.isAutorizada()) ? 1 : 0);
        pstmt.executeUpdate();

        idConsulta = pstmt.getGeneratedKeys().getInt(1);

        sql = "INSERT INTO EQUIPAMENTOS_EXAMES(ID_EQUIPAMENTO, ID_CONSULTA) VALUES(?,?)";

        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, eq.getID());
        pstmt.setInt(2, idConsulta);
        pstmt.executeUpdate();

        return idConsulta;
    }
    
    public static ArrayList<Especialidade> selectEspecialidades() throws SQLException {
        ArrayList<Especialidade> especialidades = new ArrayList<Especialidade>();

        String sql = "select * from ESPECIALIDADES;";
        ResultSet rs;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            especialidades.add(new Especialidade(rs.getInt("ID"),
                                                 rs.getString("DESCRICAO")));
        }

        return especialidades;
    }
    
    /*
    public static ArrayList<Consulta> selectHorariosDisponiveis(int especialidadeId, Date data, Date data0) throws SQLException {
        ArrayList<Consulta> consultas = new ArrayList<>();

        String sql = "select * from ;";
        ResultSet rs;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            especialidades.add(new Especialidade(rs.getInt("ID"),
                                                 rs.getString("DESCRICAO")));
        }

        return especialidades;
    
    }
    */
}
