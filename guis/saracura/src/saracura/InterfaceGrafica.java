package saracura;

import java.awt.*;
import javax.swing.*;
    
class InterfaceGrafica extends JFrame{
    protected final Color rosa;
    protected final Color cinza;
    protected final Color cinzaClaro;
    protected final ImageIcon icone;
    protected final ImageIcon logo;
   
    InterfaceGrafica() {
        this.rosa = new Color(237, 151, 160);
        this.cinza = new Color(70, 73, 82);
        this.cinzaClaro = new Color(217, 217, 217);
        this.icone = new ImageIcon("imgs/logo.png", "icon");
        this.logo = new ImageIcon("imgs/logo2.png", "icon");
    }
} 
