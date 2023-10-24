package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Book> libros = new ArrayList<>();
        libros.add(new Book("4563S","Don Quijote","Cervantes",2000,"1754"));
        libros.add(new Book("2341Z","El libro troll","ElRubius",120,"2015"));
        libros.add(new Book("9989L","Martin Fierro","Jose Hernandez",360,"1890"));

        Path archivoJSON = Path.of("src","main","resources","archivoJSON.json");
        escribirListaObjetosJson(libros,archivoJSON);

        /*List<Book> libros2;
        libros2 = leerArrayObjetosJson(archivoJSON);

        libros2.stream().forEach(System.out::println);
         */

        //System.out.println(buscarLibro(libros,"libro"));

        Scanner sc = new Scanner(System.in);
        int respuesta=0;
        String palabra;
        do {
            System.out.println("Bienvenido al menu de la libreria, indique que desea hacer:" +
                    "\n1)Agregar nuevo libro" +
                    "\n2)Buscar libro" +
                    "\n3)Ver lista de libros disponibles" +
                    "\n4)Salir del programa");

        respuesta=sc.nextInt();
        switch (respuesta){
            case 1: libros = añadirLibro(libros);
                    break;
            case 2: System.out.println("¿Por que palabra desea buscar?");
                    palabra=sc.next();
                    buscarLibro(libros, palabra).stream().forEach(System.out::println);
                    break;
            case 3: libros.stream().forEach(System.out::println);
            break;
            case 4:
                break;
            default: System.out.println("No has introducido una opcion disponible");
            break;
        }
        }while (respuesta!=4);

    }

    public static void escribirListaObjetosJson(List<Book> libros, Path ruta) {

        try {
            Files.deleteIfExists(ruta);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(ruta.toFile(), libros);
        } catch (IOException e) {
            System.out.println("El fichero no existe");
        }
    }

    public static List<Book> leerArrayObjetosJson(Path ruta) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(ruta.toFile(), new TypeReference<>() { });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Book> buscarLibro(List<Book> libros, String palabra){
        return libros.stream().filter((libro)->libro.titulo().contains(palabra) || libro.autor().contains(palabra)).toList();
    }

    public static Book crearLibro() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String isbn, nombre, autor, añoPubli;
        int paginas;

        System.out.println("Indique el isbn:");
        isbn=br.readLine();
        System.out.println("Indique el nombre del libro");
        nombre = br.readLine();
        System.out.println("Indique el autor");
        autor = br.readLine();
        System.out.println("Indique las paginas");
        paginas = Integer.parseInt(br.readLine());
        System.out.println("Indique año publicacion");
        añoPubli = br.readLine();

        Book libro = new Book(isbn,nombre,autor,paginas,añoPubli);
        return libro;
    }

    public static List<Book> añadirLibro(List<Book> libros) throws IOException {
        Set<Book> setLibros = new HashSet<>(libros);
        Book libro = crearLibro();
        if(setLibros.contains(libro)){
            System.out.println("Ya existe el libro en la lista");
        }
        else setLibros.add(libro);
        return setLibros.stream().toList();
    }
}