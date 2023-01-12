# Redes
Implementação de um Socket para a disciplina de Redes

Integrantes do grupo:
* Ana Beatriz
* Daniele Santiago

## Objetivos e Requisitos
Desenvolver um servidor usando a tecnologia Socket que seja capaz de:
* Receber um arquivo de dados e armazená-lo em disco;
* Retornar o arquivo gravado anteriormente para um cliente requisitante.

## Especificações e Implementações
* O servidor deverá ser concorrente e usar TCP;
* O servidor deverá ser capaz de receber vários arquivos ao mesmo tempo, ou seja, deverá ser multithread.
* O cliente deverá invocar o servidor para enviar e receber o arquivo.
* Para testar o multithreading do servidor, execute várias instâncias do cliente ao mesmo tempo ou use threads também do lado do cliente.
* O cliente deverá ser desenvolvido na linguagem A e o servidor na linguagem B, sendo A ≠ B.

## Execução e Requisitos
Para a realização do projeto, foi escolhido a linguagem **Python** para o servidor e **Java** para o cliente, logo, é necessário ter o **JDK 8** e **Python 3** instalados, bem como uma IDE capaz de compilar e interpretar os códigos.
A variável *path* em ```Cliente.java``` e ```Servidor.py``` deverá ser alterada para o diretório onde contém os arquivos que serão enviados ao servidor.
A porta deve ser alterada para alguma porta disponível na máquina em que o código será executado. O host em ```Cliente.java``` também deverá ser editado para o hostname do servidor.
A máquina onde será executado o ```Servidor.py``` deverá conter a pasta *RecebidoServidor* no path indicado em código. De modo semelhante, a máquina em que será executado o ```Cliente.java```deverá conter a pasta *EnviadoServidor*.

A ordem a ser seguida para a execução é:
* Executar o Servidor.py
* Executar o Cliente.java

Haverá um menu em ```Cliente.java```que será responsável por:
* [1] - Enviar um arquivo ao servidor
* [2] - Receber um arquivo do servidor
* [3] - Sair do programa
