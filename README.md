# IL LABIRINTO DI CNOSSO: l'impresa di Tesèo contro il Minotauro<br/><br/>

Sull’isola di Creta, nel periodo del regno di Minosse, che sposa Pasifae, in seguito ad un’offesa fatta ad Afrodite 
nasce un figlio mostruoso, metà uomo e metà toro, il Minotauro. Minosse lo imprigiona in un labirinto e, per sfamarlo, 
impone alle città sotto la sua dominazione un tributo annuale di sette fanciulle e sette fanciulli. Un anno, però, tra 
loro viene condotto il principe di Atene, Tesèo, che non vuole arrendersi al tragico destino e tenterà di sconfiggere il
letale mostro...<br/><br/><br/>![Map](MapNew.svg)<br/><br/><br/>Il gioco inizia con Tesèo posto all'esterno del labirinto, 
dirigendosi verso **Sud** si avrà modo di entrare nell'*Ala d'ingresso* in cui possiamo osservare un 
**gomitolo**. Raccogliere ed usare il **gomitolo** 
permetterà di uscire automaticamente dal labirinto una volta sconfitto il Minotauro.<br/><br/>Dirigendosi verso **Est** si 
entra nell'*Ala del bivio*.<br/><br/>Andando verso **Sud** si entra nell'*Ala della Spada* dove è appunto 
possibile raccogliere la **spada** che servirà in seguito quando si incontrerà il Minotauro per sconfiggerlo. Da questa 
stanza è possibile soltanto tornare alla 
precedente inserendo **Nord** da cui poi si può proseguire il cammino.<br/><br/>Proseguendo quindi verso **Est**
si entra nell'*Ala del grande passo*.<br/><br/>Verso **Nord** si entra 
nella *Stanza della Battaglia* dove si dovrà affrontare il **Minotauro** se si è in possesso della **spada**, altrimenti 
incontrarlo sarà letale e il gioco finirà.<br/><br/>A **Sud** dell'*Ala del grande passo* c'è l'*Ala finale*.


# scacchi-thacker <p> 
![](res/img/UniBaLogo.svg)    
    
# Indice  

### 1. [Introduzione](#1)  
  
