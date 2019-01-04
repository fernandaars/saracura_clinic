package saracura;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

class InterfaceGrafica extends JFrame {

    protected final Color corFundoPrincipal;
    protected final Color corFundoSecundaria;
    protected final Color corFonte;

    protected final ImageIcon imgIcone;
    protected final ImageIcon imgLogoPrincipal;
    protected final ImageIcon imgLogoSecundaria;
    protected final ImageIcon imgTelaInicial;

    protected final int tamAlturaTela;
    protected final int tamLarguraTela;
    protected final int tamBorda;

    protected final Font fonteTituloGrande;
    protected final Font fonteTituloMedia;
    protected final Font fonteTextoNormal;

    protected JPanel painel;

    InterfaceGrafica() {
        this.setNimbusLookAndFeel();
        this.corFundoPrincipal = new Color(251, 205, 190);
        this.corFonte = new Color(70, 73, 82);
        this.corFundoSecundaria = new Color(217, 217, 217);

        this.imgIcone = new ImageIcon("imgs/logo_100x100.png", "icon");
        this.imgLogoPrincipal = new ImageIcon("imgs/logo_1600x1600.png",
                "icon");
        this.imgLogoSecundaria = new ImageIcon("imgs/logo_300x300.png", "icon");
        this.imgTelaInicial = new ImageIcon("imgs/tela_inicial.png", "icon");

        this.tamAlturaTela = 600;
        this.tamLarguraTela = 600;
        this.tamBorda = 10;

        Font novaFonte;
        try {
            novaFonte = Font.createFont(Font.TRUETYPE_FONT,
                    new File("fonts/Righteous-Regular.ttf"))
                    .deriveFont(28f);
        } catch (FontFormatException | IOException ex) {
            System.out.println("Fonte Alternativa Carregada.");
            novaFonte = new Font("Arial", Font.PLAIN, 35);
        }
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        ge.registerFont(novaFonte);

        this.fonteTituloGrande = novaFonte;
        this.fonteTituloMedia = novaFonte;
        this.fonteTextoNormal = new Font("Helvetica", Font.PLAIN, 16);

        this.painel = new JPanel();
        this.painel.setBackground(this.corFundoPrincipal);
        this.painel.setForeground(this.corFonte);
        this.painel.setBorder(BorderFactory.createEmptyBorder(this.tamBorda,
                this.tamBorda, this.tamBorda, this.tamBorda));

    }
    
    protected void criaBotaoNormal(JButton botaoNormal){
        botaoNormal.setBackground(this.corFundoSecundaria);
        botaoNormal.setForeground(this.corFonte);
        botaoNormal.setFocusPainted(false);
        botaoNormal.setFont(this.fonteTituloMedia);
    }
    
    protected void criaBotaoGrande(JButton botaoInicial){
        botaoInicial.setBackground(this.corFundoSecundaria);
        botaoInicial.setForeground(this.corFonte);
        botaoInicial.setFocusPainted(false);
        botaoInicial.setFont(this.fonteTituloGrande);
    
    }
    
    protected void defineConfiguracoes(){
        this.painel.setBackground(this.corFundoPrincipal);
        this.painel.setForeground(this.corFonte);
        this.painel.setBorder(BorderFactory.createEmptyBorder(this.tamBorda,
                              this.tamBorda, this.tamBorda, this.tamBorda));
        this.setContentPane(this.painel);
        this.setIconImage(this.imgIcone.getImage());
        this.setPreferredSize(new Dimension(this.tamAlturaTela, this.tamLarguraTela));

        this.setFont(this.fonteTituloGrande);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
    }
    
    private void setNimbusLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(this, "Nimbus look and feel seems not to be available.");
        }
    }
}
