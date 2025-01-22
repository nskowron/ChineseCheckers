# Paths to external libraries
JAVAFX_LIB=./lib
JACKSON_LIB=./lib

# Compilation output directory
BIN_DIR=bin

# Java compilation flags
JAVAC_FLAGS=--module-path $(JAVAFX_LIB):$(JACKSON_LIB) --add-modules javafx.controls,com.fasterxml.jackson.databind -d $(BIN_DIR)

# Targets
all:
	javac $(JAVAC_FLAGS) server/CheckersServer.java
	javac $(JAVAC_FLAGS) client/CheckersClientApp.java utils/*.java shared/*.java
	javac $(JAVAC_FLAGS) replay/ReplayApp.java

clean:
	rm -rf $(BIN_DIR)/client/*.class
	rm -rf $(BIN_DIR)/server/*.class
	rm -rf $(BIN_DIR)/shared/*.class
	rm -rf $(BIN_DIR)/utils/*.class
	rm -rf $(BIN_DIR)/replay/*.class

runS:
	java --module-path $(JAVAFX_LIB):$(JACKSON_LIB) --add-modules javafx.controls,com.fasterxml.jackson.databind -cp $(BIN_DIR) server.CheckersServer $(ARGS)

runC:
	java --module-path $(JAVAFX_LIB) --add-modules javafx.controls -cp $(BIN_DIR) client.CheckersClientApp

runCR:
	java --module-path $(JAVAFX_LIB) --add-modules javafx.controls -cp $(BIN_DIR) client.CheckersClientApp resize

runRep:
	java --module-path $(JAVAFX_LIB):$(JACKSON_LIB) --add-modules javafx.controls,com.fasterxml.jackson.databind -cp $(BIN_DIR) replay.ReplayApp $(ARGS)

