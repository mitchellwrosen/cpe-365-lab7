JAVAC = javac
JFLAGS = -d bin -cp ojdbc14.jar #-g

SOURCES = $(wildcard src/*.java)
CLASSES = $(SOURCES:.java=.class)

all: $(CLASSES)

%.class : %.java
	$(JAVAC) $(JFLAGS) $<

clean:
	rm -f bin/*.class
