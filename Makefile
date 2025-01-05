all:
	javac CheckersServer.java

clean:
	rm -rf *.class

runS:
	java CheckersServer

runC:
	java --module-path ./javaFX/lib --add-modules javafx.controls CheckersClient
	javac --module-path ./javaFX/lib --add-modules javafx.controls CheckersClient.java