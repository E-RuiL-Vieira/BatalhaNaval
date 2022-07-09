/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//Classe principal, onde o jogo será rodado.
final class Jogo {
    Jogador jogador;
    Jogador oponente;
    Bot ai;
    Tabuleiro tab1;
    Tabuleiro tab2;
    
    public Jogo(){
        //Cria tabuleiro para os dois jogadores
        tab1 = new Tabuleiro (10, 10);
        tab2 = new Tabuleiro (10, 10);
        
        //Cria o objeto jogador do usuário
        String nomeP1 = JOptionPane.showInputDialog("Por favor insira seu nome");
        jogador = new Jogador (tab1, nomeP1);
        //jogador.posicionarNavios();
        
        for (Navio n : jogador.getNavios()) {
            colocarNavios colocarnavios = new colocarNavios(tab1, n);
            while(colocarnavios.estaEmUso());
            colocarnavios.dispose();
        }
        
        Object[] opcoes = {"Singleplayer", "Multiplayer"};
        Object reply = JOptionPane.showInputDialog(null, "Escolha um modo de jogo", "Menu", JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        if (reply.equals("Singleplayer")){ //Caso o selecionado será o modo singleplayer, o usuário jogará contra um computador.
            ai = new Bot (tab2);
            while(jogador.isVivo() && ai.isVivo()){ //O jogo continua até pelo menos um dos jogadores morrer
                Partida oTab = new Partida(true, tab2);
                while(oTab.estaEmUso()); //Enquanto o usuário estiver fazendo a sua jogada, o resto do ojogo parará
                esperar(5000); //Pausa para que o usuário analise o tabuleiro
                oTab.dispose();
                Partida jTab = new Partida(false, tab1);
                esperar(5000); //Pausa dramática
                jTab.tirodadooponente(ai.atirar(tab1)); //Jogada do bot é feita
                esperar(3000); //Pausa para que o usuário analise o tabuleiro
                jTab.dispose();
            }
            vitoria(jogador, ai); //Verifica quem ganhou, e mostra. 
        }
    }
    
    public void esperar(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    
    }
    
    public void vitoria (Jogador jogador, Jogador oponente){
        JLabel texto;
        if (jogador.isVivo()){
            texto = new JLabel(jogador.getNome() + " venceu!");
        }
        else{
            texto = new JLabel(oponente.getNome() + " venceu!");
        }
        JFrame mostrartexto = new JFrame();
        mostrartexto.add(texto);
        mostrartexto.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        mostrartexto.setSize( 200, 100 ); 
        mostrartexto.setResizable(false);
        mostrartexto.setVisible( true );
    }
    
}