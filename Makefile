JAVAC = javac
JFLAGS = -d bin #-g

SOURCES = $(wildcard src/*.java)
CLASSES = $(SOURCES:.java=.class)

all: $(CLASSES)

%.class : %.java
	$(JAVAC) $(JFLAGS) $(SOURCES)

clean:
	rm -f bin/*.class
