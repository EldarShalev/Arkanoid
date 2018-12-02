#Eldar Shalev
# 312349103
# shaleve5

compile: bin
	javac -d bin -cp biuoop-1.4.jar:. src/*/*.java
jar:
	jar cfm ass6game.jar Manifest.mf -C bin . -C resources .
run:
	java -cp biuoop-1.4.jar:bin:resources game.Ass6Game
bin:
	mkdir bin
