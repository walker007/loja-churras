package com.academiadesenvolvedor.models;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Produto {
    private String nome;
    private UUID id;
    private int quantidade;
    private double preco;


    public Produto(String nome, int quantidade, double preco){
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }
}
