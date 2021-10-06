all:
	javac -d bin src/com/rabelo/*.java

servidor:
	java -cp bin com.rabelo.StartServer

cliente:
	java -cp bin com.rabelo.StartClient