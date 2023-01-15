package ast;

public class VarDeclarationType extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast idf;
    public Ast type;
    public Ast expr;

    public VarDeclarationType(int lineNumber, Ast idf,Ast type,Ast expr){
        super(lineNumber);
        this.idf=idf;
        this.type=type;
        this.expr=expr;
    }
}
