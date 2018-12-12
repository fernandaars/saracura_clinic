import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Clinica {
	public static ArrayList<Medico> medicos;
	public static ArrayList<String> especialidades;
	public static ArrayList<Equipamento> equipamentos;

	public static void main(String[] args) {
		medicos = new ArrayList<Medico>();
		especialidades = new ArrayList<String>();
		equipamentos = new ArrayList<Equipamento>();

		popularEspecialidades();
		popularMedicos();
		popularEquipamentos();

		for (Equipamento eq : equipamentos) {
			System.out.println(eq.getDescricao());
		}

		for (Equipamento m : equipamentos) {
			m.carregarConsultas();
		}

		Consulta c = new Consulta("DINHO", "91234-5678", LocalDateTime.of(2018, 12, 3, 19, 40), 1);
		c.autorizar();
		try {
			equipamentos.get(0).agendarConsulta(c);
		} catch (IllegalArgumentException | AssertionError e) {
			System.out.println("ERRO AO AGENDAR CONSULTA " + c.getNomeCliente() + ": ");
			System.out.println(e.getMessage());
		}

	}

	// Adiciona médico à base de dados
	public static void adicionarMedico(String nome, int horaInicio, int duracaoConsulta, int dia1, int dia2, int dia3,
			int especialidade) {

		Medico novoMedico;
		try {
			novoMedico = new Medico(nome, horaInicio, dia1, dia2, dia3, duracaoConsulta, especialidade);
			medicos.add(novoMedico);
			int id = SQLiteConnection.insertMedico(novoMedico);
			novoMedico.setID(id);
		} catch (IllegalArgumentException | SQLException e) {
			System.out.println("ERRO: " + e.getMessage());
		}
	}

	// Adiciona especialidade à base de dados
	public static void adicionarEspecialidade(String nome) {

		try {
			SQLiteConnection.insertEspecialidade(nome);
		} catch (IllegalArgumentException | SQLException e) {
			System.out.println("ERRO: " + e.getMessage());
		}
	}

	// Preenche ArrayList medicos com entradas do banco de dados
	public static void popularMedicos() {
		ResultSet rs = SQLiteConnection.selectAll("MEDICOS");

		try {
			while (rs.next()) {
				Medico m = new Medico(rs.getString("NOME"), rs.getInt("HORA_INICIO"), rs.getInt("DIA_ATEND1"),
						rs.getInt("DIA_ATEND2"), rs.getInt("DIA_ATEND3"), rs.getInt("DURAC_CONSULTA"),
						rs.getInt("ESPECIALIDADE"));
				m.setID(rs.getInt("ID"));
				medicos.add(m);
			}
		} catch (SQLException e) {
			System.out.println("ERRO AO RECUPERAR MEDICOS: " + e.getMessage());
		}
	}

	// Preenche ArrayList especialidades com entradas do banco de dados
	public static void popularEspecialidades() {
		ResultSet rs = SQLiteConnection.selectAll("ESPECIALIDADES");

		try {
			while (rs.next()) {
				especialidades.add(rs.getString("DESCRICAO"));
			}
		} catch (SQLException e) {
			System.out.println("ERRO AO RECUPERAR ESPECIALIDADES: " + e.getMessage());
		}
	}

	// Preenche ArrayList equipamentos com entradas do banco de dados
	public static void popularEquipamentos() {
		ResultSet rs = SQLiteConnection.selectAll("EQUIPAMENTOS");

		try {
			while (rs.next()) {
				Equipamento eq = new Equipamento(rs.getString("DESCRICAO"), rs.getInt("TIPO_EXAME"));
				eq.setID(rs.getInt("ID"));
				equipamentos.add(eq);
			}
		} catch (SQLException e) {
			System.out.println("ERRO AO RECUPERAR EQUIPAMENTOS: " + e.getMessage());
		}
	}

}
