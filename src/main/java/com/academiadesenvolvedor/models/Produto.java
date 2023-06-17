package com.academiadesenvolvedor.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Entity
@Table(name = "produto")
@Setter
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue
    private int id;
    private int quantidade;
    private double preco;
    private String nome;
    @Column(name = "created_at", nullable = false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    @Column(name = "updated_at", nullable = false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;
    @PrePersist
    protected void onCreate(){
        this.created_at = new Date();
        this.updated_at = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updated_at = new Date();
    }
    public Produto(String nome, int quantidade, double preco){
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }
}
