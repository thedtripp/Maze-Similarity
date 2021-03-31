//DANIEL TRIPP
//CECS 328
//PROGRAMMING ASSIGNMENT 4
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
//file IO boilerplate https://www.w3schools.com/java/java_files_read.asp
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Main m = new Main();
        String[][] mazes;
        mazes = m.readData("input.txt");
        //mazes = m.readData("input3.txt");
        //mazes = m.readData("./mazeSamples/inputTest.txt");
        //m.printMaze(mazes);
        int numCells = (int)Math.pow(mazes[0].length, 2);
        //System.out.println(numCells); //SANITY CHECK
        myGraph[] graphs = m.convertMazesToGraphs(mazes, numCells, mazes.length);
        // for(int i = 0; i < graphs.length; i++) {
        //     System.out.println(graphs[i]);
        // }
        //System.out.println(graphs[0]);

        //EXPLORE MAZES. GENERATE SEQUNCES
        String[] sequences = new String[graphs.length];
        for(int i = 0; i < graphs.length; i++) {
            sequences[i] = graphs[i].exploreMaze(graphs[i].vertexList[0], graphs[i].vertexList[0], sequences[i]).strip();
            //System.out.println(sequences[i] + " " + sequences[i].length());
        }

        //ANALYZE SEQUENCES
        LongestCommonSubsequence l = new LongestCommonSubsequence();
        //System.out.println(l.LCSLoop(sequences[0], sequences[1]));
        //l.LCSLoop("TGCCATGCT", "CTAGGATC");
        //l.printLCSgrid();

        int min = Integer.MAX_VALUE;
        int index1 = -1;
        int index2 = -1;
        int current;
        for (int i = 0; i < sequences.length; i++) {
            for (int j = 0; j < sequences.length; j++) {
                if (i != j ) {
                    //System.out.println(i + ": " + sequences[i] + " " + j + ": " + sequences[j]);
                    current = l.LCSLoop(sequences[i], sequences[j]);
                    //System.out.println(i + " " + j + " " +  current);
                    if (current < min) {
                        min = current;
                        index1 = i;
                        index2 = j;
                        System.out.println("NEW LOW " + index1 + " " + index2 + " " +  current);
                    }
                }
            }
        }
        System.out.println(index1 + " " + index2);
        try {
            System.out.println("RECORDING OUTPUT MAZES");
            FileWriter r = new FileWriter("output.txt");
            r.append(index1 + " " + index2);
            r.close();
        } catch (FileNotFoundException e ) {
            System.out.println("Error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }

    }   //END MAIN

    public myGraph convertAMazeToGraph(String[] maze, int numCells, int mazeLength) {
        //System.out.println("convertAMazeToGraph");
        myGraph g = new myGraph(numCells);
        for (int i = 0; i < maze.length; i++) {
            char[] mc = maze[i].toCharArray();
            for (int j = 0; j < maze.length; j ++) {
                //System.out.print(j + i*maze.length);
                for (int k = 0; k < 4; k++) {
                    //System.out.print(mc[4*j + k]);
                    int currentPosition = j + i*maze.length;
                    switch (k) {
                        case 0:
                            //System.out.print(" " + mc[4*j + k]);
                            //System.out.print("N ");
                            if (currentPosition >= maze.length && mc[4*j + k] == '0') {
                                g.addEgde(g.vertexList[j + i*maze.length], g.vertexList[North(maze.length, j + i*maze.length)]);
                            }
                            break;
                        case 1:
                            //System.out.print((currentPosition) + ":" + mc[4*j + k]);
                            //System.out.print("S ");
                            if (currentPosition < (maze.length-1) * maze.length && mc[4*j + k] == '0') {
                                g.addEgde(g.vertexList[j + i*maze.length], g.vertexList[South(maze.length, j + i*maze.length)]);
                            }
                            break;
                        case 2:
                            //System.out.print((currentPosition) + ":" + mc[4*j + k]);
                            //System.out.print("W ");
                            if (currentPosition % maze.length != 0 && mc[4*j + k] == '0') {
                                g.addEgde(g.vertexList[j + i*maze.length], g.vertexList[West(maze.length, j + i*maze.length)]);
                            }
                            break;
                        case 3:
                            //System.out.print((currentPosition) + ":" + mc[4*j + k]);
                            //System.out.print("E ");
                            if (currentPosition % maze.length != maze.length - 1 && mc[4*j + k] == '0') {
                                g.addEgde(g.vertexList[j + i*maze.length], g.vertexList[East(maze.length, j + i*maze.length)]);
                            }
                            break;
                        default:
                            System.out.println("Error.");
                    }
                } //System.out.print(" ");
            }
            //System.out.println();
        }
        //SORT THE ADJACENCY LIST NSWE.
        
        for (int i = 0; i < g.vertexList.length; i++) {
            //g.vertexList[i].adjacencyList =  g.vertexList[i].sortAdjacencyList(g.dimension);
            g.vertexList[i].adjacencyList = g.vertexList[i].sortAdajencyListBubble(mazeLength);
            //System.out.println("Sorting...");
            //Collections.sort(g.vertexList[i].adjacencyList);
            //System.out.print("Vertex List: " + g.vertexList[i]);
        }
        //System.out.println(g);
        return g;
    }

    public myGraph[] convertMazesToGraphs(String[][] mazes, int numCells, int mazeLength) {
        myGraph[] graphs = new myGraph[mazes.length];
        for(int i = 0; i < mazes.length; i++) {
            graphs[i] = convertAMazeToGraph(mazes[i], numCells, mazes[i].length);
            //System.out.println();
        }
        return graphs;
    }

    //READ DATA
    public String[][] readData(String fileName) {
        String[][] mazes;
        String[][] junk = new String[0][0];
        try {
            File aFile = new File(fileName);
            if (aFile.exists()) {
                //READING DATA
                Scanner fileReader = new Scanner(aFile);
                int numberOfMazes = Integer.parseInt(fileReader.nextLine());
                int dimensionOfMazes = Integer.parseInt(fileReader.nextLine());
                System.out.println(numberOfMazes + "\n" + dimensionOfMazes);
                mazes = new String[numberOfMazes][dimensionOfMazes];
                for (int i = 0; i < numberOfMazes; i++) {
                    for (int j = 0; j < dimensionOfMazes; j++) {
                        mazes[i][j] = fileReader.nextLine();
                    }
                    fileReader.nextLine();
                }
                return mazes;
            }
        } catch (Exception e ) {
            System.out.println("Error:");
            e.printStackTrace();
        }
        return junk;
    }

    public void printMaze(String[][] mazes) {
        //PRINT MAZES ARRAY
        for (int i = 0; i < mazes.length; i++) {
            System.out.println("Maze: " + i);
            for (int j = 0; j < mazes[0].length; j++) {
                System.out.println(mazes[i][j]);
            }
        }
    }

    public static int North(int dimension, int currentPosition) {
        return currentPosition - dimension;
    }
    public int South(int dimension, int currentPosition) {
        return currentPosition + dimension;
    }
    public int West(int dimension, int currentPosition) {
        return currentPosition - 1;
    }
    public int East(int dimension, int currentPosition) {
        return currentPosition + 1;
    }
}