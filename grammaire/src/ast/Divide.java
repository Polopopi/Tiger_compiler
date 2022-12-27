package ast;

public class Divide implements Ast{

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;

    /* expr / expr */
    public Divide(Ast left, Ast right){
        this.left = left;
        this.right = right;
    }    
}
