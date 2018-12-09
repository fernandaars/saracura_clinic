import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Medico extends Recurso {
	private String nome;
	private DayOfWeek[] diasAtendimento;
	private Especialidade especialidade;

	public Medico(String nome, int hora, int dia1, int dia2, int dia3, int intervalo, Especialidade especialidade) {
		super(hora, 6, intervalo);
		this.nome = nome;

		if (dia1 == dia2 || dia2 == dia3 || dia1 == dia3)
			throw new IllegalArgumentException("Os dias de atendimento devem ser diferentes.");
		this.diasAtendimento = new DayOfWeek[3];
		this.diasAtendimento[0] = DayOfWeek.of(dia1);
		this.diasAtendimento[1] = DayOfWeek.of(dia1);
		this.diasAtendimento[2] = DayOfWeek.of(dia1);

		this.especialidade = especialidade;
	}
	
	public boolean disponivelNoHorario(LocalDateTime horario) {
		boolean atende = true;

		if (!Arrays.asList(diasAtendimento).contains(horario.getDayOfWeek()))
			atende = false;
		else {
			atende = super.disponivelNoHorario(horario);
		}
			
		return atende;
	}
	
	public String getNome() {
		return nome;
	}

	public DayOfWeek[] getDiasAtendimento() {
		return diasAtendimento;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	@Override
	public String toString() {
		return "Medico [nome=" + nome + ", especialidade=" + especialidade + "]";
	}
}
