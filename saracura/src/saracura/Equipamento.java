package saracura;

import java.sql.SQLException;
import java.util.ArrayList;

public class Equipamento extends Recurso {
	private String descricao;
	private int tipoExame;

	public Equipamento(String descricao, int tipoExame) {
		super(7, 13, 20); // HoraInicioDisponibilidade, DuracaoJornada, IntervaloAtendimento

		this.descricao = descricao;
		this.tipoExame = tipoExame;
	}
	
	// M�todo abstrato herdado da superclasse
	public void agendarConsulta(Consulta consulta) {
		if (!this.agenda.isCarregada()) {
			throw new AssertionError("A agenda deve ser carregada antes de agendar novas consultas.");
		}

		if (consulta.isAutorizada()) {
			if (!this.agenda.horarioLivre(consulta.getHorario()))
				throw new IllegalArgumentException("Erro ao agendar consulta: conflito de horarios");

			try {
				SQLiteConnection.insertExame(consulta, this);
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
		ArrayList<Consulta> exames;

		try {
			exames = SQLiteConnection.selectExames(this.id);
			this.agenda.carregar(exames);
		} catch (SQLException e) {
			System.out.println("ERRO AO CARREGAR CONSULTAS DE MEDICO " + this);
			System.out.println(e.getMessage());
		}
	}

	public String getDescricao() {
		return descricao;
	}

	public int getTipoExame() {
		return tipoExame;
	}

}
