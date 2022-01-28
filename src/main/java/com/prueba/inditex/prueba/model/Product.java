package com.prueba.inditex.prueba.model;

public class Product {

    private Integer id;
    private Integer sequence;

    public Product(Integer id, Integer sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
