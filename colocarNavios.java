/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Color;



//Essa classe é responsável pela tela na qual o usuário posiciona os seus navios no tabuleiro. 
public class colocarNavios extends Oceano{
    private Direcao direcao;
    private ActionListener ButtonListener;
    private JButton Norte = new JButton("Norte");
    private JButton Sul = new JButton("Sul");
    private JButton Oeste = new JButton("Oeste");
    private JButton Leste = new JButton("Leste");
    private JButton Enter = new JButton("Criar");
    private int xCoord;
    private int yCoord;
    private Navio navio;
    
    public colocarNavios(Tabuleiro tab, Navio n){
        super(tab);
        super.ativarbotoes(true);
        this.navio = n;
        this.direcao = Direcao.VAZIO; //Define a direção padrão como vazia, apenas para não dar erro de tentar acessar um null
        
        
        ButtonListener = (ActionEvent e) -> {
            atualizar();
            //As coordenadas do botão clicado vão para os atributos de coordenada
            xCoord = Character.getNumericValue(e.getActionCommand().charAt(0));
            yCoord = Character.getNumericValue(e.getActionCommand().charAt(1)); 
            casas[xCoord][yCoord].setBackground(Color.WHITE); //Muda o botão clicado para branco
            simularColocar();
        };
        
        //Acrescenta o button listener para todos os botões
        for (int i = 0; i < tab.getX(); i++){
            for (int j = 0; j < tab.getY(); j++){
                casas[i][j].addActionListener(ButtonListener); 
        } }
        
        
        //Cria e coloca label que informa ao usuário sobre qual tipo de navio está sendo inserido
        constraints.gridx = 0;
        constraints.gridy = tab.getY() + 1;
        constraints.gridwidth = 10;
        constraints.ipady = 20;
        JLabel navioatual = new JLabel("Colocando agora: " + n.getTipo().name() + ", de tamanho: " + n.getComprimento());
        oceano.add(navioatual, constraints);
        
        //Botões para determinação da criação
        constraints.gridx = 0; 
        constraints.gridy = tab.getY() + 2;
        Norte.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                atualizar();
                casas[xCoord][yCoord].setBackground(Color.WHITE);
                direcao = Direcao.NORTE;
                simularColocar();
            }
        });
        oceano.add(Norte, constraints);
        constraints.gridx = 3; 
        Sul.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                atualizar();
                casas[xCoord][yCoord].setBackground(Color.WHITE);
                direcao = Direcao.SUL;
                simularColocar();
            }
        });
        oceano.add(Sul, constraints);
        constraints.gridx = 7; 
        Leste.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                atualizar();
                casas[xCoord][yCoord].setBackground(Color.WHITE);
                direcao = Direcao.LESTE;
                simularColocar();
            }
        });
        oceano.add(Leste, constraints);
        constraints.gridx = 5; 
        Oeste.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                atualizar();
                casas[xCoord][yCoord].setBackground(Color.WHITE);
                direcao = Direcao.OESTE;
                simularColocar();
            }
        });
        oceano.add(Oeste, constraints);
        constraints.gridx = 9; 
        Enter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                atualizar();
                if(direcao != Direcao.VAZIO){
                    colocarNavio();
                }
            }
        });
        oceano.add(Enter, constraints);
        /*
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(Norte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Sul)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Leste)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Oeste)
                .addGap(52, 52, 52)
                .addComponent(Enter)
                .addGap(112, 112, 112))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(569, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Norte)
                    .addComponent(Sul)
                    .addComponent(Leste)
                    .addComponent(Oeste)
                    .addComponent(Enter))
                .addGap(8, 8, 8))
        );*/
    }
    
    private void simularColocar(){
        if (tab.podeColocar(xCoord, yCoord, direcao, navio.getComprimento())){
            switch(direcao){
            case NORTE, SUL->{ //Casos verticais
                int y2 = yCoord + (direcao.getY() *navio.getComprimento()); //Coordenada da poupa do navio
                    for(int j = yCoord; j != y2; j=j+direcao.getY()){ //Itera por todos os quadrados do navio que está sendo criado
                        casas[xCoord][j].setBackground(Color.WHITE); //Muda os botões correspondentes para branco
                    }
                }
            case OESTE, LESTE->{ //Casos horizontais
                int x2 = xCoord + (direcao.getX() * navio.getComprimento()); //Coordenada da poupa do navio
                for(int i = xCoord; i != x2; i=i+direcao.getX()){ //Itera por todos os quadrados do navio que está sendo criado
                    casas[i][yCoord].setBackground(Color.WHITE); //Muda os botões correspondentes para branco
                }
            }
        }
        }
    }
    
    private void colocarNavio(){
        if (tab.podeColocar(xCoord, yCoord, direcao, navio.getComprimento())){
            navio.criarNavio(xCoord, yCoord, direcao, tab);
            emuso=false;
            synchronized(Jogo.LOCK){
                Jogo.LOCK.notifyAll();
            }
        }
        else{   
            JOptionPane.showMessageDialog(null, "Você não pode colocar esse navio", "Alerta", JOptionPane.ERROR_MESSAGE);
        }
        atualizar();
    }
}
