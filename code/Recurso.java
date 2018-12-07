import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Recurso {
	protected Agenda agenda;
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
	}
	
	public Agenda getAgenda() {
		return agenda;
	}
	
	public int getIntervaloConsulta() {
		return intervaloConsulta;
	}
	
	public boolean disponivelNoHorario(LocalDateTime horario) {
		boolean atende = true;

		LocalTime maxInicioAtendimento = LocalTime.of(horaInicioDisponibilidade + duracaoJornada, 0);
		maxInicioAtendimento.minusMinutes(this.intervaloConsulta);
			
		if (horario.toLocalTime().isAfter(maxInicioAtendimento))
			atende = false;
			
		return atende;
	}
}
