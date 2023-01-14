package tds;

import ast.Ast;

public class LaterVerifFunc implements LaterVerif{
    private String typeId;
    private Ast bloc;
    
    public LaterVerifFunc(String typeId, Ast bloc){
        this.typeId = typeId;
        this.bloc = bloc;
    }

    public void check(TdsCreator creator){
        String computeType = bloc.accept(creator);
        if (!typeId.equals(computeType)){
            System.out.println("Error Type");
        }
    }
}