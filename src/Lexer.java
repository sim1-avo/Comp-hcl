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
                        state= //impostarlo al prossimo case o pattern (switch) oppure riportarlo a zero in modo che ricominci dagli spazi
                    }
                    break;
                case 10:
                    if(c == '-') {
                        lessema+=c;
                        state=11;
                    } else {
                        retrack();
                        state= 12; //Significa che non è l'assegnazione e può essere uno degli operatori relazionali
                    }
                    break;
                case 11:
                    if(c == '-') {
                        lessema+=c;
                        return new Token('assign', lessema);
                    } else {
                        return new Token('notDefined', lessema); //Perchè se arriva fin qui, significa che nel lessema abbiamo <- ma non esiste un lessema <- quindi da errore
                    }
                    break;

                case 12: //Da implementare

            }








        }

    }

    //Dato che usiamo il pattern dell'identificatore anche per le keys, va usato install id ogni volta che si individua un id
    //per verificare se sia una key. Nel caso non sia una key viene ritornato il lessema come un id
    //Va modificato perchè è lo stub fornito dal prof
    private Token installID(String lessema){
        Token token;

        if(stringTable.containsKey(lessema))
            return symbolTable.get(lessema);
        else{
            token =  new Token("ID", lessema);
            stringTable.put(lessema, token);
            return token;
        }
    }

    //Da implementare, torna indietro di un carattere, perche in alcuni casi il compilatore deve andare in avanti
    //di un carattere per capire quando termina il lessema
    private void retrack(){
        // fa il retract nel file di un carattere
    }




}
