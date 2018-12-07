import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.time.*;

public class Agenda {
	private Medico medico;
	private ArrayList<Consulta> consultas;

	public Agenda(Medico medico) {
		this.medico = medico;
		this.consultas = new ArrayList<Consulta>();
	}

	public void agendarConsulta(Consulta consulta) {
		if (consulta.isAutorizada()) {
			if (!this.horarioLivre(consulta.getHorario()))
				throw new AssertionError("Erro ao agendar consulta: conflito de horarios");
			
			consultas.add(consulta);
			Collections.sort(consultas);
		} else {
			throw new AssertionError("Consulta nao autorizada: verificar pagamento");
		}
	}

	public boolean horarioLivre(LocalDateTime dataHora) {
		boolean livre = true;

		if (!medico.atendeNoHorario(dataHora.getDayOfWeek(), dataHora.getHour(), dataHora.getMinute()))
			livre = false;

		// Recupera ultima consulta anterior a horario informado
		ListIterator<Consulta> it = consultas.listIterator();
		while (it.hasNext()) {
			Consulta c = it.next();
			if (c.getHorario().isAfter(dataHora)) {
				// Verifica se horario de consulta posterior gera conflito
				if (Duration.between(dataHora, c.getHorario()).toMinutes() < medico.getIntervaloConsulta()) {
					livre = false;
					break;
				}
				
				// Verifica se horario de consulta anterior gera conflito
				if (it.hasPrevious()) {
					c = it.previous();
					if (Duration.between(c.getHorario(), dataHora).toMinutes() < medico.getIntervaloConsulta()) {
						livre = false;
						break;
					}
				}
				else
					break;
			}
		}

		return livre;
	}

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
