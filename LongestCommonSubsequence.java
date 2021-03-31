//DANIEL TRIPP
//CECS 328
//PROGRAMMING ASSIGNMENT 4
public class LongestCommonSubsequence {
    public int LCS(String X, String Y, int i, int j) {
        //System.out.println("i: " + i + " j: " + j);
        if (i == 0 || j == 0) {
            return 0;
        } else if (X.charAt(i-1) == Y.charAt(j-1)) {
            return LCSgrid[i-1][j-1] + 1;
        } else {
            return Math.max( LCSgrid[i][j-1], LCSgrid[i-1][j] );
        }
    }

    private int[][] LCSgrid;

    public int LCSLoop(String X, String Y) {
        LCSgrid = new int[X.length()+1][Y.length()+1];
        for (int k = 0; k < LCSgrid.length; k++) {
            for (int l = 0; l < LCSgrid[k].length; l++) {
                LCSgrid[k][l] = LCS(X, Y, k, l);
            }
        }
        return LCSgrid[X.length()][Y.length()];
    }

    public void printLCSgrid() {
        for (int i = 0; i < LCSgrid.length; i++) {
            for (int j = 0; j < LCSgrid[i].length; j++) {
                System.out.print(LCSgrid[i][j] + " ");
            }System.out.println();
        }
    }    
}
