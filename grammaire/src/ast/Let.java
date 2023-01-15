package ast;

public class Let extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return(visitor.visit(this));
    }

    public Ast declarationList;
    public Ast seqExpr;

    public Let(int lineNumber, Ast declarationList, Ast seqExpr){
        super(lineNumber);
        this.declarationList = declarationList;
        this.seqExpr = seqExpr;
    }
}
