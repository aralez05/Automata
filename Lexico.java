import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lexico {
    private static final Map<String, String> TABLA_TOKENS = new HashMap<>();

    static {
        TABLA_TOKENS.put("{", "01 Apertura TK01");
        TABLA_TOKENS.put("Import", "02 Import TK02");
        TABLA_TOKENS.put("}", "03 CierreCor TK03");
        TABLA_TOKENS.put(";", "04 Cierre TK04");
        TABLA_TOKENS.put(".", "05 Continuacion TK05");
        TABLA_TOKENS.put("Public", "06 Estado1 TK06");
        TABLA_TOKENS.put("Private", "07 Estado2 TK07");
        TABLA_TOKENS.put("Static", "08 Estado Instancia TK08");
        TABLA_TOKENS.put("Class", "09 Clase TK09");
        TABLA_TOKENS.put("Main", "10 Main TK10");
        TABLA_TOKENS.put("New", "11 New TK11");
        TABLA_TOKENS.put("Int", "12 Int TK12");
        TABLA_TOKENS.put("String", "13 String TK13");
        TABLA_TOKENS.put("Double", "14 Double TK14");
        TABLA_TOKENS.put("Char", "15 Char TK15");
        TABLA_TOKENS.put("Boolean", "16 Boolean TK16");
        TABLA_TOKENS.put("Void", "17 Void TK17");
        TABLA_TOKENS.put("(", "18 ParentesisApertura TK18");
        TABLA_TOKENS.put(")", "19 ParentesisCierre TK19");
        TABLA_TOKENS.put("[", "20 CorcheteApertura TK20");
        TABLA_TOKENS.put("]", "21 CorcheteCierre TK21");
        TABLA_TOKENS.put(",", "22 Coma TK22");
        TABLA_TOKENS.put(".", "23 Punto TK23");
        TABLA_TOKENS.put(":", "24 DosPuntos TK24");
        TABLA_TOKENS.put(";", "25 PuntoYComa TK25");
        TABLA_TOKENS.put("=", "26 Igual TK26");
        TABLA_TOKENS.put("+", "27 Mas TK27");
        TABLA_TOKENS.put("-", "28 Menos TK28");
        TABLA_TOKENS.put("*", "29 Asterisco TK29");
        TABLA_TOKENS.put("/", "30 Diagonal TK30");
        TABLA_TOKENS.put("%", "31 Porcentaje TK31");
        TABLA_TOKENS.put("<", "32 MenorQue TK32");
        TABLA_TOKENS.put(">", "33 MayorQue TK33");
        TABLA_TOKENS.put(" ", "34 Espacio TK34");
        TABLA_TOKENS.put("\n", "35 Salto De Linea TK35");
        TABLA_TOKENS.put("\t", "34 Espacio TK34");
        TABLA_TOKENS.put("\"", "36 Comillas TK36");
        TABLA_TOKENS.put("System", "37 System TK37");
        TABLA_TOKENS.put("Out", "38 Out TK38");
        TABLA_TOKENS.put("Println", "39 Println TK39");

        TABLA_TOKENS.put("import", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("public", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("private", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("static", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("class", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("main", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("new", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("int", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("string", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("double", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("char", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("boolean", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("void", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("System", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("out", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("println", "0 ERROR TK_ERROR");
    }
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

        String[] Palabras = codigo.toString().split(
                "(?<=[\\s\\{\\}\\(\\)\\[\\]\\;\\,\\.\\:\\=\\+\\-\\*\\/\\%\\<\\>\\\"])|(?=[\\s\\{\\}\\(\\)\\[\\]\\;\\,\\.\\:\\=\\+\\-\\*\\/\\%\\<\\>\\\"])");
        // Convulcione un poco
        String codigoCompleto = codigo.toString();
        // System.out.println(codigoCompleto);
        if (evaluar(Palabras)) {// Pedo del booleand
            System.out.println("Todo Bien");
        } else {
            System.out.println("Algo Mal");
        }
    }

    private static boolean evaluar(String[] Palabras) {
        boolean enCadena = false;

        for (int i = 0; i < Palabras.length; i++) {
            String palabraActual = Palabras[i];

            if (palabraActual.isEmpty() || palabraActual.equals("\n"))
                continue;

            // Si encontramos una c"omilla, invertimos el estado de 'enCadena'
            if (palabraActual.equals("\"")) {
                System.out.println(TABLA_TOKENS.get("\""));
                enCadena = !enCadena;
                continue;
            }

            // Si el interruptor está encendido, todo es un token de cadena
            if (enCadena) {
                System.out.println("37 Cadena TK37");
                continue;
            }

            String infoToken = TABLA_TOKENS.get(palabraActual);
            if (infoToken != null) {
                System.out.println(infoToken);
                continue;
            }

            if (validarIde(palabraActual)) {
                System.out.println("05 Identificador TK05");
                continue;
            }

            System.out.println("Error: '" + palabraActual + "' no es un componente valido");
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
