/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projet_picross;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author val10
 */
public class Interface extends JFrame {
    private JPanelImage panel;
    public Interface(int nbDeLignes,int nbDeColonnes){
        super();
        panel = new JPanelImage(2000, 2000);
        getContentPane().add(panel);
        setTitle("Picross de taille" + nbDeLignes + nbDeColonnes);
        setSize(2000, 2000);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
