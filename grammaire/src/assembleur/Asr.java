package assembleur;

import java.io.PrintWriter;
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



    public void setVar(int valeur) {
        this.asr.add("    LDR R11, ="+valeur);
    }
    public void setVar(String cond, int valeur) {
        this.asr.add("    LDR" + cond + " R11, ="+valeur);
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

    public void stockerRegistreSP(String param){ // C'est un peu restrictif d'utiliser que R1 pour ça nan ?
        this.asr.add("    STR "+param+ ", [SP, #0]");
    }
    public void empilerValeurs(String registres){
        this.asr.add("    STMFA SP!, {"+registres+"}");
    }
    public void empilerValeurs(String cond, String registres){
        this.asr.add("    STMFA"+cond+" SP!, {"+registres+"}");
    }
    
    public void depilerValeurs(String registres){
        this.asr.add("    LDMFA SP!, {"+registres+"}");
    }
    public void depilerValeurs(String cond, String registres){
        this.asr.add("    LDMFA"+cond+" SP!, {"+registres+"}");
    }

    public void incrementerSp(int nbr){
        this.asr.add("    ADD SP, SP, #"+nbr*4);
    }
    public void decrementerSp(int nbr) {
        this.asr.add("    SUB SP, SP, #"+nbr*4);
    }
    /**stockerValeurSP est destiné à stocker une valeur dans le stack qui est pointé par SP
     * */
    public void stockerValeurSP(){ // C'est un peu restrictif d'utiliser que R1 pour ça nan ?
        this.asr.add("    STR R1, [SP, #0]");
    }
    public void lireVarSP(String reg){ //nul, nan je rigole
        this.asr.add("    LDR "+reg+", [SP, #0]");
    }
    
    /*
    public void empilerFlags(){
        this.asr.add("    PUSHFD");
    }
    public void depilerFlags(){
        this.asr.add("    POPFD");
    }
    */


    public void positionnerBP(){
        this.asr.add("    MOV R12,SP");
    }
    public void stockerValeurBP(String registre,int ordrePile){
        this.asr.add("    STR "+registre+", [R12, #"+ordrePile*4+"]");
    }
    /**lireVarPile est destiné à lire un stack en référençant BP
     * @param ordrePile :nombre de stack à incrémenter pour atteindre le stack objectif
     * */
    public void lireVarPile(String registre,int ordrePile){
        this.asr.add("    LDR "+registre+", [R12, #"+ordrePile*4+"]");
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

    public void mov(String op1, String op2){
        this.asr.add("    MOV " + op1 +", " +op2);
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
