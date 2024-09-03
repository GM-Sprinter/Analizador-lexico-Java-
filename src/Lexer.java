
package AnalizadorLexico2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Clase principal que realiza el análisis léxico
public class Lexer {
    private String input;        // El texto que vamos a analizar
    private int currentIndex;    // Posición actual en el texto
    private char currentChar;    // Carácter actual que estamos viendo

    private List<Token> tokens;  // Lista para almacenar los tokens identificados

    // Constructor de la clase Lexer
    public Lexer(String input) {
        this.input = input;          // Inicializamos el texto a analizar
        this.currentIndex = 0;       // Comenzamos en el primer carácter
        this.currentChar = input.charAt(currentIndex);  // Establecemos el carácter actual
        this.tokens = new ArrayList<>();  // Inicializamos la lista de tokens
    }

    // Avanza al siguiente carácter en el texto
    private void advance() {
        currentIndex++;  // Incrementamos la posición en el texto
        if (currentIndex >= input.length()) {
            currentChar = '\0';  // Fin del texto
        } else {
            currentChar = input.charAt(currentIndex);  // Actualizamos el carácter actual
        }
    }

    // Salta los espacios en blanco
    private void skipWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            advance();  // Avanzamos hasta encontrar un carácter no blanco
        }
    }

    // Analiza identificadores (palabras o nombres)
    private String parseIdentifier() {
        StringBuilder result = new StringBuilder();
        while (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
            result.append(currentChar);  // Añadimos el carácter al resultado
            advance();  // Avanzamos al siguiente carácter
        }
        return result.toString();  // Devolvemos el identificador completo
    }

    // Analiza números
    private String parseNumber() {
        StringBuilder result = new StringBuilder();
        while (Character.isDigit(currentChar)) {
            result.append(currentChar);  // Añadimos el carácter al resultado
            advance();  // Avanzamos al siguiente carácter
        }
        return result.toString();  // Devolvemos el número completo
    }

    // Analiza el texto y clasifica cada parte en tokens
    private void parseToken() {
        while (currentChar != '\0') {  // Mientras no lleguemos al final del texto
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();  // Saltamos espacios en blanco
                continue;
            }

            if (Character.isLetter(currentChar)) {
                String identifier = parseIdentifier();  // Analizamos un identificador
                tokens.add(new Token("IDENTIFIER", identifier));  // Creamos un token de identificador
                continue;
            }

            if (Character.isDigit(currentChar)) {
                String number = parseNumber();  // Analizamos un número
                tokens.add(new Token("NUMBER", number));  // Creamos un token de número
                continue;
            }

            // Analizamos operadores (+, -, *, /)
            if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                tokens.add(new Token("OPERATOR", Character.toString(currentChar)));  // Creamos un token de operador
                advance();
                continue;
            }

            // Analizamos delimitadores (p.ej., ;, ,, .)
            if (currentChar == ';' || currentChar == ',' || currentChar == '.') {
                tokens.add(new Token("DELIMITER", Character.toString(currentChar)));  // Creamos un token de delimitador
                advance();
                continue;
            }

            // Si encontramos un carácter inesperado, lanzamos una excepción
            throw new RuntimeException("Unexpected character: " + currentChar);
        }
    }

    // Método principal para obtener la lista de tokens
    public List<Token> tokenize() {
        parseToken();  // Iniciamos el análisis léxico
        return tokens;  // Devolvemos la lista de tokens encontrados
    }

    // Método principal para ejecutar el programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el texto para analizar:");
        String input = scanner.nextLine();  // Leemos el texto del usuario
        Lexer lexer = new Lexer(input);  // Creamos una instancia del analizador léxico
        List<Token> tokens = lexer.tokenize();  // Realizamos el análisis léxico

        System.out.println("Tokens encontrados:");
        for (Token token : tokens) {
            System.out.println(token);  // Mostramos cada token encontrado
        }
    }
}

// Clase para representar los tokens identificados
class Token {
    private final String type;  // Tipo de token (e.g., IDENTIFIER, NUMBER)
    private final String value; // Valor del token (e.g., 'x', '10')

    // Constructor de la clase Token
    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    // Método para representar el token como una cadena de texto
    @Override
    public String toString() {
        return "Token{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
