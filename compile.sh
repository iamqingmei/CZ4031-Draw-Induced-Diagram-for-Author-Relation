d=`pwd`
export CLASSPATH=.:$d/Library/antlr4-runtime-4.5.3.jar:$d/Library/jgraph-5.13.0.0.jar:$d/Library/jgrapht-core-1.0.0.jar:$d/Library/jgrapht-demo-1.0.0.jar:$d/Library/jgrapht-ext-1.0.0-uber.jar:$d/Library/jgrapht-ext-1.0.0.jar:$d/Library/jgraphx-2.0.0.1.jar:$d/Library/postgresql-9.4.1211.jre6.jar;
 
javac -d $d/bin $d/src/graph/JGraphAdaptor.java $d/src/MainClass.java
