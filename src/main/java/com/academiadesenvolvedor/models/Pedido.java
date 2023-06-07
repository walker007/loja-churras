package com.academiadesenvolvedor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Pedido {
    private UUID id;
    private List<Produto> produto;
    private int quantidade;
    private double precoTotal;
}
