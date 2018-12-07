import java.time.*;

public class Consulta implements Comparable<Consulta> {
	private String nomeCliente;
	private int telefoneCliente;

	private LocalDateTime horario;
	private boolean atendimentoConvenio;
	private int matriculaConvenio;
	private int tipoPagamento;
	private boolean autorizada;

	private Consulta(String cliente, int telefone, int dia, int mes, int ano, int hora, int minuto) {
		this.nomeCliente = cliente;
		this.telefoneCliente = telefone;
		this.horario = LocalDateTime.of(ano, mes, dia, hora, minuto);
		this.autorizada = false;
	}
	
	// Construtor para consulta de convenio
	public Consulta(String cliente, int telefone, int dia, int mes, int ano, int hora, int minuto, int convenio, int matricula) {
		this(cliente, telefone, dia, mes, ano, hora, minuto);
		
		this.atendimentoConvenio = true;
		this.matriculaConvenio = matricula;
	}
	
	// Construtor para consulta particular
	public Consulta(String cliente, int telefone, int dia, int mes, int ano, int hora, int minuto, int tipoPagamento) {
		this(cliente, telefone, dia, mes, ano, hora, minuto);
		
		this.atendimentoConvenio = false;
		this.tipoPagamento = tipoPagamento;
	}
	
	public void autorizar() {
		if (this.atendimentoConvenio) {
			// SOLICITAR AUTORIZACAO DO CONVENIO
		}
		
		this.autorizada = true;
	}
	
	public boolean isAutorizada() {
		return this.autorizada;
	}

	public LocalDateTime getHorario() {
		return horario;
	}
	
	public void alterarHorario(int dia, int mes, int ano, int hora, int minuto) {
		this.horario = LocalDateTime.of(ano, mes, dia, hora, minuto);
	}

	@Override
	public int compareTo(Consulta c) {
		return this.horario.compareTo(c.getHorario());
	}
}
