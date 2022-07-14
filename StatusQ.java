/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/

package com.mycompany.batalhanaval;

//Responsável por determinar como está um objeto da classe Quadrado

import java.io.Serializable;

public enum StatusQ implements Serializable {
    VAZIO, //Status padrão. Nada aconteceu com ele.
    NAVIO, //Um navio foi colocado.
    AFUNDADO,  //Anteriormente um navio, esse quadrado foi alvo de um tiro.
    ERRADO //Esse quadrado também foi alvo de um tiro, porém ele estava vazio. 
}
