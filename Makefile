JAVAC = javac
JFLAGS = -d bin #-g

SOURCES = $(wildcard src/*.java)

all:
	$(JAVAC) $(JFLAGS) $(SOURCES)

clean:
	rm -f bin/*.class
