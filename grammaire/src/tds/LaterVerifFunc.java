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
        //System.out.println("tds : "+creator.getCuurentTds().getId());
        String computeType = bloc.accept(creator);
        //System.out.println(computeType);
        //System.out.println(typeId);
       
        if (!typeId.equals(computeType) ){
            System.out.println("Erreur : il faut retourner une valeur de type "+typeId+ "mais une valeur de type "+computeType+" est retournée");
        }
        creator.setTds(tds.getParent());
      

    }
}