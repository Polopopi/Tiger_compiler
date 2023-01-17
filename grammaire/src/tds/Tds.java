package tds;

import java.util.ArrayList;



public class Tds {
    private int numeroImbrication;
    private int identifiant;
    private Tds tablePrecedente;
    private static int idGen = 0;

    private ArrayList<VarFuncEntry> varFuncEntries;
    private ArrayList<TypeEntry> typeEntries;

    public Tds(int numeroImbrication, Tds tablePrecedente){
        this.varFuncEntries = new ArrayList<VarFuncEntry>();
        this.typeEntries = new ArrayList<TypeEntry>();
        this.numeroImbrication = numeroImbrication;
        this.tablePrecedente = tablePrecedente;
        this.identifiant = idGen;
        idGen ++;
    }

    public void addVarFunc(VarFuncEntry entry){
        //System.out.println(entry.getSymbol()+" ok");
        varFuncEntries.add(entry);
    }
    public void addType(TypeEntry entry){
        typeEntries.add(entry);
    }

    public boolean existVarFunc(String symbol){
        //V2RIF TABLES PR2C2DANTES
        Tds tds = this;
        while (tds != null){
            for (VarFuncEntry entry : tds.varFuncEntries){
                if (entry.getSymbol().equals(symbol)){
                    return(true);
                }
            }
            tds = tds.getParent();
        }
        return(false);
    }

    public boolean existLocalVarFunc(String id){
        for (VarFuncEntry entry : varFuncEntries){
            if (entry.getSymbol().equals(id)){
                return(true);
            }
        }
        return false;
    }

    public boolean existType(String symbol){
        if (symbol.equals("int") || symbol.equals("string")){
            return true;
        }

        Tds tds = this;
        while (tds != null){
            for(TypeEntry entry : tds.typeEntries){
                if (entry.getSymbol().equals(symbol)){
                    return(true);
                }
            }
            tds = tds.getParent();
        }
        return(false);
    }

    public boolean existLocalType(String id){
        if (id.equals("int") || id.equals("string")){
            return true;
        }

        for(TypeEntry entry : typeEntries){
            if (entry.getSymbol().equals(id)){
                return(true);
            }
        }
        return false;
    }

    public String typeOfVarFunc(String symbol){
        Tds tds = this;
        while (tds != null){
            for (VarFuncEntry entry : tds.varFuncEntries){
                if (entry.getSymbol().equals(symbol)){
                    return(entry.getSymbol());
                }
            }
            tds = tds.getParent();
        }
        return("");
    }

    public int getImbrication(){
        return numeroImbrication;
    }

    public int getId(){
        return identifiant;
    }

    public Tds getParent(){
        return tablePrecedente;
    }

    //On ne peut pas remonter dans les TDS seulement avec idParent
    //Il faudrait un pointeur
    //Sinon le visiteur remonte tout seul
    public TypeEntry getTypeEntry(String type_id){
        if (type_id == null){
            return null;
        }

        if (type_id.equals("int") || type_id.equals("string")){
            return new TypeEntry(type_id);
        }

        Tds tds = this;
        while (tds != null){
            for (TypeEntry entry : tds.typeEntries){
                if (entry.getSymbol().equals(type_id)){
                    if (entry.isAlias()){
                        TypeEntry aliasEntry = tds.getTypeEntry(((AliasEntry)entry).getParent());
                        return aliasEntry;
                    }
                    else{
                        return entry;
                    }
                }
            }
            tds = tds.getParent();
        }
        return null;
    }

    public VarFuncEntry getVarFuncEntry(String varFunc_id){
        Tds tds = this;
        while (tds != null){
            //System.out.println(tds.getId());
            for (VarFuncEntry entry : tds.varFuncEntries){
                
                if (entry.getSymbol().equals(varFunc_id)){
                    return entry;
                }
            }
            tds = tds.getParent();
        }
     
        return null;
    }


    public void removeTypeEntry(TypeEntry typeEntry){
        this.typeEntries.remove(typeEntry);
    }

    //public String getTypeOfRecordField(String idf)
    //public String typeOfFuncParam(String symbol)



    public void printTds(){
        for (VarFuncEntry varFuncEntry : varFuncEntries){
            varFuncEntry.print();
        }
        for (TypeEntry typeEntry : typeEntries){
            typeEntry.print();
        }
    }
}
