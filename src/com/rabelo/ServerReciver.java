package com.rabelo;

import java.io.InputStream;
import java.util.Scanner;

public class ServerReciver implements Runnable {
  private InputStream in;

  public ServerReciver(InputStream in) {
    this.in = in;
  }
  
  public void run() {
    try(Scanner scanner = new Scanner(this.in)) {
      while(scanner.hasNextLine()) {
        System.out.println(scanner.nextLine());
      }
    }
  }
}
