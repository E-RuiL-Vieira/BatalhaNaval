/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batalhanaval;
import java.util.Random;
import java.util.*;
/**
 *
 * @author rurineco
 */
public final class Bot extends IJogador {
    private int[] ultimotiro;
    private boolean acertou;
    private Direcao direcao;
    private final Random rand = new Random();
    private int c;
    private List<Direcao> possiveis;
        
    public Bot(Tabuleiro tab){
        super(tab);
        acertou = false;
        nome = "Computador";
        direcao = Direcao.VAZIO;
        c = 0;
        possiveis = Arrays.asList(Arrays.copyOfRange(Direcao.values(), 0, 4));
        posicionarNavios();
    }
    
    
    public boolean atirar(int x, int y, Tabuleiro oTab){
        Direcao novaDirecao;
        if(c >= 5 || possiveis.isEmpty()){
            c = 0;
            acertou = false;
            direcao = Direcao.VAZIO;
            possiveis = Arrays.asList(Arrays.copyOfRange(Direcao.values(), 0, 4));
            return atirar(x, y, oTab);
        }
        if (!acertou){
            x = rand.nextInt(oTab.getX());
            y = rand.nextInt(oTab.getY());
            while(!oTab.podeAtirar(x, y)){
                x = rand.nextInt(oTab.getX());
                y = rand.nextInt(oTab.getY());
            }
            if(oTab.getCasa(x, y).Alvo()){
                acertou = true;
                ultimotiro[0] = x;
                ultimotiro[1] = y;
                return true;
            }
            return false;
        }
        if(direcao == Direcao.VAZIO){
            novaDirecao = possiveis.get(rand.nextInt(4));
            x = ultimotiro[0]+novaDirecao.getX();
            y = ultimotiro[1]+novaDirecao.getY();
            if(oTab.getCasa(x, y).Alvo()){
                direcao = novaDirecao;
                c++;
                ultimotiro[0] = x;
                ultimotiro[1] = y;
                return true;
            }
            else{
                possiveis.remove(novaDirecao);
                return false;
            }
        }
        x = ultimotiro[0] + direcao.getX();
        y = ultimotiro[1] + direcao.getY();
        if (oTab.getCasa(x, y).Alvo()){
            c++;
            ultimotiro[0] = x;
            ultimotiro[1] = y;
            return true;
        }
        c = 0;
        direcao = Direcao.VAZIO;
        acertou = false;
        possiveis = Arrays.asList(Arrays.copyOfRange(Direcao.values(), 0, 4));
        return false;           
    }  
    
    public void posicionarNavios(){
        for(Navio i : navios){
            posicionarNavio(i);
        }
    }
    
    public void posicionarNavio(Navio navio){
        Direcao ndirecao;
        int x;
        int y;
        
        do{
            ndirecao = (Direcao.values())[rand.nextInt(4)];
            x = rand.nextInt(tab.getX());
            y = rand.nextInt(tab.getY());
        }
        while(!tab.podeColocar(x, y, ndirecao, navio.getComprimento())); 
        navio.criarNavio(x, y, direcao, tab);

    }
}