import jdk.nashorn.internal.parser.Token;

import java.io.*;
import java.util.ArrayList;

public class Lexer {
    ArrayList<Token> Tokentable;
    ArrayList<String> keywords;
    FileReader fileR;
    Reader reader;
    File file;
    public Lexer(String filename) throws FileNotFoundException {
        Tokentable = new ArrayList<Token>();
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

            /* ##### Assegnazione <-- e operatori relazionali < <= >= > == != #### */
            switch(state) {
                case 9:
                    if (c =='<') {
                        lessema = c;
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
                    return new Token("lessequal", lessema);

                } else {
<<<<<<< HEAD
                    retrack();
                    state= 12; //Significa che non è l'assegnazione e può essere uno degli operatori relazionali
=======
                    retracK();
                    return new Token("less", lessema);
                    //state= 12; //Significa che non è l'assegnazione e può essere uno degli operatori relazionali
>>>>>>> Lexer switch operatori relazionali e metodo installID
                }
                    break;
                case 11:
                    if(c == '-') {
                        lessema+=c;
                        return new Token("assign", lessema);
                    } else {
                        return new Token("notDefined", lessema); //Perchè se arriva fin qui, significa che nel lessema abbiamo <- ma non esiste un lessema <- quindi da errore
                    }
                    break;

                case 12:
                    if(c == '>'){
                        lessema=c;
                        state= 13;
                    }else{
                        state= 14;
                    }
                    break;

                case 13:
                    if(c == '='){
                        lessema+=c;
                        return new Token("greaterequal", lessema);
                    }else{
                        return new Token("greater", lessema);
                    }
                    break;

                case 14:
                    if(c == '='){
                        lessema=c;
                        state=15;
                    }else{
                        state= 16;
                    }
                    break;

                case 15:
                    if(c=='='){
                        lessema+=c;
                        return new Token("equal", lessema);
                    }else{
                        return new Token("notDefined", lessema); //Non assegno = all'assegnazione poichè per l'assegnazione abbiamo usato <--
                    }
                    break;

                case 16:
                    if(c=='!'){
                        lessema=c;
                        state=17;
                    }else{
                        state=18;
                    }
                    break;

                case 17:
                    if(c == '='){
                        lessema+=c;
                        return new Token("notequal", lessema);
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
                        lessema = c;
                    }else{

                    }
                    break;

                case 19:
                    if(Character.isDigit(c)){
                        lessema+=c;
                    }else{
                        return new Token("Number", lessema);
                    }
                    break;
            }


        }

    }
    private Token installID(String lessema){
        Token token;
        //Controllo se il lessema è una parola chiave
        for(String kw:keywords){
            if(lessema.equals(kw)){
                return kw;
            }else{
                token =  new Token("ID", lessema);
                Tokentable.add(token);
                return token;
            }
        }




        private void retrack(){


        }

    }

