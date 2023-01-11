import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Cliente {

    public static String caminho = "C:\\Users\\danie\\OneDrive\\Documentos\\Redes\\";
    public static void main(String[] args) throws IOException {  

        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("\n[1] Enviar arquivo");
            System.out.println("[2] Receber arquivo");
            System.out.println("[3] Sair");

            System.out.print("Selecione uma opção: ");
            int option = sc.nextInt();

            if(option == 1){
                sendFile();
            } else if(option == 2){
                receiveFile();
            } else if(option == 3){
                System.out.println("Programa encerrado");
                break;
            }
        }
    }

    public static void sendFile() throws IOException {
    Scanner sc = new Scanner(System.in);

    try (Socket socket = new Socket("localhost", 1238)) {
        // Solicita o nome do arquivo a ser enviado
        System.out.print("Informe o nome do arquivo para enviar: ");
        String fileName =   sc.nextLine();
        fileName = caminho + fileName;


        // Verifica se o arquivo existe
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Arquivo não encontrado");
            return;
        }
        
        fileName = "send " + fileName;

        // Envia o arquivo
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        OutputStream os = socket.getOutputStream();
        int bytes;
        while ((bytes = bis.read(buffer)) > 0) {
            os.write((fileName).getBytes());
        }
        os.flush();

        // Recebe a resposta do servidor
        InputStream is = socket.getInputStream();
        String response = new BufferedReader(new InputStreamReader(is)).readLine();
        

        // Exibe a resposta do servidor
        System.out.println(response);

        // Fecha os streams e o socket
        fis.close();
        bis.close();
        os.close();
        is.close();
        socket.close();
    } catch (ConnectException e) {
        System.out.println("Não foi possível conectar ao servidor.");
    }
}


    public static void receiveFile() throws IOException {
    Scanner sc = new Scanner(System.in);
    
    // Conecta ao servidor e solicita o arquivo
    try (Socket socket = new Socket("localhost", 1238)) {
        // Envia o nome do arquivo para o servidor
        System.out.print("Informe o nome do arquivo para receber: ");
        String nome = sc.nextLine();
        String fileName = "receive " + nome;
        OutputStream os = socket.getOutputStream();
        os.write(fileName.getBytes());

        // Recebe a resposta do servidor
        InputStream is = socket.getInputStream();
        String response = new BufferedReader(new InputStreamReader(is)).readLine();

        // Verifica se o arquivo existe no servidor
        if (response.equals("Arquivo não encontrado no servidor")) {
            System.out.println("Arquivo não encontrado no servidor");
        } else {
            // Recebe o arquivo
            FileWriter file = new FileWriter(new File(caminho + "EnviadoServidor\\" + nome));
            file.write(response);
            file.close();
            System.out.println("Arquivo " + nome + " salvo!");
            // Fecha streams e socket
            is.close();
            os.close();
            socket.close();
        }
    }// Trata erros de conexão
    catch (ConnectException e) {
        System.out.println("Não foi possível conectar ao servidor.");
    }
  }
}
    

