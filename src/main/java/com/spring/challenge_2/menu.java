package com.spring.challenge_2;

import com.spring.challenge_2.Model.*;
import com.spring.challenge_2.Repository.LibroRepository;
import com.spring.challenge_2.Servicio.combierteDatos;
import com.spring.challenge_2.Servicio.obtenerDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;


public class menu{

    private Scanner teclado = new Scanner(System.in);
    private String URL_BASE = "http://gutendex.com/books/";
    private obtenerDatos consumo = new obtenerDatos();
    private combierteDatos conversor = new combierteDatos();

    private LibroRepository repo;
    public menu(LibroRepository repository) {
        this.repo = repository;
    }

    public void mostrar(){
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo (los guarda en la base de datos)
                    2 - Listado de libros registrados
                    3 - Listado de autores registrado
                    4 - Listado de autores vivos
                    5 - Listado de libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
        private void buscarLibro() {
            System.out.println("que queres buscar: ");
            String buscar = teclado.next();

            var json = consumo.datos(URL_BASE+"?search="+buscar.replace(" ", "+"));
            Datos datos_busqueda = conversor.datos(json, Datos.class);

            Optional<DatosLibro> libroBuscado = datos_busqueda.libros().stream()
				.filter(l -> l.titulo().toUpperCase().contains(buscar.toUpperCase()))
				.findFirst();

		    if(libroBuscado.isPresent()){
			    System.out.println("libro encontrado");
		    	System.out.println(libroBuscado.get());

                Libro libro = new Libro(libroBuscado);
                repo.save(libro);

                List<Autor> datosAutor = libroBuscado.get().autor().stream()
                        .map(a -> new Autor(Optional.ofNullable(a)))
                        .collect(Collectors.toList());
                System.out.println(datosAutor);

                //Autor autor = new Autor(datosAutor);
                libro.setAutor(datosAutor);
                repo.save(libro);


		    }else {
		    	System.out.println("no encontrado");
		    }

        }
        private void librosRegistrados(){
            System.out.println("libros registrados: ");

            List<Libro> libros_registrados = repo.findAll();
            libros_registrados.forEach(System.out::println);
    }
    private void autoresRegistrados(){
        System.out.println("autores registrados: ");

        List<Autor> autores_registrados = repo.autores();
        autores_registrados.forEach(System.out::println);
    }


}
