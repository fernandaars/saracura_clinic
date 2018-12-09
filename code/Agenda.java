import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Agenda {
	private Recurso recurso;
	private ArrayList<Consulta> consultas;
	private boolean carregada;

	public Agenda(Recurso recurso) {
		this.recurso = recurso;
		this.consultas = new ArrayList<Consulta>();
		this.carregada = false;
	}
	
	// Popula lista de consultas. Só funciona agendas pertencentes a Médicos
	// Presumir que consultas carregadas no BD são válidas e não conflituosas
	// TODO: Generalizar para agendas pertencentes a qualquer recurso.
	private void carregarConsultas() throws SQLException {
		ResultSet rs = SQLiteConnection.selectConsultas(recurso.id);
		
		try {
			while(rs.next()) {
				// CONSULTA MARCADA PELO CONVENIO
				if (rs.getInt("TIPO_PAGAMENTO") == 0) {
					Consulta novaConsulta = new Consulta(rs.getString("NOME_CLIENTE"), 
							rs.getString("TEL_CLIENTE"), 
							LocalDateTime.parse(rs.getString("HORARIO")),
							999999,
							rs.getInt("MATRICULA_CONVENIO"));
					consultas.add(novaConsulta);
					novaConsulta.setID(rs.getInt("ID"));
				}
				// CONSULTA PARTICULAR
				else {
					Consulta novaConsulta = new Consulta(rs.getString("NOME_CLIENTE"), 
							rs.getString("TEL_CLIENTE"), 
							LocalDateTime.parse(rs.getString("HORARIO")), 
							rs.getInt("TIPO_PAGAMENTO"));
					consultas.add(novaConsulta);
					novaConsulta.setID(rs.getInt("ID"));
				}
			}
		} catch (SQLException e) {
			System.out.println("ERRO AO CARREGAR AGENDA DE " + this.recurso.toString());
		}
	}

	public void agendarConsulta(Consulta consulta) throws SQLException {
		if (!this.carregada)
			carregarConsultas();
		
		if (consulta.isAutorizada()) {
			if (!this.horarioLivre(consulta.getHorario()))
				throw new IllegalArgumentException("Erro ao agendar consulta: conflito de horarios");
			
			consultas.add(consulta);
			SQLiteConnection.insertConsulta(consulta, recurso);
			Collections.sort(consultas);
		} else {
			throw new AssertionError("Consulta nao autorizada: verificar pagamento");
		}
	}
	
	// Informa se o horario correpondente a DataHora informado está livre para agendamento e não gera conflitos
	public boolean horarioLivre(LocalDateTime dataHora) {
		boolean livre = true;

		if (!recurso.disponivelNoHorario(dataHora))
			livre = false;

		// Recupera primeira consulta posterior a horario informado
		ListIterator<Consulta> it = consultas.listIterator();
		
		Consulta prox = null;
		Consulta ant = null;
		
		while (it.hasNext()) {
			prox = it.next();
			if (prox.getHorario().isAfter(dataHora)) 
				break;
			ant = prox;
		}
		
		if (prox != null) {
			// Verifica se horario de consulta posterior gera conflito
			if (Math.abs(Duration.between(dataHora, prox.getHorario()).toMinutes()) < recurso.getIntervaloConsulta())
				livre = false;
			
			// Verifica se horario de consulta anterior gera conflito
			else if (ant != null) {
				if (Duration.between(ant.getHorario(), dataHora).toMinutes() < recurso.getIntervaloConsulta())
					livre = false;
			}
		}
		
		return livre;
	}
	
	// Retorna primeira consulta marcada para a data informada
	// Possivelmente inútil
	public Consulta getPrimeiraConsultaDoDia(LocalDate data) {
		Consulta consulta = null;
		LocalDateTime horario;

		for (Consulta c : consultas) {
			horario = c.getHorario();

			// Compara horarios e armazena o mais anterior
			if (data.equals(horario.toLocalDate())) {
				if (consulta == null)
					consulta = c;
				else {
					if (horario.isBefore(consulta.getHorario()))
						consulta = c;
				}
			}
		}

		return consulta;
	}
}
