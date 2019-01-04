package saracura;

import java.time.*;

public class Consulta implements Comparable<Consulta> {
	private String nomeCliente;
	private String telefoneCliente;
	private int matriculaConvenio;
	
	private int id;
	private LocalDateTime horario;
	private boolean atendimentoConvenio;
	private int tipoPagamento;
	private boolean autorizada;

	private Consulta(String cliente, String telefone, LocalDateTime horario) {
		this.nomeCliente = cliente;
		this.telefoneCliente = telefone;
		this.horario = horario;
		this.autorizada = false;
	}
	
	// Construtor para consulta de convenio
	public Consulta(String cliente, String telefone, LocalDateTime horario, int convenio, int matricula) {
		this(cliente, telefone, horario);
		
		this.atendimentoConvenio = true;
		this.matriculaConvenio = matricula;
	}
	
	// Construtor para consulta particular
	public Consulta(String cliente, String telefone, LocalDateTime horario, int tipoPagamento) {
		this(cliente, telefone, horario);
		
		this.atendimentoConvenio = false;
		this.tipoPagamento = tipoPagamento;
	}
	
	public void autorizar() {
		if (this.atendimentoConvenio) {
			// TODO: SOLICITAR AUTORIZACAO DO CONVENIO
		}
		
		this.autorizada = true;
	}
	
	public boolean isAutorizada() {
		return this.autorizada;
	}
	
	public int getID() {
		return id;
	}

	public LocalDateTime getHorario() {
		return horario;
	}
	
	public String getNomeCliente() {
		return nomeCliente;
	}

	public String getTelefoneCliente() {
		return telefoneCliente;
	}

	public boolean isAtendimentoConvenio() {
		return atendimentoConvenio;
	}

	public int getMatriculaConvenio() {
		return matriculaConvenio;
	}

	public int getTipoPagamento() {
		return tipoPagamento;
	}

	public void alterarHorario(int dia, int mes, int ano, int hora, int minuto) {
		this.horario = LocalDateTime.of(ano, mes, dia, hora, minuto);
	}
	
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public int compareTo(Consulta c) {
		return this.horario.compareTo(c.getHorario());
	}
}
