all:
	javac --module-path ./javaFX/lib --add-modules javafx.controls -d bin server/CheckersServer.java client/CheckersClientApp.java shared/*.java utils/*.java

clean:
	rm -rf ./bin/client/*.class
	rm -rf ./bin/server/*.class
	rm -rf ./bin/shared/*.class
	rm -rf ./bin/utils/*.class

runS:
	java --module-path ./javaFX/lib --add-modules javafx.controls -cp bin server.CheckersServer

runC:
	java --module-path ./javaFX/lib --add-modules javafx.controls -cp bin client.CheckersClientApp