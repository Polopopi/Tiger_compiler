package tds;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ast.*;

public class TdsCreator implements AstVisitor<String> {

    private int idCurrentTds;
    private ArrayList<Tds> listeTds;

    TdsCreator(){
        this.listeTds=new ArrayList<Tds>(5);
        this.idCurrentTds=0;

    }
    
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
        String leftType = or.left.accept(this);
        String rightType = or.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(And and){
        String leftType = and.left.accept(this);
        String rightType = and.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Equal equal){
        String leftType = equal.left.accept(this);
        String rightType = equal.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Diff diff){
        String leftType = diff.left.accept(this);
        String rightType = diff.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Inf inf){
        String leftType = inf.left.accept(this);
        String rightType = inf.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Sup sup){
        String leftType = sup.left.accept(this);
        String rightType = sup.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(InfEqual infEqual){
        String leftType = infEqual.left.accept(this);
        String rightType = infEqual.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(SupEqual supEqual){
        String leftType = supEqual.left.accept(this);
        String rightType = supEqual.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Plus plus){
        String leftType = plus.left.accept(this);
        String rightType = plus.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Minus minus){
        String leftType = minus.left.accept(this);
        String rightType = minus.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Mult mult){
        String leftType = mult.left.accept(this);
        String rightType = mult.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Divide divide){
        String leftType = divide.left.accept(this);
        String rightType = divide.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            //ERREUR TYPE
        }

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


    public String visit(Let let){
        let.declarationList.accept(this);

        String seqExprType = let.seqExpr.accept(this);

        return seqExprType;
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


    public String visit(BreakExpr breakExpr){
        Ast current = breakExpr;
        while (current.parent != null){
            if (current instanceof While | current instanceof For){
                return "";
            }
            current = current.parent;
        }
        //ERREUR
        return "";
    };


    public String visit(NilExpr affect){


    };


    public String visit(IntExpr affect){
        return "int";
    };


    public String visit(StrExpr affect){
        return "string";
    };


    public String visit(SeqExpr seqExpr){
        String lastType;

        if (!seqExpr.listExpr.isEmpty()){
            lastType = seqExpr.listExpr.get(seqExpr.listExpr.size() - 1).accept(this);
        }
        else{
            lastType = "";
        }

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
        String id=affect.idf.accept(this);
        String exprType=affect.expr.accept(this);
        
        VarFuncEntry varFuncEntryr=new VarFuncEntry(exprType,id,4);
        this.listeTds.get(idCurrentTds).addVarFunc(varFuncEntryr);
        return "";

    };


    public String visit(FctDeclaration affect){
        String id=affect.fonctionID.accept(this);
        String typeRetor=affect.fct2Declaration.accept(this);
        String typeParametre=affect.typeField.accept(this);
        Parameter parameter=new Parameter(typeParametre, 4);
        FunctionEntry functionEntry=new FunctionEntry(typeRetor, id, 4);
        functionEntry.addParameter(parameter);
        this.listeTds.get(idCurrentTds).addVarFunc(functionEntry);
        return "";

    };


    public String visit(ProcDeclaration affect){
        String id=affect.fonctionID.accept(this);
        String typeRetor=affect.fct2Declaration.accept(this);
        FunctionEntry procEntry=new FunctionEntry(typeRetor, id, 4);
        this.listeTds.get(idCurrentTds).addVarFunc(procEntry);
        return "";
    };


    public String visit(Fct2Declaration affect){
        
        return "";

    };


    public String visit(Fct2DeclarationType affect){
        String typeRetour=affect.typeID.accept(this);
        String lastType=affect.exprAffect.accept(this);
        if (!typeRetour.equals(lastType)){
            //ERREUR
        }
        
        return typeRetour;

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
