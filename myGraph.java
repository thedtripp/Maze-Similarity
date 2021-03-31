//DANIEL TRIPP
//CECS 328
//PROGRAMMING ASSIGNMENT 4
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
public class myGraph {

    protected static class myVertex { 
        public int data;            // an integer indicating which node this is. range: 0 - [(n**2)-1]
        public ArrayList<Integer> adjacencyList = new ArrayList<>();
        public boolean isFinished;
        public boolean isVisited;

        public myVertex(int d) {
            data = d;
            isVisited = false;
            isFinished = false;
        }

        public void printVertex() {
            System.out.println();
        }

        public ArrayList<Integer> sortAdajencyListBubble(int dimension) {
            ArrayList<Integer> newAdjacencyList = new ArrayList<>();
            int[] adjl = new int[this.adjacencyList.size()];
            
            for (int i = 0; i < this.adjacencyList.size(); i++) {
                adjl[i] = this.adjacencyList.get(i);
            }
            int temp;
            //System.out.println("sorting nums...");
            for (int h = 0; h < adjl.length - 1; h++) {
                for (int i = 0; i < adjl.length - 1 ; i++) { //CONSIDER FIXING THIS BUBBLE SORT. N^2
                    //System.out.println(adjl[i] + ":" + getPosition(adjl[i], this.data));
                    //System.out.println(nums[getPosition(nums[i], current, dimension)] + " " + nums[getPosition(nums[i+1], current, dimension)]);
                    if ( getPosition(adjl[i], this.data) > getPosition(adjl[i+1], this.data) ) {
                    //if ( adjl[getPosition(adjl[i], this.data)] > adjl[getPosition(adjl[i+1], this.data)] ) {
                        temp = adjl[i];
                        adjl[i] = adjl[i+1];
                        adjl[i+1] = temp;
                    }
                }
            }
            for (int i: adjl) {
                newAdjacencyList.add(i);
            }
            
            return newAdjacencyList;
        }

        //TO DO: FIX THIS FUNCTION.
        //probably use an array to specify index...
        public ArrayList<Integer> sortAdjacencyList(int dimension) {
            ArrayList<Integer> newAdjacencyList = new ArrayList<>();
            for (int i = 0; i < this.adjacencyList.size(); i++) {
                if ( this.adjacencyList.get(i) == this.data - dimension ) {//NORTH
                    //System.out.println("NORTH");
                    newAdjacencyList.add(this.adjacencyList.get(i));
                    
                    continue;
                } else if ( this.adjacencyList.get(i) == this.data + dimension ) {//SOUTH
                    //System.out.println("SOUTH");
                    newAdjacencyList.add(this.adjacencyList.get(i));
                    continue;
                } else if ( this.adjacencyList.get(i) == this.data - 1 ) {//WEST
                    //System.out.println("WEST");
                    newAdjacencyList.add(this.adjacencyList.get(i));
                    continue;
                } else if ( this.adjacencyList.get(i) == this.data + 1 ) {//EAST
                    //System.out.println("EAST");
                    newAdjacencyList.add(this.adjacencyList.get(i));
                    continue;
                } else {
                    System.out.println("This should not have hapened. There's an issue with NSWE claulation.");
                }
            }
            // System.out.print(this.data + ": ");
            // for (int i = 0; i < newAdjacencyList.size(); i++) {
            //     System.out.print(newAdjacencyList.get(i) + " ");
            // } System.out.println();
            return newAdjacencyList;
        }

        @Override
        public String toString()  {
            String str = "";
            str += String.valueOf(this.data) + "\t" + 
            ((this.isVisited) ? " Visited" : " Not Visited") + "\t" + 
            ((this.isFinished) ? " Finished" : " Not Finished") + "\t";
            for(int i = 0; i < adjacencyList.size(); i++) {
                str += this.adjacencyList.get(i) + " ";
            }
            return str;
        }
    }

    public myVertex[] vertexList;
    public int size;
    public int dimension;
    public Stack<myVertex> s = new Stack<>();

    public myGraph(int numVertices) {
        this.vertexList = new myVertex[numVertices];
        for(int i = 0; i < vertexList.length; i++) {
            vertexList[i] = new myVertex(i);
        }
        this.size = vertexList.length; //number of vertices
        this.dimension = (int) Math.sqrt(size);
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = 0; i < this.vertexList.length; i++) {
            str += vertexList[i] + "\n";
        }
        return str;
    }

    public void addEgde(myVertex v, myVertex w) {
        if (!v.adjacencyList.contains(w.data)) {
            v.adjacencyList.add(w.data);
        }
        if (!w.adjacencyList.contains(v.data)) {
            w.adjacencyList.add(v.data);
        }
    }

    public String exploreMaze(myVertex v, myVertex prev, String sequence) { //DEPTH FIRST SEARCH
        String seq;
        if ( sequence != null )
            seq = sequence;
        else
            seq = "";
        //System.out.println(v.data + " " + prev.data + " " + getDirection(v, prev)); //FORWARD
        seq += getDirection(v, prev); //FORWARD
        s.push(v);
        v.isVisited = true;
        Iterator<Integer> i = v.adjacencyList.listIterator();
        while (i.hasNext()) {
            //System.out.println();
            int n = i.next();
            if (vertexList[n].isVisited == false) {
                seq = exploreMaze(vertexList[n], v, seq);
                if (!v.isFinished) {
                    myVertex temp = s.pop();
                    //System.out.println(v.data + " " + temp.data + " " + getDirection(v, temp));//+ prev.data); //BACKWARDS
                    seq += getDirection(v, temp);//+ prev.data); //BACKWARDS
                }
            }
        } v.isFinished = true;
        return seq;
    }

    public void exploreMazeVerbose(myVertex v, myVertex prev) { //DEPTH FIRST SEARCH
        //System.out.println(v.data + " " + prev.data + " " + getDirection(v, prev)); //FORWARD
        //System.out.print(getDirection(v, prev) + " "); //FORWARD
        s.push(v);
        v.isVisited = true;
        Iterator<Integer> i = v.adjacencyList.listIterator();
        while (i.hasNext()) {
            //System.out.println();
            int n = i.next();
            if (vertexList[n].isVisited == false) {
                exploreMazeVerbose(vertexList[n], v);
                if (!v.isFinished) {
                    myVertex temp = s.pop();
                    //System.out.println(v.data + " " + temp.data + " " + getDirection(v, temp));//+ prev.data); //BACKWARDS
                    //System.out.print(getDirection(v, temp) + " ");//+ prev.data); //BACKWARDS

                }
            }
        } v.isFinished = true;
    }

    public char getDirection(myVertex to, myVertex from) {
        if ( to.data - from.data == this.dimension ) {
            return 'S';
        } else if ( to.data + this.dimension == from.data) {
            return 'N';
        } else if (from.data - 1 == to.data) {
            return 'W';
        } else if (from.data + 1 == to.data) {
            return 'E';
        }
        return ' ';
    }
    public static int getPosition(int to, int from) {
        //System.out.println("to: " + to + " current: " + current + " dimension: " + dimension);
        if ( from - 1 == to ) {
            return 2;
        } else if ( from + 1 == to ) {
            return 3;
        } else if ( to - from < 1 ) {
            //to + this.dimension == from ) {
            return 0;
        } else if ( to - from > 1) {
            //to == this.dimension + from ) {
            return 1;
        } else {
            return -1;
        }
    }

}

