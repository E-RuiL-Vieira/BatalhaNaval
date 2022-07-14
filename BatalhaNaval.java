/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/

package com.mycompany.batalhanaval;


public class BatalhaNaval {

    public static void main(String[] args) {
        try{
            //Inicio do jogo
            Jogo jogo = new Jogo();
            if (jogo.isMultiplayer()){//Caso Multiplayer
                jogo.iniciarmultiplayer();
            }
            else{// Caso Singleplayer
                jogo.iniciarsingleplayer();
            }
            do {// Inicia as jogadas dos jogadores até que a partida acabe
                jogo.jogadas();
            } while (jogo.verificarPartida());
            
            //Verifica qual dos dois ganhou
            jogo.vitoria();
        } catch (Exception e){
        }

    }
}
