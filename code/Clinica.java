import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Clinica {
	public static ArrayList<Medico> medicos;
	public static ArrayList<Especialidade> especialidades;

	public static void main(String[] args) {
		medicos = new ArrayList<Medico>();
		especialidades = new ArrayList<Especialidade>();
		
		popularEspecialidades();
		popularMedicos();
		
		for (Medico m : medicos) {
			System.out.println(m);
		}

		Consulta c = new Consulta("MARIQUINHA", "91234-5678", LocalDateTime.of(2018, 12, 3, 9, 00), 1);
		c.autorizar();
		try {
			medicos.get(0).getAgenda().agendarConsulta(c);
		} catch (IllegalArgumentException | SQLException e) {
			System.out.println("ERRO AO AGENDAR CONSULTA " + c.getNomeCliente() + ": ");
			System.out.println(e.getMessage());
		}

		c = new Consulta("LUISINHA", "91234-5678", LocalDateTime.of(2018, 12, 3, 9, 20), 1);
		c.autorizar();
		try {
			medicos.get(0).getAgenda().agendarConsulta(c);
		} catch (IllegalArgumentException | SQLException e) {
			System.out.println("ERRO AO AGENDAR CONSULTA " + c.getNomeCliente() + ": ");
			System.out.println(e.getMessage());
		}
		
		c = new Consulta("TEREZINHA", "91234-5678", LocalDateTime.of(2018, 12, 3, 9, 40), 1);
		c.autorizar();
		try {
			medicos.get(0).getAgenda().agendarConsulta(c);
		} catch (IllegalArgumentException | SQLException e) {
			System.out.println("ERRO AO AGENDAR CONSULTA " + c.getNomeCliente() + ": ");
			System.out.println(e.getMessage());
		}

	}
	
	// Adiciona médico à base de dados
	public static void adicionarMedico(String nome, int horaInicio, int duracaoConsulta, int dia1,
			int dia2, int dia3, int especialidade) {
		
		Medico novoMedico;
		try {
			novoMedico = new Medico(nome, horaInicio, dia1, dia2, dia3, duracaoConsulta,
					especialidades.get(especialidade));
			medicos.add(novoMedico);
			int id = SQLiteConnection.insertMedico(novoMedico);
			novoMedico.setID(id);
		} catch (IllegalArgumentException | SQLException e) {
			System.out.println("ERRO: " + e.getMessage());
		}
	}

	// Adiciona especialidade à base de dados
	public static void adicionarEspecialidade(String nome) {
		
		Especialidade novaEspecialidade;
		try {
			novaEspecialidade = new Especialidade(nome);
			especialidades.add(novaEspecialidade);
			int id = SQLiteConnection.insertEspecialidade(novaEspecialidade);
			novaEspecialidade.setID(id);
		} catch (IllegalArgumentException | SQLException e) {
			System.out.println("ERRO: " + e.getMessage());
		}
	}
	
	// Preenche ArrayList medicos com entradas do banco de dados
	public static void popularMedicos() {
		ResultSet rs = SQLiteConnection.selectAll("MEDICOS");
		
		try {
			while(rs.next()) {
				Medico m = new Medico(
						rs.getString("NOME"),
						rs.getInt("HORA_INICIO"),
						rs.getInt("DIA_ATEND1"),
						rs.getInt("DIA_ATEND2"),
						rs.getInt("DIA_ATEND3"),
						rs.getInt("DURAC_CONSULTA"),
						especialidades.get(rs.getInt("ESPECIALIDADE")));
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
			while(rs.next()) {
				Especialidade e = new Especialidade(rs.getString("DESCRICAO"));
				e.setID(rs.getInt("ID"));
				especialidades.add(e);
			}
		} catch (SQLException e) {
			System.out.println("ERRO AO RECUPERAR ESPECIALIDADES: " + e.getMessage());
		}
	}

}
