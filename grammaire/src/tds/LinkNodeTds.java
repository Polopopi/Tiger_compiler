package tds;

import ast.Ast;

public class LinkNodeTds {
    private Tds tds;
    private Ast ast;

    public LinkNodeTds(Ast ast, Tds tds){
        this.ast = ast;
        this.tds = tds;
    }

    public Tds getTds() {
        return tds;
    }

    public Ast getAst() {
        return ast;
    }
    
}
