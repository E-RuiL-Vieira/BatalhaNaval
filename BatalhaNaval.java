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
            do {
                jogo.jogadas();
                System.out.println("Loop min");
            } while (jogo.verificarPartida());
            jogo.vitoria();
        } catch (Exception e){
        }

    }
}
