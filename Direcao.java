/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;


//Esse enum é responsável por armazenar as possíveis direções de um movimento. Ele é utilizado na hora de posicionar um navio e na hora do bot realizar um tiro aleatório.
public enum Direcao {
    NORTE(0, -1), //Direção norte: Também pode ser considerada "Para cima". Não há variação na coordenada x, e há variação negativa na coordenada y.
    SUL(0, 1),  //Direção sul: Também pode ser considerada "Para baixo". Não há variação na coordenada x, e há variação positiva na coordenada y.
    LESTE(1, 0), //Direção leste: Também pode ser considerada "Para direita". Há variação positiva na coordenada x, e não há variação na coordenada y.
    OESTE(-1, 0), //Direção oeste: Também pode ser considerada "Para esquerda". Há variação negativa na coordenada x, e não há variação na coordenada y.
    VAZIO(0, 0); //Nenhuma direção. Utilizada apenas para o caso de nenhuma direção ter sido escolhida, para que nenhuma variável direção tenha valor null.
    
    private final int x;
    private final int y;
    Direcao(int x, int y) { 
        this.x = x; 
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    
}
