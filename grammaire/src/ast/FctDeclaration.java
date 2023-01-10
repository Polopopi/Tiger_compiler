package ast;

public class FctDeclaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast fonctionID;
    public Ast typeFields;
    public Ast fct2Declaration;

    public FctDeclaration(Ast fonctionID, Ast typeFields, Ast fct2Declaration){
        this.fonctionID=fonctionID;
        this.typeFields=typeFields;
        this.fct2Declaration=fct2Declaration;   
    }
}
