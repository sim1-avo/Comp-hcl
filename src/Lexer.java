import java.io.*;
import java.util.ArrayList;

public class Lexer {

    ArrayList<Token> keywords;
    ArrayList<String> SymbolTable;
    FileReader fileR;
    Reader reader;
    File file;
    boolean retrack;
    int r;

    public Lexer(String filename) throws FileNotFoundException{
        SymbolTable= new ArrayList<String>();
        keywords= new ArrayList<Token>();
        keywords.add(new Token("if","if"));
        keywords.add(new Token("else","else"));
        keywords.add(new Token("while","while"));
        keywords.add(new Token("then","then"));
        keywords.add(new Token("int","int"));
        keywords.add(new Token("float","float"));

        retrack= false;
        initialize(filename);

    }

    public void initialize(String f) throws FileNotFoundException {
        file=new File(f);
        fileR= new FileReader(file);
        InputStream in = new FileInputStream(file);
        reader = new InputStreamReader(in);
    }

    public Token nextToken()throws Exception{
        int state=0;
        String lessema="";

        while ((r = reader.read()) != -1) {

            if (retrack == false) {
                r=reader.read();
            } else {
                retrack = false;
            }

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
             /*##### Fine separatori ####*/

             /*#### Assegnazione <-- e operatori relazionali < <= >= > == != #### */
            switch(state) {
                case 9:
                    if (c =='<') {
                        lessema = ""+c;
                        state=10;
                    } else {
                        state= 12; //impostarlo al prossimo case o pattern (switch) oppure riportarlo a zero in modo che ricominci dagli spazi
                    }
                    break;
                case 10:
                    if(c == '-') {
                        lessema+=c;
                        state=11;
                    }if(c == '='){
                    lessema+=c;
                    return new Token("relop","lessequal");

                    } else {
                    if(c != '-') return new Token("relop","less");
                    }
                break;

                case 11:
                    if(c == '-') {
                        lessema+=c;
                        return new Token("relop","assign");
                    } else {
                        return new Token("notDefined", lessema); //Perchè se arriva fin qui, significa che nel lessema abbiamo <- ma non esiste un lessema <- quindi da errore
                    }
                break;

                case 12:
                    if(c == '>'){
                        lessema=""+c;
                        state= 13;
                    }else{
                        state= 14;
                    }
                    break;

                case 13:
                    if(c == '='){
                        lessema+=c;
                        return new Token("relop","greaterequal");
                    }else{
                        return new Token("relop","greater");
                    }
                    break;

                case 14:
                    if(c == '='){
                        lessema=""+c;
                        state=15;
                    }else{
                        state= 16;
                    }
                    break;

                case 15:
                    if(c=='='){
                        lessema+=c;
                        return new Token("relop","equal");
                    }else{
                        return new Token("notDefined", lessema); //Non assegno = all'assegnazione poichè per l'assegnazione abbiamo usato <--
                    }
                    break;

                case 16:
                    if(c=='!'){
                        lessema=""+c;
                        state=17;
                    }else{
                        state=18;
                    }
                    break;

                case 17:
                    if(c == '='){
                        lessema+=c;
                        return new Token("relop","notequal");
                    }else{
                        return new Token("notDefined", lessema);
                    }
                    break;
            }

            //Numeri
            switch(state){
                case 18:
                    if(Character.isDigit(c)){
                        state = 19;
                        lessema = ""+c;
                    }else{
                        return new Token ("notDefined");
                    }
                    break;

                case 19:
                    if(Character.isDigit(c)){
                        lessema+=c;
                    }else{
                        retrack();
                        SymbolTable.add(lessema);
                        return new Token("NUM", listaStringhe.indexOf(lessema));
                    }
                    break;
            }


        }

    }

    private Token installID(String lessema) {
        Token token=null;
        //Controllo se il lessema è una parola chiave
        for (Token kw : keywords) {
            if (lessema.equals(kw.getName())) {
                return kw;
            } else {
                SymbolTable.add(lessema);
                token= new Token("ID",SymbolTable.indexOf(lessema))
                return token;
            }
        }
        return token;
    }

    private void retrack(){
            retrack = true;
        }

    }


