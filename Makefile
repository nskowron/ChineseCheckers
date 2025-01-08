all:
	javac --module-path ./Jackson --add-modules com.fasterxml.jackson.databind -d bin server/CheckersServer.java
	javac --module-path ./javaFX/lib --add-modules javafx.controls -d bin client/CheckersClientApp.java utils/*.java shared/*.java

clean:
	rm -rf ./bin/client/*.class
	rm -rf ./bin/server/*.class
	rm -rf ./bin/shared/*.class
	rm -rf ./bin/utils/*.class

runS:
	java --module-path ./Jackson --add-modules com.fasterxml.jackson.databind -cp bin server.CheckersServer

runC:
	java --module-path ./javaFX/lib --add-modules javafx.controls -cp bin client.CheckersClientApp