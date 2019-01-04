package saracura;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class JanelasIniciais extends InterfaceGrafica{
    
    public JanelasIniciais(){
        super();
        this.criaJanelaInicial();
    }
    
    private void criaJanelaInicial(){  
        this.setTitle("SARACURA - Tela Inicial");
        JLabel imagem = new JLabel(this.imgTelaInicial);
        JButton botao = new JButton("Prosseguir");
        this.criaBotaoGrande(botao);
        botao.addActionListener((java.awt.event.ActionEvent evt) -> {
            this.setVisible(false);
            this.criaJanelaHome();
        });
        
        this.painel.setLayout(new BorderLayout());
        this.painel.add(imagem, BorderLayout.CENTER);
        this.painel.add(botao, BorderLayout.SOUTH);
        this.defineConfiguracoes();
    }

    private void criaJanelaHome() {
        this.setTitle("SARACURA - Home");
        JButton botoes[] = new JButton[4];
        // Thiago
        String[] opcoes = { "Agendamento de Consultas",
            "Agendamento de Exames", "Atualização de Agendas", "Encerrar"};
        this.painel = new JPanel();
        this.painel.setLayout(new GridLayout(4,1));
        for(int i=0; i<4; i++){
            botoes[i] = new JButton(opcoes[i]);
            this.criaBotaoNormal(botoes[i]);
            botoes[i].addActionListener((java.awt.event.ActionEvent evt) -> {                    
                this.handlePressedButton((JButton) evt.getSource(), botoes);
            });
            this.painel.add(botoes[i]);
        }
        botoes[2].setEnabled(false);
        this.getContentPane().removeAll();
        this.repaint();
        this.add(painel);
        this.defineConfiguracoes();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void handlePressedButton(JButton jButton, JButton[] buttons) {
        int option = 3;
        for(int i=0; i<4; i++)
            if(jButton == buttons[i]){
                option = i;
                break;
            }
          
        switch(option){
            case 0: 
                AgendamentoDeConsultas a = new AgendamentoDeConsultas();
                break;
            case 1:
                AgendamentoDeExames b = new AgendamentoDeExames();
                break;
            case 2:
                AtualizacaoDeAgendas c = new AtualizacaoDeAgendas();
            case 3:
                System.exit(0);
        
        }
    }
}    