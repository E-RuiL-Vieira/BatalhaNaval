/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;

import java.util.ArrayList;
import java.util.Random;


//Essa classe é um template no qual serão baseados tanto o jogador dos usuários quando o Bot.
abstract public class Jogador {
    protected Tabuleiro tab;
    protected final Random rand = new Random();
    protected ArrayList<Navio> navios; 
    protected String nome;
    
    public Jogador(Tabuleiro tab, nome name){
        this.nome = name;
        navios = new ArrayList();
        this.tab = tab;
        //1 porta aviões, 2 navio tanque, 3 contra torpedos e 4 submarinos
        this.navios.add(new Navio(TipoNavio.PORTAAVIOES));
        this.navios.add(new Navio(TipoNavio.NAVIOTANQUE));
        this.navios.add(new Navio(TipoNavio.NAVIOTANQUE));
        this.navios.add(new Navio(TipoNavio.CONTRATORPEDOS));
        this.navios.add(new Navio(TipoNavio.CONTRATORPEDOS));
        this.navios.add(new Navio(TipoNavio.CONTRATORPEDOS));
        this.navios.add(new Navio(TipoNavio.SUBMARINO));
        this.navios.add(new Navio(TipoNavio.SUBMARINO));
        this.navios.add(new Navio(TipoNavio.SUBMARINO));
        this.navios.add(new Navio(TipoNavio.SUBMARINO));

    }
    
    
    public boolean isVivo() {
        for(Navio i : navios){
            if(!i.verificarAfundado()){ //Se houver um único navio que não esteja afundado, então o jogador ainda está vivo.
                return true;
            }
        }
        return false;    
    }
    
    public Tabuleiro getTab() {
        return tab;
    }
    
    public ArrayList<Navio> getNavios(){
        return navios;
    }

    public String getNome() {
        return nome;
    }
     
    public void posicionarNavios(){ //Itera por todos os navios do jogador, posicionando cada um deles de maneira aleatória no tabuleiro.
        for(Navio i : navios){
            posicionarNavio(i);
        }
    }
    
    //Esse método é responsável por posicionar um navio de maneira aleatória no tabuleiro.
    public void posicionarNavio(Navio navio){
        Direcao ndirecao;
        int x;
        int y;
        
        do{
            ndirecao = (Direcao.values())[rand.nextInt(4)];
            x = rand.nextInt(tab.getX());
            y = rand.nextInt(tab.getY());
        }
        while(!tab.podeColocar(x, y, ndirecao, navio.getComprimento())); //Procura uma direção e coordenadas novas que podem ser colocadas no tabuleiro
        
        navio.criarNavio(x, y, ndirecao, tab);
    }
    
}
