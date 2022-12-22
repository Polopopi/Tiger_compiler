package tds;

import java.util.ArrayList;

public class Tds {
    private int numeroImbrication;
    private int identifiant;
    private int identifiantTablePrecedente;
    private static int idGen = 0;

    private ArrayList<Entry> varFuncEntries;
    private ArrayList<Entry> typeEntries;

    public Tds(int numeroImbrication, int idPrec){
        this.numeroImbrication = numeroImbrication;
        this.identifiantTablePrecedente = idPrec;
        this.identifiant = idGen;
        idGen ++;
    }

    public void addVarFunc(Entry entry){
        varFuncEntries.add(entry);
    }
    public void addType(Entry entry){
        typeEntries.add(entry);
    }
}
