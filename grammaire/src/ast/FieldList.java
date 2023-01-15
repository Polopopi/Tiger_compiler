package ast;

import java.util.ArrayList;

public class FieldList extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> listAst;

    public FieldList(int lineNumber){
        super(lineNumber);
        this.listAst = new ArrayList<>();
    }

    public void addField(Ast field){
        this.listAst.add(field);
    }
}
