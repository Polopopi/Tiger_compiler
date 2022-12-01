package ast;

public class For implements Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
    
    public Idf id;
    public Ast debut;
    public Ast fin;
    public Ast bloc;

    public For(Idf id,Ast debut,Ast fin,Ast bloc){
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.bloc = bloc;
    }


}
