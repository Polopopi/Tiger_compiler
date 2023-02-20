package assembleur;

import java.io.PrintWriter;

//Par default, R12 est BP
//valeur à donner à une variable est par convention enregistrée dans R1
//pour lire une variable dans la pile, sa valeur est lue et enregistrée dans R2
public class Asr {


    public String incrementerSp(int nbrVar){
        return "ADD SP, SP, #"+nbrVar*4;
    }
    public String donneVar(int valeur){
        return "LDR R1, ="+valeur ;
    }

    public String positionnerBP(){
        return "MOV R12,SP";
    }

    public String stockerValeur(int ordrePile){
        return  "STR R1, [R12, #"+ordrePile*4+"]";
    }

    public String lireVarPile(int ordrePile){
        return "LDR R2, [R12, #"+ordrePile*4+"]";
    }




}
