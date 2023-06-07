package com.academiadesenvolvedor.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Endereco {
    private String rua;
    private String bairro;
    private String cep;
    private String cidade;
    private String uf;

    public String toString(){
        return "Rua: " + this.getRua() + "\n"+
                "Bairro: " + this.getBairro() + "\n"+
                "Cidade: " + this.getCidade() + "\n"+
                "UF: " + this.getUf() + "\n"+
                "CEP: " + this.getCep() ;

    }
}
