package ast;

public class Affect implements Ast{

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast idf;
    public Ast expr;
    
    public Affect(Ast idf, Ast expr){
        this.idf = idf;
        this.expr = expr;
    }

}
