import java.util.Arrays;
import java.util.Scanner;

public class LexicoInaceptado {
    // No recuerdo como es el orden de los archivos que nos pidio
    // Silerio, asi que si hay algo que dividir o asi lo vemos y corregimos

    public static void main(String[] args) {
        Scanner sca = new Scanner(System.in);

        System.out.println("Aqui comienza la insercion de codigo");
        System.out.println("(Presiona Enter 3 veces para terminar)");

        StringBuilder codigo = new StringBuilder();
        int Vacios = 0;

        while (sca.hasNextLine()) {
            String linea = sca.nextLine();

            if (linea.isEmpty()) {
                Vacios++;
                if (Vacios >= 3) {
                    break;
                }
            } else {
                for (int i = 0; i < Vacios; i++) {
                    codigo.append("\n");
                }
                Vacios = 0;
                codigo.append(linea).append("\n");
            }
        }

        String[] Palabras = codigo.toString().split(" |\n");

        String codigoCompleto = codigo.toString();
        // System.out.println(codigoCompleto);
        if (evaluar(Palabras)) {// Pedo del booleand
            System.out.println("Es una palabra reservada");
        } else {
            System.out.println("No es una palabra reservada");
        } // Por que no deja de mamar la vrg que no se usa el pinche metodo
    }

    private static boolean evaluar(String[] Palabras) {
        // Simbolos permitidos
        char[] letraMin = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        char[] letraMay = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        int[] digitos = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        String apertura = "{"; // los que son solo 1 cosa... lo sigo manejando
        char[] cierre = { '}', ';' }; // como una matriz ?? o como en estados?
        char[] continuacion = { '.' };

        // Palabras Reservadas
        String[] PR = { "Import", "Class", "Main", "New" };
        // Tipo de retorno

        // Estados
        String[] estado = { "Public", "Private" };

        int balanceLlaves = 0;
        int i = 0;
        cicloPrincipal: while (i < Palabras.length) {
            String palabraActual = Palabras[i].trim();

            if (palabraActual.isEmpty()) {
                i++;
                continue cicloPrincipal;
            }

            if (palabraActual.equals(apertura)) {
                i++;
                balanceLlaves++;
                System.out.println("01 Apertura TK01");
                continue cicloPrincipal;
            }
            if (palabraActual.equals(String.valueOf(cierre[0]))) { // '}'
                i++;
                balanceLlaves--;
                System.out.println("03 Cierre TK03");
                continue cicloPrincipal;
            }
            if (palabraActual.equals(String.valueOf(cierre[1]))) { // ';'
                i++;
                System.out.println("03 Cierre TK03");
                continue cicloPrincipal;
            }

            if (palabraActual.equals(PR[0])) { // "Import"
                i++;
                System.out.println("02 Import TK02");
                if (i >= Palabras.length || !validarIde(Palabras[i])) {
                    System.out.println("Error: Se esperaba un identificador despues de Import");
                    return false;
                }
                System.out.println("05 Identificador TK05");
                i++;
                while (i < Palabras.length) {
                    String actual = Palabras[i].trim();
                    if (actual.isEmpty()) {
                        i++;
                        continue;
                    }
                    if (actual.equals(String.valueOf(cierre[1]))) { // ';'
                        i++;
                        System.out.println("03 Cierre TK03");
                        break;
                    } else if (actual.equals(String.valueOf(continuacion[0]))) { // '.'
                        i++;
                        System.out.println("04 Continuacion TK04");
                        if (i >= Palabras.length || !validarIde(Palabras[i])) {
                            System.out.println("Error: Se esperaba un identificador despues de '.'");
                            return false;
                        }
                        System.out.println("05 Identificador TK05");
                        i++;
                    } else {
                        System.out.println("Error: Caracter inesperado en Import: " + actual);
                        return false;
                    }
                }
                continue cicloPrincipal;
            }

            if (palabraActual.equals(estado[0]) || palabraActual.equals(estado[1])) {
                i++;
                System.out.println("06 Estado TK06");
                continue cicloPrincipal;
            }

            switch (palabraActual) {
                case "Static":
                    i++;
                    System.out.println("07 Estado Instancia TK07");
                    continue cicloPrincipal;

                case "Class":
                    i++;
                    System.out.println("09 Clase TK09");
                    if (i < Palabras.length && validarIde(Palabras[i])) {
                        System.out.println("05 Identificador TK05");
                        i++;
                        if (i < Palabras.length && Palabras[i].equals(apertura)) {
                            System.out.println("01 Apertura TK01");
                            balanceLlaves++;
                            i++;
                        }
                    } else {
                        System.out.println("Error: Se esperaba un identificador despues de Clase");
                        return false;
                    }
                    continue cicloPrincipal;

                case "Int":
                case "String":
                case "Double":
                case "Char":
                case "Boolean":
                case "Void":
                    i++;
                    System.out.println("08 Tipo de retorno TK08");
                    if (i < Palabras.length && validarIde(Palabras[i])) {
                        System.out.println("05 Identificador TK05");
                        i++;
                        if (i < Palabras.length && Palabras[i].equals(apertura)) {
                            System.out.println("01 Apertura TK01");
                            balanceLlaves++;
                            i++;
                        }
                    } else {
                        System.out.println("Error: Se esperaba un identificador despues del tipo");
                        return false;
                    }
                    continue cicloPrincipal;

                default:
                    System.out.println("Error: Token inesperado '" + palabraActual + "'");
                    return false;
            }
        }

        if (balanceLlaves != 0) {
            System.out.println("Error: Llaves no balanceadas (faltan cierres o sobran aperturas)");
            return false;
        }

        return true;
    }

    private static boolean validarIde(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return false;
        }
        if (!Character.isLetter(palabra.charAt(0))) {
            return false;
        }
        for (int i = 1; i < palabra.length(); i++) {
            char caracter = palabra.charAt(i);
            if (!Character.isLetter(caracter) && !Character.isDigit(caracter)) {
                return false;
            }
        }
        return true;
    }
}
