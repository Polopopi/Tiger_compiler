package tds;

import java.util.ArrayList;

public class Tds {
    private int numeroImbrication;
    private int identifiant;
    private int identifiantTablePrecedente;
    private static int idGen = 0;

    private ArrayList<VarFuncEntry> varFuncEntries;
    private ArrayList<TypeEntry> typeEntries;

    public Tds(int numeroImbrication, int idPrec){
        this.numeroImbrication = numeroImbrication;
        this.identifiantTablePrecedente = idPrec;
        this.identifiant = idGen;
        idGen ++;
    }

    public void addVarFunc(VarFuncEntry entry){
        varFuncEntries.add(entry);
    }
    public void addType(TypeEntry entry){
        typeEntries.add(entry);
    }

    public boolean existVarFunc(String symbol){
        for (VarFuncEntry entry : varFuncEntries){
            if (entry.getSymbol().equals(symbol)){
                return(true);
            }
        }
        return(false);
    }

    public boolean existType(String symbol){
        for(TypeEntry entry : typeEntries){
            if (entry.getSymbol().equals(symbol)){
                return(true);
            }
        }
        return(false);
    }

    public String typeOfVarFunc(String symbol){
        for (VarFuncEntry entry : varFuncEntries){
            if (entry.getSymbol().equals(symbol)){
                return(entry.getSymbol());
            }
        }
        return("");
    }

    public int getImbrication(){
        return numeroImbrication;
    }

    public int getId(){
        return identifiant;
    }

    public int getIdParent(){
        return identifiantTablePrecedente;
    }

    //public String typeOfFuncParam(String symbol)
}
