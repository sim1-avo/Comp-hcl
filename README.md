In questo readme sono riportate tutte le caratteristiche di come è stato implementato il lexer.

Il readme è suddiviso nelle seguenti sezioni:
    1. TABELLA DEI SIMBOLI;
    2. GESTIONE ERRORI;
    3. PAROLE CHIAVI E IDENTIFICATORI;
        a. GESTIONE CONFLITTO TRA PAROLE CHIAVI E IDENTIFICATORI;
    4. SEPARATORI;


1. TABELLA DEI SIMBOLI
Nella tabella dei simboli andranno inseriti solo i lessemi di tipo NUM (numeri) e gli ID (identificatori).
La sua struttura è stata realizzata tramite ArrayList ed ogni elemento è formato dalla combinazione id_numerico -> valore.
Viene ritornata l'intera symbol table al parser (in questo caso al Tester).

2. GESTIONE ERRORI
Per quanto riguarda la gestione degli errori, abbiamo deciso di non arrestare l'esecuzione del lexer e di proseguire con l'analisi di tutti i caratteri. Ogni qualvolta il lexer individua un errore, ovvero un carattere o un insieme di caratteri non identificati, ritorna un token di tipo (ERROR, 'info'), dove al posto di info si avrà il carattere che ha causato errore.
Abbiamo scelto di gestire cosi gli errori perché così facendo il programmatore avrà già una visione più ampia sugli errori nel codice.
In alcuni casi è stato necessario effettuare retrack per alcuni errori, altrimenti si sarebbe perso un carattere (esempio 2a).

    Esempio 2a
Supponiamo di aver trovato il carattere '!', andremo un carattere in avanti per vedere da cosa è seguito, dato che potrebbe essere il carattere 'differente' (!=). Supponiamo che il carattere dopo il '!' non sia '=', in questo caso dovrebbe lanciare errore sul '!', perché da solo non è riconosciuto da nessun pattern, quindi prima di ritornare errore va fatto un retrack per non perdere il carattere successivo al '!'.


3. PAROLE CHIAVI E IDENTIFICATORI
Le parole chiavi che sono state utilizzate in questo linguaggio sono le seguenti: int, float, if, then, else, while e sono state inserite tutte all'interno di una tabella (array) delle parole chiavi.
Mentre per quanto riguarda gli identificatori, è stato scelto che facessero parte di questo pattern tutti i lessemi che inizino con una lettera (maiuscola o minuscola) e può essere seguita da altre lettere o numeri.
Dato che il nostro obiettivo è quello di riuscire a tirar fuori lessemi che siano il più lunghi possibile, alla fine del diagramma di transizione degli identificatori, prima di ritornare il token va fatto un retrack perchè faremo sempre il controllo sul carattere successivo per assicurarci di non lasciar fuori nessun carattere.

    a. CONFLITTO TRA PAROLE CHIAVI E IDENTIFICATORI
Dato che i pattern di parole chiavi ed identificatori sono identici, per gestire questo conflitto si è proceduto in questo modo:
abbiamo utilizzato anche per le parole chiavi lo stesso diagramma degli identificatori, dove prima di ritornare il token, viene richiamato il metodo installID() che va a verificare se il lessema individuato è una parola chiave o un identificatore.
Gli identificati ritorneranno un token composto da classe a cui appartengono (ID) più l'id corrispondente nella tabella dei simboli (esempio 2a), mentre le parole chiavi ritorneranno un token non contenente il parametro aggiuntivo (esempio 2b)
    
    Esempio 3a
Supponiamo di avere aggiunto nella tabella dei simboli 1 -> x,dove x è un identificatore, avremo che il token di x sarà (ID, "1").

    Esempio 3b
Supponiamo che il lessema sia la parola chiave 'else', avremo come token (ELSE).


4. SEPARATORI
Per i separatori è stato utilizzato un pattern specifico per ogni parentesi.
Lessema -> token
   (    -> (leftPar)
   )    -> (rightPar)
   {    -> (leftBrace)
   }    -> (rightBrace)
   ,    -> (comma)
   ;    -> (stopInstruction)





L'esercitazione 1 sul lexer è stata realizzata dagli studenti:

                        Mauro Gaetano                          Avolicino Simone                                                    
                        g.mauro14@studenti.unisa.it            @studenti.unisa.it 
                        matricola: 0522500909                  matricola:
        