package ast;

public class Minus implements Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;


    /* expr_mult - expr_mult */
    public Minus(Ast left, Ast right){
        this.left = left;
        this.right = right;
    }
}
