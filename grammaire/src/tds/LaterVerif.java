package tds;

import ast.Ast;

public class LaterVerif {
    private String typeId;
    private Ast bloc;
    
    public LaterVerif(String typeId, Ast bloc){
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

// type a := {x:b}
// type b := {x:a}