
package saracura;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JanelasIniciais extends InterfaceGrafica{
    
    public JanelasIniciais(){
        super();
        this.criaJanelaInicial();
    }
    
    private void criaJanelaInicial(){  
        JFrame initWindow = new JFrame("SARACURA - Tela Inicial");
        JLabel initText = new JLabel("Bem-Vindo ao Sistema SARACURA", SwingConstants.CENTER);
        JLabel imgLabel = new JLabel(this.logo);
        JButton nextButton = new JButton("Prosseguir");
        nextButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            initWindow.setVisible(false);
            this.criaJanelaHome();
        });
        
        initWindow.setIconImage(this.icone.getImage());
        initWindow.setLayout(new BorderLayout());
        initWindow.add(initText, BorderLayout.CENTER);
        initWindow.add(imgLabel, BorderLayout.NORTH);
        initWindow.add(nextButton, BorderLayout.SOUTH);
        
        initWindow.setPreferredSize(new Dimension(400,400));
        initWindow.setResizable(false);
        initWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initWindow.setVisible(true);
        initWindow.setLocationRelativeTo(null);
        initWindow.pack();
    }

    private void criaJanelaHome() {
        JFrame homeWindow = new JFrame("SARACURA - Home");
        JButton buttons[] = new JButton[4];
        String[] buttonsNames = { "Agendamento de Consultas",
            "Agendamento de Exames", "Atualização de Agendas", "Encerrar"};
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1));
        for(int i=0; i<4; i++){
            buttons[i] = new JButton(buttonsNames[i]);
            buttons[i].addActionListener((java.awt.event.ActionEvent evt) -> {                    
                this.handlePressedButton((JButton) evt.getSource(), buttons);
            });
            panel.add(buttons[i]);
        }
        homeWindow.add(panel);
        homeWindow.setPreferredSize(new Dimension(400,400));
        homeWindow.setResizable(false);
        homeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeWindow.setVisible(true);
        homeWindow.setLocationRelativeTo(null);
        homeWindow.pack();
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
            case 1:
                AgendamentoDeExames b = new AgendamentoDeExames();
            case 2:
                AtualizacaoDeAgendas c = new AtualizacaoDeAgendas();
            case 3:
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        
        }
    }
}
