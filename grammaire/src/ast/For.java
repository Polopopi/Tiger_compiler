package ast;


public class For extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public Ast id;
    public Ast debut;
    public Ast fin;
    public Ast bloc;

    public For(Ast id,Ast debut,Ast fin,Ast bloc){
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.bloc = bloc;
    }


}
