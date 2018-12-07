import java.time.DayOfWeek;
import java.util.Arrays;

public class Medico {
	private String nome;
	private int horaInicioAtendimento;
	private DayOfWeek[] diasAtendimento;
	private int intervaloConsulta;
	private Especialidade especialidade;
	private Agenda agenda;

	public Medico(String nome, int hora, int dia1, int dia2, int dia3, int intervalo, Especialidade especialidade) {
		this.nome = nome;

		if (hora < 7 || hora > 14)
			throw new IllegalArgumentException("Hora de inicio de atendimento invalida.");
		this.horaInicioAtendimento = hora;

		if (dia1 == dia2 || dia2 == dia3 || dia1 == dia3)
			throw new IllegalArgumentException("Os dias de atendimento devem ser diferentes.");
		this.diasAtendimento = new DayOfWeek[3];
		this.diasAtendimento[0] = DayOfWeek.of(dia1);
		this.diasAtendimento[1] = DayOfWeek.of(dia1);
		this.diasAtendimento[2] = DayOfWeek.of(dia1);

		this.intervaloConsulta = intervalo;
		this.especialidade = especialidade;
		this.agenda = new Agenda(this);
	}

	public String getNome() {
		return nome;
	}

	public int getIntervaloConsulta() {
		return intervaloConsulta;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public boolean atendeNoHorario(DayOfWeek diaDaSemana, int hora, int minuto) {
		boolean atende = true;

		if (!Arrays.asList(diasAtendimento).contains(diaDaSemana))
			atende = false;
		else if (hora < horaInicioAtendimento || 
				 hora > horaInicioAtendimento+6 || 
				 (hora > horaInicioAtendimento+5) && (minuto > 60-intervaloConsulta))
			atende = false;
			
		return atende;
	}
}
