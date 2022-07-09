/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;

import java.util.ArrayList;


//Essa classe é responsável pelo tabuleiro do jogo em si, desconsiderando a parte gŕafica (que é atribuição da classe Oceano). 
public class Tabuleiro {
    private final int x; //Tamanho horizontal do tabuleiro
    private final int y; //Tamanho vertical do tabuleiro
    private Quadrado[][] oceano;

    public Tabuleiro(int x, int y){
        this.x = x;
        this.y = y;
        oceano = new Quadrado[x][y];
        for (int i = 0; i < x; i++){ //Preenche a matriz oceano com quadrados
            for(int j = 0; j < y; j++){
                oceano[i][j] = new Quadrado();
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean podeAtirar(int x, int y){ //Caso o quadrado escolhido já tenha sido alvo antes - ou seja, seja afundado ou errado - ele não é passível de um tiro. Caso contrário, retornamos verdadeiro.
        return oceano[x][y].getStatus() == StatusQ.NAVIO || oceano[x][y].getStatus() == StatusQ.VAZIO;
    }
    
    
    public boolean podeColocar(int x, int y, Direcao direcao, int comprimento){
        //Essa função é responsável por determinar se um navio pode ser colocado neste tabuleiro. Seus parâmetros são apenas os detalhes necessários.
        if (direcao == Direcao.NORTE || direcao == Direcao.SUL) {
            int y2 =  y + (direcao.getY() * comprimento); //Coordenadas da poupa do navio
                if (y2 >= this.y || y2 < 0){ //Caso a poupa do navio ultrapasse os limites do tabuleiro, ele não pode ser colocado neste tabuleiro. 
                    return false;
                }
                for(int j = y; j != y2; j=j+direcao.getY()){ //Verificamos todos os quadrados do futuro navio. Caso haja algum que não esteja vazio, um navio não pode ser colocado lá.
                    if (!isVazio(x, j)){
                            return false;                    
                }
            }   
        }
        else if (direcao == Direcao.LESTE || direcao == Direcao.OESTE) {
            int x2 = x + (direcao.getX() * comprimento); //Coordenadas da poupa do navio
            if (x2 >= this.x || x2 < 0 ) { //Caso a poupa do navio ultrapasse os limites do tabuleiro, ele não pode ser colocado neste tabuleiro.
                return false;
            }
            for(int i = x; i != x2; i=i+direcao.getX()) { //Verificamos todos os quadrados do futuro navio. Caso haja algum que não esteja vazio, um navio não pode ser colocado lá.
                if (!isVazio(i, y)){
                    return false;
                }
            }
        }
        return true;
    }
    
    
    public boolean isVazio(int x, int y){
        return oceano[x][y].getStatus() == StatusQ.VAZIO;
    }
    
    public ArrayList<Quadrado> colocarNavio(int x, int y, Direcao direcao, int comprimento){
        //Método responsável por colocar o navio no tabuleiro, transformando todos os seus quadrados em navios.
        ArrayList<Quadrado> Navio; //Essa arra
        Navio = new ArrayList();
        
        switch(direcao){
            case NORTE, SUL->{ //Casos verticais
                int y2 = y + (direcao.getY() * comprimento); //Coordenada da poupa do navio
                    for(int j = y; j != y2; j=j+direcao.getY()){ //Itera por todos os quadrados do navio que está sendo criado
                        oceano[x][j].navio(); //Transforma cada quadrado do navio em status navio
                        Navio.add(oceano[x][j]); //Acrescenta cada quadrado para a lista
                    }
                }
            case OESTE, LESTE->{ //Casos horizontais
                int x2 = x + (direcao.getX() * comprimento); //Coordenada da poupa do navio
                for(int i = x; i != x2; i=i+direcao.getX()){ //Itera por todos os quadrados do navio que está sendo criado
                    oceano[i][y].navio(); //Transforma cada quadrado do navio em status navio
                    Navio.add(oceano[i][y]); //Acrescenta cada quadrado para a lista
                }
            }
        }
        return Navio;
    } 
     
    
    public Quadrado getCasa(int x, int y){
        return oceano[x][y];
    }
}
