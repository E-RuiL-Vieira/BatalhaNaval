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
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean multiplayer;
    private boolean host;
    private boolean vez;
    
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
        tab2 = new Tabuleiro (10, 10);
        
        //Cria o objeto jogador do usuário
        String nomeP1 = JOptionPane.showInputDialog("Por favor insira seu nome");
        jogador = new Jogador (tab1, nomeP1);
        jogador.posicionarNavios(); //Posiciona navios automaticamente
        /*
        for (Navio n : jogador.getNavios()) {
            colocarNavios colocarnavios = new colocarNavios(tab1, n);
            while(colocarnavios.estaEmUso());
            colocarnavios.dispose();
        }*/
    }
  
    
    public void iniciarmultiplayer() throws IOException, ClassNotFoundException {
        Object reply2 = null;
        Object[] hostopcoes = {"Hostear", "Entrar"};
        reply2 = JOptionPane.showInputDialog(null, "Hostear ou entrar", "Menu", JOptionPane.INFORMATION_MESSAGE, null, hostopcoes, hostopcoes[0]);
        Socket socket;
        if (reply2.equals("Hostear")) {// HOST
            host = true;
            vez = true;
            ServerSocket server = new ServerSocket(1234);
            System.out.println("Aguardando conexões...");
            socket = server.accept();
            System.out.println("Conectado...");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        }
        else { // CLIENTE
            host = false;
            vez = false;
            socket = new Socket("192.168.1.101", 1234);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        }
    }
    
    public void iniciarsingleplayer() {
        ai = new Bot (tab2);
    }
    
    public void jogadas() throws ClassNotFoundException, IOException {
        if (multiplayer){
            if (vez){
                oponente = readJogador();
                tab2 = oponente.getTab();
                Partida oTab = new Partida(true, tab2);
                while(oTab.estaEmUso()); 
                sendJogador(oponente);
                vez = false;
            }
            else{
                Partida jTab = new Partida(false, tab1);
                sendJogador(jogador); 
                //espera o oponente 
                jogador = readJogador();
                jTab.atualizar();
                vez = true;
            }
        }
        
        else{
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
    }
    
    public boolean verificarPartida(){
        if (multiplayer)
            return jogador.isVivo() && oponente.isVivo();
        else 
            return jogador.isVivo() && ai.isVivo();
    }
    
    public void sendJogador (Jogador jogador) throws IOException, ClassNotFoundException {
        output.writeObject(jogador);
        output.flush();
    }
    
    public Jogador readJogador() throws IOException, ClassNotFoundException {
        return (Jogador)input.readObject();
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