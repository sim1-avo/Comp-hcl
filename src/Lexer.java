import java.io.*;
import java.util.ArrayList;

public class Lexer {
    ArrayList<SymbolTable> Symboltable;
    ArrayList<String> keywords;
    FileReader fileR;
    Reader reader;
    File file;
    public Lexer(String filename) throws FileNotFoundException {
        Symboltable = new ArrayList<SymbolTable>();
        keywords= new ArrayList<String>();
        keywords.add("if");
        keywords.add("else");
        keywords.add("while");
        keywords.add("then");
        keywords.add("int");
        keywords.add("float");

        initialize(filename);

    }
    public void initialize(String f) throws FileNotFoundException {
        file=new File(f);
        fileR= new FileReader(file);
        InputStream in = new FileInputStream(file);
        reader = new InputStreamReader(in);
    }

    public Token nextToken()throws Exception{
        int r;
        int state=0;
        String lessema="";

        while ((r = reader.read()) != -1) {
            char c = (char) r;

            //Spazio, tab, new line
            switch(state){
                case 0:
                    if (c != ' ' || c != '\n'){
                        state=1;
                        break;
                    }
                    break;
            }

            //identificatori
            switch (state) {
                case 1:
                    if(Character.isLetter(c)){
                        state = 2;
                        lessema += c;
                        break;
                    }
                    state = 12; //va modificato, deve passare al prossimo pattern (switch)
                    break;

                case 2:
                    if(Character.isLetterOrDigit(c)){
                        lessemq += c;
                    }else{
                        state = 11; //passa al successivo pattern
                        retrack(); //torna indietro di un carattere dato che siamo andati di un carattere in avanti
                        return installID(lessema);
                    }
                    default: break;

            }

            /* ##### Inizio separatori ####*/

            //Parentesi tonda aperta
            switch(state) {

            }




        }

    }


}
