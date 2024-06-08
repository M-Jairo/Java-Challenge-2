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
    private List<Autor> autores_registrados;

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
                case 4:
                    autoresVivos();
                    break;
                case 5:
                    librosPorIdioma();
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

                libro.setAutor(datosAutor);
                repo.save(libro);


		    }else {
		    	System.out.println("no encontrado");
		    }

        }
        private void librosRegistrados(){
            System.out.println("libros registrados: ");

            List<Libro> libros_registrados = repo.findAll();
            libros_registrados.forEach(s ->
                    System.out.println("----------------" +
                            "\nLibro: " + s.getTitulo()
                            +"\nAutor: " + s.getAutor().getFirst().getNombre()+
                            "\nIdioma: " + s.getIdiomas() +
                            "\nN° de descargas: " + s.getnDeDescargas()+
                            "\n----------------" ));
    }
    private void autoresRegistrados(){
        System.out.println("autores registrados: ");

        autores_registrados = repo.autores();
        autores_registrados.forEach(a ->
                System.out.println("----------------" +
                        "\nNombre: " + a.getNombre()
                        +"\nFecha De Nacimiento: " + a.getFechaDeNacimiento()+
                        "\nFecha De Fallecimiento: " + a.getFechaDeFallecimiento() +
                        "\nLibros: " + a.getLibros().getTitulo() +
                        "\n----------------"));
    }

    private void autoresVivos(){
        System.out.println("ingrese año: ");
        Integer fechaElegida = teclado.nextInt();

        List<Autor> autoresVivos = repo.autores_vivos(fechaElegida);
        autoresVivos.forEach(a ->
                System.out.println("----------------" +
                "\nNombre: " + a.getNombre()
                +"\nFecha De Nacimiento: " + a.getFechaDeNacimiento()+
                "\nFecha De Fallecimiento: " + a.getFechaDeFallecimiento() +
                "\nLibros: " + a.getLibros().getTitulo() +
                "\n----------------"));
    }

    private void librosPorIdioma() {
        System.out.println(
                "Ingrese idioma: \n" +
                "es = Español\n" +
                "en = Ingles\n" +
                "fr = Frances\n" +
                "pt = Portuges");
        String idiomaElegido = teclado.next();
        List<Libro> librosIdioma = repo.findByIdiomasContainsIgnoreCase(idiomaElegido);
        librosIdioma.forEach(s ->
                System.out.println("----------------" +
                        "\nLibro: " + s.getTitulo()
                        +"\nAutor: " + s.getAutor().getFirst().getNombre()+
                        "\nIdioma: " + s.getIdiomas() +
                        "\nN° de descargas: " + s.getnDeDescargas()+
               "\n----------------" ));

    }

}
