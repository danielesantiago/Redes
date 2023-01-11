import os
import socket
import threading

def handle_client(client_socket):

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

        
        if operation == "send":
            # Recebe o arquivo e o salva em disco
            with open(file_name, 'r') as arquivo_existente, open(os.path.join(path,'RecebidoServidor\\' + nome_arquivo), 'w') as file_received:
                for linha in arquivo_existente.readlines():
                    file_received.write(linha)

            arquivo_existente.close()
            file_received.close()

            # Envia uma confirmação para o cliente
            client_socket.send("Arquivo recebido com sucesso\n".encode())
        elif operation == "receive":
            try:
                # Lê o arquivo e o envia de volta para o cliente
                with open(os.path.join(path, 'RecebidoServidor\\' + nome_arquivo), "rb") as f:
                    data = f.read()
                    client_socket.send(data +"\n".encode())
                f.close()

                client_socket.send("Arquivo enviado com sucesso\n".encode())
                
            except IOError:
                # Envia uma mensagem de erro para o cliente caso o arquivo não exista no servidor
                client_socket.send("Arquivo não encontrado no servidor\n".encode())

    client_socket.close()


def main():
    # Inicia o socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(("0.0.0.0", 1238))
    server_socket.listen(5)

    while True:
        client_socket, client_address = server_socket.accept()
        print(f"Conexão de {client_address}")

        # Cria uma nova thread para lidar com a conexão do cliente
        client_handler = threading.Thread(
            target=handle_client, args=(client_socket,))
        client_handler.start()

if __name__ == "__main__":
    main()
