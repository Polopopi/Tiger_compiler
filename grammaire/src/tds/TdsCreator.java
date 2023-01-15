package tds;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.swing.CellEditor;

import ast.*;

public class TdsCreator implements AstVisitor<String> {

    private int idCurrentTds;
    private int whileForNode;
    private boolean inFunctionDecBloc;
    private boolean inTypeDecBloc;
    private ArrayList<Tds> listeTds;
    private Entry currentEntry;
    private ArrayList<LaterVerif> verifList;
    private boolean nameIdf = false;

    public TdsCreator(){
        this.listeTds=new ArrayList<Tds>();
        this.idCurrentTds=0;
        this.inFunctionDecBloc = false;
        this.inTypeDecBloc = false;
        this.verifList=new ArrayList<LaterVerif>();
    }

    public ArrayList<Tds> getTds(){
        return this.listeTds;
    }

    public Tds getCuurentTds(){
        return this.listeTds.get(idCurrentTds);
    }


    public void checkList(){
        for (LaterVerif toCheck : verifList){
            toCheck.check(this);
        }
        verifList.clear();
        inFunctionDecBloc = false;
        inTypeDecBloc = false;
    }

    public void setTds(Tds tds){
        idCurrentTds = tds.getId();
    }
    
    public String visit(Idf idf){
        if (nameIdf){
            return idf.name;
        }

        // APPEL DE VARIABLE
        else if (!listeTds.get(idCurrentTds).existVarFunc(idf.name)){
            System.out.println("Erreur line" + idf.lineNumber + " : la variable " + idf.name + " n'est pas définie");
            return null;
        }
        else if (!listeTds.get(idCurrentTds).getVarFuncEntry(idf.name).isVariable()){
            System.out.println("Erreur line" + idf.lineNumber + " : l'identificateur " + idf.name + " est celui d'une fonction");
            return null;
        }
        
        return listeTds.get(idCurrentTds).getVarFuncEntry(idf.name).getType();
    };


    public String visit(Print print){
        String parameterType = print.value.accept(this);
        if (!parameterType.equals("int")){
            System.out.println("Erreur line" + print.lineNumber + " : le paramètre de print est incorrect, int attendu");
        }
        return("");
    };


    public String visit(Program program){
        //AJOUTER int et string à la TDS DU PROG
        //idPrec de la 1ere tds est null
        Tds tds = new Tds(0, null);
        // Ajouter string et int mais sous quelle classe ? On en crée une nouvelle ?
        // On sait que le type existe donc bon...
        //tds.addType(new );
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        program.affect.accept(this);

        for (Tds tdsBis : listeTds){
            System.out.printf("%d - %d\n", tdsBis.getImbrication(), tdsBis.getId());
            tdsBis.printTds();
            System.out.println("\n");
        }

        return "";
    };

    // Partie 1 :
    public String visit(Affect affect){
        String idfType = affect.idf.accept(this);
        String exprType = affect.expr.accept(this);

        Tds tds = listeTds.get(idCurrentTds);

        if (!idfType.equals(exprType)){
            System.out.println("Erreur line" + affect.lineNumber + " : affectation du type " + exprType + ", " + idfType + " était attendu");
        }

        return "";
    };


    public String visit(Or or){
        String leftType = or.left.accept(this);
        String rightType = or.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur line" + or.lineNumber + " : les opérandes du OR ne sont pas des int");
        }

