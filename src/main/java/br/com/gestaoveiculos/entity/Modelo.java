
package br.com.gestaoveiculos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "modelos")
public class Modelo extends EntidadeBase {

    @Column(nullable = false, length = 100)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    public Modelo() {
    }

    public Modelo(String nome, Marca marca) {
        this.nome = nome;
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
