package assembleur;

import java.util.ArrayList;

//Par default, R12 est BP
//valeur à donner à une variable est par convention enregistrée dans R1
//pour lire une variable dans la pile, sa valeur est lue et enregistrée dans R2
public class Asr {

    private ArrayList<String> asr;

    public Asr(){
        this.asr=new ArrayList<String>();
    }

    public ArrayList<String> getAsr(){
        return this.asr;
    }



    public void setRetour(int valeur) {
        this.asr.add("    LDR R0, ="+valeur);
    }
    public void setRetour(String cond, int valeur) {
        this.asr.add("    LDR" + cond + " R0, ="+valeur);
    }



    /**
     * Cette fonction sert à empiler la valeur enregistrée dans le registre.
     * @param registre est le registre dans lequel la valeur est enregistrée*/
    public void empiler(String registre){
        incrementerSp(1);
        stockerRegistreSP(registre);
    }
    /**
     * Cette fonction sert à dépiler la pile
     * @param registre est le registre dans lequel la valeur sera enregistrée */
    public void depiler(String registre){
        lireVarSP(registre);
        decrementerSp(1);
    }

    public void newBlock(){
        empiler("r12");
        mov("r12","sp");
    }

    public void quitBlock(){
        depiler("r12");
    }

    public void stockerRegistreSP(String param){ // C'est un peu restrictif d'utiliser que R1 pour ça nan ?
        this.asr.add("    STR "+param+ ", [SP, #0]");
    }
    public void lireVarSP(String reg){ //nul, nan je rigole
        this.asr.add("    LDR "+reg+", [SP, #0]");
    }

    public void empilerSP(String registres){
        this.asr.add("    STMFA SP!, {"+registres+"}");
    }
    public void empilerSP(String cond, String registres){
        this.asr.add("    STMFA"+cond+" SP!, {"+registres+"}");
    }
    
    public void depilerSP(String registres){
        this.asr.add("    LDMFA SP!, {"+registres+"}");
    }
    public void depilerSP(String cond, String registres){
        this.asr.add("    LDMFA"+cond+" SP!, {"+registres+"}");
    }

    public void incrementerSp(int nbr){
        this.asr.add("    ADD SP, SP, #"+nbr*4);
    }
    public void decrementerSp(int nbr) {
        this.asr.add("    SUB SP, SP, #"+nbr*4);
    }
    
    
    /*
    public void empilerFlags(){
        this.asr.add("    PUSHFD");
    }
    public void depilerFlags(){
        this.asr.add("    POPFD");
    }
    */

    public void incrementerBP(int nbr){
        this.asr.add("    ADD R12, R12, #"+nbr);
    }
    public void decrementerBP(int nbr) {
        this.asr.add("    SUB R12, R12, #"+nbr);
    }// nbr présente le déplacement ici
    public void positionnerBP(String registre){
        this.asr.add("    MOV R12,"+registre);
    }
    public void stockerValeurBP(String registre,int ordrePile){
        this.asr.add("    STR "+registre+", [R12, #"+ordrePile*4+"]");
    }
    /**lireVarPile est destiné à lire un stack en référençant BP
     * @param ordrePile : nombre de stack à incrémenter pour atteindre le stack objectif
     * */
    public void lireValBP(String registre, int ordrePile){
        this.asr.add("    LDR "+registre+", [R12, #"+ordrePile*4+"]");
    }

    public void lireAdrBP(String registre){
        this.asr.add("    MOV "+registre+", R12");
    }


    public void empilerHP(String registre){
        stockerRegistreHP(registre);
        decrementerHP(1);
    }
    /**
     * Cette fonction sert à dépiler la pile
     * @param registre est le registre dans lequel la valeur sera enregistrée */
    public void depilerHP(String registre){
        incrementerHP(1);
        lireVarHP(registre);
    }
    public void stockerRegistreHP(String param){ // C'est un peu restrictif d'utiliser que R1 pour ça nan ?
        this.asr.add("    STR "+param+ ", [R11, #0]");
    }
    public void stockerRegistreHPDepl(String param, int depl){ // C'est un peu restrictif d'utiliser que R1 pour ça nan ?
        this.asr.add("    STR "+param+ ", [R11, #"+ depl +"]");
    }
    public void lireVarHP(String reg){ //nul, nan je rigole
        this.asr.add("    LDR "+reg+", [R11, #0]");
    }
    public void lireAdrHP(String registre){ //nul, nan je rigole
        this.asr.add("    MOV "+registre+", R11");
    }

    public void incrementerHP(int nbr){
        this.asr.add("    ADD R11, R11, #"+nbr*4);
    }
    public void decrementerHP(int nbr) {
        this.asr.add("    SUB R11, R11, #"+nbr*4);
    }

    public void positionneHP(String registreSrc){
        this.asr.add("    MOV R11,"+registreSrc);
    }


    public void  plus(String param1,String param2,String param3){
        this.asr.add("    ADD "+param1+", "+param2+", "+param3); //R1-R2=>R1
    }
    public void  moins(String param1,String param2,String param3){
        this.asr.add("    SUB "+param1+", "+param2+", "+param3); //R1-R2=>R1
    }
    public void or(String regDest, String reg1, String reg2){
        this.asr.add("    ORR " + regDest + ", " + reg1 + ", " + reg2);
    }
    public void multiplie(String regDest, String reg1, String reg2){
        this.asr.add("    mul " + regDest + ", " + reg1 + ", " + reg2);
    }
    public void negate(String reg){
        this.asr.add("    RSB " + reg + ", " + reg + ", #0");
    }


    public void enregistreValeur(String registre1,String registre2){
        this.asr.add( "    MOV "+registre1+ ","+registre2);
    }
    public void cmp(String reg1, String reg2){
        this.asr.add("    CMP "+reg1+", "+reg2);
    }
    public void cmp(String cond, String reg1, String reg2){
        this.asr.add("    CMP"+cond+" "+reg1+", "+reg2);
    }

    public void mov(String op1, String op2){
        this.asr.add("    MOV " + op1 +", " +op2);
    }

    public void charSuivant(String registre){
        this.asr.add("    SUB "+registre+", "+registre+", #4");
    }
    public void charSuivant(String cond, String registre){
        this.asr.add("    SUB"+cond+ " "+registre+", "+registre+", #4");
    }
    public void lireVarReg(String registreRecepteur, String registrePointeur){
        this.asr.add("    LDR " + registreRecepteur + " , [" + registrePointeur + "]");
    }
    public void ecrireVarReg(String registreSource, String registreReceveur){
        this.asr.add("    STR " + registreSource + " , [" + registreReceveur+ "]");
    }

    public void b(String flag){
        this.asr.add("    B " + flag);
    }
    public void b(String cond, String flag){
        this.asr.add("    B" + cond + " " + flag);
    }

    public void link(String label){
        this.asr.add("    BL " + label);
    }

    public void and(String regDest, String reg1, String reg2){
        this.asr.add("    AND " + regDest + ", " + reg1 + ", " + reg2);
    }
    public void and(String cond, String regDest, String reg1, String reg2){
        this.asr.add("    AND" + cond + " " + regDest + ", " + reg1 + ", " + reg2);
    }
    

    public void label(String label){
        this.asr.add(label);
    }

    public void addFunction(String content){
        this.asr.add(content);
    }

    public void end(){
        this.asr.add("    END");
    }

}