### 2. [Modello di dominio](#2)  
 1. [Generalizzazione](#2.1)  
     1.1 [`BasePiece`](#2.1.1)  
 2. [Aggregazione e composizione](#2.2)  
      2.1 [`Match`](#2.2.1)  
      2.2 [`Board`](#2.2.2)  
      2.3 [`Move`](#2.2.3)  

### 3. [Requisiti specifici](#3)          
 1. [Requisiti funzionali](#3.1)          
 2. [Requisiti non funzionali](#3.2)        
        
### 4. [System Design](#4)          
 1. [Stile architetturale](#4.1)          
 2. [Rappresentazione dell’architettura](#4.2)          
        
### 5. [OO Design](#5)        
 1. [Information hiding](#5.1)          
 2. [Alta coesione](#5.2)          
 3. [Basso accoppiamento](#5.3)     
 4. [Don't Repeat Yourself (DRY)](#5.4)     

        
### 6. [Riepilogo del test](#6)   
 1. [Analisi statica del codice](#6.1)   
     1.1 [Checkstyle](#7.1.1)  
     1.2 [SpotBugs](#7.1.1)  
 2. [Analisi dinamica del codice](#6.2)   
      2.1 [Test di unità](#6.2.1)  
      2.2 [Test funzionale](#6.2.2)  


### 7. [Manuale utente](#7)
 1. [Docker](#7.1)  
    1.1 [Requisiti di sistema](#7.1.1)  
    1.2 [Installazione Docker](#7.1.2)  
    1.3 [Esecuzione immagine Docker](#7.1.3)
 2. [Pezzi e movimenti sulla scacchiera](#7.2)  
    2.1 [Notazione algebrica](#7.2.1)  
    2.2 [Pedone](#7.2.2)  
    2.3 [Torre](#7.2.3)  
    2.4 [Cavallo](#7.2.4)  
    2.5 [Alfiere](#7.2.5)  
    2.6 [Donna](#7.2.6)  
    2.7 [Re](#7.2.7)  
    2.8 [Cattura](#7.2.8)  
 3. [Menu](#7.3)  
    3.1 [Menu principale](#7.3.1)  
    3.2 [Menu di gioco](#7.3.2)  
 4. [Comandi di gioco](#7.4)  
    4.1 [`help`](#7.4.1)  
    4.2 [`play`](#7.4.2)  
    4.3 [`board`](#7.4.3)  
    4.4 [`moves`](#7.4.4)  
    4.5 [`captures`](#7.4.5)  
    4.6 [`quit`](#7.4.6)

### 8. [Processo di sviluppo e organizzazione del lavoro](#8)  
 1. [Manifesto Agile](#8.1)  
 2. [Scrum](#8.2)  
    2.1 [Product backlog](#8.2.1)  
    2.2 [Sprint goals](#8.2.2)  
  3. [Strumenti di lavoro](#8.3)
    
### 9. [Analisi retrospettiva](#9)          
 1. [Soddisfazioni](#9.1)          
 2. [Insoddisfazioni](#9.2)          
              
# <span id = "1">1. Introduzione</span> 
Questo documento è una relazione tecnica finale per il progetto che implementa il gioco degli Scacchi del gruppo Thacker.     
<p>Lo scopo di questo progetto è quello di creare un'applicazione, in completa conformità con il regolamento degli scacchi 
FIDE, che possa essere utilizzata da utenti che abbiano una conoscenza almeno dilettantistica di quest'ultimo.   
<p>L'applicazione può ricevere l'input in modalità interattiva, utilizzando la notazione algebrica italiana, oppure in   
modalità batch con una partita registrata in formato PGN <em>(Portable Game Notation)</em>, utilizzando la notazione   
algebrica inglese.   
<p>Supporta partite interattive esclusivamente contro avversari umani.   
<p>Si osserva che l'obiettivo di questo progetto è quello di dimostrare le competenze acquisite durante le lezioni del   
corso di Ingegneria del Software, piuttosto che produrre una soluzione completa e avanzata.    

<a href="#top">Torna all'inizio</a> 

# <span id = "2">2. Modello di dominio</span> 
Questa sezione contiene una panoramica sulla tassonomia delle classi e delle loro relazioni.  
     
## <span id = "2.1">2.1. Generalizzazione</span> 
Le relazioni di generalizzazione vengono utilizzate nei diagrammi di classe per indicare che la sottoclasse riceve tutti 
gli attributi, le relazioni e le operazioni definite nella superclasse.

### <span id = "2.1.1">2.1.1 `BasePiece`</span>
La classe <code>BasePiece</code> rappresenta l'astrazione di un generico pezzo degli scacchi e in quanto 
<strong>superclasse</strong> contiene gli attributi, le relazioni e le operazioni comuni a tutte le sue 
<strong>sottoclassi</strong>.</br>
<br>
<br>
![](res/img/imgModelliDominio/BasePiece.png)
>_Diagramma di classe di <code>BasePiece</code>._

<br>

- l'attributo <code>pieceStartPosition</code> di tipo <code>Point</code> rappresenta le coordinate iniziali del pezzo 
sulla matrice della scacchiera;

- l'attributo <code>firstMove</code> segnala se il pezzo sia stato mosso per la prima volta;

- l'attributo <code>color</code> comunica il colore del pezzo: 
  - <code>true</code> se nero 
  - <code>false</code> se bianco
- l'attributo <code>pieceTypeId</code> identifica il tipo di pezzo attraverso un <code>int</code>;
- l'attributo <code>pieceSymbol</code> rappresenta il tipo di pezzo di pezzo attraverso il suo carattere Unicode;
- l'attributo <code>piecePosition</code> rappresenta le coordinate attuali del pezzo sulla matrice della scacchiera;

Ogni sottoclasse che specializza la superclasse è anche un oggetto di quel tipo.<br>

La classe <code>Basepiece</code> viene 
estesa dai 6 tipi pezzo:
1. Il <code>Pawn</code> reimplementa metodi di cattura e di movimento anche disambigui, durante la cattura userà l'attributo 
<code>playedMatch</code> per accedere alla partita e verificare se coinvolga un <code>Pawn</code> nemico vulnerabile in 
En Passant. 

2. Il <code>Bishop</code> reimplementa metodi di cattura e di movimento senza la neccessità di disambiguare in quanto i 
due pezzi si muovo diagonalmente su case con colore diverso.  
3. La <code>Queen</code> reimplementa metodi di cattura e di movimento senza la neccessità di disambiguare in quanto 
esiste un unico pezzo di questo tipo sulla scacchiera.
4. Il <code>Knight</code> reimplementa metodi di cattura e di movimento anche disambigui.
5. Il <code>King</code> reimplementa metodi di cattura e di movimento senza la neccessità di disambiguare in quanto esiste
un unico pezzo di questo tipo sulla scacchiera. Durante l'esecuzione dell'arrocco, l'attributo <code>firstMove</code> 
consentirà di verificare se è legale durante la partita attuale.
6. La <code>Rock</code> reimplementa metodi di cattura e di movimento anche disambigui. Durante l'esecuzione dell'arrocco,
l'attributo <code>firstMove</code> consentirà di verificare se è legale durante la partita attuale.

## <span id = "2.2">2.2. Aggregazione e composizione</span>
Un'associazione rappresenta una relazione strutturale che collega due classificatori. In relazioni tra classi, si utilizzano le associazioni per visualizzare 
le scelte di progettazione effettuate sulle classi che contengono dati e per mostrare quali di tali classi richiedono la 
condivisione dati. 
### <span id = "2.2.1">2.2.1 `Match`</span>
La classe <code>Match</code> rappresenta l'istanza di una partita e contiene gli attributi e le informazioni sul suo stato.
<br>
<br>
![](res/img/imgModelliDominio/Match.png) 
>_Diagramma di classe di <code>Match</code>._

<br/>

- l'attributo <code>playerTurn</code> segnala di quale giocatore sia il tratto:

  - <code>true</code> se nero 
  - <code>false</code> se bianco. 
  
- l'attributo <code>chessBoard</code> contiene l'istanza di una <code>Board</code>;

Un oggetto di classe <code>Match</code> si associa con un unico oggetto di classe <code>Board</code> in quanto ad 
ogni partita è associata un'unica scacchiera.

### <span id = "2.2.2">2.2.2 `Board`</span>
La classe <code>Board</code> rappresenta una scacchiera 8x8 di case di colore diverso contenenti i pezzi istanziati in 
una partita, definiti dal proprio carattere Unicode.
<br>
<br>
![](res/img/imgModelliDominio/Board.png)
>_Diagramma di classe di <code>Board</code>._

<br>

- l'attributo <code>matrixChessBoard</code> rappresenta una matrice 8x8 che può contenere oggetti di classe 
<code>BasePiece</code> o <code>null</code>.
<br><br>

Un oggetto di classe <code>Board</code> si associa con diversi oggetti di classe <code>BasePiece</code> in quanto ad 
ogni scacchiera sono associati diversi pezzi.


### <span id = "2.2.3">2.2.3 `Move`</span>
Un oggetto di classe <code>Move</code> contiene le informazioni e il movimento di un pezzo. 
<br>
<br>
![](res/img/imgModelliDominio/Match_BasePiece_Move.png)
>_Diagramma di classe di <code>Move</code>._

<br>

L' oggetto di classe <code>Match</code> delega il controllo sulla possibilità della mossa all'insieme specifico dell'id 
del pezzo (0 → <code>Pawn</code>, 1 → <code>Rock</code>, 2 → <code>Knight</code> ...). Se esiste un pezzo con quell'id 
che può portare a termine la mossa allora il pezzo si relaziona con il <code>Match</code> attraverso un oggetto di classe 
<code>Move</code>.<br><br>
La classe <code>Move</code> è quindi una classe associativa che permette di legare i pezzi che estendono 
<code>BasePiece</code> all'oggetto di classe <code>Match</code>.<br><br>
Se nessun pezzo dell'id interpellato può portare a termine la mossa, non si creerà alcuna relazione.

<p><a href="#top">Torna all'inizio</a>

# <span id = "3">3. Requisiti specifici</span> 
Questa sezione specifica tutti i requisiti per il software di gioco degli scacchi. I requisiti si riferiscono a 
funzionalità e vincoli.    
  
L'applicazione, in completa conformità alle
[Regole degli scacchi FIDE](http://www.federscacchi.it/doc/reg/d20140623055252_fide.pdf), padroneggia i fondamentali 
movimenti dei pezzi.  
  
L'obiettivo principale è consentire a due utenti di giocare in modo interattivo da una stessa postazione.  
  
## <span id = "3.1">3.1 Requisiti funzionali</span> 
I FUR *(Functional User Requirement)* descrivono le funzionalità del software in termini di:
 - servizi che il software stesso deve fornire;
 - risposte che l’utente aspetta dal software in determinate condizioni;
 - risultati che il software deve produrre in risposta a specifici input.
 
Questa applicazione fornisce le seguenti funzionalità:  

| Requisito | Descrizione |
|--|--|
| Mostrare l'elenco dei comandi | Al comando <code>help</code> l'applicazione deve mostrare una lista di comandi, uno <br>per riga. |
| Iniziare una nuova partita | Al comando <code>play</code> l'applicazione si deve predisporre a ricevere comandi <br>tra cui la prima mossa del bianco ed è in grado di ricevere altri comandi <br>di gioco (es. <code>show</code>). |
| Mostrare la scacchiera | Al comando <code>board</code>, l'applicazione deve mostrare i pezzi in formato <br>Unicode e la loro posizione sulla scacchiera. |
| Muovere un Pedone | L'applicazione deve accettare mosse in [notazione algebrica abbreviata in<br>italiano](https://it.wikipedia.org/wiki/Notazione_algebrica), rispetta le regole degli scacchi, il Pedone può catturare pezzi, <br>può catturare in en passant, se si tenta una mossa non valida è mostrato il <br>messaggio <code>mossa non valida</code> e l'applicazione rimane in attesa di una mossa <br>valida. |
| Muovere un Cavallo | L'applicazione deve accettare mosse in [notazione algebrica abbreviata in<br>italiano](https://it.wikipedia.org/wiki/Notazione_algebrica), rispetta le regole degli scacchi, il Cavallo può catturare pezzi, se si <br>tenta una mossa non valida è mostrato il messaggio <code>mossa non valida</code> e <br>l'applicazione rimane in attesa di una mossa valida. |
| Muovere un Alfiere | L'applicazione deve accettare mosse in [notazione algebrica abbreviata in<br>italiano](https://it.wikipedia.org/wiki/Notazione_algebrica), rispetta le regole degli scacchi, l'Alfiere può catturare pezzi, se si <br>tenta una mossa non valida è mostrato il messaggio <code>mossa non valida</code> e <br>l'applicazione rimane in attesa di una mossa valida. |
| Muovere una Torre | L'applicazione deve accettare mosse in [notazione algebrica abbreviata in<br>italiano](https://it.wikipedia.org/wiki/Notazione_algebrica), rispetta le regole degli scacchi, la Torre può catturare pezzi, se si <br>tenta una mossa non valida è mostrato il messaggio <code>mossa non valida</code> e <br>l'applicazione rimane in attesa di una mossa valida. |
| Muovere la Donna | L'applicazione deve accettare mosse in [notazione algebrica abbreviata in<br>italiano](https://it.wikipedia.org/wiki/Notazione_algebrica), rispetta le regole degli scacchi, la Donna può catturare pezzi, se si <br>tenta una mossa non valida è mostrato il messaggio <code>mossa non valida</code> e <br>l'applicazione rimane in attesa di una mossa valida. |
| Muovere il Re | L'applicazione deve accettare mosse in [notazione algebrica abbreviata in<br>italiano](https://it.wikipedia.org/wiki/Notazione_algebrica), rispetta le regole degli scacchi, il Re può catturare pezzi, se si <br>tenta una mossa non valida è mostrato il messaggio <code>mossa non valida</code> e <br>l'applicazione rimane in attesa di una mossa valida. |
| Arroccare corto | L'applicazione deve accettare mosse in [notazione algebrica abbreviata in<br>italiano](https://it.wikipedia.org/wiki/Notazione_algebrica), rispetta le regole degli scacchi, il giocatore non ha ancora mosso né <br>il Re né la Torre coinvolti nell'arrocco, non ci devono essere pezzi (amici o <br>avversari) fra il Re e la Torre utilizzata, né la casa di partenza del Re, né la <br>casa che esso deve attraversare e né quella di arrivo devono essere <br> minacciate da un pezzo avversario, se si tenta una mossa non valida è <br>mostrato il messaggio <code>mossa non valida</code> e l'applicazione rimane in attesa <br>di una mossa valida. |
| Arroccare lungo | L'applicazione deve accettare mosse in [notazione algebrica abbreviata in<br>italiano](https://it.wikipedia.org/wiki/Notazione_algebrica), rispetta le regole degli scacchi, il giocatore non ha ancora mosso né <br>il Re né la Torre coinvolti nell'arrocco, non ci devono essere pezzi (amici o <br>avversari) fra il Re e la Torre utilizzata, né la casa di partenza del Re, né la <br>casa che esso deve attraversare e né quella di arrivo devono essere <br> minacciate da un pezzo avversario, se si tenta una mossa non valida è <br>mostrato il messaggio <code>mossa non valida</code> e l'applicazione rimane in attesa <br>di una mossa valida. |
| Mostrare le mosse giocate | Al comando <code>moves</code> l'applicazione deve mostrare la storia delle mosse con <br>[notazione algebrica abbreviata in italiano](https://it.wikipedia.org/wiki/Notazione_algebrica). |
| Mostrare le catture | Al comando <code>captures</code> l'applicazione deve mostra la lista delle catture del <br> giocatore Bianco e del Nero con caratteri Unicode. |
| Mostrare le mosse giocate | Al comando <code>moves</code> l'applicazione deve mostrare la storia delle mosse con <br>[notazione algebrica abbreviata in italiano](https://it.wikipedia.org/wiki/Notazione_algebrica). |
| Chiudere il gioco | Al comando <code>quit</code> l'applicazione si deve chiude e compare il prompt del <br>sistema operativo. |

## <span id = "3.2">3.2 Requisiti non funzionali</span>  
I NFR *(Non Functional Requirement)* descrivono le caratteristiche di qualità del prodotto software da sviluppare, i 
requisiti di sistema/ambiente, le tecnologie e gli standard di cui il software deve tenere conto.

<table>
<thead>
  <tr>
    <th>Requisito</th>
    <th>Sottorequisito</th>
    <th>Descrizione</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td rowspan="3">Usabilità</td>
    <td>Apprendibilità</td>
    <td>Il software prevede che l'utente conosca le regole generali del gioco degli Scacchi e la notazione algebrica abbreviata italiana.</td>
  </tr>
  <tr>
    <td>Protezione dall’errore utente</td>
    <td>Il livello di protezione dagli errori dell'utente consiste nella rigorosa analisi dell'input con opportune notifiche di comandi o mosse errate.</td>
  </tr>
  <tr>
    <td>Estetica dell’interfaccia utente</td>
    <td>Il software implementata una TUI <em>(Text-based user interfaces)</em> rappresentando le case della scacchiera attraverso ANSI escape code e i pezzi attraverso caratteri Unicode. La gradevolezza dell’uso dell’interfaccia utente è garantita da uno profondo studio dei colori su diverse tipologie di terminale aventi diversi temi.</td>
  </tr>
  <tr>
    <td rowspan="3">Manutenibilità</td>
    <td>Modularità</td>
    <td>Il sotware è creato utilizzando le best practices del linguaggio OO Java ed è suddiviso in package e classi in modo che le modifiche abbiano un minimo impatto sulle altre componenti.</td>
  </tr>
  <tr>
    <td>Riusabilità</td>
    <td>Il software è progettato in modo da poter essere utilizzato per l'implementazione di altri giochi simili (es. Dama) apportando lievi modifiche.</td>
  </tr>
  <tr>
    <td>Testabilità</td>
    <td>Il software è testato attraverso il framework JUnit, tutto documentato attraverso Coveralls e il toolkit JaCoCo, che garantiscono la totale copertura. Alcune classi sono provviste del proprio <code>main</code> per poter essere eseguite e testate autonomamente (es. ParserPGN.java ricevendo in input una partita in formato PGN)</td>
  </tr>
  <tr>
    <td rowspan="4">Portabilità</td>
    <td>Adattabilità</td>
    <td>La larghezza dello schermo prevede un minimo di 80 caratteri.</td>
  </tr>
  <tr>
    <td>Installabilità</td>
    <td> L'unico software da installare per far funzionare l'applicazione è Docker che consente di creare ed eseguire container su Linux, Windows e macOS.</td>
  </tr>
</tbody>
</table>  
<a href="#top">Torna all'inizio</a>

# <span id = "4">4. System Design</span> 
Questa sezione si occupa dell'identificazione dei principali componenti e delle relazioni tra questi, definendo un 
modello compatto del modo in cui il sistema è strutturato. 

## <span id = "4.1">4.1 Stile architetturale</span>
Questo software e la sua interfaccia utente possono essere interpretati come un **MVC** 
*(Model-View-Controller)*, un modello architettonico che isola l'amministrazione del gioco (business logic - Model), 
dalla presentazione dei dati (View).
![](res/img/MVC.svg)
>_Diagramma del funzionamento di un'architettura MVC._

<br><br>
<p>Il <b>View</b> si occupa dell'interazione con utenti e agenti, e mostra i dati contenuti nel Model come la notazione 
del gioco, la lista delle mosse, la lista con i simboli dei pezzi catturati, i menu e la scacchiera.
<p>Il <b>Controller</b> riceve i comandi da due fonti:

- dalla tastiera (attraverso il View)
- da un file in formato PGN  

e avvia una risposta effettuando chiamate sugli oggetti del Model e modificando lo stato degli altri due componenti.
<p>Il <b>Model</b> fornisce i metodi per accedere ai dati del dominio su cui opera l'applicazione, come le informazioni 
sulla posizione iniziale e le posizioni correnti dei pezzi sulla scacchiera.
<p>Il Model e il Controller implementano un 
automa a stati finiti che controlla il gioco, i suoi stati, le transizioni di stato e le azioni.


![](res/img/ChessGameGraph2.svg)
>_Automa a stati finiti per il gioco degli scacchi._

<br>

In una partita di scacchi i due giocatori muovono alternativamente, il giocatore che ha i pezzi di colore Bianco esegue 
la prima mossa. Il gioco può terminare, sia se il Bianco ha il tratto sia che lo abbia il Nero, in due occasioni:
 1. **vincendo** se si pone il Re avversario ‘sotto attacco’
 2. **pareggiando** se la posizione è tale che nessuno dei due giocatori possa in alcun modo dare scaccomatto al Re 
    avversario
 
## <span id = "4.2">4.2 Rappresentazione dell’architettura</span> 
![](res/img/unibaPackageDiagram3.svg)
>_Diagramma dei package di uniba._

<br>
Il <code>Package main</code>, che si occupa di avviare una partita, è messo in relazione con il 
<code>Package gameController</code> per il suo compito di iniziare e contestualizzare il gioco, e verificare l'input 
ricevuto dall'utente. 
<p>Questo è connesso con il <code>Package gameElements</code> per permettere di istanziare gli elementi di una partita, 
come i pezzi e la scacchiera. 
<p>La dipendenza diretta con il <code>Package subTypeMenu</code> è data dalla possibilità che specifici comandi siano o 
meno validi in un dato contesto di gioco.

<p><a href="#top">Torna all'inizio</a>

# <span id = "5">5. OO Design</span>  
Questa sezione specifica attraverso riferimenti diretti alle <em>user story</em> più significative, che identificano le 
decisioni di progetto, in che modo è stato applicato l’OO Design <em>(Object Oriented Design)</em> all’interno
del software.

## <span id = "5.1">5.1 Information hiding</span>        
Per il principio di *information hiding* ogni componente deve custodire dei segreti al proprio interno per
salvaguardare la propria integrità e correttezza. 
<p>Questo è permesso grazie a classi composte generalmente da attributi e metodi privati che favoriscono l'incapsulamento 
dei dati).
<p>Solo i metodi strettamente necessari all’interazione con altre classi sono pubblici, nonché i metodi
<code>get</code> e <code>set</code>, per un accesso sicuro e salvaguardato agli attributi della classe dall’esterno.
<br>
<br>

![BasePiece](res/img/BasePiece.png)
>_Diagramma di classe di <code>BasePiece</code>._

<br>
La classe <code>BasePiece</code> ne è un esempio poichè:

 - i metodi necessari per l'interazione con le altre classi sono pubblici, come quelli <code>possibleMovePiece</code>che 
 restituisce alla classe <code>Match</code> la disponibilità della mossa a seconda del contesto di gioco;
 
 - gli attributi sono privati a cui sono annessi metodi <code>get</code> e <code>set</code> pubblici per l'accesso e 
 l'impostazione dei valori.

Con questo principio l'applicazione ci permette di isolare le modifiche dovute a scelte progettuali o
correzioni di bug, senza compromettere le classi esterne a quella in questione.

## <span id = "5.2">5.2 Alta coesione</span> 
Il concetto di coesione rappresenta il grado di dipendenza tra elementi di uno stesso componente.

Un componente ad alta coesione ha una responsabilità ben definita, che ne favorisce la:
 - riutilizzabilità;
 - manutenibilità;
 - leggibilità → coaudiuvata dall'utilizzo del tool <em>Checkstyle</em> per la verifica della conformità del codice alle 
 regole di codifica;
 - verificabilità → in quanto la correttezza del sistema è certificata dall'utilizzo del tool <em>SpotBugs</em>.
    
Il manifesto di questo principio si trova all’interno della classe <code>ParserPGN</code>, a cui è delegata la 
responsabilità di decifrare la tipologia di input (es: xa4 <code>isCapture</code>) a prescindere dal contesto di gioco. 
<br>
<br>

![Parser](res/img/parser_gameController.png)
>_Diagramma di classe per la decodifica di un input._

<br>
<p>Questo diagramma di classe mostra la relazione tra la classe <code>GameController</code> (che richiede l'input
alla classe <code>InputManager</code>) e la classe <code>ParserPGN</code> a cui verrà passato l'input ricevuto, e che 
tramite l'uso di <em>regular expression</em> ne restituirà la tipologia. 
<p>La scelta di avere un componente specifico per esaudire tale necessità, rende la decodifica dell’input più compatta e
ottimizzata. A questo si aggiunge una maggiore leggibilità ed estensibilità del codice.

## <span id = "5.3">5.3 Basso accoppiamento</span>  
L’accoppiamento misura il grado di dipendenza tra componenti diversi.
<p>Un basso accoppiamento fa si che un cambiamento ad un componente non si propaghi su altri
componenti.
<p>La scelta progettuale di delegare ai singoli tipi di pezzo il controllo sulle proprie mosse permette di avere per ogni 
pezzo una gestione ottimizzata delle mosse, rendendo la modifica, la manutenzione e la correzione indipendenti.
<br>
<br>

![KingMove](res/img/kingMove.png)
>_Diagramma di sequenza di <code>possibleMovePiece</code>._

<br>
<p>Nel diagramma di sequenza riportato è facile osservare come la delega del controllo della mossa al pezzo specifico 
(in questo caso <code>King</code>), fa sì che il pezzo possa gestirla in maniera consona alle proprie esigenze: 

 - il metodo <code>check</code> verifica che la casa di destinazione sia raggiungibile dal Re;
 
 - il metodo <code>isThreatened</code> verifica che la mossa restituita non metta il Re sotto scacco; 
 
Solo dopo queste due verifiche il Re crea una <code>Move</code> che viene restituita alla classe <code>Match</code>.
<p>In questo senso le classi ignorano azioni e/o controlli che riguardino altre, ad esempio la gestione delle ambiguità
che nel pezzo <code>King</code> non avviene mai.

## <span id = "5.4">5.4 Don't Repeat Yourself (DRY)</span> 
Il principio DRY <em>(Don't Repeat Yourself)</em> prevede che ogni parte significativa di una funzionalità dovrebbe 
essere implementata in un unico posto del codice sorgente, evitando sequenze di istruzioni uguali fra loro.
<p>Una rappresentazione di questo impiego si può osservare attraverso il diagramma di sequenza che descrive
l’utilizzo del metodo <code>kingThreatened</code> all’interno della <em>user story</em> del movimento di
un qualsiasi pezzo.
<br>
<br>

![kingThreatened](res/img/kingThreatened.png)
>_Diagramma di sequenza di <code>kingThreatened</code>._

<br>
<p>Il metodo <code>kingThreatened</code> effettua l’ultimo controllo prima che un pezzo possa effettuare una mossa 
verificando che quest’ultima non lasci sotto scacco il proprio Re. Questo controllo viene effettuato in modo identico a 
prescindere da quale sia il tipo del pezzo che effettui la mossa. Di conseguenza, stando al principio <b>DRY</b>, il 
metodo non viene clonato, andando potenzialmente incontro ad errori di <em>copy and paste</em>, bensì è scritto una sola 
volta, rendendo il codice più leggibile, snello e facilmente manutenibile.

<a href="#top">Torna all'inizio</a>
 
# <span id = "6">6. Riepilogo del test</span> 
Questa sezione espone i risultati e le modalità con cui è stato testato il software.
## <span id = "6.1">6.1 Analisi statica del codice</span>

L'analisi statica del codice è l'analisi del software che viene eseguita senza l'esecuzione del programma. 
In questo caso l'analisi viene eseguita da strumenti automatizzati.

### <span id = "6.1.1">6.1.1 Checkstyle</span>
Il tool di *Checkstyle* ha permesso di scoprire e correggere violazioni dello stile di programmazione. 
<br><br>
Tutte le violazioni sono state risolte sia nelle classi del <code>main</code> <br><br>

![](res/img/CheckstyleMain2.png)  
>_Risultato di Checkstyle del main._
  
<br>

Sia nelle classi del <code>test</code> <br><br>

![](res/img/checkstyeTest.png)  
>_Risultato di Checkstyle del test._

### <span id = "6.1.2">6.1.2 SpotBugs</span>

Il tool di *SpotBugs* ha permesso di scoprire e correggere difetti del codice noti. 
<br><br>
Tutti i difetti sono stati risolti sia nelle classi del <code>main</code> <br>

![](res/img/SpotBugsMain.svg)    
>_Risultato di SpotBugs del main._

<br>
Sia nelle classi del <code>test</code> <br><br>

![](res/img/spotbugsTest.png)    
>_Risultato di SpotBugs del test._


## <span id = "6.2">6.2 Analisi dinamica del codice</span>
L'analisi dinamica del codice è il processo di valutazione di un software basato sull’osservazione del suo comportamento 
in esecuzione. 
Il criterio principale per la selezione dei casi di test è stato il *whitebox*, ovvero quando i casi di test sono 
selezionati conoscendo la struttura interna del software, in questo caso riferendoci a criteri basati sulla percentuale 
di copertura delle istruzioni.<br><br>
Lo scopo dei test è stato quello di verificare se il risultato fosse conforme a quello atteso.

### <span id = "6.2.1">6.2.1 Test di unità</span>
Nei programmi OO le unità sono il risultato di un lavoro individuale sulle singole classi, isolato dal resto del sistema.
In questo software è stato utilizzato il framework di *unit testing* *JUnit*.<br><br>
Per la terminazione dei test è stato seguito un **criterio di copertura** pari al 100% delle linee di codice, visibile
dal servizio web di *Coveralls* che ha consentito di tenere traccia della copertura del codice nel tempo e di garantire che 
tutto il nuovo codice fosse completamente coperto.<br><br>

![](res/img/coverage.png)
>_Pagina web di [Coveralls](https://coveralls.io/github/softeng1920-inf-uniba/progetto1920-thacker) del gruppo._

### <span id = "6.2.2">6.2.2 Test funzionale</span>   
I requisiti funzionali del sistema sono stati collaudati in modo dinamico all'avanzamento del progetto grazie alla 
possibilità di avviare l'applicazione in modalità batch se una o più partite in formato PGN sono presenti come argomenti 
del programma:

    java -cp build/classes/java/main  it.uniba.main.AppMain filename.pgn


![](res/img/AppMainPGNOutput.svg)
>_Output dopo l'avvio dell'applicazione in modalità batch passando il file game-1.pgn._

<br>

Nella classe <code>ParserPGN</code> sono presenti dei metodi di pulizia e traduzione del file PGN, essendo più comune 
reperire in internet partite in notazione algebrica inglese e corredate di data, risultato e vari commenti.

![](res/img/GamePGN.svg)
>_Esempio di partita in formato PGN._

<br>
<a href="#top">Torna all'inizio</a> 


# <span id = "7">7. Manuale utente</span> 
Questa sezione è destinata all'utilizzatore finale del prodotto, che si presuppone privo di competenze tecniche specifiche. 
## <span id = "7.1">7.1 Docker</span>
*Docker* è un'applicazione per computer MacOS, Linux e Windows per la creazione e la condivisione di applicazioni 
containerizzate, unico strumento da installare per utilizzare dell'applicazione.
### <span id = "7.1.1">7.1.1 Requisiti di sistema</span>
I requisiti di sistema di un software sono l'insieme di hardwar e sistema operativo indicati dall'editore del software stesso:

Componente|Caratteristiche minime|Caratteristiche consigliate
---|---|---|
OS| Windows 10 64-bit: [Home](https://www.microsoft.com/it-it/p/windows-10-home/d76qx4bznwk4?activetab=pivot%3aoverviewtab "Acquista Windows 10 Home"), macOS, Linux|Windows 10 64-bit: [Pro](https://www.microsoft.com/it-it/p/windows-10-pro/df77x4d43rkt?activetab=pivot%3aoverviewtab "Acquista Windows 10 Pro")/Enterprise/Education, macOS, Linux   
RAM| 1 GB |2 GB (4 GB per Hyper-V, **solo** per Windows)
HDD| 1 GB |3 GB
> _Requisiti minimi di Docker._

### <span id = "7.1.2">7.1.2 Installazione Docker</span>
L'installazione prevede la copia sulla macchina di Docker, scaricabile da 
[qui](https://docs.docker.com/get-docker/ "Scarica Docker").

![](res/img/homedocker.PNG)
>_Pagina web per download di Docker._

### <span id = "7.1.3">7.1.3 Esecuzione immagine Docker</span> 
Installata l'applicazione, si svolgano le seguenti operazioni:

- avviare Docker localmente;
- se si utilizza Windows, selezionare `Switch to Linux containers` nel menu di Docker;
- incollare ed eseguire nel terminale il comando: 

      docker pull docker.pkg.github.com/softeng1920-inf-uniba/docker_1920/thacker:latest` 
      
- digitare infine il comando: 

      docker run -it --rm docker.pkg.github.com/softeng1920-inf-uniba/docker_1920/thacker:latest

## <span id = "7.2">7.2 Pezzi e movimenti sulla scacchiera</span>

### <span id = "7.2.1">7.2.1 Notazione algebrica</span>
Il movimento dei pezzi in questa applicazione si basa sull'utilizzo della notazione algebrica, 
come da regolamento [FIDE](http://www.federscacchi.it/doc/reg/d20140623055252_fide.pdf),
per la registrazione e la descrizione delle mosse e delle partite dei giocatori.
Ogni pezzo (tranne il pedone) è identificato da una lettera
maiuscola, solitamente la prima lettera del nome del pezzo:
in italiano, quindi, il Re è indicato con la R, la Donna con la D,
la Torre con la T, l'Alfiere con la A e il Cavallo con la C.


### <span id = "7.2.2">7.2.2 Pedone</span>
- Il pedone si può muovere sulla casa ad esso immediatamente successiva sulla stessa colonna, 
a condizione che detta casa non sia occupata;

- alla sua prima mossa, il pedone si può muovere come in 7.2.2.a oppure, 
in alternativa, il pedone può avanzare di due case lungo la stessa colonna, 
a condizione che dette case non siano occupate;
- il pedone si può muovere su una casa posta diagonalmente di fronte ad esso
su una colonna adiacente, occupata da un pezzo avversario, catturando il pezzo;
- un pedone che occupi la casa nella stessa traversa e sulla colonna adiacente di un pedone avversario
il quale sia appena stato avanzato di due case dalla sua casa d’origine, 
può catturare il pedone avversario come se quest’ultimo fosse stato 
avanzato di una sola casa. **N.B.:** Questa cattura è legale solo nella mossa immediatamente successiva 
al suddetto avanzamento ed è detta cattura ‘enpassant’ (‘al varco’);
- quando un giocatore, avendo il tratto, avanza un pedone alla traversa più lontana 
dalla sua posizione iniziale, come parte integrante della stessa mossa deve scambiare quel pedone 
con una nuova Donna, Torre, Alfiere o Cavallo dello stesso colore, 
ponendolo sulla prevista casa di destinazione. 
Quest’ultima è detta ‘casa di promozione’. 
La scelta del giocatore non è limitata ai pezzi catturati in precedenza.
Lo scambio del pedone con un altro pezzo è detto promozione, e l’effetto del nuovo pezzo è immediato.

Azione|Notazione algebrica
---|---
"Muovi pedone e2 in e4"|`e4`

![](res/img/PawnMoveandCapture.svg)
> _7.2.2.a Movimento possibile del pedone._

<br>

![](res/img/PawnEnPassant.svg)
>_Cattura 'en passant', disponibile **solo** con il pedone._

### <span id = "7.2.3">7.2.3 Torre</span>
La Torre si può muovere su una qualunque casa 
lungo la colonna o la traversa sulle quali si trova.

Azione|Notazione algebrica
---|---
"Muovi torre e5 in e8"|`Te8`

![](res/img/RockMoves.svg)
> _Movimenti possibili della torre._
<br>

### <span id = "7.2.4">7.2.4 Cavallo</span>
Il Cavallo si può muovere su ciascuna delle case più vicine a quella sulla quale si trova 
ma non poste sulla stessa colonna, traversa o diagonale.

Azione|Notazione algebrica
---|---
"Muovi cavallo d4 in b5"|`Cb5`

![](res/img/KnightMoves.svg)
> _Movimenti possibili del cavallo._
<br>

### <span id = "7.2.5">7.2.5 Alfiere</span>
L’Alfiere si può muovere su una qualunque casa 
lungo una diagonale su cui si trova.

Azione|Notazione algebrica
---|---
"Muovi alfiere e5 in g5"|`Ag5`

![](res/img/BishopMoves.svg)
> _Movimenti possibili dell'alfiere._
<br>

### <span id = "7.2.6">7.2.6 Donna</span>
La Donna si può muovere su una qualunque casa lungo la colonna, 
la traversa o una diagonale sulle quali si trova.

Azione|Notazione algebrica
---|---
"Muovi donna in f4"|`Df4`

![](res/img/QueenMoves.svg)
> _Movimenti possibili della donna._
<br>

### <span id = "7.2.7">7.2.7 Re</span>
Il re si può spostare in qualunque casa adiacente, purché sia libera.

Azione|Notazione algebrica
---|---
"Muovi re in d5"|`Rd5`

![](res/img/KingMoves.svg)
> _Movimenti possibili del Re._

#### Arrocco
Per utilizzare l’arrocco:
- il Re non dev’essere ancora stato **mosso**;

- la Torre non dev’essere ancora stata **mossa**;
- il Re non dev’essere **sotto scacco** nè nella casa di **partenza**,
nè in quella **d’arrivo**, nè, durante il movimento dell’arrocco,
può attraversare caselle nelle quali sarebbe **sotto scacco**;
- fra Torre e Re la traiettoria deve essere **sgombra** da pezzi amici o nemici.


Azione|Notazione algebrica
---|---
"Arrocco corto"|`O-O`
"Arrocco lungo"|`O-O-O`

![](res/img/Arrocchi.svg)
>_Arrocco lungo (fianco sx) e corto (fianco dx)._


### <span id = "7.2.8">7.2.8 Cattura</span>
Per effettuare una cattura, si inserisce una *x* tra l'iniziale del nome e la casa di destinazione.

Azione|Notazione algebrica
---|---
"Alfiere cattura in e5"|`Axe5`
"Cavallo colonna e cattura in f3"|`Cexf3`
"Pedone colonna d cattura in e5"|`dxe5`


## <span id = "7.3">7.3 Menu</span>
Ad ogni contesto di gioco è presentato un menu.

### <span id = "7.3.1">7.3.1 Menu principale</span>
All'avvio dell'applicazione è mostrato il Menu principale come segue:


![](res/img/menuprincipale.PNG)
> _Schermata Menu principale._

### <span id = "7.3.2">7.3.2 Menu di gioco</span>
Nel menu di gioco, si possono svolgere le seguenti operazioni:

![](res/img/menugioco.PNG)
> _Schermata Menu di gioco._


## <span id = "7.4">7.4 Comandi di gioco</span>
I comandi di gioco rappresentano le azioni esterne alla partita che si possono eseguire.
### <span id = "7.4.1">7.4.1 `help`</span>
Il comando `help` mostra i comandi possibili a seconda del contesto in cui si trova l'utente.

![](res/img/menuprincipale.PNG)
>_Comando `help` nel Menu principale._
<br>

<br>

![](res/img/menugioco.PNG)
>_Comando `help` nel Menu di gioco._


### <span id = "7.4.2">7.4.2 `play`</span>
Il comando `play` permette di iniziare una nuova partita se si è nel menu principale, oppure abbandonare e ne cominciarne
un'altra, se si è già in una partita, digitando il comando `play` e poi il comando `si`.

<br>
<br>

![](res/img/playmenugioco.PNG)
>_Interfaccia del comando `play` durante la partita._
<br>



### <span id = "7.4.3">7.4.3 `board`</span>
Il comando `board` mostra la scacchiera con la posizione attuale dei pezzi al giocatore che ha il tratto.
<br><br><br>
![](res/img/ScacchieraBlack.svg)
>_Esempio di scacchiera ad inizio partita._


### <span id = "7.4.4">7.4.4 `moves`</span>
Il comando `moves` mostra le mosse effettuate da entrambi i giocatori fino a quel momento.

![](res/img/listamoves.PNG)
>_Comando `moves` con lista delle mosse piena._

<br>

Se nessuno dei due giocatori ha effettuato una mossa, verrà visualizzato un messaggio di errore
e verrà richiesto all'utente di inserire un comando valido.

![](res/img/movesnomove.PNG)
>_Comando `moves` con lista delle mosse vuota._

### <span id = "7.4.5">7.4.5 `captures`</span>
Il comando `captures` mostra le catture effettuate fino a quel momento con i simboli dei pezzi catturati divisi per 
giocatore.

![](res/img/listacaptures.PNG)
>_Comando `captures` con lista delle catture piena._

<br>


Se nessuno dei due giocatori ha catturato un pezzo, verrà visualizzato un messaggio di errore 
e verrà richiesto all'utente di inserire un comando valido.

![](res/img/capturesnocapture.PNG)
>_Comando `captures` con lista delle catture vuota._


### <span id = "7.4.6">7.4.6 `quit`</span>
Il comando `quit` esce dal gioco, se si è convinti di voler abbandonare la partita, digitare il comando `si`, altrimenti
`no` per annullare.

Il comando funziona allo stesso modo, indipendentemente dal contesto di gioco in cui l'utente si trova.

![](res/img/quitmenuprincipale.PNG)
>_Comando `quit` nel Menu principale._

<br>


![](res/img/quitmenugioco.PNG)
>_Comando `quit` nel Menu di gioco._  

<br>
<a href="#top">Torna all'inizio</a> 

  
# <span id = "8">8. Processo di sviluppo e organizzazione del lavoro</span>
Questa sezione descrive i metodi e la dinamica per lo sviluppo del software.
## <span id = "8.1">8.1 Manifesto agile</span>
Per la realizzazione del prodotto il gruppo ha lavorato seguendo i 12 principi 
del [manifesto agile](https://agilemanifesto.org/iso/it/manifesto.html "Versione italiana del manifesto Agile"):

1. il team si è impegnato a soddisfare il cliente rilasciando fin da subito software di valore;

2. i requisiti iniziali sono cambiati in corso d'opera; ciononostante, il team ha reagito senza problemi 
all'implementazione di tali requisiti per garantire al cliente un prodotto adeguato alle sue richieste, specificate in ogni _sprint review_;
3. il software è stato consegnato funzionante e puntuale ad ogni _sprint_;
4. il committente si è confrontato con gli sviluppatori nelle _sprint review_ collettive;
5. al team di lavoro hanno partecipato individui motivati e affidabili, che hanno lavorato giorno per giorno;
6. vista la situazione di pandemia globale, il gruppo si è confrontato "faccia a faccia" (per quanto fosse stato possibile farlo)
tramite l'hub di collaborazione 
[Microsoft Teams](https://www.microsoft.com/it-it/microsoft-365/microsoft-teams/group-chat-software?&ef_id=CjwKCAjwtqj2BRBYEiwAqfzur0-16AYE21Zo35HZJYxTFy1__i_I2fgJjivVgf8EXDfD9K-1gHHbrRoCUIkQAvD_BwE:G:s&OCID=AID2001446_SEM_CjwKCAjwtqj2BRBYEiwAqfzur0-16AYE21Zo35HZJYxTFy1__i_I2fgJjivVgf8EXDfD9K-1gHHbrRoCUIkQAvD_BwE:G:s)
per le comunicazioni con il team ed all'interno del team;
7. il progresso del team viene misurato in base al funzionamento del software;
8. gli sponsor, gli sviluppatori e gli utenti hanno lavorato mantenendo un ritmo costante e sostenibile
per tutta la durata del progetto, consegnando sempre un prodotto di qualità.
9. la continua attenzione all'eccellenza tecnica e alla buona progettazione esaltano l'agilità del team;
10. la semplicità - l'arte di massimizzare la quantità di lavoro non svolto - è essenziale; 
11. le architetture, i requisiti e la progettazione migliori emergono da team che si auto-organizzano;
12. il team ha riflettuto regolarmente su come diventare più efficace, 
adattando il proprio comportamento di conseguenza.

## <span id = "8.2">8.2 Scrum</span>

![](res/img/scrum.PNG)
>_Modello di un tipico progetto Scrum._


L'applicazione per il gioco degli Scacchi nasce dal progetto **Scrum** del gruppo Thacker, poiché il software 
è stato realizzato in una serie di quattro iterazioni (_sprint_), 
ciascuna con un diverso obiettivo (_sprint goal_)
ma con la stessa durata (2 settimane cadauna).

### <span id = "8.2.1">8.2.1 Product backlog</span>

![](res/img/productbacklog.PNG)
>_Parte della product backlog del gruppo Thacker._ 

La product backlog di questo gruppo è stata modificata dinamicamente,
sprint dopo sprint, con l'aggiunta di **user stories** in base alle richieste del committente.
Le _user story_ sono un tipo di oggetto limite che facilitano la sensibilizzazione 
e la comunicazione: aiutano, quindi, l'organizzazione dei team,
la loro comprensione del sistema e del suo contesto.

**N.B.:** non tutte le _user story_ della product backlog 
sono state implementate, dando la priorità a quelle previste per gli _sprint goal_.

### <span id = "8.2.2">8.2.2 Sprint goals</span>
Sprint|Goal
---|---
Sprint 0|Dimostrare familiarità con GitHub e il processo agile
Sprint 1|Apertura partita con soli pedoni
Sprint 2|Mediogioco
Sprint 3|Comunicare la qualità del lavoro svolto

## <span id = "8.3">8.3 Strumenti di lavoro</span>
Questo gruppo ha utilizzato principalmente l'hub di collaborazione [Microsoft Teams](https://www.microsoft.com/it-it/microsoft-365/microsoft-teams/group-chat-software?&ef_id=CjwKCAjwtqj2BRBYEiwAqfzur0-16AYE21Zo35HZJYxTFy1__i_I2fgJjivVgf8EXDfD9K-1gHHbrRoCUIkQAvD_BwE:G:s&OCID=AID2001446_SEM_CjwKCAjwtqj2BRBYEiwAqfzur0-16AYE21Zo35HZJYxTFy1__i_I2fgJjivVgf8EXDfD9K-1gHHbrRoCUIkQAvD_BwE:G:s)
e la piattaforma di sviluppo software [GitHub](https://github.com/) 
per condividere il lavoro tra gli sviluppatori del prodotto.
GitHub, infatti, è stato utilizzato per il Version Control per gestire il controllo del lavoro da remoto,
adattandosi dunque alle restrizioni dovute all'emergenza CoVid-19;
di notevole importanza, inoltre, è stato il tool di [GitHub Actions](https://github.com/marketplace?type=actions), 
il cui compito è stato quello di automatizzare il workflow development relativo al prodotto da realizzare.
Tutti i membri del gruppo hanno utilizzato di comune accordo l'ambiente di sviluppo 
[IntelliJ IDEA](https://www.jetbrains.com/idea/).


![](res/img/githubactions.PNG)
>_Esempio di workflow automatizzato con GitHub Actions._

<a href="#top">Torna all'inizio</a> 

# <span id = "9">9. Analisi retrospettiva</span>  
Questa sezione illustra brevemente gli aspetti che hanno accompagnato lo sviluppo del software.    
## <span id = "9.1">9.1 Soddisfazioni</span> 
Ciò che ha permesso al gruppo di lavorare in modo costruttivo è stata l'intesa sull'obiettivo da raggiungere, 
nonostante sia stato formato indirettamente e tra soggetti estranei fra loro. 
<p>In particolar modo è stata gradita la modalità d'esame che attraverso la distribuzione del lavoro e la sua revisione 
ha incoraggiato e motivato il singolo e il collettivo a migliorarsi sempre, dalle relazioni umane alla qualità del 
prodotto. 
<p>L'apprezzamento dello sforzo per fornire un'estetica gradevole alla scacchiera è stato gratificante ed ha rafforzato 
l'unione e la stima all'interno del gruppo. La ricerca di un background che rendesse uniforme la visualizzazione su 
terminali diversi con temi diversi non è stata facile ed ha impegnato più componenti nell'impresa di provare ogni 
soluzione sui diversi dispositivi e sistemi operativi a disposizione.
<p>È stato soprattutto interessante imparare ad usare strumenti di <em>version control</em> come Git e servizi per la gestione 
dello sviluppo di software di tipo collaborativo come Github che torneranno utili nell'immediato futuro.
<p>Mentre questo difficile periodo a causa dell'emergenza COVID-19 ha sicuramente isolato lo studente dalla realtà 
universitaria, grazie a questo progetto e alla materia (che lo ha permesso), l'esperienza di sviluppo agile ha creato 
nuovi rapporti e amicizie, che probabilmente non sarebbero nate, e ha imposto agli studenti un rapporto 
quotidiano il quale, tra armonia e conflitti, è riuscito ad impegnare positivamente e proficuamente le giornate.

## <span id = "9.2">9.2 Insoddisfazioni</span> 
La curiosità ha spinto il gruppo a chiedersi di più su quali fossero le funzionalità e l'utilizzo di Docker che sarebbe 
stato interessante scoprire al fianco di professionisti nel campo durante il corso.
<p>È stato un dispiacere anche non aver potuto concludere il Product Backlog per questioni di tempistica, ma il 
collettivo rimane motivato a farlo al di fuori del contesto d'esame.
<br>
<br>
<br>
<br>
<br>
<br>
<em>Grazie per l'attenzione e l'opportunità,  
<br>a nome del gruppo Thacker.</em>
<br>
<br>
<a href="#top">Torna all'inizio</a> 

