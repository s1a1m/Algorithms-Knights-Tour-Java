import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The knight class provides a static main
 * method to read the dimensions of a board and
 * print a solution of the knight tour.
 *
 * See <a href="http://en.wikipedia.org/wiki/Knight%27s_tour">Wikipedia:
 * Knight's tour</a> for more information.
 *
 * The algorithm employed is similar to the standard backtracking
 * <a href="http://en.wikipedia.org/wiki/Eight_queens_puzzle">eight
 * queens algorithm</a>.
 *
 */

public class knight{

    static long recurCalls1 = 0;      
    static long recurCalls2 = 0;
    static long recurCalls3 = 0;
    static long recurCalls4 = 0;
    static final int[][] direction={ {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1} };
    static int M = 4; //Default values
    static int N = 4; //Default values
    static int[][] Board;
    
	
    private static BufferedReader read;

    static boolean solve1(int step, int i, int j) {
	//Recursive backtrack search to find Knights tour. Uses brute force.
	recurCalls1++;
        Board[i][j] = step;
	//All positions are filled	
	if(step == N*M){
            return true;
        }  	
        for(int k = 0; k < 8; k++){
            int i1 = i + direction[k][0];
            int j1 = j + direction[k][1];         
	    if(0 <= i1 && i1 < N && 0 <= j1 && j1 < M && Board[i1][j1] == 0){    
                if(solve1(step+1, i1, j1)){           
                    return true;
                }
            }
        }
        Board[i][j] = 0; //No more next position, reset on backtrack
        return false;
    }
    
    static boolean solve2(int step, int i, int j) {
	//Trys to find Knight's tour using Warnsdorf's rule.
        int low = 9; //Max can be 8, so we start at 9.
        recurCalls2++;
        Board[i][j] = step;
        //All positions are filled
        if(step == N*M){
            return true;
        }
        for(int k = 0; k < 8; k++){
            int i2 = 0;
            int j2 = 0;
            int i1 = i + direction[k][0];
            int j1 = j + direction[k][1];
            if(0 <= i1 && i1 < N && 0 <= j1 && j1 < M && Board[i1][j1] == 0){ 
                if(getPossibleMoves(i1, j1) < low){
                    low = getPossibleMoves(i1, j1);
                    i2 = i1;
                    j2 = j1;
                    if(solve2(step+1, i2, j2)){
                        return true;
                    }
                }
            }
        }
        Board[i][j] = 0; //No more next position, reset on backtrack 
        return false;
    }
    
    static boolean solve3(int step, int i, int j){
        //Trys to find Knight's tour that is also a cycle. Implements Warnsdorf's rule.
        int low = 9;
        recurCalls3++;
        Board[i][j] = step;
        //All positions are filled
        if(step == (N * M)){
            return true;
        }
        for(int k = 0; k < 8; k++){
            int i2 = 0;
            int j2 = 0;
            int i1 = i + direction[k][0];
            int j1 = j + direction[k][1];
            if((0 <= i1 && i1 < N && 0 <= j1 && j1 < M && Board[i1][j1] == 0)||(0 <= i1 && i1 < N && 0 <= j1 && j1 < M && Board[i1][j1] == -1 && step == ((M * N) - 1))){ 
                if(getPossibleMoves(i1, j1) < low){
                    low = getPossibleMoves(i1, j1);
                    i2 = i1;
                    j2 = j1;
                    if(solve3(step+1, i2, j2)){
                        return true;
                    }
                }
            }
        }
        Board[i][j] = 0; //No more next position, reset on backtrack 
        return false;
    }
    
    static boolean solve4(int step, int i, int j) {
        //Used to display partial solutions. 
        int low = 9; //Max can be 8, so we start at 9.
        recurCalls4++;
        Board[i][j] = step;
        //All positions are filled
        if(step == (M*N)-1){
            return true;
        }
        for(int k = 0; k < 8; k++){
            int i2 = 0;
            int j2 = 0;
            int i1 = i + direction[k][0];
            int j1 = j + direction[k][1];
            if(0 <= i1 && i1 < N && 0 <= j1 && j1 < M && Board[i1][j1] == 0){ 
                if(getPossibleMoves(i1, j1) < low){
                    low = getPossibleMoves(i1, j1);
                    i2 = i1;
                    j2 = j1;
                    if(solve4(step+1, i2, j2)){
                        return true;
                    }
                }
            }
        }
        Board[i][j] = 0; //No more next position, reset on backtrack 
        if(step == (M*N)){
            printBoard(Board, "Solve4 Partial Solution");
        }
        return false;
    }
    
