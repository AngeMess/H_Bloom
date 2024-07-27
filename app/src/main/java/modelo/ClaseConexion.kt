package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection? {
        try {
            val url = "jdbc:oracle:thin:@192.168.1.12:1521:xe"
            val usuario = "CHRISTIAN_MARIN"
            val contrasena = "123456"

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        } catch (e: Exception) {
            println("Este es el error en la cadena de conexion: $e")
            return null
        }
    }
}