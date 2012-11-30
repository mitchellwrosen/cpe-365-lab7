JAVAC = javac
JFLAGS = -d bin #-g

SOURCES = $(wildcard src/calpoly/*.java) $(wildcard src/calpoly/owner/*.java)

all:
	$(JAVAC) $(JFLAGS) $(SOURCES)

clean:
	rm -f bin/*.class
