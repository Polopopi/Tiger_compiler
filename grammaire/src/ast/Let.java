package ast;

public class Let implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return(visitor.visit(this));
    }

    public Ast declarationList;
    public Ast seqExpr;

    public Let(Ast declarationList, Ast seqExpr){
        this.declarationList = declarationList;
        this.seqExpr = seqExpr;
    }
}
