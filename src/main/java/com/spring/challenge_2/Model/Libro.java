package com.spring.challenge_2.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    @OneToMany(mappedBy = "libros", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autor;

    private String idiomas;
    private Double nDeDescargas;



    public Libro(Optional<DatosLibro> datosBusqueda){
        this.titulo = datosBusqueda.get().titulo();
        this.idiomas = String.valueOf(datosBusqueda.get().idiomas());
        this.nDeDescargas = datosBusqueda.get().nDeDescargas();
    }

    public Libro(){}

    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idiomas='" + idiomas + '\'' +
                ", nDeDescargas=" + nDeDescargas +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getnDeDescargas() {
        return nDeDescargas;
    }

    public void setnDeDescargas(Double nDeDescargas) {
        this.nDeDescargas = nDeDescargas;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public void setAutor(List<Autor> autor) {
        autor.forEach(e -> e.setLibros(this));
        this.autor = autor;
    }

}
