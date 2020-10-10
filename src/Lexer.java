import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Lexer {
    ArrayList<SymbolTable> Symboltable;
    ArrayList<String> keywords;
    FileReader fileR;
    File file;
    public Lexer(String filename) throws FileNotFoundException {
        Symboltable = new ArrayList<SymbolTable>();
        keywords= new ArrayList<String>();
        keywords.add("if");
        keywords.add("else");
        keywords.add("while");
        keywords.add("for");
        keywords.add("then");
        keywords.add("int");
        keywords.add("float");
        file=new File(filename);
        fileR= new FileReader(file);

    }


}
