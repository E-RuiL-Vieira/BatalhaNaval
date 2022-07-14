/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//Classe principal, onde o jogo será rodado.
final class Jogo implements Serializable {
    private Jogador jogador;
    private Jogador oponente;
    private Bot ai;
    private Tabuleiro tab1;
    private Tabuleiro tab2;
    public static Object LOCK = new Object();
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean multiplayer;
    private boolean host;
    
    
    public Jogo() throws IOException, ClassNotFoundException {
        host = false;
        multiplayer = false;
        Object[] opcoes = {"Singleplayer", "Multiplayer"};
        Object reply = JOptionPane.showInputDialog(null, "Escolha um modo de jogo", "Menu", JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
        if (reply.equals("Multiplayer")) {
            multiplayer = true;    
        }
        
        //Cria tabuleiro para os dois jogadores
        tab1 = new Tabuleiro (10, 10);
        
        //Cria o objeto jogador do usuário
        String nomeP1 = JOptionPane.showInputDialog("Por favor insira seu nome");
        jogador = new Jogador (tab1, nomeP1);
        
        //Posiciona navios automaticamente
        //jogador.posicionarNavios();
        
       for (Navio n : jogador.getNavios()) {
            colocarNavios colocarnavios = new colocarNavios(tab1, n);
            synchronized(LOCK){
                while(colocarnavios.estaEmUso()){
                    try{LOCK.wait();}
                    catch(InterruptedException e){break;}
                }
            }
            colocarnavios.dispose(); 
       }
    }
  
    
    public void iniciarmultiplayer() throws IOException, ClassNotFoundException {
        Object reply2 = null;
        Object[] hostopcoes = {"Hostear", "Entrar"};
        reply2 = JOptionPane.showInputDialog(null, "Hostear ou entrar", "Menu", JOptionPane.INFORMATION_MESSAGE, null, hostopcoes, hostopcoes[0]);
        Socket socket;
        if (reply2.equals("Hostear")) {// HOST
            host = true;
            jogador.setVezJogador(true);
            ServerSocket server = new ServerSocket(1234);
            System.out.println("Aguardando conexões...");
            socket = server.accept();
            System.out.println("Conectado...");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        }
        else { // CLIENTE
            host = false;
            jogador.setVezJogador(false);
            socket = new Socket("192.168.1.101", 1234);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        }
    }
    
    public void iniciarsingleplayer() {
        tab2 = new Tabuleiro (10, 10);
        ai = new Bot (tab2);
    }
    
    public void jogadas() throws ClassNotFoundException, IOException {
        if (multiplayer){
            if (jogador.isVezJogador()){ //Vez do Jogador
                try {
                    oponente = (Jogador)input.readObject();
                    tab2 = oponente.getTab();
                    Rodada oTab = new Rodada(true, tab2, jogador.getNome());
                    while(oTab.estaEmUso()){
                        esperar(10);
                    }
                    oponente.setVezJogador(true);
                    jogador.setVezJogador(false);
                    output.writeObject(oponente); //Envia dados
                    oTab.dispose();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                output.writeObject(jogador); //Envia dados
                jogador = (Jogador)input.readObject(); 
            }
            else{ // Vez do oponente
                tab1 = jogador.getTab(); //Tab1 recebe o tabuleiro do jogador local
                Rodada jTab = new Rodada(false, tab1, jogador.getNome()); //Cria uma noja janela com o tabuleiro do player local
                output.writeObject(jogador); //Envia o tabuleiro do jogador local para o oponente
                while (!jogador.isVezJogador()) { //Enquanto a vez do jogador local não chegar, continua atualizando a tela
                    //Espera até que a vez do oponente termine
                    jogador = (Jogador)input.readObject(); //Recebe de input do oponente o local do tiro
                    jTab.setTab(jogador.getTab()); //Muda a janela para o novo tabuleiro recebido
                    jTab.atualizar(); // Atualiza a tela
                } 
                oponente = (Jogador)input.readObject(); //Recebe de 
                jogador.setVezJogador(true);
                oponente.setVezJogador(false);
                output.writeObject(oponente);
                jTab.atualizar();
                esperar(2000);
                jTab.dispose();
            }
        }
        
        else{
            Rodada oTab = new Rodada(true, tab2, jogador.getNome());
            synchronized(LOCK){
                while(oTab.estaEmUso()){ //Enquanto o usuário estiver fazendo a sua jogada, o resto do ojogo parará
                    try{LOCK.wait();}
                    catch(InterruptedException e){break;}
                }
            }
            esperar(2000); //Pausa para que o usuário analise o tabuleiro
            oTab.dispose();
            Rodada jTab = new Rodada(false, tab1, "Computador");
            esperar(2000); //Pausa dramática
            jTab.tirodadooponente(ai.atirar(tab1)); //Jogada do bot é feita
            esperar(2000); //Pausa para que o usuário analise o tabuleiro
            jTab.dispose();
        }
    }
    
    public boolean verificarPartida(){
        if (multiplayer)
            return jogador.isVivo() && oponente.isVivo();
        else 
            return jogador.isVivo() && ai.isVivo();
    }
    
    public void esperar(int ms){
        try{
            Thread.sleep(ms);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    
    }

    public boolean isMultiplayer() {
        return multiplayer;
    }
    
    
    public void vitoria (){
        if (jogador.isVivo()){
            JOptionPane.showMessageDialog(null, jogador.getNome() + " venceu!", "FIM DO JOGO", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, oponente.getNome() + " venceu!", "FIM DO JOGO", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
}