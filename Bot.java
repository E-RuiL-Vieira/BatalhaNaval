/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;
import java.util.Random;
import java.util.*;


//Essa classe é responsável pela IA do bot. Como o posicionamento aleatório dos navios já está contido na sua classe mãe, seu único método único é determinar como o tiro será feito.
public final class Bot extends Jogador {
    private int[] ultimotiro;
    private boolean acertou;
    private Direcao direcao;
    private int c;
    private LinkedList<Direcao> possiveis = new LinkedList<>();
        
    public Bot(Tabuleiro tab){
        super(tab);
        acertou = false;
        ultimotiro = new int[2];
        nome = "Computador";
        direcao = Direcao.VAZIO;
        c = 0;
        possiveis.addAll(Arrays.asList(Arrays.copyOfRange(Direcao.values(), 0, 4)));
        posicionarNavios();
    }
    
    public boolean atirar(Tabuleiro oTab){
        Direcao novaDirecao;
        int x;
        int y;
        if(c >= 5 || possiveis.isEmpty()){ 
//Caso já se tenha dado 5 tiros numa mesma direção, ou já se tenha tentado todas as direções, resetamos todos os atributos e tentamos dar um tiro aleatório
            c = 0;
            acertou = false;
            direcao = Direcao.VAZIO;
            possiveis.addAll(Arrays.asList(Arrays.copyOfRange(Direcao.values(), 0, 4)));
            return atirar(oTab);
        }
        if (!acertou){ 
//Caso o tiro anterior não tenha sido um acerto, dá-se um tiro aleatório
            do{            
                x = rand.nextInt(oTab.getX());
                y = rand.nextInt(oTab.getY());}
            while(!oTab.podeAtirar(x, y)); //Verifica se o valor adquirido é uma posição válida
            if(oTab.getCasa(x, y).Alvo()){ //Se acertar o tiro, armazenamos as coordenadas do tiro dado
                acertou = true;
                ultimotiro[0] = x;
                ultimotiro[1] = y;
                c++;
                return true;
            }
            return false;
        }
        
        if(direcao == Direcao.VAZIO){ 
//Para chegar aqui, teríamos que ter acabado de dar um tiro certo, porém ainda não encontramos uma direção que contenha um navio
            do{
                novaDirecao = possiveis.get(rand.nextInt(possiveis.size()));
                x = ultimotiro[0]+novaDirecao.getX();
                y = ultimotiro[1]+novaDirecao.getY();
            }while((x < 0 || x >= tab.getX() || y < 0 || y >= tab.getY()) || tab.podeAtirar(x, y)); //Verifica se as coordenadas adquiridas estão além dos limites do tabuleiro ou se já foram alvos anteriormente

            if(oTab.getCasa(x, y).Alvo()){
                //Caso o tiro tenha dado certo, isso significa que há um navio potencialmente nessa direção. Aumentamos o comprimento do navio e armazenamos a direção certa adquirida. 
                direcao = novaDirecao;
                c++;
                ultimotiro[0] = x;
                ultimotiro[1] = y;
                return true;
            }
            else{
                possiveis.remove(novaDirecao); //Caso tenha errado, removemos a direção errada da lista de direções que pode ser escolhida.
                return false;
            }
        }
        //Para chegar até aqui,  já determinamos uma direço certeira. Apenas continuamos.
        x = ultimotiro[0] + direcao.getX();
        y = ultimotiro[1] + direcao.getY();
        //Porém, existe a chance de acabar ultrapassando os limites do vetor após continuar. primeiro, temos que verificar isso.
        if(x < 0 || x >= tab.getX() || y < 0 || y >= tab.getY()){ //Se já tiver chegado no limite, vamos apenas resetar tudo e tentar novamente.
            c = 0;
            acertou = false;
            direcao = Direcao.VAZIO;
            possiveis.addAll(Arrays.asList(Arrays.copyOfRange(Direcao.values(), 0, 4)));
            return atirar(oTab);
        }
        if (oTab.getCasa(x, y).Alvo()){
            c++;
            ultimotiro[0] = x;
            ultimotiro[1] = y;
            return true;
        }
        c = 0;
        direcao = Direcao.VAZIO;
        acertou = false;
        possiveis.addAll(Arrays.asList(Arrays.copyOfRange(Direcao.values(), 0, 4)));
        return false;           
        //Assim como antes, se acertamos o tiro, armazenamos as coordenadas novas. Caso erramos, resetamos todos os atributos.
    }  
    

}
