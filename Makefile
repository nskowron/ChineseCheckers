all:
	javac --module-path ./javaFX/lib --add-modules javafx.controls -d bin server/CheckersServer.java client/CheckersClient.java shared/*.java utils/*.java

clean:
	rm -rf *.class

runS:
	java --module-path ./javaFX/lib --add-modules javafx.controls -cp bin server.CheckersServer

runC:
	java --module-path ./javaFX/lib --add-modules javafx.controls -cp bin client.CheckersClient