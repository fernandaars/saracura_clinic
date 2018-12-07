import java.util.ArrayList;
import java.util.Scanner;

public class Clinica {
	public static ArrayList<Medico> medicos;
	public static ArrayList<Especialidade> especialidades;

	public static void main(String[] args) {
		medicos = new ArrayList<Medico>();
		especialidades = new ArrayList<Especialidade>();

		especialidades.add(new Especialidade("Pediatria"));
		especialidades.add(new Especialidade("Geriatria"));
		especialidades.add(new Especialidade("Psicologia"));

		medicos.add(new Medico("LUIS", 7, 1, 3, 5, 20, especialidades.get(2)));
		medicos.add(new Medico("JONAS", 10, 2, 4, 5, 30, especialidades.get(0)));
		
		Consulta c = new Consulta("MARIQUINHA", 912345678, 3, 12, 2018, 8, 00, 1);
		c.autorizar();
		medicos.get(0).getAgenda().agendarConsulta(c);

		int escolha;
		boolean sair = false;
		Scanner in = new Scanner(System.in);
		int i;

		System.out.println("GERENCIAMENTO DE CONSULTAS\n");

		while (!sair) {
			printMenu();
			escolha = Integer.parseInt(in.nextLine());

			switch (escolha) {
			case 0:
				sair = true;
				break;
			case 1:
				adicionarMedico(in);
				break;
			case 2:
				i = 0;
				for (Medico m : medicos) {
					System.out.println(i++ + " - " + m.getNome());
				}
				break;
			case 3:
				adicionarEspecialidade(in);
				break;
			case 4:
				i = 0;
				for (Especialidade e : especialidades) {
					System.out.println(i++ + " - " + e.getDescricao());
				}
				break;
			default:
				System.out.println("OPCAO INVALIDA");
				break;
			}

			System.out.println();
		}

		in.close();
	}

	public static void adicionarMedico(Scanner in) {
		System.out.print("NOME DO MEDICO: ");
		String nome = in.nextLine();

		System.out.print("HORA DE INICIO DO ATENDIMENTO: ");
		int horaInicio = Integer.parseInt(in.nextLine());

		System.out.println("DIAS DE ATENDIMENTO (1 (segunda) a 7 (domingo))");
		System.out.print("DIA 1: ");
		int dia1 = Integer.parseInt(in.nextLine());
		System.out.print("DIA 2: ");
		int dia2 = Integer.parseInt(in.nextLine());
		System.out.print("DIA 3: ");
		int dia3 = Integer.parseInt(in.nextLine());

		System.out.print("INTERVALO DA CONSULTA: ");
		int intervalo = Integer.parseInt(in.nextLine());

		System.out.print("ESPECIALIDADE: ");
		int especialidade = Integer.parseInt(in.nextLine());

		Medico novoMedico;
		try {
			novoMedico = new Medico(nome, horaInicio, dia1, dia2, dia3, intervalo, especialidades.get(especialidade));
			medicos.add(novoMedico);
		} catch (IllegalArgumentException e) {
			System.out.println("ERRO: " + e.getMessage());
		}
	}

	public static void adicionarEspecialidade(Scanner in) {
		System.out.println("NOME DA ESPECIALIDADE:");
		String nome = in.nextLine();

		Especialidade especialidade = new Especialidade(nome);
		especialidades.add(especialidade);
	}

	public static void printMenu() {
		System.out.println("-------------MENU----------------");
		System.out.println("SELECIONE OPCAO:");
		System.out.println();
		System.out.println("0 - SAIR");
		System.out.println();
		System.out.println("1 - ADICIONAR MEDICO");
		System.out.println("2 - LISTAR MEDICOS");
		System.out.println("3 - ADICIONAR ESPECIALIDADE");
		System.out.println("4 - LISTAR ESPECIALIDADES");
		System.out.println("---------------------------------");
		System.out.print("ESCOLHA: ");
	}

}
