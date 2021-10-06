package com.rabelo;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
  
  private static final int PORTA = 5000;
  
  private List<Socket> clientes;
  private ServerSocket serverSocket;
  
  private final String[] MODOS = {"Pedra", "Papel", "Tesoura"};
  private String[] jogadas;


  public Servidor(){
    this.clientes = new ArrayList<>();
    this.jogadas = new String[2];
  }

  public void boot() throws IOException  {
    serverSocket = new ServerSocket(PORTA);

    try {
      System.out.println("Servidor iniciado na porta " + PORTA);

      while (true) {
        Socket cliente = serverSocket.accept();
        System.out.println("Cliente conectado: " + cliente.getInetAddress());
        PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
        
        this.clientes.add(cliente);

        out.println("Bem vindo ao servidor!");

        new Thread(new ReceptorDeMensagem(cliente, this)).start();
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Erro ao iniciar servidor: " + e);
    } finally {
      serverSocket.close();
    }
  }

  public void enviarMensagem(Socket socket, String mensagem) {
    if (mensagem.equalsIgnoreCase("Sair"))  {
      this.clientes.remove(socket);

      return;
    }
    
    if (mensagem.equalsIgnoreCase("Listar")) {
      String lista = "";
      for (Socket cliente : this.clientes) {
        lista += cliente.getInetAddress() + "\n";
      }
      mensagem = lista;

      return;
    }

    if (mensagem.equalsIgnoreCase("Limpar")) {
      this.clientes.clear();
      mensagem = "Todos os clientes foram desconectados";

      return;
    }

    if (mensagem.equalsIgnoreCase("Pedra")) {
      addJogada("Pedra");

      mensagem = "O outro jogador escolheu Pedra";
    }

    if (mensagem.equalsIgnoreCase("Papel")) {
      addJogada("Papel");

      mensagem = "O outro jogador escolheu Papel";
    }

    if (mensagem.equalsIgnoreCase("Tesoura")) {
      addJogada("Tesoura");

      mensagem = "O outro jogador escolheu Tesoura";
    }

    for (int i = 0; i <= jogadas.length - 1; i++) {
      System.out.println(jogadas[i]);
    }

    for (Socket socketCliente : this.clientes) {
      if (socketCliente != socket) {
        try {
          PrintWriter out = new PrintWriter(socketCliente.getOutputStream(), true);
          out.println(mensagem);
        } catch (IOException e) {
          System.err.println("Erro ao enviar mensagem: " + e);
        }
      }
    }
  }

  private void addJogada(String jogada) {
    this.jogadas[this.jogadas.length - 1] = jogada;
  }
}