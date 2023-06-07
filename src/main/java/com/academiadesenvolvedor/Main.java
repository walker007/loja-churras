package com.academiadesenvolvedor;

import com.academiadesenvolvedor.application.App;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App app = new App(scanner);

        app.executar();

        scanner.close();
    }
}