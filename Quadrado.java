/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;

//O objetivo desse objeto é compor o tabuleiro. Seu único atributo é o status, feito do enum StatusQ. Ele informa sobre o status daquela casa específica do tabuleiro. 

import java.io.Serializable;

public class Quadrado implements Serializable {
    private StatusQ status;


    
    public Quadrado() {
        status = StatusQ.VAZIO;
    }
    
    public boolean Alvo(){ //Esse método será chamado quando um jogador escolher este determinado quadrado como alvo de um tiro. Retornará verdadeiro caso o tiro acerte, retornará falso caso erre.
        if (status == StatusQ.VAZIO){ //Se o quadrado escolhido estiver vazio, o jogador errou o tiro. Mudamos o status para errado e avisamos que foi um tiro errado.
            status = StatusQ.ERRADO;
            return false;
        }
        if (status == StatusQ.NAVIO){ //Se o quadrado escolhido estiver com um navio, o jogador acertou o tiro. Mudamos o status para afundado e avisamos que foi um tiro certo.
            status = StatusQ.AFUNDADO;
            return true;
        }
        return false; //Caso não seja nenhum dos dois, sobra apenas um quadrado que já foi atingido. O tiro foi errado então.
    }
    
    
    public void navio(){ //Coloca um navio no quadrado.
        status = StatusQ.NAVIO;
    }
    
    
    public StatusQ getStatus(){ return status; }
}
