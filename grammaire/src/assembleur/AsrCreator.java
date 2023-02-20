package assembleur;

import ast.*;

import java.io.PrintWriter;

public class AsrCreator implements AstVisitor<String> {
    private Asr asr;
    public AsrCreator(){
        this.asr=new Asr();
    }
    @Override
    public String visit(Idf affect) {
        return null;
    }

    @Override
    public String visit(Print affect) {
        return null;
    }

    @Override
    public String visit(Program affect) {
        return null;
    }

    @Override
    public String visit(Affect affect) {
        return null;
    }

    @Override
    public String visit(Or affect) {
        return null;
    }

    @Override
    public String visit(And affect) {
        return null;
    }

    @Override
    public String visit(Equal affect) {
        return null;
    }

    @Override
    public String visit(Diff affect) {
        return null;
    }

    @Override
    public String visit(Inf affect) {
        return null;
    }

    @Override
    public String visit(Sup affect) {
        return null;
    }

    @Override
    public String visit(InfEqual affect) {
        return null;
    }

    @Override
    public String visit(SupEqual affect) {
        return null;
    }

    @Override
    public String visit(Plus affect) {
        return null;
    }

    @Override
    public String visit(Minus minus) {//
        String left = minus.left.accept(this);
        int leftValue= Integer.parseInt(left);
        String right = minus.right.accept(this);
        int rightValue = Integer.parseInt(right);


        return null;
    }

    @Override
    public String visit(Mult affect) {
        return null;
    }

    @Override
    public String visit(Divide affect) {
        return null;
    }

    @Override
    public String visit(MinusExpr affect) {
        return null;
    }

    @Override
    public String visit(IfThen affect) {
        return null;
    }

    @Override
    public String visit(IfThenElse affect) {
        return null;
    }

    @Override
    public String visit(Let affect) {
        return null;
    }

    @Override
    public String visit(For affect) {
        return null;
    }

    @Override
    public String visit(While affect) {
        return null;
    }

    @Override
    public String visit(BreakExpr affect) {
        return null;
    }

    @Override
    public String visit(NilExpr affect) {
        return null;
    }

    @Override
    public String visit(IntExpr affect) {
        return null;
    }

    @Override
    public String visit(StrExpr affect) {
        return null;
    }

    @Override
    public String visit(SeqExpr affect) {
        return null;
    }

    @Override
    public String visit(DeclarationList affect) {
        return null;
    }

    @Override
    public String visit(ListExpr affect) {
        return null;
    }

    @Override
    public String visit(Type_Declaration affect) {
        return null;
    }

    @Override
    public String visit(Type_Fields affect) {
        return null;
    }

    @Override
    public String visit(Type_Field affect) {
        return null;
    }

    @Override
    public String visit(TypeType affect) {
        return null;
    }

    @Override
    public String visit(TypeRecord affect) {
        return null;
    }

    @Override
    public String visit(TypeRecordVoid affect) {
        return null;
    }

    @Override
    public String visit(TypeArray affect) {
        return null;
    }

    @Override
    public String visit(Field affect) {
        return null;
    }

    @Override
    public String visit(FieldList affect) {
        return null;
    }

    @Override
    public String visit(VarDeclaration affect) {
        return null;
    }

    @Override
    public String visit(FctDeclaration affect) {
        return null;
    }

    @Override
    public String visit(ProcDeclaration affect) {
        return null;
    }

    @Override
    public String visit(Fct2Declaration affect) {
        return null;
    }

    @Override
    public String visit(Fct2DeclarationType affect) {
        return null;
    }

    @Override
    public String visit(LvalueField affect) {
        return null;
    }

    @Override
    public String visit(LvalueIndex affect) {
        return null;
    }

    @Override
    public String visit(Array affect) {
        return null;
    }

    @Override
    public String visit(LvalueRecord affect) {
        return null;
    }

    @Override
    public String visit(Call affect) {
        return null;
    }

    @Override
    public String visit(VarDeclarationType affect) {
        return null;
    }
}
