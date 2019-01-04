package saracura;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Recurso {
	protected Agenda agenda;
	protected int id;
	protected int horaInicioDisponibilidade;
	protected int duracaoJornada;
	protected int intervaloConsulta;

	public Recurso(int horaInicioDisponibilidade, int duracaoJornada, int intervaloConsulta) {
		if (horaInicioDisponibilidade < 7 || horaInicioDisponibilidade > 14)
			throw new IllegalArgumentException("Hora de inicio de atendimento invalida.");

		this.agenda = new Agenda(this);
		this.horaInicioDisponibilidade = horaInicioDisponibilidade;
		this.duracaoJornada = duracaoJornada;
		this.intervaloConsulta = intervaloConsulta;
		this.id = -1;
	}

	public boolean disponivelNoHorario(LocalDateTime horario) {
		boolean atende = true;

		if (horario.getHour() < this.horaInicioDisponibilidade)
			atende = false;
		else {
			LocalTime maxInicioAtendimento = LocalTime.of(horaInicioDisponibilidade + duracaoJornada, 0);
			maxInicioAtendimento = maxInicioAtendimento.minusMinutes(this.intervaloConsulta);

			if (horario.toLocalTime().isAfter(maxInicioAtendimento))
				atende = false;
		}

		return atende;
	}

	// Popula lista de consultas. Presumir que consultas carregadas no BD s�o
	// v�lidas e n�o conflituosas. Deve ser implementado pelas subclasses.
	abstract void carregarConsultas() throws SQLException;
	
	// Agenda instancia de Consulta. Deve fazer todas as verifica��es necess�rias ao ser implementada.
	abstract void agendarConsulta(Consulta consulta) throws SQLException;

	public Agenda getAgenda() {
		return agenda;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getHoraInicioDisponibilidade() {
		return horaInicioDisponibilidade;
	}

	public int getDuracaoJornada() {
		return duracaoJornada;
	}

	public int getIntervaloConsulta() {
		return intervaloConsulta;
	}

}
