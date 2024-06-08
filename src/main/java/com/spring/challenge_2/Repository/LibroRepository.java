package com.spring.challenge_2.Repository;

import com.spring.challenge_2.Model.Autor;
import com.spring.challenge_2.Model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    List<Libro> findAll();

    @Query("SELECT a FROM Autor a")
    List<Autor> autores();

    @Query("SELECT a FROM Autor a WHERE :fechaElegida BETWEEN a.fechaDeNacimiento AND a.fechaDeFallecimiento")
    List<Autor> autores_vivos(Integer fechaElegida);

    List<Libro> findByIdiomasContainsIgnoreCase(String idiomaElegido);
}
