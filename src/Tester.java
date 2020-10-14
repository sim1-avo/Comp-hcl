import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {

        String filePath = args[0];

        try {
            Lexer lexicalAnalyzer = new Lexer(filePath);

            Token token;
            try {
                int x=0;
                while ((token = lexicalAnalyzer.nextToken()) != null) {
                    System.out.println(token);
                    x++;
                }
                ArrayList<String> symbolTable= lexicalAnalyzer.getSymbolTable();
                for(int i=0; i<symbolTable.size();i++) {
                    System.out.println(String.valueOf(i + " " + symbolTable.get(i)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File non trovato");
        }


    }

}

