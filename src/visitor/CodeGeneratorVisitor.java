package visitor;

/**
 * Questo package contiene la classe CodeGeneratorVisitor, che implementa un visitor per generare codice
 * a partire dall'AST (Abstract Syntax Tree) del linguaggio specifico.
 * 
 * CodeGeneratorVisitor implementa l'interfaccia IVisitor e visita i nodi dell'AST per generare il codice
 * corrispondente. Il codice generato viene costruito utilizzando una stringa StringBuilder.
 * 
 * La classe offre i seguenti metodi:
 * - Un costruttore che inizializza il generatore di codice e il logger per la registrazione degli errori.
 * - Il metodo getGeneratedCode() per ottenere il codice generato sotto forma di stringa.
 * - Metodi di visita per i diversi tipi di nodi presenti nell'AST, come la visita dei nodi di assegnamento,
 *   delle espressioni binarie, dei costanti, delle istruzioni di stampa, etc.
 * 
 * CodeGeneratorVisitor sfrutta la SymbolTable per gestire gli identificatori e le loro definizioni durante
 * la generazione del codice.
 * 
 * Questo visitor è fondamentale per tradurre l'astrazione del codice sorgente in codice eseguibile,
 * fornendo una componente chiave per il processo di compilazione o interpretazione del linguaggio.
 */



import AST.*;
import SymbolTable.Attributes;
import SymbolTable.SymbolTable;

import java.util.logging.Logger;

public class CodeGeneratorVisitor implements IVisitor {
    private StringBuilder codiceDc;
    private Logger log;

    public CodeGeneratorVisitor() {
        this.codiceDc = new StringBuilder();
        this.log = Logger.getLogger(CodeGeneratorVisitor.class.getName());
    }

    public String getGeneratedCode() {
        return codiceDc.toString();
    }

    @Override
    public void visit(NodeProgram node) {
        for (NodeDecSt nodeDecSt : node.getDecSts()) {
            nodeDecSt.accept(this);
        }
    }

    @Override
    public void visit(NodeId node) {
        codiceDc.append(node.getDefinition().getRegistro());
    }

    @Override
    public void visit(NodeDecl node) {
        NodeId id = node.getId();
        Attributes attributes = id.getDefinition();

        if (SymbolTable.lookup(id.getName()) != null) {
            // Errore: L'identificatore è già definito
            log.warning("L'identificatore '" + id.getName() + "' è già definito.");
            return;
        }

        SymbolTable.enter(id.getName(), attributes);

        if (node.getInit() != null) {
            node.getInit().accept(this);
            codiceDc.append("store ").append(id.getDefinition().getRegistro()).append(";");
            codiceDc.append("precision 0;");
        }
    }

    @Override
    public void visit(NodeAssing node) {
        NodeId id = node.getId();
        NodeExpr expr = node.getExpr();

        id.accept(this);
        expr.accept(this);

        codiceDc.append("store ").append(id.getDefinition().getRegistro()).append(";");
        codiceDc.append("precision 0;");
    }

    @Override
    public void visit(NodeBinOp node) {
        NodeExpr left = node.getLeft();
        NodeExpr right = node.getRight();

        left.accept(this);
        right.accept(this);

        codiceDc.append("precision 5;"); // Imposta la precisione a 5 cifre decimali per le operazioni

        switch (node.getOp()) {
            case PLUS:
                codiceDc.append("add;");
                break;
            case MINUS:
                codiceDc.append("sub;");
                break;
            case TIMES:
                codiceDc.append("mul;");
                break;
            case DIV:
                codiceDc.append("div;");
                break;
            default:
                log.warning("Operatore non supportato.");
        }
    }

    @Override
    public void visit(NodeCost node) {
        if (node.getType() == LangType.TYFLOAT) {
            codiceDc.append(node.getValue()).append(" k;");
        } else {
            codiceDc.append(node.getValue());
        }
    }

    @Override
    public void visit(NodePrint node) {
        NodeId id = node.getId();
        id.accept(this);

        codiceDc.append("la p P;"); // Carica sullo stack il registro associato all'identificatore
        codiceDc.append("precision 5;"); // Imposta la precisione a 5 cifre decimali
        codiceDc.append("print;");
        codiceDc.append("pop;");
    }

    @Override
    public void visit(NodeDeref node) {
        NodeId id = node.getId();
        id.accept(this);

        codiceDc.append("la p P;"); // Carica sullo stack il registro associato all'identificatore
    }

    @Override
    public void visit(NodeConvert node) {
        NodeExpr expr = node.getExpression();
        expr.accept(this);

        codiceDc.append("toF;");
    }
}
