##############################################################################
#                                                                             #
#                                 makefile                                    #
#                                                                             #
#   This is a generic makefile for compiling java files, written by Sam Lu.   #
#                                                                             #
#   Source: https://gist.github.com/senmu/1277486                             #
#                                                                             #
###############################################################################

JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) *.java

default: .java.class

clean:
	$(RM) *.class