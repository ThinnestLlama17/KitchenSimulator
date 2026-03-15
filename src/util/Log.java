package util;

public class Log {
    public static synchronized void print(String mensaje) {
        System.out.println(mensaje);
    }
}
