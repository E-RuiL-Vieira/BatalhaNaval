
/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//Essa classe é responsável pela interface que é exibida para o usuário durante uma rodada. 
public class Rodada extends GUI{
    private boolean vez; 
    private JLabel textovez;
    private ActionListener ButtonListener;

    
    public Rodada(boolean vez, Tabuleiro tab, String titulo){
        super(tab);
        this.vez = vez;
        this.emuso = true;
        ativarbotoes(vez); //Caso seja a vez do jogador, os botões serão clicáveis. Caso não seja, e ele esteja apenas assistindo, ele não poderá interagir com o tabuleiro.
        atualizar(); //Atualizamos o tabuleiro para que seja aplicado o override da função cor
        this.setTitle(titulo);
        
        ButtonListener = (ActionEvent e) -> {
            int xCoord = Character.getNumericValue(e.getActionCommand().charAt(0));
            int yCoord = Character.getNumericValue(e.getActionCommand().charAt(1)); 
            //Armazena as coordenadas do botão
            if (tab.podeAtirar(xCoord, yCoord)){ 
                //Caso o botão seja passível de ser atirado, o tiro é feito.
                tirodado(tab.getCasa(xCoord, yCoord).Alvo()); 
                cor(xCoord, yCoord); 
                ativarbotoes(false); //Como já acabou a rodada do jogador, os botões são desativados e o emuso é mudado para falso
                emuso = false;
                synchronized(Jogo.LOCK){
                    Jogo.LOCK.notifyAll();
                }}
        };
        for (int i = 0; i < tab.getX(); i++){ //Acrescenta o listener dos botões para todos os botões
            for (int j = 0; j < tab.getY(); j++){
                    casas[i][j].addActionListener(ButtonListener); 
        } }
        
        //Acrescenta texto abaixo do tabuleiro para informar ao jogador se é a vez dele ou do oponente
        constraints.gridx = 5;
        constraints.gridy = tab.getY() + 1;
        constraints.gridwidth = 3;
        constraints.ipady = 18;
        if (vez){
            textovez = new JLabel("Sua vez!");
        }
        else{
            textovez = new JLabel("Vez do adversário");
        }
        oceano.add(textovez, constraints);
    }
    

    
    public void tirodadooponente(boolean certo){ //Chamado quando o oponente está jogando.
        atualizar(); //Atualiza todos os quadrados, para que seja exibido onde o tiro foi dado.
        if (certo){
            JOptionPane.showMessageDialog(null, "O oponente acertou o tiro", "Tiro", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "O oponente errou o tiro", "Tiro", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    @Override
    protected void cor(int i, int j){
        super.cor(i,j);
        Quadrado.StatusQ status = tab.getCasa(i, j).getStatus();
        if(status == Quadrado.StatusQ.NAVIO){ //Caso seja a vez do jogador de jogar, queremos que os quadrados do navio do inimigo fiquem da mesma cor que o oceano. Caso não seja, os navios podem ter a cor normal.
            if(vez){
                casas[i][j].setBackground(Color.CYAN);
            }
        }
    
    }
    
    private void tirodado(boolean certo){ //Abre uma janela para informar ao jogador se ele acertou ou não o tiro.
        if (certo){
            JOptionPane.showMessageDialog(null, "Você acertou o tiro", "Tiro", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Você errou o tiro", "Tiro", JOptionPane.INFORMATION_MESSAGE);
        }      
    }
    
    
}
