package assembleur;

import ast.*;
import tds.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AsrCreator implements AstVisitor<String> {
    private Asr asr;
    private ArrayList<Tds> listTds;
    private Tds currentTds;
    private int oldTdsId; //???
    private int idGenerator;
    private ArrayList<String> loopLabel;
    private TypeEntry currentTypeEntry;
    private boolean nameIdf = false;



    private Tds getTds(){
        return(listTds.get(oldTdsId++));
    }

    

    private int generateId(){
        return(idGenerator++);
    }

    private String generateLabel(){
        return("label"+generateId());
    }

    public AsrCreator(){
        this.asr=new Asr();
        this.idGenerator = 0;
        this.loopLabel = new ArrayList<String>();
    }

    public void asrFichier(String asrFileName) throws IOException {
        ArrayList<String> data=asr.getAsr();
        Path fichier= Paths.get(asrFileName);
        Files.write(fichier,data, StandardCharsets.UTF_8);
    }


    @Override
    public String visit(Print affect) {
        return null;
    }

    @Override
    public String visit(Affect affect) {
        return null;
    }

    @Override
    public String visit(Program program) {
        String endLabel = generateLabel();

        asr.b(endLabel);

        try {
            Path path = Path.of("./src/assembleur/fonctions.S");
            String div_str = Files.readString(path);
            asr.addFunction(div_str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        asr.label(endLabel);

        asr.mov("R11", "SP");

        String texte = program.affect.accept(this);

        asr.end();

        return null;
    }

    @Override
    public String visit(Or or) {
        String left = or.left.accept(this);

        asr.cmp("R0", "#0");
        asr.setRetour("NE", 1);
        String endLabel = generateLabel();
        asr.b("NE", endLabel);

        asr.empiler("R0");

        //asr.empilerFlags();
        String right = or.right.accept(this);
        //asr.depilerFlags();

        asr.depiler("R1");
        
        asr.or("R0", "R1", "R0");

        asr.label(endLabel);

        return null;
    }

    @Override
    public String visit(And and) {
        String left = and.left.accept(this);

        asr.cmp("R0", "#0");
        asr.setRetour("EQ", 0);
        String endLabel = generateLabel();
        asr.b("EQ", endLabel);

        asr.empiler("R0");

        //asr.empilerFlags();
        String right = and.right.accept(this);
        //asr.depilerFlags();

        asr.depiler("R1");
        
        asr.or("R0", "R1", "R0");

        asr.label(endLabel);

        return null;
    }

    @Override
    public String visit(Equal equal) {
        String left = equal.left.accept(this);

        asr.empiler("R0");

        String right = equal.right.accept(this);

        asr.depiler("R1");

        if (right.equals("String")){

            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            //Tant qu'on peut avancer et que tout les char sont égaux
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");

            // Pour passer à la case vide suivante (replacer le HP)
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("EQ", 1);
            asr.setRetour("NE", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("EQ", 1);
            asr.setRetour("NE", 0);
        }

        return null;
    }

    @Override
    public String visit(Diff diff) {
        String left = diff.left.accept(this);

        asr.empiler("R0");

        String right = diff.right.accept(this);

        asr.depiler("R1");

        if (right.equals("String")){

            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont égaux
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("NE", 1);
            asr.setRetour("EQ", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("NE", 1);
            asr.setRetour("EQ", 0);
        }

        return null;
    }

    @Override
    public String visit(Inf inf) {
        String left = inf.left.accept(this);

        asr.empiler("R0");

        String right = inf.right.accept(this);

        asr.depiler("R1");

        if (right.equals("String")){
            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont inférieurs
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("LT", 1);
            asr.setRetour("GE", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("LT", 1);
            asr.setRetour("GE", 0);
        }

        return null;
    }

    @Override
    public String visit(Sup sup) {
        String left = sup.left.accept(this);

        asr.empiler("R0");

        String right = sup.right.accept(this);

        asr.depiler("R1");

        if (right.equals("String")){
            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont inférieurs
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("GT", 1);
            asr.setRetour("LE", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("GT", 1);
            asr.setRetour("LE", 0);
        }

        return null;
    }

    @Override
    public String visit(InfEqual infEqual) {
        String left = infEqual.left.accept(this);

        asr.empiler("R0");

        String right = infEqual.right.accept(this);

        asr.depiler("R1");

        if (right.equals("String")){
            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont inférieurs
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("LE", 1);
            asr.setRetour("GT", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("LE", 1);
            asr.setRetour("GT", 0);
        }

        return null;
    }

    @Override
    public String visit(SupEqual supEqual) {
        String left = supEqual.left.accept(this);

        asr.empiler("R0");

        String right = supEqual.right.accept(this);

        asr.depiler("R1");

        if (right.equals("String")){
            String loopLabel = generateLabel();
            String endLabel = generateLabel();

            asr.label(loopLabel);

            asr.lireVarReg("R3", "R1");
            asr.lireVarReg("R2", "R0");

            // Si on est à la fin des 2 strings
            asr.and("R5", "R3", "#0x00000003");
            asr.cmp("R5", "#0");
            asr.b("EQ", endLabel);
            asr.and("R4", "R2", "#0x00000003");
            asr.cmp("R4", "#0");
            asr.b("EQ", endLabel);

            asr.cmp("R3", "R2");

            asr.charSuivant("R1");
            asr.charSuivant("R0");

            //Tant qu'on peut avancer et que tout les char sont inférieurs
            asr.b("EQ", loopLabel);

            asr.label(endLabel);
            asr.cmp("R3", "R2");
            asr.charSuivant("R1");
            asr.charSuivant("R0");

            asr.setRetour("GE", 1);
            asr.setRetour("LT", 0);
        }
        else{
            asr.cmp("R1", "R0");
            asr.setRetour("GE", 1);
            asr.setRetour("LT", 0);
        }

        return null;
    }

    @Override
    public String visit(Plus plus) {
        String left= plus.left.accept(this);
        
        asr.empiler("R0");

        String right=plus.right.accept(this);

        asr.depiler("R1");

        asr.moins("R0","R1","R0");

        return null;
    }

    @Override
    public String visit(Minus minus) {
        String left= minus.left.accept(this);
        
        asr.empiler("R0");

        String right=minus.right.accept(this);

        asr.depiler("R1");

        asr.moins("R0","R1","R0");

        return null;
    }

    @Override
    public String visit(Mult mult) {
        String left = mult.left.accept(this);

        asr.empiler("R0");

        String right = mult.right.accept(this);

        asr.depiler("R1");
        
        asr.link("mult");

        return null;
    }

    @Override
    public String visit(Divide divide) {
        String left = divide.left.accept(this);

        asr.empiler("R0");

        String right = divide.right.accept(this);

        asr.depiler("R1");
        
        asr.link("div");

        return null;
    }


    @Override
    public String visit(MinusExpr minusExpr) {
        minusExpr.expr.accept(this);
        asr.negate("R0");
        return null;
    }

    @Override
    public String visit(IfThen ifThen) {
        String thenLabel = generateLabel();
        String endLabel = generateLabel();

        ifThen.condition.accept(this);
        asr.cmp("r0","#0");
        asr.b("NE", thenLabel);
        asr.b(endLabel);
        asr.label(thenLabel);
        ifThen.thenBlock.accept(this);
        asr.label(endLabel);
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        String thenLabel = generateLabel();
        String elseLabel = generateLabel();

        ifThenElse.condition.accept(this);
        asr.cmp("r0","#0");
        asr.b("NE", thenLabel);
        asr.b(elseLabel);
        
        asr.label(thenLabel);
        ifThenElse.thenBlock.accept(this);
        asr.label(elseLabel);
        ifThenElse.elseBlock.accept(this);
        return null;
    }

    @Override
    public String visit(Let let) {
        let.declarationList.accept(this);
        let.seqExpr.accept(this);
        return null;
    }

    @Override
    public String visit(For forNode) {
        String forLabel = generateLabel();
        String forEndLabel = generateLabel();
        Tds oldTds = currentTds;

        currentTds=getTds();
        forNode.debut.accept(this); 
        asr.empiler("r0"); //on empile la variable i
        asr.newBlock(); //on entre dans le bloc (on met le chainage en place)
        forNode.fin.accept(this); 
        asr.empiler("rO"); //on stock dans la pile la valeur limite
        asr.label(forLabel);
        asr.lireValBP("r1", 1);         // récupère i
        asr.lireValBP("r2", -1);                  // récupère valeur limite
        asr.cmp( "r2","r1");     //compare i et valeur limite
        asr.b("N",forEndLabel);

        loopLabel.add(forEndLabel);
        forNode.bloc.accept(this);
        loopLabel.remove(loopLabel.size()-1);

        asr.lireValBP("r1", -1); //on récupère i dans r1
        asr.plus("r1","r1","#1");  //On ajoute 1 dans i
        asr.ecrireVarReg("r1", "r11,#4*-1"); // on remet i a jour dans la pile
        asr.b(forLabel);
        asr.label(forEndLabel);
        asr.moins("r13","r13", "#4"); //depile la valeur limite
        asr.quitBlock();
        asr.moins("r13","r13", "#4"); // depile la variable i
        currentTds=oldTds;

        

        return null;
    }

    @Override
    public String visit(While whileNode) {
        String whileLabel = generateLabel();
        String whileEndLabel = generateLabel();

        asr.label(whileLabel);
        whileNode.condition.accept(this);
        asr.cmp("r0", "#0");
        asr.b("EQ", whileEndLabel);

        loopLabel.add(whileEndLabel);
        whileNode.bloc.accept(this);
        loopLabel.remove(loopLabel.size()-1);

        asr.b(whileLabel);
        asr.label(whileEndLabel);

        return null;
    }

    @Override
    public String visit(BreakExpr affect) {
        asr.b(loopLabel.get(loopLabel.size()-1));
        return null;
    }

    @Override
    public String visit(NilExpr affect) {
        asr.setRetour(0);
        return null;
    }

    @Override
    public String visit(IntExpr intExpr) {
        asr.setRetour(intExpr.value);
        return "int";
    }

    @Override
    public String visit(StrExpr strExpr) {
        asr.mov("R0", "R11"); //Adresse pour le pointeur de la string dans R0

        String str = strExpr.value;

        int nbLoop = str.length()/4;

        for (int i = 0; i < nbLoop / 4; i++){
            asr.mov("R1", "#0");
            asr.mov("R1", "#"+(int)str.charAt(i)*Math.pow(10,6));
            asr.mov("R1", "#"+(int)str.charAt(i)*Math.pow(10,4));
            asr.mov("R1", "#"+(int)str.charAt(i)*Math.pow(10,2));
            asr.mov("R1", "#"+(int)str.charAt(i)*Math.pow(10,0)); //???

            asr.empilerHP("R1");
        }

        int remaining = str.length() % 4;

        asr.mov("R1", "#0");
        for (int i = 0; i < remaining; i++){
            asr.mov("R1", "#"+(int)str.charAt(str.length() - remaining + i)*Math.pow(10,6-2*i));
            asr.empilerHP("R1");
        }

        return "String";
    }

    @Override
    public String visit(SeqExpr seqExpr) {
        for (Ast expr:seqExpr.listExpr){
            expr.accept(this);
        }

        return null;
    }

    @Override
    public String visit(DeclarationList declarationList) {
        for (Ast expr:declarationList.listAst){
            expr.accept(this);
        }
        return null;
    }

    // Paramètre d'appel de fonction
    @Override
    public String visit(ListExpr listExpr) {
        for (Ast expr : listExpr.listExpr){
            expr.accept(this);
            // On empile le paramètre
            // int passage par valeur, sinon pointeur
            asr.empiler("R0");
        }
        return null;
    }

    @Override
    public String visit(Type_Declaration affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(Type_Fields affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(Type_Field affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(TypeType affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(TypeRecord affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(TypeRecordVoid affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(TypeArray affect) {
        return null; // rien à faire
    }

    @Override
    public String visit(VarDeclaration varDeclaration) {
        //String id = varDeclaration.idf.accept(this); //c'est pour trouver la valeur de déplacement.
        varDeclaration.expr.accept(this);
        //int deplacement = this.currentTds.getVarFuncEntry(id).getDeplacement();
        asr.empilerHP("R0");

        //La variable il faut juste l'empiler dans la pile
        return null;
    }

    @Override
    public String visit(VarDeclarationType affect) {//rien à faire
        return null;
    }

    // Appel d'un record avec initialisation des fields pour declaration variable
    @Override
    public String visit(LvalueRecord lvalueRecord) {
        asr.empiler("R11");

        // On récupère le nom du record
        nameIdf = true;
        currentTypeEntry = this.currentTds.getTypeEntry(lvalueRecord.id.accept(this));
        nameIdf = false;
        
        lvalueRecord.fieldList.accept(this);

        asr.depiler("R0");
        return null;
    }

    // Fields d'appel de record pour déclaration d'une variable
    @Override
    public String visit(FieldList fieldList) {
        int recordSize = 0;
        // Ajout dans le tas de chaque field (pointeur ou int)
        for (Ast field : fieldList.listAst){
            field.accept(this);
            recordSize++;
        }

        asr.decrementerHP(recordSize);
        return null; 
    }

    @Override
    public String visit(Field field) {
        // Ajouter le field dans le tas à l'endroit correspondant à son déplacement
        field.expr.accept(this);
        int depl = ((RecordEntry)currentTypeEntry).getField(field.id.accept(this)).getDeplacement();
        asr.stockerRegistreHPDepl("R0", depl*4);
        return null; 
    }

    @Override
    public String visit(Array array) {
        asr.empiler("R11");

        nameIdf = true;
        currentTypeEntry = this.currentTds.getTypeEntry(array.id.accept(this));
        nameIdf = false;

        array.exprOr1.accept(this);
        asr.mov("R1", "R0"); // R1 = taille du tableau

        String loopLabel = generateLabel();
        asr.label(loopLabel);

        // ASR va boucler sur l'expr2 qui peut donc générer en boucle des strings ou des array/record
        // Et tout simplement renvoyer le pointeur dans R0
        array.exprOr2.accept(this); 
        asr.empilerHP("R0");

        asr.moins("R1", "R1", "#1");
        asr.cmp("R1", "#0");
        asr.b("NE", loopLabel);

        asr.depiler("R0");
        return null;
    }

    @Override
    public String visit(FctDeclaration fctDeclaration) {
        String beginLabel = generateLabel();
        String endLabel = generateLabel();

        asr.mov("r1","PC");
        asr.plus("r1", "r1", "#8");
        asr.empiler("r1");
        asr.b(endLabel);

        asr.label(beginLabel);
        asr.empiler("CS,r12,LR");
        
        fctDeclaration.exprAffect.accept(this);

        asr.depiler("r12,PC");
        asr.label(endLabel);

        return(null);
    }

    @Override
    public String visit(ProcDeclaration procDeclaration) {
        String beginLabel = generateLabel();
        String endLabel = generateLabel();

        asr.mov("r1","PC");
        asr.plus("r1", "r1", "#8");
        asr.empiler("r1");
        asr.b(endLabel);

        asr.label(beginLabel);
        asr.empiler("CS,r12,LR");
        
        procDeclaration.exprAffect.accept(this);

        asr.depiler("r12,PC");
        asr.label(endLabel);

        return(null);
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
    public String visit(Idf affect) {
        if (nameIdf == true){
            return affect.name;
        }

        String id = affect.name;
        Tds tdsCourant = currentTds;

        asr.lireAdrBP("R1");// enregistrer l'adresse de BP dans R1

        while (tdsCourant.existLocalVarFunc(id) == false){ // s'il y a pas de var ou fonctions locales, alors on doit
            // se déplacer dans la dernière imbrication
            asr.lireValBP("R0",0);//lire le chaînage statique et l'enregistre dans R0
            asr.positionnerBP("R0");//positionne BP vers l'adresse du chaînage statique

            tdsCourant = tdsCourant.getParent();

        }
        // si var est locale
        int deplacement = currentTds.getVarFuncEntry(id).getDeplacement();
        asr.decrementerBP(deplacement);
        asr.lireValBP("R0",deplacement);// lire l'adresse de var cherché et l'enregistrer dans R0
        asr.positionnerBP("R1");//remettre l'ancien adr
        //imaginons que le pointeur est déjà bien pointé
        // Attention quand on est dans le membre gauche d'une affectation (noeud Affect),
        // on doit retourner l'adresse de la var qui est dans la pile
        // on pourrait faire comme pour la tds en mettant des booléens pour savoir
        // si on est dans un noeud affect ou pas
        // si on est dans un noeud lvalueField ou pas

        // Ajouter nameIdf comme pour la tds (besoin pour lvalueRecord)
        return null;
    }
    @Override
    public String visit(LvalueField affect) {
        affect.left.accept(this);// on cherche l'adresse lorsque on visite la partie left et on l'enregistre
                                        //dans R0
        asr.lireAdrHP("R1");
        //on pointe R11 vers le lvalue
        asr.positionneHP("R0");
        this.nameIdf = true;
        String idf = affect.id.accept(this);
        this.nameIdf = false;
        int deplacement = currentTds.getTypeEntry(idf).getDeplacement();
        asr.incrementerHP(deplacement);
        asr.lireVarHP("R0");//On enregistre l'adresse de cet élément dans R0
        asr.positionneHP("R1");
        return null;
    }

    @Override
    public String visit(LvalueIndex affect) {
        // Attention quand on est dans le membre gauche d'une affectation (noeud Affect), 
        // on doit retourner l'adresse de l'index qui est dans le tas et non sa valeur pcq si c'est un int ça a pas de sens
        return null;
    }

    @Override
    public String visit(Call affect) {
        return null;
    }
}
