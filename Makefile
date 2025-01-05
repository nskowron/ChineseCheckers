all:
	javac CheckersServer.java
	javac --module-path ./javaFX/lib --add-modules javafx.controls CheckersClient.java

clean:
	rm -rf *.class

runS:
	java CheckersServer

runC:
	java --module-path ./javaFX/lib --add-modules javafx.controls CheckersClient