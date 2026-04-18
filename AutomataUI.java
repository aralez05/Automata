import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AutomataUI {

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

        cuadroTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                StyledDocument doc = cuadroTexto.getStyledDocument();
                String textoCompleto = cuadroTexto.getText();

                doc.setCharacterAttributes(0, textoCompleto.length(), estiloNormal, true);

                String[] palabras = textoCompleto.split("\\s+");
                int indiceActual = 0;

                for (String palabra : palabras) {
                    if (palabra.isEmpty())
                        continue;

                    indiceActual = textoCompleto.indexOf(palabra, indiceActual);

                    String token = evaluarConAutomata(palabra);

                    if (token.equals("INCORRECTO")) {
                        doc.setCharacterAttributes(indiceActual, palabra.length(), estiloError, true);
                    }

                    indiceActual += palabra.length();
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
