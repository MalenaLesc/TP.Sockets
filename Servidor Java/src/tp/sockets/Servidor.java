/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mllov
 */
import java.io.*;
import java.net.*;
import java.util.Random;

public class Servidor {
    public static void main(String[] args) throws IOException {
        
        // Creo el servidor y queda esperando peticiones en el puerto 8080
        ServerSocket servidor = new ServerSocket(8080);
        System.out.println("Servidor iniciado en puerto 8080");

        while (true) {  //While para que el servidor se mantenga escuchando peticiones
            
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado.");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);

            // Lee el mensaje del cliente
            String mensaje = entrada.readLine();
            String respuesta;

            // Si el mensaje comienza con "NOMBRE:" entonces genera un nombre
            if (mensaje != null && mensaje.startsWith("NOMBRE:")) {
                
                try {
                    int longitud = Integer.parseInt(mensaje.split(":")[1]);
                    
                    if (longitud >= 5 && longitud <= 20) { //Validacion de longitud de nombre de usuario
                        
                        respuesta = generarNombreUsuario(longitud);
                    } else {
                        
                        respuesta = "ERROR: longitud fuera de rango (5-20).";
                    }
                } catch (NumberFormatException e) {
                    respuesta = "ERROR: longitud invalida.";
                }

            // Si el mensaje comienza con "CORREO:" entonces genera un correo
            } else if (mensaje != null && mensaje.startsWith("CORREO:")) {
                
                String usuario = mensaje.substring(7); // Utiliza el nombre de usuario para el correo
                
                if (validarNombreUsuario(usuario)) {
                    
                    respuesta = generarCorreo(usuario);
                } else {
                    
                    respuesta = "ERROR: Nombre de usuario invalido.";
                }

            } else {
                // Si se introduce una respuesta invalida tira error
                respuesta = "ERROR: Comando invalido.";
            }

            // Envia la respuesta al cliente
            salida.println(respuesta);
            cliente.close();
            System.out.println("Cliente desconectado.");
        }
    }

    // Genera un nombre de usuario con vocales y consonantes alternadas
    public static String generarNombreUsuario(int longitud) {
        
        String vocales = "aeiou";
        String consonantes = "bcdfghjklmnpqrstvwxyz";
        
        StringBuilder nombre = new StringBuilder();
        Random rand = new Random();

        boolean tieneVocal = false;
        boolean tieneConsonante = false;

        while (nombre.length() < longitud) {
            boolean usarVocal = rand.nextBoolean();
            char letra;

            if (usarVocal) {
                letra = vocales.charAt(rand.nextInt(vocales.length()));
                tieneVocal = true;
            } else {
                letra = consonantes.charAt(rand.nextInt(consonantes.length()));
                tieneConsonante = true;
            }

            nombre.append(letra);
        }

        if (!tieneVocal || !tieneConsonante)
            return generarNombreUsuario(longitud);  // Vuelve a entrar a la funcion si no cumple las condiciones

        return nombre.toString();
    }

    // Devuelve un correo generado con el nombre de usuario y cualquiera de los dominios validos
    public static String generarCorreo(String usuario) {
        
        String[] dominios = {"@gmail.com", "@hotmail.com"};
        Random rand = new Random();
        return usuario.toLowerCase() + dominios[rand.nextInt(dominios.length)];
    }

    // Verifica que el nombre tenga longitud valida, al menos una vocal y una consonante
    public static boolean validarNombreUsuario(String nombre) {
        
        if (nombre.length() < 5 || nombre.length() > 20) return false;

        boolean tieneVocal = false, tieneConsonante = false;

        for (char c : nombre.toLowerCase().toCharArray()) {
            if (!Character.isLetter(c)) return false;
            if ("aeiou".indexOf(c) >= 0) tieneVocal = true;
            else tieneConsonante = true;
        }

        return tieneVocal && tieneConsonante;
    }
}