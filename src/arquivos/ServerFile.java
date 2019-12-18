
package arquivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class ServerFile extends Thread {
    public static Vector CONECTADOS;
    private Socket conexao;
    public String nomeCliente;
    int tamanhoArq;
    String nomeArq;
    
    public ServerFile(Socket socket) {
        this.conexao = socket;
    }
    
    public static void main(String args[]) {
        CONECTADOS = new Vector();
        try {
            ServerSocket server = new ServerSocket(3030);
            System.out.println("ServidorSocket rodando na porta 3030");
            while (true) {
                Socket conexao = server.accept();
                Thread t = new ServerFile(conexao);
                t.start();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
    public void run()
    {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            PrintStream saida = new PrintStream(this.conexao.getOutputStream());
            this.nomeCliente = entrada.readLine();
            
            System.out.println(this.nomeCliente + " : Conectado ao Servidor!");
            CONECTADOS.add(saida);
            String msg = entrada.readLine();
            while (msg != null && !(msg.trim().equals("sair")))
            {    
                if (msg.trim().equals("arq")){
                    Scanner ler = new Scanner(System.in);
                    saida.println("Escolha uma opção");
                    saida.println("1 para baixar um arquivo");
                    saida.println("2 para enviar um arquivo");
                    saida.println("3 para baixar um arquivo");
                    String opcao = entrada.readLine();
                    if (opcao.equals("1")){
                        //opcaoMenuEscolhida = "1";
                        obterArquivos("c:\\servidor",opcao.toString());
                    } else if (opcao.equals("2")) {
                        obterArquivos("c:\\destino", opcao.toString());
                    } else if (opcao.equals("3")){
                        msg = "sair";
                    }
                
                }
                System.out.println(this.nomeCliente +": " + msg);
                responderTodos(saida, "> ", msg);
                msg = entrada.readLine();

            }
            
            this.conexao.close();
        } catch (IOException e) {
            System.out.println("Falha na Conexao... .. ."+" IOException: " + e);
            }
    }
    
    private void responderTodos(PrintStream saida, String acao, String msg) throws IOException {
        Enumeration e = CONECTADOS.elements();
        while (e.hasMoreElements()) {
            PrintStream chat = (PrintStream) e.nextElement();
           // if (chat != saida) {
                chat.println(nomeCliente + acao + msg);
            //}
        }
    }
    
    private void copiarArquivo(String nomeArquivo, String opcaoMenu)  {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
            PrintStream saida = new PrintStream(this.conexao.getOutputStream());
            //nomeArquivo = "";
            String inFileName1 = nomeArquivo;
            String outFileName = "";
            if ("1".equals(opcaoMenu)) {
                outFileName = "c:\\destino\\"+nomeArq;
            } else if ("2".equals(opcaoMenu)) {
                outFileName = "c:\\servidor\\"+nomeArq;
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
            saida.println("Arquivo Copiado com Sucesso");
            
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void obterArquivos(String origem1,String opcaoMenu) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(this.conexao.getInputStream()));
        PrintStream saida = new PrintStream(this.conexao.getOutputStream());
        Scanner ler = new Scanner(System.in);
	File file = new File(origem1);
	File afile[] = file.listFiles();
        int i = 0;
        for (int j = afile.length; i < j; i++) {	
            saida.println("Código: "+ i + " - "+ afile[i].getName());
	}
        if ("1".equals(opcaoMenu)) {
            saida.println("Escolha o codigo do arquivo para ser baixado");
        } else if ("2".equals(opcaoMenu)){
            saida.println("Escolha o codigo do arquivo para ser enviado");
        }
        //Integer itemLista = ler.nextInt();
        String itemLista = entrada.readLine();
        
        
        File obterArquivos = afile[Integer.parseInt(itemLista)];
        nomeArq = afile[Integer.parseInt(itemLista)].getName();
        //tamanhoArq = obterArquivos.length(); 
        copiarArquivo(obterArquivos.getPath(),opcaoMenu);
       /* int i = 4;
	for (int j = afile.length; i < j; i++) {
		File obterArquivos = afile[i];
              
		System.out.println(obterArquivos.getName()+ " - " +obterArquivos.length()  +"bytes" );
	}*/

    }
    
}