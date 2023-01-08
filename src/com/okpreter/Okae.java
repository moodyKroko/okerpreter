package com.okpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Okae {

  private static boolean hadError = false;

  public static void main(String[] args) throws IOException {
    if (args.length > 1) {
      System.out.println("Usage: Okae [scripts]");
      System.exit(64);
    } else if (args.length == 1) {
      runFile(args[0]);
    } else {
      rumPrompt();
    }
  }

  private static void rumPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for (; ; ) {
      System.out.println("> ");
      String line = reader.readLine();

      if (line == null) {
        break;
      }

      run(line);
      hadError = false;
    }
  }

  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));

    if (hadError) {
      System.exit(65);
    }
  }

  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    for (Token token : tokens) {
      System.out.println(token);
    }
  }

  // error handling
  static void error(int line, String message) {
    report(line, "", message);
  }

  private static void report(int line, String where, String message) {
    String errReport = "[line %d ] Error %s: %s";
    System.out.printf((errReport) + "%n", line, where, message);
    hadError = true;
  }
}
