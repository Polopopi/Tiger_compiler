%:	
	java -jar ./lib/antlr-4.9.2-complete.jar  ./tiger.g4 -no-listener -visitor -o ./src/parser
	java -jar ./lib/visual_headless.jar ./out/asr.S
	javac -cp  ./lib/visual_headless.jar:./lib/antlr-4.9.2-complete.jar:./src ./src/Main.java -d ./bin
	java -cp ./lib/visual_headless.jar:./lib/antlr-4.9.2-complete.jar:./bin Main ../exemples/Good/$@.exp
	dot -Tsvg ./out/AST.dot -o ./out/AST.svg
	