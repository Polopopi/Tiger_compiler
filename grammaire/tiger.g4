grammar tiger;
 
@header{
package parser;
}
 
program : expr EOF ;
 
expr	: string-constant 
| integer-constant 
| nil 
| lvalue 
| - expr 
| expr binary-operator expr 
| lvalue := expr 
| id ( expr-list ) // optional expr-list
| ( expr-seq ) // optional expre-seq
| type-id { field-listopt } 
| type-id [ expr ] of expr 
| if expr then expr if expr 
| then expr else expr 
| while expr do expr 
| for id := expr to expr do expr 
| break 
| let declaration-list in expr-seq end // optional expre-seq
;
 
expr-seq : expr 
| expr-seq ; expr
; 
 
expr-list: expr
| expr-list , expr 
;
 
field-list: id = expr 
| field-list , id = expr 
 
lvalue: id 
| lvalue . id 
| lvalue [ expr ]
	;
declaration-list: declaration 
| declaration-list declaration 
;
 
declaration: type-declaration 
| variable-declaration 
| function-declaration
;
 
type-declaration: type type-id = type 
	;
 
type: type-id 
| { type-fields } // optional declaration
| array of type-id 
;
 
type-fields: type-field 
| type-fields , type-field 
;
 
type-field: id : type-id
	;
 
 
 
 
 
// Les terminaux (def des exp régulières reconnaissant les tokens)
 
ID     : 'a'..'z'|'A'..'Z')(‘_’|'a'..'z'|'A'..'Z'|'0'..'9')*
       ;
 
INT    : ('0'..'9')+
       ;
 
STR    : ‘“‘(‘!’|'#'..'[' | ']'..'~'|’\n’|’\t’|’\\’|’\”’|’\^c’|’\ddd’|’ ‘)*’”’
	  ;
 
WS     : [ \n\t\r]+ ->skip
       ;
 
COM    : [(‘\*’.*‘*\’)]+ ->skip
       ;
 
