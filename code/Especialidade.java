
public class Especialidade {
	private int id;
	private String descricao;

	public Especialidade(String descricao) {
		this.descricao = descricao;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return descricao;
	}
}
