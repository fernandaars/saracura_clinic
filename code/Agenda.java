import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Agenda {
	private Recurso recurso;
	private ArrayList<Consulta> consultas;
	private boolean carregada;

	public Agenda(Recurso recurso) {
		this.recurso = recurso;
		this.carregada = false;
	}

	// Informa se o horario correpondente a DataHora informado está livre para
	// agendamento e não gera conflitos
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

	// Informa se agenda está carregada com informações do BD
	public boolean isCarregada() {
		return this.carregada;
	}

	// Altera estado de carregamento
	public void carregar(ArrayList<Consulta> consultas) {
		this.consultas = consultas;
		this.carregada = true;
	}

	// Adiciona instancia de Consulta na agenda
	public void agendar(Consulta consulta) {
		this.consultas.add(consulta);
		Collections.sort(consultas);
	}
}
