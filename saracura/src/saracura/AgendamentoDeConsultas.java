
package saracura;

import java.util.Calendar;
import javax.swing.JFrame;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXDatePicker;


public class AgendamentoDeConsultas extends InterfaceGrafica{
    private int numEspecialidades;
    private String formaDePagamento;
    
    public AgendamentoDeConsultas(){
        super();
        this.listaEspecialidades();
    }
    
    private void listaEspecialidades(){
        this.setTitle("SARACURA - Lista de Especialidades");
        ArrayList<Especialidade> especialidades = new ArrayList<Especialidade>();
        try {
            especialidades = SQLiteConnection.selectEspecialidades();
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDeConsultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.numEspecialidades = especialidades.size();
        String[] tiposEsp = new String[this.numEspecialidades];
        int i = 0;
        while(i < this.numEspecialidades){
            tiposEsp[i] = especialidades.get(i).getDescricao();
            i += 1;
        }
        
        JButton botoes[] = new JButton[this.numEspecialidades];
        this.painel.setLayout(new GridLayout(this.numEspecialidades,1));
        for(i=0; i<this.numEspecialidades; i++){
            botoes[i] = new JButton(tiposEsp[i]);
            this.criaBotaoNormal(botoes[i]);
            botoes[i].addActionListener((java.awt.event.ActionEvent evt) -> {
                this.setVisible(false);
                this.agendaConsulta(((JButton) evt.getSource()).getText());
            });
            painel.add(botoes[i]);
        }
        this.defineConfiguracoes();
    }
    
    private void agendaConsulta(String especialidade){
        this.setTitle("SARACURA - Agendamento de Consulta");
        JLabel espEsc = new JLabel("ESPECIALIDADE: " + especialidade, SwingConstants.CENTER);
        espEsc.setFont(this.fonteTituloGrande);
        JLabel texto1 = new JLabel("Selecione o Período da Consulta:");
        texto1.setFont(this.fonteTituloGrande);
        JXDatePicker inicio = new JXDatePicker();
        JXDatePicker fim = new JXDatePicker();
        inicio.setDate(Calendar.getInstance().getTime());
        inicio.setFont(this.fonteTextoNormal);
        fim.setDate(Calendar.getInstance().getTime());
        fim.setFont(this.fonteTextoNormal);
        
        int especialidadeId = this.getEspecialidadeId(especialidade);
        ArrayList<Consulta> horariosDisponiveis;
        /*
        fim.addActionListener((java.awt.event.ActionEvent evt) -> {
            horariosDisponiveis = this.verificaHorariosDisponiveis(
                                  inicio.getDate(), fim.getDate(),
                                  especialidadeId);
        });
        */
        JPanel periodoPainel = new JPanel();
        periodoPainel.setBackground(this.corFundoPrincipal);
        periodoPainel.setLayout(new BorderLayout());
        JPanel tempoPainel = new JPanel();
        tempoPainel.setBackground(this.corFundoPrincipal);
        tempoPainel.setLayout(new GridLayout(1,4));
        tempoPainel.add(inicio);
        tempoPainel.add(fim);
        
        JLabel texto2 = new JLabel("Escolha o Médico e o Horário:");
        texto2.setFont(this.fonteTituloGrande);
        String[] escolhas = {"12/01/2018 | 14:00 | Dra Jane Austen | R$450,00",
                             "12/01/2018 | 14:00 | Dra Jane Austen | R$450,00"};
        JComboBox opcoes = new JComboBox(escolhas);
        opcoes.setFont(this.fonteTextoNormal);
        JButton botao2 = new JButton("Confirmar");
        this.criaBotaoNormal(botao2);
        JPanel painel2 = new JPanel();
        painel2.setBackground(this.corFundoPrincipal);
        painel2.setLayout(new BorderLayout());
        painel2.add(opcoes, BorderLayout.CENTER);
        painel2.add(botao2, BorderLayout.EAST);
        
        String opcaoPadrao = "12/01/2018,14:00,Dra Jane Austen,R$450,00";
        String[] opcaoPartes = opcaoPadrao.split(",");
        JLabel medico = new JLabel("Médico: " + opcaoPartes[2]);
        medico.setFont(this.fonteTextoNormal);
        JLabel data = new JLabel("Data: " + opcaoPartes[0]);
        data.setFont(this.fonteTextoNormal);
        JLabel horario = new JLabel("Horário: " + opcaoPartes[1]);
        horario.setFont(this.fonteTextoNormal);
        JLabel valor = new JLabel("Valor: " + opcaoPartes[3]);
        valor.setFont(this.fonteTextoNormal);
        JPanel painel3 = new JPanel();
        painel3.setBackground(this.corFundoPrincipal);
        painel3.setLayout(new BorderLayout());
        painel3.add(medico, BorderLayout.NORTH);
        painel3.add(data, BorderLayout.WEST);
        painel3.add(horario, BorderLayout.EAST);
        painel3.add(valor, BorderLayout.SOUTH);
        
        JLabel texto4 = new JLabel("Escolha a Forma de Pagamento:");
        texto4.setFont(this.fonteTituloGrande);
        JPanel painel4 = new JPanel();
        painel4.setBackground(this.corFundoPrincipal);
        painel4.setLayout(new GridLayout(1, 3));
        JRadioButton dinheiro = new JRadioButton("Dinheiro");
        dinheiro.setFont(this.fonteTextoNormal);
        JRadioButton cartao = new JRadioButton("Cartão");
        cartao.setFont(this.fonteTextoNormal);
        JRadioButton convenio = new JRadioButton("Convênio");
        convenio.setFont(this.fonteTextoNormal);
        JRadioButton cheque = new JRadioButton("Cheque");
        cheque.setFont(this.fonteTextoNormal);
        this.formaDePagamento = "dinheiro";
        convenio.addActionListener((java.awt.event.ActionEvent evt) -> {
                this.formaDePagamento = "Convênio";
        });
        painel4.add(dinheiro);
        painel4.add(cartao);
        painel4.add(convenio);
        painel4.add(cheque);
        
        JButton botao3 = new JButton("Finalizar");
        this.criaBotaoNormal(botao3);
        botao3.addActionListener((java.awt.event.ActionEvent evt) -> {
                //this.setVisible(false);
                this.finalizarConsulta();
        });
        
        this.painel = new JPanel();
        this.painel.setLayout(new GridLayout(9,1));
        this.painel.add(espEsc);
        this.painel.add(texto1);
        this.painel.add(tempoPainel);
        this.painel.add(texto2);
        this.painel.add(painel2);
        this.painel.add(painel3);
        this.painel.add(texto4);
        this.painel.add(painel4);
        this.painel.add(botao3);
        this.setFont(this.fonteTituloGrande);
        this.getContentPane().removeAll();
        this.repaint();
        this.add(painel);
        this.setVisible(true);
        this.defineConfiguracoes();
    }

    private void finalizaOperacao() {
        int randomNum = ThreadLocalRandom.current().nextInt(0,12);
        if(this.formaDePagamento == "Convênio" && randomNum != 12){
            JOptionPane.showMessageDialog(this, "Desculpe! O Número do Convênio não Existe.");
        }else{
            JOptionPane.showMessageDialog(this, "Obrigada! Operação Realizada com Sucesso.");
        }
        this.setVisible(false);
    }

    private int getEspecialidadeId(java.lang.String especialidade) {
        ArrayList<Especialidade> especialidades = new ArrayList<>();
        try {
            especialidades = SQLiteConnection.selectEspecialidades();
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDeConsultas.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.numEspecialidades = especialidades.size();
        int i = 0;
        int id = -1;
        while(i < this.numEspecialidades){
            if(especialidade.equals(especialidades.get(i).getDescricao()))
                id = especialidades.get(i).getId();
            i += 1;
        }
        return id;
    }

    private ArrayList<Consulta> verificaHorariosDisponiveis(Date date, Date date0, int especialidadeId) {
        ArrayList<Consulta> consultas = new ArrayList<>();
        return consultas;
    }

    private void finalizarConsulta() {
        this.setTitle("SARACURA - Agendamento de Consulta");
        
        int numElementos = 4;
        if(this.formaDePagamento == "convenio")
            numElementos += 2;
        
        JPanel subPainel = new JPanel();
        subPainel.setBackground(this.corFundoPrincipal);
        subPainel.setLayout(new GridLayout(numElementos ,2));
        
        JLabel texto1 = new JLabel("Insira Seus Dados Pessoais:");
        texto1.setFont(this.fonteTituloGrande);
        JLabel nome = new JLabel("Nome:");
        subPainel.add(nome);
        JTextArea insereNome = new JTextArea();
        nome.setFont(this.fonteTextoNormal);
        subPainel.add(new JScrollPane(insereNome));
        
        //insereNome.setFont(this.fonteTextoNormal);
        JLabel telefone = new JLabel("Telefone:");
        JTextArea insereTelefone = new JTextArea();
        telefone.setFont(this.fonteTextoNormal);
        subPainel.add(telefone);
        subPainel.add(insereTelefone);
        if(this.formaDePagamento == "Convênio"){
            JLabel nomeDoConvenio = new JLabel("Nome do Convênio:");
            JTextArea insereConvenio = new JTextArea();
            JLabel matricula = new JLabel("Matrícula:");
            JTextArea insereMatricula = new JTextArea();
            nomeDoConvenio.setFont(this.fonteTextoNormal);
            matricula.setFont(this.fonteTextoNormal);
            subPainel.add(nomeDoConvenio);
            subPainel.add(insereConvenio);
            subPainel.add(matricula);
            subPainel.add(insereMatricula);
        }
        JButton botao = new JButton("Confirmar");
        this.criaBotaoGrande(botao);
        botao.addActionListener((java.awt.event.ActionEvent evt) -> {
                this.finalizaOperacao();
        });
        
        this.painel = new JPanel();
        this.painel.setLayout(new BorderLayout());
        this.painel.add(texto1, BorderLayout.NORTH);
        this.painel.add(subPainel, BorderLayout.CENTER);
        this.painel.add(botao, BorderLayout.SOUTH);
        
        this.getContentPane().removeAll();
        this.repaint();
        this.add(painel);
        this.defineConfiguracoes();
    }
    
}