        return "int";
    };


    public String visit(And and){
        String leftType = and.left.accept(this);
        String rightType = and.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur line" + and.lineNumber + " : les opérandes du AND ne sont pas des int");
        }

        return "int";
    };


    public String visit(Equal equal){
        String leftType = equal.left.accept(this);
        String rightType = equal.right.accept(this);

        if (!leftType.equals(rightType) && !(leftType.equals("int") | leftType.equals("string"))){
            System.out.println("Erreur line" + equal.lineNumber + " : les opérandes de l'égalité ne sont pas du même type"); 
        }

        return "int";
    };


    public String visit(Diff diff){
        String leftType = diff.left.accept(this);
        String rightType = diff.right.accept(this);

        if (!leftType.equals(rightType) && !(leftType.equals("int") | leftType.equals("string"))){
            System.out.println("Erreur line" + diff.lineNumber + " : les opérandes du différent ne sont pas du même type"); 
        }

        return "int";
    };


    public String visit(Inf inf){
        String leftType = inf.left.accept(this);
        String rightType = inf.right.accept(this);

        if (!leftType.equals(rightType) && !(leftType.equals("int") | leftType.equals("string"))){
            System.out.println("Erreur line" + inf.lineNumber + " : les opérandes de l'inférieur ne sont pas du même type"); 
        }

        return "int";
    };


    public String visit(Sup sup){
        String leftType = sup.left.accept(this);
        String rightType = sup.right.accept(this);

        if (!leftType.equals(rightType) && !(leftType.equals("int") | leftType.equals("string"))){
            System.out.println("Erreur line" + sup.lineNumber + " : les opérandes du supérieur ne sont pas du même type"); 
        }

        return "int";
    };


    public String visit(InfEqual infEqual){
        String leftType = infEqual.left.accept(this);
        String rightType = infEqual.right.accept(this);

        if (!leftType.equals(rightType) && !(leftType.equals("int") | leftType.equals("string"))){
            System.out.println("Erreur line" + infEqual.lineNumber + " : les opérandes de l'inférieur ou égal ne sont pas du même type"); 
        }

        return "int";
    };


    public String visit(SupEqual supEqual){
        String leftType = supEqual.left.accept(this);
        String rightType = supEqual.right.accept(this);

        if (!leftType.equals(rightType) && !(leftType.equals("int") | leftType.equals("string"))){
            System.out.println("Erreur line" + supEqual.lineNumber + " : les opérandes du supérieur ou égal ne sont pas du même type"); 
        }

        return "int";
    };


    public String visit(Plus plus){
        String leftType = plus.left.accept(this);
        String rightType = plus.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur line" + plus.lineNumber + " : les opérandes du plus ne sont pas des int"); 
        }

        return "int";
    };


    public String visit(Minus minus){
        String leftType = minus.left.accept(this);
        String rightType = minus.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur line" + minus.lineNumber + " : les opérandes du moins ne sont pas des int"); 
        }

        return "int";
    };


    public String visit(Mult mult){
        String leftType = mult.left.accept(this);
        String rightType = mult.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur line" + mult.lineNumber + " : les opérandes de la multiplication ne sont pas des int"); 
        }

        return "int";
    };


    public String visit(Divide divide){
        String leftType = divide.left.accept(this);
        String rightType = divide.right.accept(this);

        if (!(leftType.equals("int") && rightType.equals("int"))){
            System.out.println("Erreur line" + divide.lineNumber + " : les opérandes de la division ne sont pas des int"); 
        }

        return "int";
    };



    // Partie 2 :
    public String visit(MinusExpr minusExpr){
        String type = minusExpr.expr.accept(this);

        if (!type.equals("int")){
            System.out.println("Erreur line" + minusExpr.lineNumber + " : l'expression n'est pas un int"); 
        }

        return "int";
    };


    public String visit(IfThen ifThen){
        String condType = ifThen.condition.accept(this);
        String blocType = ifThen.thenBlock.accept(this);

        if (!condType.equals("int")){
            System.out.println("Erreur line" + ifThen.lineNumber + " : la condition ne renvoie pas de int"); 
        }
        if (!blocType.equals("")){
            System.out.println("Erreur line" + ifThen.lineNumber + " : le bloc renvoie une valeur"); 
        }

        return "";
    };


    public String visit(IfThenElse ifThenElse){
        String condType = ifThenElse.condition.accept(this);
        String thenBlocType = ifThenElse.thenBlock.accept(this);
        String elseBlocType = ifThenElse.elseBlock.accept(this);

        if (!condType.equals("int")){
            System.out.println("Erreur line" + ifThenElse.lineNumber + " : la condition ne renvoie pas de int"); 
        }
        if (!thenBlocType.equals(elseBlocType)){
            System.out.println("Erreur line" + ifThenElse.lineNumber + " : les blocs Then et Else ne renvoient pas le même type"); 
        }

        return thenBlocType;
    };


    public String visit(Let let){
        int imbrication = listeTds.get(idCurrentTds).getImbrication();
        Tds tds = new Tds(imbrication + 1, listeTds.get(idCurrentTds));
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        
        let.declarationList.accept(this);
        
        String seqExprType = let.seqExpr.accept(this);

        idCurrentTds = tds.getParent().getId();

        return seqExprType;
    };


    public String visit(For forNode){
        whileForNode++;
        nameIdf = true;
        String id = forNode.id.accept(this);
        nameIdf = false;
        String debutType = forNode.debut.accept(this);
        String finType = forNode.fin.accept(this);

        int imbrication = listeTds.get(idCurrentTds).getImbrication();
        Tds tds = new Tds(imbrication + 1, listeTds.get(idCurrentTds));
        listeTds.add(tds);
        idCurrentTds = tds.getId();
        tds.addVarFunc(new VariableEntry("int",id,4));

        String blocType = forNode.bloc.accept(this);

        idCurrentTds = tds.getParent().getId();

        if (!(debutType.equals("int")) && finType.equals("int")){
            System.out.println("Erreur line" + forNode.lineNumber + " : les bornes ne sont pas des int"); 
        }
        if(! blocType.equals("")){
            System.out.println("Erreur line" + forNode.lineNumber + " : le bloc renvoie une valeur"); 
        }
        whileForNode--;
        return "";
    };


    public String visit(While whileNode){
        whileForNode++;
        String condType = whileNode.condition.accept(this);
        String blocType = whileNode.bloc.accept(this);
        whileForNode--;

        if (!condType.equals("int")){
            System.out.println("Erreur line" + whileNode.lineNumber + " : la condition ne renvoie pas de int"); 
        }
        if (!blocType.equals("")){
            System.out.println("Erreur line" + whileNode.lineNumber + " : le bloc renvoie une valeur"); 
        }

        return "";
    };


    //public String visit(LvalueExpr affect);
    //public String visit(LvalueExprTypeID affect);


    public String visit(BreakExpr breakExpr){
        if (whileForNode <= 0){
            System.out.println("Erreur line" + breakExpr.lineNumber + " : le break est en dehors d'une boucle For ou While");
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
        String lastType = "";

        for (Ast expr:seqExpr.listExpr){
            lastType = expr.accept(this);
        }
    
        return lastType;
    };


    public String visit (DeclarationList declarationList){
        ArrayList<LaterVerif> memory = verifList;
        verifList = new ArrayList<>();

        for (Ast dec : declarationList.listAst){
            dec.accept(this);
        }

        //VERIF RECURSIF
        checkList();

        verifList = memory;
        return "";
    };


    public String visit (ListExpr listExpr){
        ArrayList<Parameter> parameters = ((FunctionEntry) currentEntry).getParameters();
        int i = 0;
        for (Ast expr : listExpr.listExpr){
            String exprType = expr.accept(this);
            if (!parameters.get(i).getType().equals(exprType)){
                System.out.println("Erreur line" + listExpr.lineNumber + " : affectation du type " + exprType + " vers le paramètre " + parameters.get(i).getSymbole() + " de type " + parameters.get(i).getType() + " pour la fonction " + currentEntry.getSymbol());
            }
            i++;
        }
        return "";
    };

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Attention danger : Code WTF
    
    // Partie 3 :
    public String visit(Type_Declaration type_Declaration){
        if (!inTypeDecBloc){
            inTypeDecBloc = true;
            if (inFunctionDecBloc){
                inFunctionDecBloc = false;
                checkList();
            }
        }

        Entry oldEntry = currentEntry;

        nameIdf = true;
        String type_id = type_Declaration.type_id.accept(this);
        nameIdf = false;
        currentEntry = new TypeEntry(type_id);

        if (listeTds.get(idCurrentTds).existLocalType(type_id)){
            System.out.println("Erreur line" + type_Declaration.lineNumber + " : le type " + type_id + " est déjà défini");
        }
        
        String type = type_Declaration.type.accept(this);

        currentEntry = oldEntry;
        /*
        if (type.startsWith("ArrayOf")){
            String composite = type.substring(7);
            TypeEntry typeEntry = new ArrayEntry(type_id,4,composite);
            this.listeTds.get(idCurrentTds).addType(typeEntry);         
        }
        else if (type.startsWith("Record")){
            // VERIF ID FIELD UNIQUE

            String composites = type.substring(7);
            String[] list =composites.split(",");

            RecordEntry typeEntry = new RecordEntry(type_id,4);
            for (String typeName: list){
                String[] fieldArray = typeName.split(":");
                Field field = new Field(fieldArray[0], fieldArray[1], 4);
                // Je sais pas comment retrouver tous les fields deja créer ... ou si je dois créer de nouveaux ...
                typeEntry.addField(field);
            }
            this.listeTds.get(idCurrentTds).addType(typeEntry);         
        }
        else {
            */
            /* let
             *      type steven = {adiruin : int}
             *      type cloée = {adiruin : int}
             *      type stevenAlias = steven
             *      type stevenAlias2 = stevenAlias
             * 
             *      steven 1
             *      stevenAlias : listType = (steven) 2
             *      stevenAlias2 : listType = (stevenAlias) 3
             * 
             *      
             * 
             *      var moi : steven := cloée{adiruien = 1} // ERREUR
             *      var moi : steven := stevenAlias2{adiruien = 1} // OUI
             * 
             *      function encoreMoi(oui : cloée) = expr
             * in
             *      moi := let 
             *                  var wenjia := cloée{adiruin = 1}
             *             in wenjia end; // ERREUUUUUUUUUUUUUUUUUUUUUUUUUUUUR 
             * 
             *      encoreMoi(moi); //ERREUUUUUR
             * end
             * 
             * 
             * 
             * // CAS DE SOLUTIONS SI LES TDS NE PEUVENT PAS REMONTER VERS LEUR PARENT D'ELLES MEME
             * 
             * Solution pour la vérif des types si alias contient que le type_id de la délcaration :
             *      "var moi : steven := stevenAlias{adiruien = 1}"
             *      type_id <-- steven
             *      type_alias_id <-- stevenAlias
             *      
             *      if (!type_id.equals(type_alias_id)){
             *          boolean found = false;
             *          TypeEntry type;
             *          Tds tds = listeTds.get(idCurrentTds);
             *          while (tds != null && found != true){
             *              type = tds.getTypeEntry(type_alias_id);
             *              while(type != null && type.getAlias() != null){
             *                  String alias = type.getAlias();
             *                  if (type_id.equals(alias)){
             *                      found = true;
             *                      break;
             *                  }
             *                  type = tds.getTypeEntry(alias);
             *              }
             *              try{
             *                  tds = listeTds.get(tds.getIdParent());
             *              }
             *              catch (IndexOutOfBoundsException ex){
             *                  tds = null;
             *              }
             *          }
             * 
             *          if (!found)
             *              System.out.println("ERREUR");
             *      }
             * 
             * JE SUIS LE MEILLEUR
             * Je suis C(HUSKI)1 enfaite
             * 
             * ...
             * 
             * Solution pour la vérif des types si alias est une liste d'alias :
             *      "var moi : steven := stevenAlias{adiruien = 1}"
             *      type_id <-- steven
             *      type_alias_id <-- stevenAlias
             *      
             *      if (!type_id.equals(type_alias_id)){
             *          TypeEntry type = null;
             *          Tds tds = listeTds.get(idCurrentTds);
             *          while (type == null && tds != null){
             *              type = tds.getTypeEntry(type_alias_id);
             *              try{
             *                  tds = listeTds.get(tds.getIdParent());
             *              }
             *              catch (IndexOutOfBoundsException ex){
             *                  tds = null;
             *              }
             *          }
             * 
             *          if (type == null)
             *              System.out.println("ERREUR");  ALIAS EXISTE PAS
             *          else (!type.getAlias().contains(type_id))
             *              System.out.println("ERREUR"); DE 
             *              
             *      }
             */

            //type est un type_id pour l'alias
            
            /*
            if (!listeTds.get(idCurrentTds).existType(type)){
                System.out.println("ERREUR");
            }
            
            // MODIFF GET POUR IT2RER SUR LES PARENTS
            TypeEntry type_alias = listeTds.get(idCurrentTds).getTypeEntry(type);
            
            if (type_alias instanceof ArrayEntry){
                ArrayEntry typeEntry = new ArrayEntry(type_id, 4, ((ArrayEntry)type_alias).getTypeComposite(), (ArrayEntry)type_alias);
                this.listeTds.get(idCurrentTds).addType(typeEntry);
            }
            else{ //RecordEntry
                RecordEntry typeEntry = new RecordEntry(type_id, 4, (RecordEntry)type_alias);
                this.listeTds.get(idCurrentTds).addType(typeEntry);
            }
            
        }
        */
        return "";
    }

    /*
    public boolean sameTypes(String type1, String type2){
        TypeEntry parentAlias1 = listeTds.get(idCurrentTds).getTypeEntry(type1);
        while (parentAlias1 != null){
            if (type1.equals(type2)){
                return true;
            }
            parentAlias1 = parentAlias1.getParentAlias();
        }

        TypeEntry parentAlias2 = listeTds.get(idCurrentTds).getTypeEntry(type2);
        while (parentAlias2 != null){
            if (type1.equals(type2)){
                return true;
            }
            parentAlias2 = parentAlias2.getParentAlias();
        }

        return false;
    }
*/

    public String visit(Type_Fields type_Fields){
        ArrayList<String> fieldsId = new ArrayList<String>();
        for (Ast field : type_Fields.listAst){
            //this.verifList.add(new LaterVerif(type_id, type_Field.type_id));
            String fieldId = field.accept(this);
            if (fieldsId.contains(fieldId)){
                System.out.println("Erreur line" + type_Fields.lineNumber + " : le field " + fieldId + " a été défini plusieurs fois pour le record " + currentEntry.getSymbol());
            }
            fieldsId.add(fieldId);
        }
        return("");
    };

/*
 * type test1={ok : test2}
 * type test2={ok: test1}
 * 
 * 
 * 
 * 
 */
    public String visit(Type_Field type_Field){
        nameIdf = true;
        String type_id = type_Field.type_id.accept(this); 
        
        String id = type_Field.id.accept(this);
        nameIdf = false;
        
        if (currentEntry instanceof RecordEntry){
            ((RecordEntry) currentEntry).addField(new tds.Field(id, type_id));
            verifList.add(new LaterVerifType(type_id, listeTds.get(idCurrentTds)));
        }
        else{
            
            if (!listeTds.get(idCurrentTds).existType(type_id)){
                System.out.println("Erreur line" + type_Field.lineNumber + " : le type " + type_id + " n'est pas défini pour le record " + currentEntry.getSymbol());
            }

            String typeParamAlias = listeTds.get(idCurrentTds).getTypeEntry(type_id).getSymbol();
            //dans l'ancienne tds
            Parameter parameter=new Parameter(id, typeParamAlias,4);
            ((FunctionEntry) currentEntry).addParameter(parameter);
            //dans la nouvelle tds
            VariableEntry var=new VariableEntry(typeParamAlias, id, 4);
            listeTds.get(idCurrentTds).addVarFunc(var);
        }
        
        /*if ( !this.listeTds.get(idCurrentTds).existType(type_id) ){ 
            System.out.println("Type pas trouvé");
        }
        */
        
        return id;
    };


    public String visit(TypeType typeType){         
        nameIdf = true;
        String typeID = typeType.typeCopie.accept(this);
        nameIdf = false;
        Tds tds = listeTds.get(idCurrentTds);
        currentEntry = new AliasEntry((TypeEntry) currentEntry);
        tds.addType((AliasEntry) currentEntry);

        verifList.add(new LaterVerifType(typeID,tds));
        return typeID;
    };


    public String visit(TypeRecord typeRecord){
        Tds tds = listeTds.get(idCurrentTds);
        currentEntry = new RecordEntry((TypeEntry) currentEntry);
        tds.addType((RecordEntry) currentEntry);
        typeRecord.fields.accept(this);

        return "";
    };


    public String visit(TypeRecordVoid typeRecordVoid){            
        Tds tds = listeTds.get(idCurrentTds);
        currentEntry = new RecordEntry((TypeEntry) currentEntry);
        tds.addType((RecordEntry) currentEntry);

        return "";
    };


    public String visit(TypeArray typeArrayy){
    // verifier les types dans Array : soit string, int ou autre déjà existants
        Tds tds = listeTds.get(idCurrentTds);
        currentEntry = new ArrayEntry((TypeEntry) currentEntry);
        tds.addType((ArrayEntry) currentEntry);
        nameIdf = true;
        String typeComp = typeArrayy.typeArray.accept(this);
        nameIdf = false;
        ((ArrayEntry) currentEntry).setTypeComposite(typeComp);
        verifList.add(new LaterVerifType(typeComp, tds));
        return ("");
    };


    public String visit(ast.Field fieldd){    
        nameIdf = true;          
        String id_f = fieldd.id.accept(this); //verres
        nameIdf = false;
        String expr_f = fieldd.expr.accept(this); //2 (int)

        if (!((RecordEntry)currentEntry).existField(id_f)){
            System.out.println("Erreur line" + fieldd.lineNumber + " : le field " + id_f + " n'est pas défini pour le record " + currentEntry.getSymbol());
        }

        String type_id = ((RecordEntry) currentEntry).getFieldType(id_f);
    
        /*
        Il faut avoir l'idf du record actuel pcq quand on est dans le field "adiruien := 2" 
        on sait pas si on doit regarder dans cloée (erreur type) ou dans steven (tout va bien)

        type steven = {adiruin : int}; oui
        type cloée = {adiruin : string}; non

        var wenjia := steven{adiruin = 2};
        */

        if ( !type_id.equals(expr_f) ){
            System.out.println("Erreur line" + fieldd.lineNumber + " : affectation du type " + expr_f + " vers le field " + id_f + " de type " + type_id + " pour le record " + currentEntry.getSymbol());
        }
        return id_f;
    };


    public String visit(FieldList fieldList){
        ArrayList<String> fieldsId = new ArrayList<String>();
        for (Ast expr : fieldList.listAst){
            String fieldId = expr.accept(this);
            if (fieldsId.contains(fieldId)){
                System.out.println("Erreur line" + fieldList.lineNumber + " : le field " + fieldId + " a été initialisé plusieurs fois pour le record " + currentEntry.getSymbol());
            }
            fieldsId.add(fieldId);
        }
        return "";
    };

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Fin du danger le code est mieux

    // Partie 4 :
    public String visit(VarDeclaration affect){
        if (inFunctionDecBloc || inTypeDecBloc){
            checkList();
        }

        nameIdf = true;
        String id=affect.idf.accept(this);
        nameIdf = false;
        String exprType=affect.expr.accept(this);


        if(this.listeTds.get(idCurrentTds).existLocalVarFunc(id)){
            System.out.println("Erreur line" + affect.lineNumber + " : la variable " + id + " est déjà définie VARDEC");
        }
        
        VariableEntry varFuncEntryr=new VariableEntry(exprType,id,4);
        this.listeTds.get(idCurrentTds).addVarFunc(varFuncEntryr);
        return "";
    };

    public String visit(VarDeclarationType affect){
        if (inFunctionDecBloc || inTypeDecBloc){
            checkList();
        }

        nameIdf = true;
        String id=affect.idf.accept(this);
        String type=affect.type.accept(this);
        nameIdf = false;
        String exprType=affect.expr.accept(this);

        if(this.listeTds.get(idCurrentTds).existLocalVarFunc(id)){
            System.out.println("Erreur line" + affect.lineNumber + " : la variable " + id + " est déjà définie VARTYPEDEC");
        }

        if (!this.listeTds.get(idCurrentTds).existType(type)) {
            System.out.println("Erreur line" + affect.lineNumber + " : le type " + type + " n'est pas défini");
        }

        String typeAlias = listeTds.get(idCurrentTds).getTypeEntry(type).getSymbol();

        if (!typeAlias.equals(exprType)){
            System.out.println("Erreur line" + affect.lineNumber + " : affectation du type " + exprType + " vers la variable " + id + " de type " + typeAlias);
        }

        VariableEntry variableEntry=new VariableEntry(typeAlias, id, 4);
        this.listeTds.get(idCurrentTds).addVarFunc(variableEntry);
        return "";

    };


    public String visit(FctDeclaration affect){
        if (!inFunctionDecBloc){
            if (inTypeDecBloc){
                inTypeDecBloc = false;
                checkList();
                System.out.println("check");
            }
            inFunctionDecBloc = true;
        }
/* 'function' id '(' type_fields? ')' (':' type_id)? '=' expr_affect */
        nameIdf = true;
        String id=affect.fonctionID.accept(this);
        String typeId=affect.typeId.accept(this);
        nameIdf = false;

        String typeAlias = listeTds.get(idCurrentTds).getTypeEntry(typeId).getSymbol();
        FunctionEntry functionEntry=new FunctionEntry(typeAlias, id, 4);

        if(!this.listeTds.get(idCurrentTds).existType(typeId)){
            System.out.println("Erreur line" + affect.lineNumber + " : le type " + typeId + " n'est pas défini pour la fonction " + functionEntry.getSymbol());
        }

        if(this.listeTds.get(idCurrentTds).existLocalVarFunc(id)){
            System.out.println("Erreur line" + affect.lineNumber + " : la fonction " + id + " est déjà définie");
        }
        //nouvelle tds
        Tds tdsFonction=new Tds(this.listeTds.get(idCurrentTds).getImbrication()+1,this.listeTds.get(idCurrentTds));
        //ajout de nouvelle tds
        this.listeTds.add(tdsFonction);

        idCurrentTds = tdsFonction.getId();
        Entry oldEntry = currentEntry;
        currentEntry = functionEntry;
        
        affect.typeFields.accept(this);
    

        currentEntry = oldEntry;
        idCurrentTds = tdsFonction.getParent().getId();


        //vérification
        //this.verifList.add(new LaterVerifFunc(typeParametres, affect.typeFields)); //BIZARRE FAUDRIAT METTRE LE BLOC PAS LE  FIELD
        this.verifList.add(new LaterVerifFunc(typeAlias, affect.exprAffect, tdsFonction));
        //ajout de nouvelle fct
        this.listeTds.get(idCurrentTds).addVarFunc(functionEntry);
        return "";
    };


    public String visit(ProcDeclaration affect){
        if (!inFunctionDecBloc){
            if (inTypeDecBloc){
                inTypeDecBloc = false;
                checkList();
            }
            inFunctionDecBloc = true;
        }

        nameIdf = true;
        String id=affect.fonctionID.accept(this);
        nameIdf = false;
        FunctionEntry procEntry=new FunctionEntry("", id, 4);

        if(this.listeTds.get(idCurrentTds).existLocalVarFunc(id)){
            System.out.println("Erreur line" + affect.lineNumber + " : la procédure " + id + " est déjà définie pour la procédure " + procEntry.getSymbol());
        }

        Tds tdsFonction=new Tds(this.listeTds.get(idCurrentTds).getImbrication()+1,this.listeTds.get(idCurrentTds));
        //ajout de nouvelle tds
        this.listeTds.add(tdsFonction);
        Entry oldEntry = currentEntry;
        currentEntry = procEntry;

        idCurrentTds = tdsFonction.getId();
        
        affect.typeFields.accept(this);

        currentEntry = oldEntry;
        idCurrentTds = tdsFonction.getParent().getId();

        verifList.add(new LaterVerifFunc("", affect.exprAffect, tdsFonction));
        this.listeTds.get(idCurrentTds).addVarFunc(procEntry);
        return "";
    };


    public String visit(Fct2Declaration affect){
        //Ajouter la Tds dans LaterVerif
        //verifList.add(new LaterVerifFunc("",affect.exprAffect));
        return ("");
    };


    public String visit(Fct2DeclarationType affect){
        nameIdf = true;
        String typeRetour=affect.typeID.accept(this);
        nameIdf = false;
        ((FunctionEntry) currentEntry).setType(typeRetour);
        //verifList.add(new LaterVerifFunc(typeRetour, affect.exprAffect));
        
        return typeRetour;
    };


    public String visit(LvalueField affect){//à modifier enattandant la fonction dans tds
        nameIdf = true;
        String id = affect.id.accept(this);
        nameIdf = false;
        String recordId = affect.left.accept(this); // type
        //VERIF ID DU FIELD DANS RECORD

        TypeEntry typeEntry = listeTds.get(idCurrentTds).getTypeEntry(recordId);
        if (!typeEntry.isRecord()){
            System.out.println("Erreur line" + affect.lineNumber + " : le type " + recordId + " n'est pas un Record");
        }

        if (!((RecordEntry)typeEntry).existField(id)){
            System.out.println("Erreur line" + affect.lineNumber + " : le field " + id + " n'est pas défini pour le record " + recordId);
        }

        /*lunettes.verres.marque --> lunettes.verres existe car verres a été vérif avec getrecordfieldTDS(id)
        */

        String fieldType = ((RecordEntry)typeEntry).getFieldType(id);

        return fieldType;
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
        String arrayId = affect.left.accept(this);
        String index=affect.exprOr.accept(this);

        //listeTds.get(idCurrentTds).printTds();
        //System.out.println(arrayId);

        TypeEntry typeEntry = listeTds.get(idCurrentTds).getTypeEntry(arrayId);
        if (!typeEntry.isArray()){
            System.out.println("Erreur line" + affect.lineNumber + " : Le type " + arrayId + " n'est pas un Array");
        }

        if (!index.equals("int")) {
            System.out.println("Erreur line" + affect.lineNumber + " : type indice dans LvalueIndex");
        }
            
        return ((ArrayEntry) typeEntry).getTypeComposite();           
    };


    public String visit(Array affect){//array of type à vérifier le type 
        nameIdf = true;
        String idType = affect.id.accept(this);
        nameIdf = false;
        String typeArray=affect.exprOr2.accept(this);
        String lengthArray=affect.exprOr1.accept(this);
        if (!lengthArray.equals("int")) {
            System.out.println("longueur d\'une liste erreur Array [longueur] of type");
        }

        try {
            ArrayEntry typeEntry = (ArrayEntry) listeTds.get(idCurrentTds).getTypeEntry(idType);
            if (!typeArray.equals(typeEntry.getTypeComposite())){
                System.out.println("Erreur line" + affect.lineNumber + " : le type attendu est " + typeEntry.getTypeComposite() + ", et non " + typeArray);
            }

        }
        catch (Exception e){
            System.out.println("Erreur line" + affect.lineNumber + " : le type " + idType +" n'est pas un array");
        }
        return idType;
    };


    public String visit(LvalueRecord record){
        nameIdf = true;
        String idType = record.id.accept(this);
        nameIdf = false;
        Entry oldEntry = currentEntry;
        currentEntry = listeTds.get(idCurrentTds).getTypeEntry(idType);
        if (!((TypeEntry) currentEntry).isRecord()){
            System.out.println("Erreur line" + record.lineNumber + " : le type " + idType + " n'est pas un record");
        }
        else{
            record.fieldList.accept(this);
        }
        currentEntry = oldEntry;

        return(idType);
    };


    public String visit(Call call){
        nameIdf = true;
        String id=call.id.accept(this);
        //System.out.println(id);
        nameIdf = false;
        VarFuncEntry entry = listeTds.get(idCurrentTds).getVarFuncEntry(id);
        if (entry==null) {
            System.out.println("fonction ou type "+id+" n'existe pas.");
        }
        else{
            Entry oldEntry = currentEntry;
            currentEntry = entry;
    
            //System.out.println(id);
            //listeTds.get(idCurrentTds).printTds();
            if (!entry.isFunction()){
                System.out.println("Erreur line" + call.lineNumber + " : le symbole " + id + " n'est pas une fonction");
            }
            else{
                call.listExpr.accept(this);
            }
    
            currentEntry = oldEntry;
            return(entry.getType());
        }
        return "";
        
    };


    //public String visit(RecordList affect);




}
