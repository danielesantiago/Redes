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

            System.out.print("Informe o nome do arquivo para enviar: ");
            String fileName =   sc.nextLine();
            fileName = caminho + fileName;
            File file = new File(fileName);

            if (!file.exists()) {
                System.out.println("Arquivo não encontrado");
                return;
            }
           
            OutputStream os = socket.getOutputStream();
            os.write(("enviar " + fileName ).getBytes()); // envia a operação e o nome do arquivo
            os.flush();

            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(file);

            int bytes;
            byte[] buffer = new byte[1024];

            while ((bytes = fis.read(buffer)) > 0) {
                bos.write(buffer, 0, bytes); // envia os bytes do arquivo
            }
            bos.flush();


            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // recebe a resposta do servidor
            String response = in.readLine(); 
            System.out.println(response);

            fis.close();
            os.close();
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
        String fileName = "receber " + nome;
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
    

