JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java.class:
			$(JC) $(JFLAGS) $*.java

CLASSES = Elenco.java 

default: classes elenco

classes: $(CLASSES:.java=.class)

elenco: 
	echo '#! /usr/bin/env bash' > elenco
	echo 'java Elenco $${@:1}' >> elenco
	chmod a+x elenco

clean:
		$(RM) elenco *.class