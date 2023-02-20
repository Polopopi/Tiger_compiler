package tds;

import ast.Ast;

public class LaterVerifFunc extends LaterVerif{
    private FunctionEntry functionEntry;
    private String typeId;
    private Ast bloc;
    private Tds tds;
    
    public LaterVerifFunc(FunctionEntry functionEntry, String typeId, Ast bloc, Tds tds){
        this.functionEntry = functionEntry;
        this.typeId = typeId;
        this.bloc = bloc;
        this.tds = tds;
    }

    public void check(TdsCreator creator){
    
        creator.setTds(tds);
        String computeType = bloc.accept(creator);
        if (computeType != null && !typeId.equals(computeType)){
            if (computeType.equals("")){
                System.out.println("Erreur ligne "+ bloc.lineNumber +" : aucun type n'est retourné, " + typeId + " était attendu pour la fonction " + functionEntry.getSymbol());
            }
            else if (typeId.equals("")){
                System.out.println("Erreur ligne "+ bloc.lineNumber +" : retour de type " + computeType + ", aucun type n'était attendu pour la fonction " + functionEntry.getSymbol());
            }
            else{
                System.out.println("Erreur ligne "+ bloc.lineNumber +" : retour de type " + computeType + ", " + typeId + " était attendu pour la fonction " + functionEntry.getSymbol());
            }
        }
        //System.out.println(computeType);
        //System.out.println(typeId);
        
        creator.setTds(tds.getParent());
      

    }
}