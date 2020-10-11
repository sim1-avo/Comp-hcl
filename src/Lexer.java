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
            state = 0;

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
                    state = 3;
                    break;

                case 2:
                    if(Character.isLetterOrDigit(c)){
                        lessema += c;
                    }else{
                        retrack(); //torna indietro di un carattere dato che siamo andati di un carattere in avanti
                        return installID(lessema);
                    }
                    default: break;

            }

            /* ##### Inizio separatori ####*/

            //Parentesi tonda aperta
            switch(state) {
                case 3:
                    if (c =='(') return new Token ("leftPar");
                    else state=4;
                    break;
            }

            //Parentesi tonda chiusa
            switch(state) {
                case 4:
                    if (c ==')') return new Token ("rightPar");
                    else state=5;
                    break;
            }

            //Parentesi graffa aperta
            switch(state) {
                case 5:
                    if (c =='{') return new Token ("leftBrace");
                    else state=6;
                    break;
            }

            //Parentesi graffa chiusa
            switch(state) {
                case 6:
                    if (c =='}') return new Token ("rightBrace");
                    else state=7;
                    break;
            }

            //virgola
            switch(state) {
                case 7:
                    if (c ==',') return new Token ("comma");
                    else state=8;
                    break;
            }

            //punto e virgola
            switch(state) {
                case 8:
                    if (c ==';') return new Token ("stopInstruction");
                    else state=9;
                    break;
            }
            /* ##### Fine separatori ####*/











        }

    }


}
