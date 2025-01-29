# Paths to external modules
JAVAFX_LIB=./lib
JACKSON_LIB=./lib
SPRING_LIB=./lib

# Compilation output directory
BIN_DIR=bin

# Java compilation flags for Spring, JavaFX, and Jackson
JAVAC_FLAGS=--module-path $(JAVAFX_LIB):$(JACKSON_LIB):$(SPRING_LIB) \
            --add-modules javafx.controls,com.fasterxml.jackson.databind,spring.context \
            -d $(BIN_DIR)

# Classpath for runtime
CLASS_PATH=$(BIN_DIR):$(JAVAFX_LIB)/*:$(JACKSON_LIB)/*:$(SPRING_LIB)/*

# Targets
all:
	javac $(JAVAC_FLAGS) server/CheckersServer.java
	javac $(JAVAC_FLAGS) client/CheckersClientApp.java utils/*.java shared/*.java
	javac $(JAVAC_FLAGS) replay/ReplayApp.java src/entity/*.java src/repository/*.java

clean:
	rm -rf $(BIN_DIR)/*.class
	rm -rf $(BIN_DIR)/client/*.class
	rm -rf $(BIN_DIR)/server/*.class
	rm -rf $(BIN_DIR)/shared/*.class
	rm -rf $(BIN_DIR)/utils/*.class
	rm -rf $(BIN_DIR)/replay/*.class
	rm -rf $(BIN_DIR)/src/entity/*.class
	rm -rf $(BIN_DIR)/src/repository/*.class

runS:
	java --module-path $(JAVAFX_LIB):$(JACKSON_LIB):$(SPRING_LIB) \
	     --add-modules javafx.controls,com.fasterxml.jackson.databind,spring.context \
	     -cp $(CLASS_PATH) server.CheckersServer $(ARGS)

runC:
	java --module-path $(JAVAFX_LIB) \
	     --add-modules javafx.controls \
	     -cp $(CLASS_PATH) client.CheckersClientApp

runCR:
	java --module-path $(JAVAFX_LIB) \
	     --add-modules javafx.controls \
	     -cp $(CLASS_PATH) client.CheckersClientApp resize

runRep:
	java --module-path $(JAVAFX_LIB):$(JACKSON_LIB):$(SPRING_LIB) \
	     --add-modules javafx.controls,com.fasterxml.jackson.databind,spring.context \
	     -cp $(CLASS_PATH) replay.ReplayApp $(ARGS)
