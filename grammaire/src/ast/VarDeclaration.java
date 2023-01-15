package ast;

public class VarDeclaration extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast idf;
    public Ast expr;

    public VarDeclaration(int lineNumber, Ast idf,Ast expr){
        super(lineNumber);
        this.idf=idf;
        this.expr=expr;
    }
}
