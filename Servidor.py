'''
Implementação do servidor
Nomes: Ana Beatriz Juvencio - 801817, Daniele Pereira Santiago - 792175

'''

import os
import socket
import threading


def handle_client(client_socket):
    # Diretório onde os arquivos serão salvos
    path = "C:\\Users\\danie\\OneDrive\\Documentos\\Redes\\"

    while True:
        # Recebe a operação desejada (send/receive) e o nome do arquivo
        request = client_socket.recv(1024).decode()
        if not request:
            break

        operation, file_name = request.split(" ")
        file_name = os.path.join(path, file_name)
        nome_arquivo = os.path.basename(file_name)
        print("Recebeu requisição para %s arquivo %s" % (operation, nome_arquivo))
        print(file_name)

        if operation == "enviar":
            # Caminho para salvar o arquivo
            file_path = os.path.join(path, 'RecebidoServidor\\' + nome_arquivo)
            # Recebe o conteúdo do arquivo em bytes
            conteudo = client_socket.recv(1024).decode()
            # Salva em disco
            with open(file_path, "wb") as f:
                f.write(conteudo.encode())
            f.close()
            print("Arquivo recebido com sucesso")
            client_socket.send("Arquivo recebido com sucesso\n".encode())



        elif operation == "receber":
            try:
                # Lê o arquivo do disco e envia os bytes oa cliente
                with open(os.path.join(path, 'RecebidoServidor\\' + nome_arquivo), "rb") as f:
                    data = f.read()
                    client_socket.send(data + "\n".encode())
                f.close()

                client_socket.send("Arquivo enviado com sucesso\n".encode())

            except IOError:
                # Envia uma mensagem de erro para o cliente caso o arquivo não exista no servidor
                client_socket.send("Arquivo não encontrado no servidor\n".encode())

    client_socket.close()


def main():
    # Inicia o socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # Liga o socket ao endereço IP e porta especificados
    server_socket.bind(("0.0.0.0", 1238))
    # Escuta por conexões entrantes
    server_socket.listen(0) # Permite quantas conexões o sistema operacional aguentar

    while True:
        # Aceita a conexão de um cliente
        client_socket, client_address = server_socket.accept()
        print(f"Conexão de {client_address}")

        # Cria uma nova thread para lidar com a conexão do cliente
        client_handler = threading.Thread(
            target=handle_client, args=(client_socket,))
        client_handler.start()

if __name__ == "__main__":
    main()
