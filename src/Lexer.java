import java.io.*;
import java.util.ArrayList;

public class Lexer {

    private ArrayList<Token> keywords;
    private ArrayList<String> SymbolTable;
    private FileReader fileR;
    private Reader reader;
    private File file;
    private int r;
    private int fileIndex;
    private ArrayList charactersFile;

    public Lexer(String filename) throws FileNotFoundException{
        SymbolTable= new ArrayList<String>();
        keywords= new ArrayList<Token>();
        keywords.add(new Token("if","if"));
        keywords.add(new Token("else","else"));
        keywords.add(new Token("while","while"));
        keywords.add(new Token("then","then"));
        keywords.add(new Token("int","int"));
        keywords.add(new Token("float","float"));
        fileIndex=-1;
        try {
            initialize(filename);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("File non trovato");
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    public void initialize(String f) throws FileNotFoundException, Exception {
        file=new File(f);
        fileR= new FileReader(file);
        InputStream in = new FileInputStream(file);
        reader = new InputStreamReader(in);
        charactersFile=new ArrayList ();

        while ((r = reader.read()) != -1) {
            charactersFile.add(r);
        }
        charactersFile.add(-1);

    }

    public Token nextToken()throws Exception{
        int state=0;
        String lessema="";
        String temp="";


        //cambiare condizione while
        boolean condition=true;
        while (condition) {
            fileIndex++;
            int int_to_char=(int) charactersFile.get(fileIndex);
            char c = (char) int_to_char;



            //Spazio, tab, new line
            switch(state){
                case 0:
                    if (c != ' ' && c != '\n' && c!= '\t' && c!= '\r'){
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
                    if (c == '<') {
                        lessema = "" + c;
                        state = 10;
                    } else {
                        state = 12; //impostarlo al prossimo case o pattern (switch) oppure riportarlo a zero in modo che ricominci dagli spazi
                    }
                    break;
                case 10:
                    if (c == '-') {
                        lessema += c;
                        state = 11;
                    }
                    if (c == '=') {
                        lessema += c;
                        return new Token("relop", "lessequal");

                    } else {
                        if (c != '-') {
                            retrack();
                            return new Token("relop", "less");
                        }
                    }
                    break;

                case 11:
                    if (c == '-') {
                        lessema += c;
                        return new Token("relop", "assign");
                    } else {
                        retrack();
                        return new Token("ERROR", lessema); //Perchè se arriva fin qui, significa che nel lessema abbiamo <- ma non esiste un lessema <- quindi da errore
                    }
            }

            switch (state) {
                case 12:
                    if (c == '>') {
                        lessema = "" + c;
                        state = 13;
                    } else {
                        state = 14;
                    }
                    break;

                case 13:
                    if (c == '=') {
                        lessema += c;
                        return new Token("relop", "greaterequal");
                    } else {
                        retrack();
                        return new Token("relop", "greater");
                    }
            }

            switch(state) {
                case 14:
                    if (c == '=') {
                        lessema = "" + c;
                        state = 15;
                    } else {
                        state = 16;
                    }
                    break;

                case 15:
                    if (c == '=') {
                        lessema += c;
                        return new Token("relop", "equal");
                    } else {
                        retrack();
                        return new Token("ERROR", lessema); //Non assegno = all'assegnazione poichè per l'assegnazione abbiamo usato <--
                    }
            }

            switch(state) {
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
                        retrack();
                        return new Token("ERROR", lessema);
                    }
            }

            switch(state) {
                case 18:
                    if(( (int) charactersFile.get(fileIndex)) == -1) {
                        return null;
                    } else {
                        state=19;
                    }
            }


            //Numeri
            switch(state){
                case 19:
                    if(Character.isDigit(c) && c!='0'){
                        state = 20;
                        lessema = ""+c;
                        break;
                    }else if(Character.isDigit(c) && c=='0'){
                        lessema= ""+c;
                        state = 21;
                        break;


                    }else{
                        return new Token ("ERROR", ""+c);
                    }


                case 20:
                    if(Character.isDigit(c)){ //aggiunto controllo sul numero decimale
                        lessema+=c;
                        break;
                    }if(c=='.'){
                        temp=""+c;
                        state=22;
                        break;

                    }
                    else{
                        retrack();
                        SymbolTable.add(lessema);
                        return new Token("NUM", ""+SymbolTable.lastIndexOf(lessema));
                    }

                case 21:
                    if(Character.isDigit(c) && c!='0') {
                        lessema=""+c;
                        state=20;
                        break;

                    }if(Character.isDigit(c) && c=='0'){
                        break;

                    }if(c=='.'){
                    temp=""+c;
                    state=22;
                    break;

                    }else{
                        retrack();
                        SymbolTable.add(lessema);
                        return new Token("NUM", ""+SymbolTable.lastIndexOf(lessema));

                    }
                case 22: //numero dopo la virgola
                    if(Character.isDigit(c) && c!='0' ){
                        lessema+=temp;
                        temp="";
                        lessema+=c;
                        break;
                    }if(Character.isDigit(c) && c=='0'){
                        temp+=c;
                        state=23;
                        break;
                    }
                    else{
                        if(temp.equals(".")) retrack();
                        retrack();
                        SymbolTable.add(lessema);
                        return new Token("NUM", ""+SymbolTable.lastIndexOf(lessema));
                    }
                //Numero dopo il .0
                case 23:
                    if(Character.isDigit(c) && c!='0'){
                        lessema+=temp;
                        temp="";
                        lessema+=c;
                        break;
                    }if(Character.isDigit(c) && c=='0'){
                        temp+=c;
                        break;
                    }else{
                        retrack();
                        SymbolTable.add(lessema);
                    return new Token("NUM", ""+SymbolTable.lastIndexOf(lessema));
                }

            }


        }
        return null;

    }

    private Token installID(String lessema) {
        Token token=null;
        //Controllo se il lessema è una parola chiave
        for (Token kw : keywords) {
            if (lessema.equals(kw.getName())) {
                return kw;
            }
        }
        if(SymbolTable.contains(lessema)){
            for(String s: SymbolTable){
                if(s.equals(lessema)){
                    token= new Token("ID",String.valueOf(SymbolTable.indexOf(lessema)));
                    return token;
                }
            }


        }
        SymbolTable.add(lessema);
        token= new Token("ID",String.valueOf(SymbolTable.indexOf(lessema)));
        return token;
    }

    private void retrack(){
        this.fileIndex--;
    }

    public ArrayList<String> getSymbolTable() {
        return SymbolTable;
    }




}


