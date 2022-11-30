package ast;

public class FctDeclaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Idf fonctionID;
    public Ast typeField;
    public Ast fct2Declaration;
    public FctDeclaration(Idf fonctionID, Ast typeField, Ast fct2Declaration){
        this.fonctionID=fonctionID;
        this.typeField=typeField;
        this.fct2Declaration=fct2Declaration;
    }
}
