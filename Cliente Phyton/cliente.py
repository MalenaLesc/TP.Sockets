import socket

HOST = '127.0.0.1'
PUERTO = 8080

print("Seleccione una opción:")
print("1. Generar nombre de usuario")
print("2. Generar correo electrónico")
opcion = input("> ")

cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
cliente.connect((HOST, PUERTO))

if opcion == "1":
    longitud = input("Ingrese la longitud para el nombre de usuario (entre 5 y 20): ")
    mensaje = f"NOMBRE:{longitud}\n"
elif opcion == "2":
    usuario = input("Ingrese nombre de usuario: ")
    mensaje = f"CORREO:{usuario}\n"
else:
    print("Opción inválida.")
    cliente.close()
    exit()

cliente.sendall(mensaje.encode("utf-8"))
respuesta = cliente.recv(1024).decode()
print("Respuesta del servidor:", respuesta)

cliente.close()
