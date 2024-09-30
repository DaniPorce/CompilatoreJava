package SymbolTable;

/**
 * Questa classe rappresenta la Symbol Table, una struttura dati che mappa gli identificatori ai loro attributi.
 */


import java.util.HashMap;

public class SymbolTable {


    private static HashMap<String, Attributes> table;

    public static void init() {
        table = new HashMap<>();
    }

    public static boolean enter(String id, Attributes entry) {
        if (table.containsKey(id)) {
            return false; // Se il simbolo è già presente
        }
        table.put(id, entry);
        return true; // Inserimento riuscito
    }

    public static Attributes lookup(String id) {
        return table.get(id);
    }

    public static String toStr() {
        
        return table.toString();
    }

    public static int size() {
        return table.size();
    }
}

