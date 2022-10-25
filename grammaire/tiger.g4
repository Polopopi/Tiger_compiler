grammar tiger;
 
@header{
package parser;
}
 
program : expr EOF ;
 
expr	: STR
       | INT
       | 'nil'
       ;

// J'ai tout supprimer pour l'instant pour pas qu'on soit trop influencé, on peut s'inspirer de la grammaire du manuel,
//  mais il y a beaucoup de chose récursive gauche à traité et de factorisation à faire.
// Adrien

 
// Les terminaux (def des exp régulières reconnaissant les tokens)
 
ID     : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
       ;
 
TYPE_ID     : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
       ;

INT    : ('0'..'9')+
       ;
 
STR    : '"'('a'..'z'|'A'..'Z'|'0'..'9')*'"'
	  ;
 
WS     : [ \n\t\r]+ ->skip
       ;
 
COM    : [(‘\*’.*‘*\’)]+ ->skip
       ;
 
