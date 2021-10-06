package com.rabelo;

import java.net.Socket;
import java.util.Scanner;

public class ReceptorDeMensagem implements Runnable {
  
  private Socket socket;
  private Servidor servidor;

  public ReceptorDeMensagem(Socket socket, Servidor servidor) {
    this.socket = socket;
    this.servidor = servidor;
  }

  @Override
  public void run() {
    try(Scanner scanner = new Scanner(socket.getInputStream())) {
      while (scanner.hasNextLine()) {
        String mensagem = scanner.nextLine();

        servidor.enviarMensagem(this.socket, mensagem);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
