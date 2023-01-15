package tds;

import ast.Ast;

public class LaterVerifFunc implements LaterVerif{
    private String typeId;
    private Ast bloc;
    private Tds tds;
    
    public LaterVerifFunc(String typeId, Ast bloc, Tds tds){
        this.typeId = typeId;
        this.bloc = bloc;
        this.tds = tds;
    }

    public void check(TdsCreator creator){
        creator.setTds(tds);
        String computeType = bloc.accept(creator);
        if (!typeId.equals(computeType)){
            System.out.println("Erreur : retour du type " + computeType + ", " + typeId + " était attendu");
        }
        creator.setTds(tds.getParent());
    }
}