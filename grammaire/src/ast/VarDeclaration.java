package ast;

public class VarDeclaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    public Ast idf;
    public Ast type;
    public Ast expr;
    public VarDeclaration(Ast idf,Ast type,Ast expr){
        this.idf=idf;
        this.type=type;
        this.expr=expr;
    }
}
