package tds;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.swing.CellEditor;

import ast.*;

public class TdsCreator implements AstVisitor<String> {

    private int idCurrentTds;
    private boolean whileForNode;
    private boolean inFunctionDecBloc;
    private boolean inTypeDecBloc;
    private ArrayList<Tds> listeTds;
    private ArrayList<Ast> verifListList;
    

    TdsCreator(){
        this.listeTds=new ArrayList<Tds>(5);
        this.idCurrentTds=0;
        this.inFunctionDecBloc = false;
        this.inTypeDecBloc = false;

    }
    
    public String visit(Idf affect){

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

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Diff diff){
        String leftType = diff.left.accept(this);
        String rightType = diff.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Inf inf){
        String leftType = inf.left.accept(this);
        String rightType = inf.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(Sup sup){
        String leftType = sup.left.accept(this);
        String rightType = sup.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(InfEqual infEqual){
        String leftType = infEqual.left.accept(this);
        String rightType = infEqual.right.accept(this);

        if (!leftType.equals(rightType)){
            //ERREUR TYPE
        }

        return "int";
    };


    public String visit(SupEqual supEqual){
        String leftType = supEqual.left.accept(this);
        String rightType = supEqual.right.accept(this);

        if (!leftType.equals(rightType)){
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
        int imbrication = listeTds.get(idCurrentTds).getImbrication();
        Tds tds = new Tds(imbrication + 1, idCurrentTds);
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        
        let.declarationList.accept(this);
        
        String seqExprType = let.seqExpr.accept(this);

        idCurrentTds = tds.getIdParent();

        return seqExprType;
    };


    public String visit(For forNode){
        whileForNode = true;
        String id = forNode.id.accept(this);
        String debutType = forNode.debut.accept(this);
        String finType = forNode.fin.accept(this);
        int imbrication = listeTds.get(idCurrentTds).getImbrication();
        Tds tds = new Tds(imbrication + 1, idCurrentTds);
        listeTds.add(tds);
        tds.addVarFunc(new VariableEntry("int",id,4));
        String blocType = forNode.bloc.accept(this);
        idCurrentTds = tds.getIdParent();

        if (!(debutType.equals("int")) && finType.equals("int")){
            //ERREUR TYPE
        }
        if(! blocType.equals("")){
            //ERREUR TYPE
        }
        whileForNode = false;
        return "";

    };


    public String visit(While whileNode){
        whileForNode = true;
        String condType = whileNode.condition.accept(this);
        String blocType = whileNode.bloc.accept(this);
        whileForNode = false;

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
        if (!whileForNode){
            //ERREUR
        }
        return "";
    };


    public String visit(NilExpr affect){
        return "nil";

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

    /*
    public String visit(DeclarationList declarationList){
        for (Ast dec : declarationList.listAst){
            dec.accept(this);
        }
        return("");
    }
    */

    public String visit (DeclarationList declarationList){
        /*
        Tds tds = listeTds.get(idCurrentTds);

        for (Ast dec : declarationList.listAst){
            if (dec instanceof VarDeclaration){
                VarDeclaration varDec = (VarDeclaration)dec;
                String idf = ((Idf)varDec.idf).name;
                String type = dec.accept(this);
                tds.addVarFunc(new VariableEntry(type, idf, 4));
            }

            else if (dec instanceof VarDeclarationType){
                VarDeclarationType varTypeDec = (VarDeclarationType)dec;
                String idf = ((Idf)varTypeDec.idf).name;
                String type = ((Idf)varTypeDec.idf).name;
                tds.addVarFunc(new VariableEntry(type, idf, 4));
            }

            else if (dec instanceof FctDeclaration){
                FctDeclaration funcDec = (FctDeclaration)dec;
                String idf = ((Idf)funcDec.fonctionID).name;

                FunctionEntry funcEntry;
                String type;
                if (funcDec.fct2Declaration instanceof Fct2Declaration){
                    type = "";
                } 
                else{
                    type = ((Idf)((Fct2DeclarationType)funcDec.fct2Declaration).typeID).name;
                    String exprType = funcDec.fct2Declaration.accept(this);
                }
                funcEntry = new FunctionEntry(type, idf, 4);



                tds.addVarFunc(funcEntry);
            }
        }
        */

        for (Ast dec : declarationList.listAst){
            dec.accept(this);
        }

        return "";
    };


    public String visit (ListExpr listExpr){
        String res = "";
        for (Ast expr : listExpr.listExpr){
            if (!res.equals("")){
                res += ",";
            }
            res += expr.accept(this);
        }
        return(res);
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
        
        VarFuncEntry varFuncEntryr=new VariableEntry(exprType,id,4);
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
        
        String type=affect.left.accept(this);
        if ( !this.listeTds.get(idCurrentTds).existType(type) ){
            System.out.println("Erreur type dans LvalueField");
        }
        return "";


    };
    public boolean estUnEntier(String chaine) {
		try {
			Integer.parseInt(chaine);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}


    public String visit(LvalueIndex affect){
        String index=affect.exprOr.accept(this);

        if (estUnEntier(index)==false) {
            System.out.println("Erreur type dans LvalueIndex");
        }
        return "";
    };


    public String visit(Array affect){//array of type à vérifier le type 
        String idType = affect.id.accept(this);
        String typeArray=affect.exprOr2.accept(this);
        String lengthArray=affect.exprOr1.accept(this);
        if (!lengthArray.equals("int")) {
            System.out.println("longueur d\'une liste erreur Array [longueur] of type");
        }

        try {
            ArrayEntry typeEntry = (ArrayEntry) listeTds.get(idCurrentTds).getTypeEntry(idType);
            if (typeArray.equals(typeEntry.getTypeComposite())){
                System.out.println("Le type attendu est " + typeEntry.getTypeComposite());
            }

        }
        catch (Exception e){
            System.out.println("Le type " + idType +"n'est pas un array");
        }
        return idType;
    };


    public String visit(LvalueRecord affect){
        
    };


    public String visit(Call call){
        String id=call.id.accept(this);

        if (inFunctionDecBloc){
            callList.add(call);

        }

        String type=call.listExpr.accept(this);
        RecordEntry newCall=new RecordEntry(id, 4);
        this.listeTds.get(idCurrentTds).addType(newCall);
        return "";

    };


    //public String visit(RecordList affect);


    public String visit(VarDeclarationType affect){


    };

}
