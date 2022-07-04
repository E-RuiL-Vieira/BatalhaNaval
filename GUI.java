/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batalhanaval;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI implements ActionListener {
    
    JLabel label;
    JFrame frame;
    JPanel panel;
    int i = 0;
    STATE state;
    Jogo jogo;

    
    private enum STATE {
        MENU,
        GAME
    }

    public GUI() {
        frame = new JFrame();
        JButton button_single = new JButton("Singleplayer");
        JButton button_multi = new JButton("Multiplayer");
        state = STATE.MENU;
        button_single.addActionListener(this);
        button_multi.addActionListener(this);
        
        label = new JLabel("Escolha o modo de jogo");
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        //panel.setLayout(new GridLayout());
        panel.add(button_single);
        panel.add(button_multi);
        
        button_single.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               jogo = new Jogo(false);
            }
        }); 
         button_single.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               jogo = new Jogo(true);
            }
        }); 
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Batalha Naval");
        frame.pack();
        frame.setVisible(true);
    }
    
    
    public int[] lerCoordenada(){
        @Override 
        void mouseClicked(MouseEvent e){
        
    }
    }
    
    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
    }
}
