
package saracura;

import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXDatePicker;


public class AgendamentoDeExames extends InterfaceGrafica{
    private int numEspecialidades;
    private String formaDePagamento;
    
    public AgendamentoDeExames(){
        super();
        this.formaDePagamento = "Dinheiro";
        this.listaTiposDeExames();
    }
    
    private void listaTiposDeExames(){
        this.setTitle("SARACURA - Lista de Especialidades");
        
        // Thiago
        this.numEspecialidades = 10;
        String[] tiposExame = {"Glicemia Em Jejum", "Hemograma",
                             "Ureia e Creatinina", "Ácido Úrico",
                             "Colesterol Total e Frações", "Triglicerídeos",
                             "Tomografia", "TSH e T4 livre",
                             "Endoscopia", "Mamografia"};
        JButton botoes[] = new JButton[this.numEspecialidades];
        this.painel.setLayout(new GridLayout(this.numEspecialidades,1));
        for(int i=0; i<this.numEspecialidades; i++){
            botoes[i] = new JButton(tiposExame[i]);
            this.criaBotaoNormal(botoes[i]);
            botoes[i].addActionListener((java.awt.event.ActionEvent evt) -> {
                this.setVisible(false);
                this.exameSelecionado((JButton) evt.getSource(), botoes);
            });
            painel.add(botoes[i]);
        }
        this.defineConfiguracoes();
    }
    
    private void agendaExame(String exame){
        this.setTitle("SARACURA - Agendamento de Consulta");
        JLabel espEsc = new JLabel("TIPO DE EXAME: " + exame, SwingConstants.CENTER);
        espEsc.setFont(this.fonteTituloGrande);
        
        JLabel texto1 = new JLabel("Selecione o Período do Exame:");
        texto1.setFont(this.fonteTituloGrande);
        JXDatePicker inicio = new JXDatePicker();
        JXDatePicker fim = new JXDatePicker();
        inicio.setDate(Calendar.getInstance().getTime());
        inicio.setFont(this.fonteTextoNormal);
        fim.setDate(Calendar.getInstance().getTime());
        fim.setFont(this.fonteTextoNormal);
        JPanel painel1 = new JPanel();
        painel1.setBackground(this.corFundoPrincipal);
        painel1.setLayout(new BorderLayout());
        JPanel tempoPainel = new JPanel();
        tempoPainel.setBackground(this.corFundoPrincipal);
        tempoPainel.setLayout(new GridLayout(1,4));
        tempoPainel.add(inicio);
        tempoPainel.add(fim);
        
        JLabel texto2 = new JLabel("Escolha o Médico Operador e o Horário:");
        texto2.setFont(this.fonteTituloGrande);
        String[] escolhas = {"17/01/2018 | 13:00 | Dr Neil Gaiman | R$650,00",
                             "17/01/2018 | 18:00 | Dr Edgar Alan Poe | R$450,00"};
        JComboBox opcoes = new JComboBox(escolhas);
        opcoes.setFont(this.fonteTextoNormal);
        JButton botao2 = new JButton("Confirmar");
        this.criaBotaoNormal(botao2);
        JPanel painel2 = new JPanel();
        painel2.setBackground(this.corFundoPrincipal);
        painel2.setLayout(new BorderLayout());
        painel2.add(opcoes, BorderLayout.CENTER);
        painel2.add(botao2, BorderLayout.EAST);
        
        String opcaoPadrao = "17/01/2018;13:00;Dr Neil Gaiman;R$650,00";
        String[] opcaoPartes = opcaoPadrao.split(";");
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
        painel3.setLayout(new GridLayout(2,2));
        painel3.add(medico);
        painel3.add(data);
        painel3.add(horario);
        painel3.add(valor);
        
        
        JLabel tipoDeExameTexto = new JLabel("Escolha a Região do Exame:");
        tipoDeExameTexto.setFont(this.fonteTituloGrande);
        JPanel tipoDeExamePainel = new JPanel();
        tipoDeExamePainel.setLayout(new BorderLayout());
        tipoDeExamePainel.setBackground(this.corFundoPrincipal);
        String[] tiposDeExame = {"Tomografia do Pescoço",
                                 "Tomografia da Tireoide",
                                 "Tomografia da Região Cervical",
                                 "Tomografia da Laringe"};
        JComboBox tipoDeExameElemento = new JComboBox(tiposDeExame);
        tipoDeExameElemento.setFont(this.fonteTextoNormal);
        JButton tipoExameBotao = new JButton("Confirmar");
        this.criaBotaoNormal(tipoExameBotao);
        tipoDeExamePainel.add(tipoDeExameElemento, BorderLayout.CENTER);
        tipoDeExamePainel.add(tipoExameBotao, BorderLayout.EAST);
        
        
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
        painel4.add(dinheiro);
        painel4.add(cartao);
        painel4.add(convenio);
        painel4.add(cheque);
        
        JButton botao3 = new JButton("Finalizar");
        this.criaBotaoNormal(botao3);
        botao3.addActionListener((java.awt.event.ActionEvent evt) -> {
                this.finalizarConsulta();
        });
        
        this.painel = new JPanel();
        this.painel.setLayout(new GridLayout(11,1));
        this.painel.add(espEsc);
        this.painel.add(texto1);
        this.painel.add(tempoPainel);
        this.painel.add(texto2);
        this.painel.add(painel2);
        this.painel.add(painel3);
        this.painel.add(tipoDeExameTexto);
        this.painel.add(tipoDeExamePainel);
        this.painel.add(texto4);
        this.painel.add(painel4);
        this.painel.add(botao3);
        this.setFont(this.fonteTituloGrande);
        this.getContentPane().removeAll();
        this.repaint();
        this.add(painel);
        this.defineConfiguracoes();
    }

    private void exameSelecionado(JButton botaoPressionado, JButton[] botoes) {
        this.agendaExame(botaoPressionado.getText());
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
    
    private void finalizaOperacao() {
        int randomNum = ThreadLocalRandom.current().nextInt(0,12);
        if(this.formaDePagamento == "Convênio" && randomNum != 12){
            JOptionPane.showMessageDialog(this, "Desculpe! O Número do Convênio não Existe.");
        }else{
            JOptionPane.showMessageDialog(this, "Obrigada! Operação Realizada com Sucesso.");
        }
        this.setVisible(false);
    }
    
}
