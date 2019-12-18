/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arquivos;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;
import jdk.nashorn.internal.ir.BreakNode;
 
/**
 *
 * @author Wellington César
 */
 
public class Arquivos{
    int tamanhoArq;
    String nomeArq;
    private void copiarArquivo(String nomeArquivo, String opcaoMenu)  {
        try {
            //nomeArquivo = "";
            String inFileName1 = nomeArquivo;
            String outFileName = "";
            if ("1".equals(opcaoMenu)) {
                outFileName = "d:\\destino\\"+nomeArq;
            } else if ("2".equals(opcaoMenu)) {
                outFileName = "d:\\servidor\\"+nomeArq;
            }
            FileInputStream in = new FileInputStream(inFileName1);
            FileOutputStream out = new FileOutputStream(outFileName);
            
            byte[] buf = new byte[1024];
            int len;
            int cont = 0;  
            
            while ((len = in.read(buf)) > 0) {
                out.write(buf,0,len);
                cont = len;
            }
     
            
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //private String origem1 = "d:\\servidor";
    public void obterArquivos(String origem1,String opcaoMenu) throws IOException {
    Scanner ler = new Scanner(System.in);
	File file = new File(origem1);
	File afile[] = file.listFiles();
        int i = 0;
        for (int j = afile.length; i < j; i++) {	
            System.out.println("Código: "+ i + " - "+ afile[i].getName());
	}
        if ("1".equals(opcaoMenu)) {
            System.out.println("Escolha o codigo do arquivo para ser baixado");
        } else if ("2".equals(opcaoMenu)){
            System.out.println("Escolha o codigo do arquivo para ser enviado");
        }
        Integer itemLista = ler.nextInt();
        
        File obterArquivos = afile[itemLista];
        nomeArq = afile[itemLista].getName();
        //tamanhoArq = obterArquivos.length(); 
        copiarArquivo(obterArquivos.getPath(),opcaoMenu);
       /* int i = 4;
	for (int j = afile.length; i < j; i++) {
		File obterArquivos = afile[i];
              
		System.out.println(obterArquivos.getName()+ " - " +obterArquivos.length()  +"bytes" );
	}*/

    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {  
        Scanner ler = new Scanner(System.in);
        System.out.println("Escolha uma opção");
        System.out.println("1 para baixar um arquivo");
        System.out.println("2 para enviar um arquivo");
        System.out.println("3 para sair");
        Integer opcaoMenu = ler.nextInt();
        Arquivos arq = new Arquivos();
        
        if (opcaoMenu == 1){
            //opcaoMenuEscolhida = "1";
            arq.obterArquivos("d:\\servidor",opcaoMenu.toString());
        } else if (opcaoMenu == 2) {
            arq.obterArquivos("d:\\destino", opcaoMenu.toString());
        } else if (opcaoMenu == 3){
            //sair
        }
                
    }   
}
