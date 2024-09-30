package token;

/**
 * Questo package contiene l'enumerazione TokenType, che definisce i diversi tipi di token
 * utilizzati all'interno del linguaggio.
 * 
 * TokenType include i seguenti tipi di token:
 * - FLOAT: token per rappresentare numeri in virgola mobile.
 * - INT: token per rappresentare numeri interi.
 * - ID: token per rappresentare identificatori.
 * - TYINT: token per rappresentare il tipo intero.
 * - TYFLOAT: token per rappresentare il tipo in virgola mobile.
 * - PRINT: token per rappresentare l'istruzione di stampa.
 * - OP_ASSIGN: token per rappresentare l'operatore di assegnazione.
 * - PLUS: token per rappresentare l'operatore di addizione.
 * - MINUS: token per rappresentare l'operatore di sottrazione.
 * - TIMES: token per rappresentare l'operatore di moltiplicazione.
 * - DIVIDE: token per rappresentare l'operatore di divisione.
 * - SEMI: token per rappresentare il punto e virgola.
 * - EOF: token per rappresentare la fine del file.
 * 
 * Questi tipi di token vengono utilizzati per identificare e categorizzare gli elementi
 * del codice sorgente durante la fase di analisi lessicale.
 */




public enum TokenType {
    FLOAT,
    INT,
    ID,
    TYINT,
    TYFLOAT,
    PRINT,
    OP_ASSIGN,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    SEMI,
    EOF
}
