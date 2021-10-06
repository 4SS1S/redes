package com.rabelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
  public Cliente() {
    //
  }

  public void boot() {
    try (Socket socket = new Socket("localhost", 5000)) {
      Scanner scanner = new Scanner(System.in);
      PrintStream saida = new PrintStream(socket.getOutputStream());

      ServerReciver serverReciver = new ServerReciver(socket.getInputStream());

      new Thread(serverReciver).start();
      
      // while (scanner.hasNextLine()) {
      //   saida.println(scanner.nextLine());
      // }

      String msg = "";

      while (!msg.equals("Sair")) {
        msg = scanner.nextLine();
        saida.println(msg);
      }
    } catch (IOException e) {
      System.out.println("Servidor foi comprar cigarro!");
    } catch (NullPointerException e) {
      System.out.println("Null" + e.getMessage());
    }
  }
}