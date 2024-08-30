package Treino_POO;

import java.util.ArrayList;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ArrayList <Computador> listaComputador = new ArrayList();
        
        int opc = 0;
        while(opc != 3)
        {
            System.out.print("\n--- | MENU | ---");
            System.out.print("\n-> 1: Adicionar computador");
            System.out.print("\n-> 2: Mostrar computadores");
            System.out.print("\n-> 3: Sair\n->");
            
            opc = entrada.nextInt();
            
            switch (opc)
            {
                case 1:
                    Computador novoComputador = new Computador();
                    
                    System.out.print("\n-> Nome:");
                    novoComputador.setNome(entrada.next());
                    
                    System.out.print("\n-> Processador:");
                    novoComputador.setProcessador(entrada.next());
                    
                    System.out.print("\n-> Memoria:");
                    novoComputador.setMemoria(entrada.nextFloat());
                    
                    System.out.print("\n-> Placa de video:");
                    novoComputador.setPlacadeVideo(entrada.next());
                    
                    System.out.print("\n-> Sistema Operacional:");
                    novoComputador.setSO(entrada.next());
                       
                    listaComputador.add(novoComputador);
                break;
                
                case 2:
                    
                    for(int i = 0; i < listaComputador.size(); i++)
                    {
                        Computador mostraComp = listaComputador.get(i);
                        System.out.print("\n--- |Computador "+(i+1)+"| ---\n"+
                                mostraComp.toString()+"\n");
                    }
                    
                break;
                     
            }
        }
        
    }
}