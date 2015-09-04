#!/usr/bin/python
# -*- coding: latin-1 -*-

import sys

fichier = open(sys.argv[1], "r")
labels=open(sys.argv[1]+"_labels", "w")
graph= open(sys.argv[1]+"_arcs", "w")
for ligne in fichier:
	if len(ligne.split(" ")) > 1:
		if ligne.split(" ")[1].startswith("\""):
			labels.write(ligne.split(" ")[1].replace("\"", "")+"\n")
		elif not ligne.startswith("*"):
			tab=ligne.split(" ")
			graph.write(str(int(tab[0]) - 1) + " " + str(int(tab[1]) - 1) + "\n")
graph.close()
labels.close()
fichier.close()
