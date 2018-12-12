
package saracura;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class AgendamentoDeConsultas extends InterfaceGrafica{
    private int numEspecialidades;
    
    public AgendamentoDeConsultas(){
        super();
        this.listaEspecialidades();
    }
    
    private void listaEspecialidades(){
        this.numEspecialidades = 6;
        
        JFrame janela = new JFrame("SARACURA - Lista de Especialidades");
        JRadioButton botoes[] = new JRadioButton[this.numEspecialidades];
        String[] nomesBotoes = { "Angiologia", "Cardiologia",
            "Gastroenterologia", "Neurologia", "Nutrologia", "Obstetrícia"};
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(this.numEspecialidades,1));
        for(int i=0; i<6; i++){
            botoes[i] = new JRadioButton(nomesBotoes[i]);
            botoes[i].setActionCommand(nomesBotoes[i]);
            botoes[i].addActionListener((java.awt.event.ActionEvent evt) -> {                    
                this.analisaPressionamentoDeBotao((JButton) evt.getSource(), botoes);
            });
            painel.add(botoes[i]);
        }
        janela.add(painel);
        
        janela.setIconImage(this.icone.getImage());
        
        janela.setPreferredSize(new Dimension(400,400));
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
        janela.setLocationRelativeTo(null);
        janela.pack();
    }

    private void analisaPressionamentoDeBotao(JButton jButton, JRadioButton[] botoes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