    static void reserveSpot(int i, int j){
        //Flag is raised when a spot is reserved
        int spotReservedFlag = 0;
        for(int k = 0; k < 8; k++){
            int i1 = i + direction[k][0];
            int j1 = j + direction[k][1];
            if(0 <= i1 && i1 < N && 0 <= j1 && j1 < M && Board[i1][j1] == 0 && spotReservedFlag == 0){ //Flag included so that only 1 spot is marked reserved.
                Board[i1][j1] = -1; //Negitive 1 will stand for a reserved spot.
                spotReservedFlag = 1;
            }
        }   
    }
    
    static int getPossibleMoves(int i, int j){
        int count = 0;
        for(int k =0; k < 8; k++){
            int i1 = i+direction[k][0];
            int j1 = j+direction[k][1];
            if (0 <= i1 && i1 < N && 0 <= j1 && j1 < M && Board[i1][j1] == 0){ 
                count++;
            }
        }    
	return count;   
    }  

    static void printBoard(int[][] solution, String title){
        System.out.println(title);
        for (int i = 0; i < N; ++i){      
            for (int j = 0; j < M; j++){
                System.out.print("------");
            }
            System.out.println("-");
            
            for (int j = 0; j < M; ++j){
                System.out.format("| %3d ", solution[i][j]);
            }  
            System.out.println("|");
        }
        
        for (int j = 0; j < M; j++){
            System.out.print("------");
        }
        System.out.println("-");
        System.out.println("");//Enter
    }
    
    static void resetBoard(boolean printDem){
    //Prints static Board Demonsions, then sets every entry of board to 0.       
        if(printDem == true){//Input true as parameter if you want to display board demonsions upon reset. 
            System.out.println("Board Demonsions");
            System.out.println("N: " + N);
            System.out.println("M: " + M);
        }
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){ 
                Board[i][j] = 0;
            }
        }        
    }
    
    static void printPossibleMovesBoard(){         
    //Prints possible moves from every position on board.
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){ 
                System.out.print(getPossibleMoves(i, j));
            }
            System.out.println("");//Enter
        }
    }
        	
    //Read in the dimensions of Knight's tour board and try to find one.   
    public static void main(String [ ] args){
        
    	read = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Please enter the number of rows: ");
            N = Integer.parseInt(read.readLine());
            
            System.out.print("Please enter the number of columns: ");
            M = Integer.parseInt(read.readLine());
                      
        } catch(Exception ex){
        	System.err.println( "Error: " + ex );
            ex.printStackTrace();
        }
        System.out.println("");//Enter
        
        //Swaps N and M if N > M
        if(N > M){
            int i = N; N = M; M = i; 
        }
        
        //Create Board
        Board = new int[N][M];
        
        //SOLVE1
        resetBoard(false);
        if(solve1(1, 0, 0)){
            printBoard(Board, "Solve1 (Brute Force)");
        }
        else{
            System.out.println("Solve1: No tour was found.");
        }
        
        //SOLVE2
        resetBoard(false);
        if(solve2(1, 0, 0)){
            printBoard(Board, "Solve2 (Warnsdorf)");
        }
        else{
            System.out.println("Solve2: No tour was found.");
        }
        
        //RESERVESPOT TEST
        //resetBoard(false);
        //reserveSpot(5,5);
        //printBoard(Board, "Reserve Spot Test");
 
        //SOLVE3    
        resetBoard(false);
        reserveSpot(0, 0);
        if(solve3(1, 0, 0)){
            printBoard(Board, "Solve3 (Cycle)");
        }
        else{
            System.out.println("Solve3: No tour was found.");
        }
        
        //SOLVE4
        resetBoard(false);
        solve4(1, 0, 0);
        printBoard(Board, "Solve4: Partial Solution (0 means never visited)");
        
	System.out.println("Solve1: Number of recursive calls = " + recurCalls1);
        System.out.println("Solve2: Number of recursive calls = " + recurCalls2);
        System.out.println("Solve3: Number of recursive calls = " + recurCalls3);
        System.out.println("Solve3: Number of recursive calls = " + recurCalls4);
    } 
}
