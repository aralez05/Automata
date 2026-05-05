import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.poi.xssf.usermodel.*;
import java.io.FileOutputStream;

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
    // creacion del timer
    private static javax.swing.Timer timer;
    private static List<String[]> matrizTokensGlobal = new ArrayList<>();
    // exportar la matriz a excel
    public static void exportarExcel(List<String[]> matriz) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {

            XSSFSheet sheet = workbook.createSheet("Tokens");

            // encabezados
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Valor");
            header.createCell(2).setCellValue("Token");
            header.createCell(3).setCellValue("Siguiente");

            int rowNum = 1;

            for (int i = 0; i < matriz.size(); i++) {
                String[] fila = matriz.get(i);

                XSSFRow row = sheet.createRow(rowNum++);

                String palabra = fila[0];
                String token = fila[1];

                String siguiente = "-";
                if (i + 1 < matriz.size()) {
                    String[] next = matriz.get(i + 1);
                    if (next[1] != null) {
                        siguiente = next[1];
                    }
                }

                row.createCell(0).setCellValue(i);
                row.createCell(1).setCellValue(palabra);
                row.createCell(2).setCellValue(token != null ? token : "-");
                row.createCell(3).setCellValue(siguiente);
            }

            FileOutputStream fileOut = new FileOutputStream("tokens.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            System.out.println("Excel generado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        TABLA_TOKENS.put("System", "38 System TK38");
        TABLA_TOKENS.put("Out", "39 Out TK39");
        TABLA_TOKENS.put("Println", "42 Println TK42");
        TABLA_TOKENS.put("#", "40 Numeral TK40");
        TABLA_TOKENS.put("Abstract", "43 Abstract TK43");
        TABLA_TOKENS.put("Assert", "44 Assert TK44");
        TABLA_TOKENS.put("Break", "45 Break TK45");
        TABLA_TOKENS.put("Byte", "46 Byte TK46");
        TABLA_TOKENS.put("Case", "47 Case TK47");
        TABLA_TOKENS.put("Catch", "48 Catch TK48");
        TABLA_TOKENS.put("Const", "49 Const TK49");
        TABLA_TOKENS.put("Continue", "50 Continue TK50");
        TABLA_TOKENS.put("Default", "51 Default TK51");
        TABLA_TOKENS.put("Do", "52 Do TK52");
        TABLA_TOKENS.put("Else", "53 Else TK53");
        TABLA_TOKENS.put("Enum", "54 Enum TK54");
        TABLA_TOKENS.put("Exports", "55 Exports TK55");
        TABLA_TOKENS.put("Extends", "56 Extends TK56");
        TABLA_TOKENS.put("False", "57 False TK57");
        TABLA_TOKENS.put("Final", "58 Final TK58");
        TABLA_TOKENS.put("Finally", "59 Finally TK59");
        TABLA_TOKENS.put("Float", "60 Float TK60");
        TABLA_TOKENS.put("For", "61 For TK61");
        TABLA_TOKENS.put("Goto", "62 Goto TK62");
        TABLA_TOKENS.put("If", "63 If TK63");
        TABLA_TOKENS.put("Implements", "64 Implements TK64");
        TABLA_TOKENS.put("Instanceof", "65 Instanceof TK65");
        TABLA_TOKENS.put("Interface", "66 Interface TK66");
        TABLA_TOKENS.put("Long", "67 Long TK67");
        TABLA_TOKENS.put("Module", "68 Module TK68");
        TABLA_TOKENS.put("Native", "69 Native TK69");
        TABLA_TOKENS.put("Non-sealed", "70 NonSealed TK70");
        TABLA_TOKENS.put("Null", "71 Null TK71");
        TABLA_TOKENS.put("Open", "72 Open TK72");
        TABLA_TOKENS.put("Opens", "73 Opens TK73");
        TABLA_TOKENS.put("Package", "74 Package TK74");
        TABLA_TOKENS.put("Permits", "75 Permits TK75");
        TABLA_TOKENS.put("Provides", "76 Provides TK76");
        TABLA_TOKENS.put("Record", "77 Record TK77");
        TABLA_TOKENS.put("Requires", "78 Requires TK78");
        TABLA_TOKENS.put("Return", "79 Return TK79");
        TABLA_TOKENS.put("Sealed", "80 Sealed TK80");
        TABLA_TOKENS.put("Short", "81 Short TK81");
        TABLA_TOKENS.put("Strictfp", "82 Strictfp TK82");
        TABLA_TOKENS.put("Super", "83 Super TK83");
        TABLA_TOKENS.put("Switch", "84 Switch TK84");
        TABLA_TOKENS.put("Synchronized", "85 Synchronized TK85");
        TABLA_TOKENS.put("This", "86 This TK86");
        TABLA_TOKENS.put("Throw", "87 Throw TK87");
        TABLA_TOKENS.put("Throws", "88 Throws TK88");
        TABLA_TOKENS.put("To", "89 To TK89");
        TABLA_TOKENS.put("Transient", "90 Transient TK90");
        TABLA_TOKENS.put("Transitive", "91 Transitive TK91");
        TABLA_TOKENS.put("True", "92 True TK92");
        TABLA_TOKENS.put("Try", "93 Try TK93");
        TABLA_TOKENS.put("Uses", "94 Uses TK94");
        TABLA_TOKENS.put("Var", "95 Var TK95");
        TABLA_TOKENS.put("Volatile", "96 Volatile TK96");
        TABLA_TOKENS.put("When", "97 When TK97");
        TABLA_TOKENS.put("While", "98 While TK98");
        TABLA_TOKENS.put("With", "99 With TK99");
        TABLA_TOKENS.put("Yield", "100 Yield TK100");
        TABLA_TOKENS.put("_", "101 GuionBajo TK101");
        TABLA_TOKENS.put("\'", "102 ComillaSimple TK102");

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
        TABLA_TOKENS.put("system", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("out", "0 ERROR TK_ERROR");
        TABLA_TOKENS.put("println", "0 ERROR TK_ERROR");
    }

    public static boolean esError(String palabra) {
        if (palabra == null || palabra.isEmpty())
            return false;

        switch (palabra) {
            case "import":
            case "public":
            case "private":
            case "static":
            case "class":
            case "main":
            case "new":
            case "int":
            case "string":
            case "double":
            case "char":
            case "boolean":
            case "void":
            case "system":
            case "out":
            case "println":
                return true;
            default:
                return false;
        }
    }

    public static boolean esPalabraReservada(String palabra) {
        if (palabra == null || palabra.isEmpty())
            return false;

        switch (palabra) {
            case "Abstract":
            case "Assert":
            case "Boolean":
            case "Break":
            case "Byte":
            case "Case":
            case "Catch":
            case "Char":
            case "Class":
            case "Const":
            case "Continue":
            case "Default":
            case "Do":
            case "Double":
            case "Else":
            case "Enum":
            case "Exports":
            case "Extends":
            case "False":
            case "Final":
            case "Finally":
            case "Float":
            case "For":
            case "Goto":
            case "If":
            case "Implements":
            case "Import":
            case "Instanceof":
            case "Int":
            case "Interface":
            case "Long":
            case "Module":
            case "Native":
            case "New":
            case "Non-sealed":
            case "Null":
            case "Open":
            case "Opens":
            case "Package":
            case "Permits":
            case "Private":
            case "Protected":
            case "Provides":
            case "Public":
            case "Record":
            case "Requires":
            case "Return":
            case "Sealed":
            case "Short":
            case "Static":
            case "Strictfp":
            case "Super":
            case "Switch":
            case "Synchronized":
            case "This":
            case "Throw":
            case "Throws":
            case "To":
            case "Transient":
            case "Transitive":
            case "True":
            case "Try":
            case "Uses":
            case "Var":
            case "Void":
            case "Volatile":
            case "When":
            case "While":
            case "With":
            case "Yield":
            case "_":
                return true;
            default:
                return false;
        }
    }

    private static boolean Comentario(String palabra, Boolean EnComentario) {
        if (palabra.equals("#")) {
            System.out.println(TABLA_TOKENS.get("\""));
            EnComentario = !EnComentario;

        }
        return EnComentario;
    }

    private static Boolean EsComillaSimple(String palabra, Boolean enCadenaSimple) {
        if (palabra.equals("\'")) {
            System.out.println(TABLA_TOKENS.get("\'"));
            enCadenaSimple = !enCadenaSimple;
        }
        return enCadenaSimple;
    }

    private static Boolean EsComilla(String palabra, Boolean EnCadena) {
        if (palabra.equals("\"")) {
            System.out.println(TABLA_TOKENS.get("\""));
            EnCadena = !EnCadena;

        }
        return EnCadena;
    }

    private static String AsignarTokens(String palabra, boolean enCadena, boolean comentario, boolean enCadenaSimple) {

        String infoToken;
        if (palabra.isEmpty())
            return null;

        if (comentario == true && !palabra.equals("#")) {
            infoToken = "41 Comenatrios TK41";
            System.out.println("41 Comenatrios TK41");
            return infoToken;
        }

        // Si el interruptor está encendido, todo es un token de cadena
        if (enCadenaSimple == true && !palabra.equals("\'")) {
            infoToken = "102 CadenaSimple TK102";
            System.out.println("102 Cadena TK102");
            return infoToken;
        }

        // Si el interruptor está encendido, todo es un token de cadena
        if (enCadena == true && !palabra.equals("\"")) {
            infoToken = "37 Cadena TK37";
            System.out.println("37 Cadena TK37");
            return infoToken;
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

        } else if (esError(palabra)) {
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
        String[] PR = { "Import", "Class", "Main", "New" };
        String apertura = "{"; // los que son solo 1 cosa... lo sigo manejando
        char[] cierre = { '}', ';' }; //
        if (palabra.toLowerCase().equals(apertura)) { // Import{}
            return "CORRECTO";
        }
        return "INCORRECTO";

    }

    public static void main(String[] args) {
        // timer para exportar tabla
        timer = new javax.swing.Timer(7000, e -> {
            System.out.println("pausa");
            //exportarExcel(matrizTokensGlobal);
        });
        timer.setRepeats(false);
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
                String textoCompleto = cuadroTexto.getText().replace("\r", "");
                doc.setCharacterAttributes(0, textoCompleto.length(), estiloNormal, true);

                String[] Palabras = textoCompleto.split(
                 "(?<=[\\s\\{\\}\\(\\)\\[\\]\\;\\,\\.\\:\\=\\+\\-\\*\\/\\%\\<\\>\\\"#'])|(?=[\\s\\{\\}\\(\\)\\[\\]\\;\\,\\.\\:\\=\\+\\-\\*\\/\\%\\<\\>\\\"#'])");
                int indiceActual = 0;
                // Reiniciamos al empezar a leer el texto
                    
                // ESTA ES TU "MATRIZ" DINÁMICA
                List<String[]> matrizTokens = new ArrayList<>();
                Boolean Cadena = false;
                Boolean CadenaSimple = false;
                Boolean Coment = false;
                for (String palabra : Palabras) {
                    if (palabra.isEmpty())
                        continue;
                    boolean esEspacio = palabra.equals(" ") || palabra.equals("\n") || palabra.equals("\t");
                    String token = AsignarTokens(palabra, Cadena, Coment, CadenaSimple);
                    int inicio = indiceActual;
                    if (token == null || "0 ERROR TK_ERROR".equals(token)) {
                        doc.setCharacterAttributes(inicio, palabra.length(), estiloError, true);
                    }
                    if ((CadenaSimple == false) && (Cadena == false)) {
                        Coment = Comentario(palabra, Coment);
                    }
                    if ((Coment == false) && (CadenaSimple == false)) {
                        Cadena = EsComilla(palabra, Cadena);
                    }
                    if ((Coment == false) && (Cadena == false)) {
                        CadenaSimple = EsComillaSimple(palabra, CadenaSimple);
                    }

                    System.out.println(Cadena);
                    if (palabra.isEmpty()) {
                        return; // o continue si estás en loop
                    }
                    matrizTokens.add(new String[] { palabra, token });
                    if (!esEspacio) {
                        if ("41 Comenatrios TK41".equals(token)) {
                            doc.setCharacterAttributes(inicio, palabra.length(), estiloTexto, true);
                        } else if (token == null || "0 ERROR TK_ERROR".equals(token)) {
                            doc.setCharacterAttributes(inicio, palabra.length(), estiloError, true);
                        }
                    }
                    if (palabra.equals("\n")) {
                        indiceActual += 1; // fuerza avance correcto
                    } else {
                        indiceActual += palabra.length();
                    }
                }
                System.out.println("--- RESULTADOS ---");
                System.out.println("--------ID---------Valor--------TOKEN ingresado---------Token Siguiente--------");
                for (int i = 0; i < matrizTokens.size(); i++) {
                    String[] fila = matrizTokens.get(i);
                    String palabra = fila[0].replace("\n", "\\n").replace("\t", "\\t");
                    String tokenInfo = fila[1];
                    
                    String id = "-";
                    String tokenIngresado = "-";
                    String tokenSiguiente = "-";

                    for (int j = i + 1; j < matrizTokens.size(); j++) {
                        String[] filaSiguiente = matrizTokens.get(j);
                        if (filaSiguiente[1] != null && !filaSiguiente[1].isEmpty()) {
                            tokenSiguiente = filaSiguiente[1];
                            break;
                        }
                    }
                    if (tokenInfo != null && !tokenInfo.isEmpty()) {
                        tokenIngresado = tokenInfo;
                    }

                    System.out.printf("        %-11s%-14s%-30s%-30s%n", id, palabra, tokenIngresado, tokenSiguiente);
                }
                System.out.println("------------------");
                //reinicia el tiempo al presionar una tecla
                matrizTokensGlobal = matrizTokens;
                timer.restart(); 
            }

        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}