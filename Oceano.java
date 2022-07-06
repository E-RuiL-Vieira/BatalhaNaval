/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batalhanaval;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
/**
 *
 * @author rurineco
 */
public abstract class Oceano extends JFrame {
    private final String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private GridBagLayout grid;
    protected GridBagConstraints constraints;
    protected Tabuleiro tab;
    protected boolean emuso;
    protected JButton casas[][];
    protected JPanel oceano = new JPanel(new FlowLayout(FlowLayout.LEFT));

    
    public Oceano(Tabuleiro tab){
        this.setSize(680, 750);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        oceano.setPreferredSize(new Dimension(650, 750));
        grid = new GridBagLayout();
        this.emuso = true;
        constraints = new GridBagConstraints();
        oceano.setLayout(grid);
        this.tab = tab;
        casas = new JButton[tab.getX()][tab.getY()];

        for (int i = 0; i < tab.getX(); i++){
            for (int j = 0; j < tab.getY(); j++){
                constraints.gridx = i + 1;
                constraints.gridy = j + 1;
                casas[i][j] = new JButton();
                casas[i][j].setPreferredSize(new Dimension(60, 60));
                casas[i][j].setBorder(BorderFactory.createEtchedBorder(0));
                casas[i][j].setActionCommand(String.valueOf(i) + String.valueOf(j));
                cor(i, j);
                oceano.add(casas[i][j], constraints);
            }
        }
        for (int i = 1; i < tab.getY() + 1; i++) {
           constraints.gridx = 0;
           constraints.gridy = i;
           JLabel gridNum = new JLabel(String.valueOf(i - 1));
           oceano.add(gridNum, constraints);
           constraints.gridx = i;
           constraints.gridy = 0;
           JLabel gridLet = new JLabel(letras[i - 1]);
           oceano.add(gridLet, constraints);
        }

        this.add(oceano);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    
    protected void atualizar(){
        for(int i = 0; i < tab.getX(); i++){
            for(int j = 0; j < tab.getY(); j++){
                cor(i, j);
            }
        }
    }
    
    protected void ativarbotoes(boolean opcao){
        for (JButton[] linha : casas) {
            for (JButton casa : linha){
                casa.setEnabled(opcao);
            }
        }
    }
    
    protected void cor(int i, int j){
        StatusQ status = tab.getCasa(i, j).getStatus();
        switch (status) {
            case VAZIO -> casas[i][j].setBackground(Color.CYAN);
            case ERRADO -> casas[i][j].setBackground(Color.RED);
            case AFUNDADO -> casas[i][j].setBackground(Color.BLACK);
            case NAVIO -> casas[i][j].setBackground(Color.CYAN);
        }
    
    }
    
    public boolean estaEmUso() {
        return emuso;
    }
    
}
