package com.academiadesenvolvedor.models;

import com.academiadesenvolvedor.utils.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Cliente {
    private String nome;
    private Telefone telefone;
    private Endereco endereco;
    private String cpf;
    private boolean ativo;
    private List<Pedido> pedidos;
    private double credito;

    public String toString(){
        return "Nome: " + this.getNome() + "\n"+
                "CPF: " + this.getCpf() + "\n" +
                "Telefone: " + this.getTelefone() + "\n"+
                "Situação: " + this.isAtivo() + "\n" +
                "Cédito dispoível: " + this.getCredito();
    }

}
