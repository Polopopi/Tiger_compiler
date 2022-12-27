package ast;

public class Print implements Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast value;

    public Print(Ast value){
        this.value = value;
    }

}
