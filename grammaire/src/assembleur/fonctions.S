
; LA DIVISION

div		;Division de R0 par R11 et res dans R11
		STMFA	SP!, {R0, R3-R6}
		
		MOV		R6, #0		;Registre de res, on fait tendre R6 vers res et on mov dans R11 à la fin
		MOV		R3, #0		;0 pour res positif, 1 pour res négatif
		
		CMP		R0, #0
		RSBLT	R0, R0, #0 	;R0 = |R0|
		EORLT	R3, R3, #1	;R3 = 1 mais autant faire un MOV
		
		CMP		R11, #0
		RSBLT	R11, R11, #0
		EORLT	R3, R3, #1
		
		
		MOV		R4, R11		;Transfert dans registre de travail
		MOV		R5, #1
		
_div_max	
		LSL		R4, R4, #1	;Multiplie R4(R11) par 2
		LSL		R5, R5, #1	;Multiplie R5 par 2
		CMP		R4, R0		;Jusqu'à ce que R4(R11) > R0, on dépasse l'égalité délibérément
		BLE		_div_max
		
		
_div_loop	
		LSR		R4, R4, #1	;Divise R4(R11) par 2
		LSR		R5, R5, #1	;Divise R5 par 2
		CMP		R4, R0		;Jusqu'à ce que R4(R11) <= R0 DONC 3 <= 12/R5 soit R5 <= 12/3
		BGT		_div_loop
		
		ADD		R6, R6, R5
		SUB		R0, R0, R4
		CMP		R0, R11		;Jusqu'à ce que R0 < R11 SOIT R0 n'est plus divisible par R11
		BGE		_div_loop
		
		CMP		R3, #1		;Si res est négatif
		BNE		_div_exit
		CMP		R0, #0		;S'il y a un reste de 1
		ADDNE	R6, R6, #1	;on arrondi à l'entier sup
		RSB		R6, R6, #0	;On remet les nombres en négatifs
		RSB		R0, R0, #0
		ADDNE	R0, R0, R1	;Différence entre le résultat exacte et approximé car entier
		
		
_div_exit	
		MOV		R11, R6
		LDMFA	SP!, {R0, R3-R6}
		MOV		PC, LR


; LA MULTIPLICATION

mult						; On multiplie r0 et r11, et le resultat sort en r1
		MOV		r2,#0
		MOV		r1,#0


_mult_loop		
		LSR		r4,r0,#1	; r4 indique si le dernier bit de r0 et un 1 ou un 0
		LSL		r4,r4,#1
		CMP		r0,r4
		BEQ		_mult_zero	; si le dernier bit est un zero
		ADD		r1,r1,r11,LSL r2


_mult_zero	
		ADD		r2,r2,#1
		LSR		r0,r0,#1
		CMP		r0,#0
		BNE		_mult_loop	; Si r0 n'est pas égale à zéro on continue la loop sinon FIN

		