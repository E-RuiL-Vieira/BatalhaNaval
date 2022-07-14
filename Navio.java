/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;
import java.io.Serializable;
import java.util.ArrayList;

//Essa classe é responsável por armazenar e gerenciar os navios de cada jogador. 
public class Navio implements Serializable {
	private boolean navioAfundado;
	private int posicaoX;
	private int posicaoY;
        private final TipoNavio tipo; 
        private ArrayList<Quadrado> quadrados;
       
        
    public Navio(TipoNavio tipo){
		navioAfundado = false;
                this.tipo = tipo;
                quadrados = new ArrayList();
    }

    public int getComprimento() {
        return tipo.comprimento();
    }
    
    
    
    public boolean verificarPosicionamento(){
        return !quadrados.isEmpty();
    
    }
    
    
    public void criarNavio(int x, int y, Direcao direcao, Tabuleiro tab){
        this.posicaoX = x;
        this.posicaoY = y;
        this.quadrados = tab.colocarNavio(posicaoX, posicaoY, direcao, tipo.comprimento()); //Coloca o navio no tabuleiro, e armazena todos os seus quadrados
    }
    
    public boolean verificarAfundado (){
        if (this.navioAfundado){
            return true;
        }
        for (Quadrado i : quadrados){
            if (i.getStatus() == StatusQ.NAVIO){ //Se houver pelo menos um quadrado que compõe o navio que esteja ainda como navio, o navio ainda não foi completamente afundado.
               return false;
            }
        }//Caso contrário, o navio realmente foi afundado.
            navioAfundado = true;
            return true;
        }

    public TipoNavio getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Navio{" + "posicaoX= " + posicaoX + ", posicaoY= " + posicaoY + ", tipo= " + tipo + '}';
    }
    
    
}
    