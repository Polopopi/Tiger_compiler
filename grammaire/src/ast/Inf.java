package ast;

public class Inf implements Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;

    /* expr_plus < expr_plus */
    public Inf(Ast left, Ast right){
        this.left = left;
        this.right = right;
    }
}
