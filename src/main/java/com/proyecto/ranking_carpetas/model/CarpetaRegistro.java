package com.proyecto.ranking_carpetas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "carpetas_ranking")
public class CarpetaRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int totalArchivos;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    // Constructor vacío requerido por JPA
    public CarpetaRegistro() {
    }

    // Constructor de conveniencia
    public CarpetaRegistro(String nombre, int totalArchivos, LocalDateTime fechaRegistro) {
        this.nombre = nombre;
        this.totalArchivos = totalArchivos;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotalArchivos() {
        return totalArchivos;
    }

    public void setTotalArchivos(int totalArchivos) {
        this.totalArchivos = totalArchivos;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
