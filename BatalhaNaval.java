/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/

package com.mycompany.batalhanaval;


public class BatalhaNaval {

    public static void main(String[] args) {
        try{
            Jogo jogo = new Jogo();
            if (jogo.isMultiplayer()){
                jogo.iniciarmultiplayer();
            }
            else{
                jogo.iniciarsingleplayer();
            }
            while(jogo.verificarPartida()){
                jogo.jogadas();
            }
            jogo.vitoria();
        } catch (Exception e){
        }

    }
}
