package tds;

import java.util.ArrayList;

import ast.Print;

public class Tds {
    private int numeroImbrication;
    private int identifiant;
    private int identifiantTablePrecedente;
    private static int idGen = 0;

    private ArrayList<VarFuncEntry> varFuncEntries;
    private ArrayList<TypeEntry> typeEntries;

    public Tds(int numeroImbrication, int idPrec){
        this.varFuncEntries = new ArrayList<VarFuncEntry>();
        this.typeEntries = new ArrayList<TypeEntry>();
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
        //V2RIF TABLES PR2C2DANTES
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

    //On ne peut pas remonter dans les TDS seulement avec idParent
    //Il faudrait un pointeur
    //Sinon le visiteur remonte tout seul
    public TypeEntry getTypeEntry(String type_id){
        for (TypeEntry entry : typeEntries){
            if (entry.getSymbol().equals(type_id)){
                return entry;
            }
        }
        return null;
    }

    public VarFuncEntry getVarFuncEntry(String varFunc_id){
        for (VarFuncEntry entry : varFuncEntries){
            if (entry.getSymbol().equals(varFunc_id)){
                return entry;
            }
        }
        return null;
    }

    //public String getTypeOfRecordField(String idf)
    //public String typeOfFuncParam(String symbol)
}
