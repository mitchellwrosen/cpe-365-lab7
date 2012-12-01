JAVAC = javac
JFLAGS = -d bin #-g

SOURCES = $(wildcard src/calpoly/*.java) $(wildcard src/calpoly/owner/*.java)

all:
	mkdir -p bin; $(JAVAC) $(JFLAGS) $(SOURCES)

clean:
	rm -f bin/*.class
