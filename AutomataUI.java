import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class AutomataUI {
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

    public static boolean esPalabraReservada(String palabra) {
        if (palabra == null || palabra.isEmpty())
            return false;

        switch (palabra) {
            case "Import":
            case "Public":
            case "Private":
            case "Static":
            case "Class":
            case "Main":
            case "New":
            case "Int":
            case "String":
            case "Double":
            case "Char":
            case "Boolean":
            case "Void":
            case "System":
            case "Out":
            case "Println":
                return true;
            default:
                return false;
        }
    }

    private static String AsignarTokens(String palabra) {
        boolean enCadena = false;
        String infoToken;
        if (palabra.isEmpty() || palabra.equals("\n"))
            return null;

        // Si encontramos una comilla, invertimos el estado de 'enCadena'
        if (palabra.equals("\"")) {
            System.out.println(TABLA_TOKENS.get("\""));
            enCadena = !enCadena;

        }

        // Si el interruptor está encendido, todo es un token de cadena
        if (enCadena) {
            infoToken = "37 Cadena TK37";

            System.out.println("37 Cadena TK37");
        }

        infoToken = TABLA_TOKENS.get(palabra);
        if (infoToken != null) {
            System.out.println(infoToken);
        }
        if (esPalabraReservada(palabra)) {
            infoToken = TABLA_TOKENS.get(palabra);
            if (infoToken != null) {
                System.out.println(infoToken);
                return infoToken;
            }

        } else {
            if (validarIde(palabra)) {
                infoToken = "05 Identificador TK05";
                System.out.println("05 Identificador TK05");
                return infoToken;

            }
        }
        return infoToken;

    }

    // 1. Función con tu lógica de matrices (Autómata)
    public static String evaluarConAutomata(String palabra) {
        // int[][] matriz = {{...}, {...}};
        // Aquí iterarías sobre las letras de la palabra

        // Simulación temporal:
        if (palabra.toLowerCase().equals("error") || palabra.matches(".*\\d.*")) {
            return "INCORRECTO";
        }
        return "CORRECTO";
    }

    public static void main(String[] args) {
        // 2. Crear la ventana
        JFrame frame = new JFrame("Interfaz en Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Etiqueta de bienvenida
        JLabel label = new JLabel("¡Bienvenido al sistema!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        frame.add(label, BorderLayout.NORTH);

        JTextPane cuadroTexto = new JTextPane();
        cuadroTexto.setFont(new Font("Arial", Font.PLAIN, 14));

        cuadroTexto.setMargin(new Insets(10, 10, 10, 10));
        frame.add(new JScrollPane(cuadroTexto), BorderLayout.CENTER);

        Style estiloError = cuadroTexto.addStyle("ErrorStyle", null);
        StyleConstants.setForeground(estiloError, Color.RED);
        StyleConstants.setUnderline(estiloError, true);

        Style estiloNormal = cuadroTexto.addStyle("NormalStyle", null);
        StyleConstants.setForeground(estiloNormal, Color.BLACK);
        StyleConstants.setUnderline(estiloNormal, false);

        Style estiloTexto = cuadroTexto.addStyle("Text Style", null);
        StyleConstants.setForeground(estiloTexto, Color.GREEN);
        StyleConstants.setUnderline(estiloTexto, false);

        cuadroTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                StyledDocument doc = cuadroTexto.getStyledDocument();
                String textoCompleto = cuadroTexto.getText();
                doc.setCharacterAttributes(0, textoCompleto.length(), estiloNormal, true);

                String[] palabras = textoCompleto.split("\\s+");
                int indiceActual = 0;
                // Reiniciamos al empezar a leer el texto

                // ESTA ES TU "MATRIZ" DINÁMICA
                List<String[]> matrizTokens = new ArrayList<>();

                for (String palabra : palabras) {
                    if (palabra.isEmpty())
                        continue;

                    indiceActual = textoCompleto.indexOf(palabra, indiceActual);
                    String token = AsignarTokens(palabra);

                    // GUARDAR EN LA MATRIZ: [Palabra, Token]
                    matrizTokens.add(new String[] { palabra, token });

                    if ("02 Import TK02".equals(token)) {

                        doc.setCharacterAttributes(indiceActual, palabra.length(), estiloError,
                                true);
                    }

                    indiceActual += palabra.length();
                }
                System.out.println("--- RESULTADOS ---");
                for (String[] fila : matrizTokens) {
                    System.out.println("Palabra: " + fila[0] + " | Token: " + fila[1]);
                }
                System.out.println("------------------");

            }

        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
