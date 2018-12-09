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
			maxInicioAtendimento.minusMinutes(this.intervaloConsulta);
				
			if (horario.toLocalTime().isAfter(maxInicioAtendimento))
				atende = false;
		}
			
		return atende;
	}
	
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
