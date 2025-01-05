all:
	javac --module-path ./javaFX/lib --add-modules javafx.controls CheckersServer.java
	javac --module-path ./javaFX/lib --add-modules javafx.controls CheckersClient.java

clean:
	rm -rf *.class

runS:
	java --module-path ./javaFX/lib --add-modules javafx.controls CheckersServer

runC:
	java --module-path ./javaFX/lib --add-modules javafx.controls CheckersClient