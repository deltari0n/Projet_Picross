/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet_pi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author vruche
 */
public class Souris implements MouseListener{
    
    //attributs
    private boolean click;
    private int x,y,bouton;
 
    //guetteur et setteur
 
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getBoutonSouris(){
        return this.bouton;
    }
    public boolean getClick(){
        return click;
    }
    public void setClick(boolean val){
        this.click = val;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
        this.bouton = e.getButton();
        this.click = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}