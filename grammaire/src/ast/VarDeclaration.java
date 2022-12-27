package ast;

public class VarDeclaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast idf;
    public Ast expr;

    public VarDeclaration(Ast idf,Ast expr){
        this.idf=idf;
        this.expr=expr;
    }
}
