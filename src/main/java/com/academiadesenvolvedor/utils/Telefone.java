package com.academiadesenvolvedor.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Telefone {
    private String codigoPais;
    private String ddd;
    private String numero;

    public String toString(){
        return "+"+this.getCodigoPais() +" ("+this.getDdd() + ") " + this.getNumero();
    }
}
