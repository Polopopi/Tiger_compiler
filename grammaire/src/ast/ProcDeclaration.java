package ast;

public class ProcDeclaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast fonctionID;
    public Ast fct2Declaration;

    public ProcDeclaration(Ast fonctionID, Ast fct2Declaration){
        this.fonctionID=fonctionID;
        this.fct2Declaration=fct2Declaration;
    }
    
}
