package com.example.mutantdetector.models;

import javax.persistence.*;

@Entity
@Table(name = "adn_verifications")
public class AdnVerifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "dna")
    private String dna;
    @Column(name = "is_mutant")
    private boolean is_mutant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDna() {
        return dna;
    }
    public void setDna(String dna) {
        this.dna = dna;
    }

    public boolean getIs_mutant() {
        return is_mutant;
    }

    public void setIs_mutant(boolean is_mutant) {
        this.is_mutant = is_mutant;
    }
}
