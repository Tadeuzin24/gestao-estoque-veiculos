
package br.com.gestaoveiculos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "marcas")
public class Marca extends EntidadeBase {

    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    public Marca() {
    }

    public Marca(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
