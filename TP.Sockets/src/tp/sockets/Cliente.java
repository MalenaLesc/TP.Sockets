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

public class Cliente {
    public static void main(String[] args) throws IOException {
        
        // Creo el socket del cliente y lo conecto al servidor en el puerto 8080
        Socket socket = new Socket("127.0.0.1", 8080);
        
        // Lee entradas del teclado
        BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
        
        // Preparacion para enviar datos al servidor
        PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        
        // Preparacion para leer la respuesta del servidor
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println("Conectado al servidor.");
        System.out.println("Seleccione una opción:\n1. Generar nombre de usuario\n2. Generar correo");
        int opcion = Integer.parseInt(consola.readLine());

        String mensaje;// = entrada.readLine();


        if (opcion == 1) {
            
            System.out.print("Ingrese la longitud para el nombre de usuario (entre 5 y 20): ");
            String longitud = consola.readLine();
            mensaje = "NOMBRE:" + longitud;
            
        } else if (opcion == 2) {
            
            System.out.print("Ingrese nombre de usuario: ");
            String usuario = consola.readLine();
            mensaje = "CORREO:" + usuario;
            
        } else {
            
            System.out.println("Opción invalida.");
            socket.close();
            return;
        }

        salida.println(mensaje); // Envia el mensaje al servidor
        
        String respuesta = entrada.readLine(); // Recibe la respuesta
        System.out.println("Respuesta del servidor: " + respuesta);

        socket.close();
    }
}
