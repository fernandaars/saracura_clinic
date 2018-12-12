import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Medico extends Recurso {
	private String nome;
	private DayOfWeek[] diasAtendimento;
	private int especialidade;

	public Medico(String nome, int hora, int dia1, int dia2, int dia3, int intervalo, int especialidade) {
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
	
	// Sobrescreve método da superclasse para verificar dia da semana
	public boolean disponivelNoHorario(LocalDateTime horario) {
		boolean atende = true;

		if (!Arrays.asList(diasAtendimento).contains(horario.getDayOfWeek()))
			atende = false;
		else {
			atende = super.disponivelNoHorario(horario);
		}

		return atende;
	}
	
	// Metodo abstrato herdado da superclasse
	public void agendarConsulta(Consulta consulta) {
		if (!this.agenda.isCarregada()) {
			throw new AssertionError("A agenda deve ser carregada antes de agendar novas consultas.");
		}

		if (consulta.isAutorizada()) {
			if (!this.agenda.horarioLivre(consulta.getHorario()))
				throw new IllegalArgumentException("Erro ao agendar consulta: conflito de horarios");

			try {
				SQLiteConnection.insertConsulta(consulta, this);
				this.agenda.agendar(consulta);
			} catch (SQLException e) {
				System.out.println("ERRO AO AGENDAR CONSULTA COM " + this);
				System.out.println(e.getMessage());
			}
		} else {
			throw new AssertionError("Consulta nao autorizada: verificar pagamento");
		}
	}
	
	// Metodo abstrato herdado da superclasse
	public void carregarConsultas() {
		ArrayList<Consulta> consultas;

		try {
			consultas = SQLiteConnection.selectConsultas(this.id);
			this.agenda.carregar(consultas);
		} catch (SQLException e) {
			System.out.println("ERRO AO CARREGAR CONSULTAS DE MEDICO " + this);
			System.out.println(e.getMessage());
		}
	}

	public String getNome() {
		return nome;
	}

	public DayOfWeek[] getDiasAtendimento() {
		return diasAtendimento;
	}

	public int getEspecialidade() {
		return especialidade;
	}

	@Override
	public String toString() {
		return "Medico [nome=" + nome + ", especialidade=" + especialidade + "]";
	}
}
