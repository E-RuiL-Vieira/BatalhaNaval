
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

//Essa classe é responsável pela interface que é exibida para o usuário durante uma rodada. 
public class Partida extends Oceano{
    private boolean vez; 
    private JLabel textovez;
    private JFrame mostrartexto = new JFrame();
    private ActionListener ButtonListener;

    
    public Partida(boolean vez, Tabuleiro tab){
        super(tab);
        this.vez = vez;
        this.emuso = true;
        ativarbotoes(vez); //Caso seja a vez do jogador, os botões serão clicáveis. Caso não seja, e ele esteja apenas assistindo, ele não poderá interagir com o tabuleiro.
        atualizar(); //Atualizamos o tabuleiro para que seja aplicado o override da função cor
        
    
        ButtonListener = (ActionEvent e) -> {
            int xCoord = Character.getNumericValue(e.getActionCommand().charAt(0));
            int yCoord = Character.getNumericValue(e.getActionCommand().charAt(1)); 
            //Armazena as coordenadas do botão
            if (tab.podeAtirar(xCoord, yCoord)){ 
                //Caso o botão seja passível de ser atirado, o tiro é feito.
                tirodado(tab.getCasa(xCoord, yCoord).Alvo()); 
                cor(xCoord, yCoord); 
                ativarbotoes(false); //Como já acabou a rodada do jogador, os botões são desativados e o emuso é mudado para falso
                System.out.println("TESTE");
                emuso = false;
            }
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
        JLabel texto;
        if (certo){
            texto = new JLabel("O oponente acertou o tiro");
        }
        else{
            texto = new JLabel("O oponente errou o tiro");
        }
        mostrartexto.add(texto);
        mostrartexto.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        mostrartexto.setSize( 200, 100 ); 
        mostrartexto.setResizable(false);
        mostrartexto.setVisible( true );
        
    }
    
    @Override
    protected void cor(int i, int j){
        super.cor(i,j);
        StatusQ status = tab.getCasa(i, j).getStatus();
        if(status == StatusQ.NAVIO){ //Caso seja a vez do jogador de jogar, queremos que os quadrados do navio do inimigo fiquem da mesma cor que o oceano. Caso não seja, os navios podem ter a cor normal.
            if(vez){
                casas[i][j].setBackground(Color.CYAN);
            }
        }
    
    }
    
    private void tirodado(boolean certo){ //Abre uma janela para informar ao jogador se ele acertou ou não o tiro.
        JLabel texto;
        if (certo){
            texto = new JLabel("Você acertou o tiro! :D");
        }
        else{
            texto = new JLabel("Você errou o tiro... :(");
        }
        mostrartexto.add(texto);
        mostrartexto.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        mostrartexto.setSize( 200, 100 ); 
        mostrartexto.setResizable(false);
        mostrartexto.setVisible( true );        
    }
    
    @Override
    public void dispose(){ //Fecha também a tela sobre o tiro quando fechar o tabuleiro
        mostrartexto.dispose();
        super.dispose();
    }
    
}
