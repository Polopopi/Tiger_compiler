package tds;

import ast.*;

public class TdsCreator implements AstVisitor<String> {
    
    public String visit(Idf affect){

    };


    public String visit(InstrList affect){


    };


    public String visit(Print affect){


    };


    public String visit(Program affect){


    };

    // Partie 1 :
    public String visit(Affect affect){


    };


    public String visit(Or or){
        or.left.accept(this);
        or.right.accept(this);

        return "int";
    };


    public String visit(And and){
        and.left.accept(this);
        and.right.accept(this);

        return "int";
    };


    public String visit(Equal equal){
        equal.left.accept(this);
        equal.right.accept(this);

        return "int";
    };


    public String visit(Diff diff){
        diff.left.accept(this);
        diff.right.accept(this);

        return "int";
    };


    public String visit(Inf inf){
        inf.left.accept(this);
        inf.right.accept(this);

        return "int";
    };


    public String visit(Sup sup){
        sup.left.accept(this);
        sup.right.accept(this);

        return "int";
    };


    public String visit(InfEqual infEqual){
        infEqual.left.accept(this);
        infEqual.right.accept(this);

        return "int";
    };


    public String visit(SupEqual supEqual){
        supEqual.left.accept(this);
        supEqual.right.accept(this);

        return "int";
    };


    public String visit(Plus plus){
        plus.left.accept(this);
        plus.right.accept(this);

        return "int";
    };


    public String visit(Minus minus){
        minus.left.accept(this);
        minus.right.accept(this);

        return "int";
    };


    public String visit(Mult mult){
        mult.left.accept(this);
        mult.right.accept(this);

        return "int";
    };


    public String visit(Divide divide){
        divide.left.accept(this);
        divide.right.accept(this);

        return "int";
    };



    // Partie 2 :
    public String visit(MinusExpr minusExpr){
        String type = minusExpr.expr.accept(this);

        if (!type.equals("int")){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(IfThen ifThen){
        String condType = ifThen.condition.accept(this);
        //String blocType = ifThen.thenBlock.accept(this);

        if (!condType.equals("int")){
            //ERREUR TYPE
        }

        return "";
    };


    public String visit(IfThenElse ifThenElse){
        String condType = ifThenElse.condition.accept(this);
        String thenBlocType = ifThenElse.thenBlock.accept(this);
        String elseBlocType = ifThenElse.elseBlock.accept(this);

        if (!condType.equals("int")){
            //ERREUR TYPE
        }
        if (!thenBlocType.equals(elseBlocType)){
            //ERREUR TYPE
        }

        return "";
    };


    public String visit(Let affect){


    };


    public String visit(For affect){


    };


    public String visit(While whileNode){
        String condType = whileNode.condition.accept(this);
        String blocType = whileNode.bloc.accept(this);

        if (!condType.equals("int")){
            //ERREUR TYPE
        }
        if (!blocType.equals("")){
            //ERREUR TYPE
        }

        return "";
    };


    //public String visit(LvalueExpr affect);
    //public String visit(LvalueExprTypeID affect);


    public String visit(BreakExpr affect){


    };


    public String visit(NilExpr affect){


    };


    public String visit(IntExpr affect){


    };


    public String visit(StrExpr affect){


    };


    public String visit(SeqExpr seqExpr){
        String lastType = seqExpr.listExpr.get(seqExpr.listExpr.size() - 1).accept(this);

        return lastType;
    };


    public String visit (DeclarationList affect){


    };


    public String visit (ListExpr affect){


    };


    
    // Partie 3 :
    public String visit(Type_Declaration affect){


    };


    public String visit(Type_Fields affect){


    };


    public String visit(Type_Field affect){


    };


    public String visit(TypeType affect){


    };


    public String visit(TypeRecord affect){


    };


    public String visit(TypeRecordVoid affect){


    };


    public String visit(TypeArray affect){


    };


    public String visit(Field affect){


    };


    public String visit(FieldList affect){


    };



    // Partie 4 :
    public String visit(VarDeclaration affect){


    };


    public String visit(FctDeclaration affect){


    };


    public String visit(ProcDeclaration affect){


    };


    public String visit(Fct2Declaration affect){


    };


    public String visit(Fct2DeclarationType affect){


    };


    public String visit(LvalueField affect){


    };


    public String visit(LvalueIndex affect){


    };


    public String visit(Array affect){


    };


    public String visit(LvalueRecord affect){


    };


    public String visit(Call affect){


    };


    //public String visit(RecordList affect);


    public String visit(VarDeclarationType affect){


    };

}
