package SymbolTable;

/**
 * Questa classe gestisce i registri disponibili per l'assegnazione agli identificatori nella Symbol Table.
 */


import java.util.ArrayList;

class Registri {
	
    private static ArrayList<Character> registriDisponibili = new ArrayList<>();

    static {
        // Aggiungi i caratteri dei registri disponibili
        for (char c = 'A'; c <= 'Z'; c++) {
            registriDisponibili.add(c);
        }
    }

    static char newRegister() {
        if (registriDisponibili.isEmpty()) {
            throw new RuntimeException("Errore: Non ci sono registri disponibili.");
        }
        // Rimuovi un carattere dall'ArrayList e ritornalo
        return registriDisponibili.remove(0);
    }
}