  /*
      THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
      CODE WRITTEN BY OTHER STUDENTS. Marcus Johnson using both late assignment 
      allowance for this assignment
      */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayDeque;


/*
 * minimal class to represent position [i,j] in the maze
 */
class Position{
	public int i;     //row
	public int j;     //column
	public char val;  //1, 0, or 'X'
	public boolean marked;
	public Position prev;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		result = prime * result + (marked ? 1231 : 1237);
		result = prime * result + val;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		if (marked != other.marked)
			return false;
		if (val != other.val)
			return false;
		return true;
	}


	Position (int a, int b, char v){ i = a; j=b; val = v; }
	
}


public class PathFinder {
	
	public static void main(String[] args) throws IOException {
                if(args.length<1){
                        System.err.println("***Usage: java PathFinder maze_file"
);
                        System.exit(-1);
                }

                // use stackSearch 
                char [][] maze;
                maze = readMaze(args[0]);
                printMaze(maze);
                Position [] path = stackSearch(maze);
                System.out.println("stackSearch Solution:");
                printPath(path);
                printMaze(maze);

               // use queueSearch
                char [][] maze2 = readMaze(args[0]);
                path = queueSearch(maze2);
                System.out.println("queueSearch Solution:");
                printPath(path);
                printMaze(maze2);
       }

	
	
	public static Position [] stackSearch(char [] [] maze){
		//todo: your path finding algorithm here using the stack to manage search list
		//your algorithm should modify maze to mark positions on the path, and also
		//return array of Position objects coressponding to path, or null if no path found
		 ArrayDeque <Position> path = new ArrayDeque<Position>();
	     ArrayDeque <Position> searched = new ArrayDeque<Position>();
	    

	        //creates position object
	        Position start = new Position(0,0,'0');
	        Position current;
	        Position north,south, east, west;

	        int i = 0; int j = 0;
	        //push (0,0) onto stack
	        path.push(start);
	        searched.push(start);
	        while(!path.isEmpty()){
	        	int size=path.size();
	        	current=path.peek();
	        	i=current.i;
	        	j=current.j;
	        	if(i==maze.length-1 && j==maze.length-1 &&  maze[i][j]=='0'){
	        		 Position[] trail= new Position [path.size()];
	        	
	        			for(int k=0; k<size;k++){
	        			maze[path.peek().i][path.peek().j]='X';
	        			
	        			trail[k]=path.pop();
	        		}
	        			return trail;
	        	
	        }
	        	
	        	
	        	//check east.
	        	east= new Position(i,j+1,'0');
	        	south= new Position(i+1,j,'0');
	        	west= new Position(i,j-1,'0');
	        	north= new Position(i-1,j,'0');
	        	
                if (j+1 >= 0 && j+1 < maze.length  && maze[i][j+1] == '0' && searched.contains(east)==false)
                {        	
                		searched.push(east);
                		path.push(east);
                	
                }
	        	//check south, add its position to the list.
	        	
	        	else if (i+1 >= 0 && i+1 < maze.length &&  maze[i+1][j] == '0' && searched.contains(south)==false)
                {
                		searched.push(south);
                		path.push(south);
            
                }
              //check west.
	        	
                 else if (j-1 >= 0 && j-1 < maze.length  && maze[i][j-1] == '0' && searched.contains(west)==false)
                {
                		searched.push(west);
                		path.push(west);
                	
                }              
                //check north
	        	
                 else if (i-1 >= 0 && i-1 < maze.length &&  maze[i-1][j] == '0' && searched.contains(north)==false)
                {
                		searched.push(north);
                		path.push(north);
                	
                }
                 else{
                	 path.pop();
                 }
                 
                
	        	
	       }
		return null;
	}
	
public static Position [] queueSearch(char [] [] maze){
		//todo: your path finding algorithm here using the queue to manage search list
		//your algorithm should modify maze to mark positions on the path, and also
		//return array of Position objects coressponding to path, or null if no path found
	ArrayDeque <Position> path = new ArrayDeque<Position>();
	ArrayDeque <Position> searched = new ArrayDeque<Position>();
	    

	        //creates position object
	        Position start = new Position(0,0,'0');
	        Position current;
	        Position north,south, east, west;

	        int i = 0; int j = 0;
	        //push (0,0) onto stack
	        path.addFirst(start);
	        searched.addFirst(start);
	        ArrayList<Position> traillist = new ArrayList<Position>();
	        while(!path.isEmpty()){
	        	
	        	current=path.removeFirst();
	        	i=current.i;
	        	j=current.j;
	        	int o=0;
	        	if(i==maze.length-1 && j==maze.length-1 &&  maze[i][j]=='0'){
	        		while(current.prev!=null){
	        			
	        			while(o<1){
	        			traillist.add(current);
	        			o++;
	        			}
	        			traillist.add(current.prev);
	        			current=current.prev;
	        		}
	        		
	        		Position[] Trail= new Position [traillist.size()];
	        		traillist.toArray(Trail);
	        		for(int k=0;k<Trail.length;k++){
	        			Position temp= Trail[k];
	        			maze[temp.i][temp.j]='X';
	        		}
	           		 
	        		 return Trail;
	        	}
	        	
	        	
	        	//check east.
	        	east= new Position(i,j+1,'0');
	        	south= new Position(i+1,j,'0');
	        	west= new Position(i,j-1,'0');
	        	north= new Position(i-1,j,'0');
	        	
	        	
                if (j+1 >= 0 && j+1 < maze.length  && maze[i][j+1] == '0' && searched.contains(east)==false)
                {         
                	searched.addFirst(east);
                	east.prev=current;
                	path.addFirst(east);               	   
                }
	        	//check south, add its position to the list.
	        	
	        	 if (i+1 >= 0 && i+1 < maze.length &&  maze[i+1][j] == '0' && searched.contains(south)==false)
                {
                		searched.addFirst(south);
                		south.prev=current;
                   		path.addFirst(south);          	
                }
              //check west.
	        	
                  if (j-1 >= 0 && j-1 < maze.length  && maze[i][j-1] == '0' && searched.contains(west)==false)
                {
                		searched.addFirst(west);
                		west.prev=current;
                		path.addFirst(west);
                	
                }              
                //check north
	        	
                  if (i-1 >= 0 && i-1 < maze.length &&  maze[i-1][j] == '0' && searched.contains(north)==false)
                {
                		searched.addFirst(north);
                		north.prev=current;
                         path.addFirst(north);
                	
                	
                }
                
	        	
	       }
		return null;
	}
	
	
        public static void printPath(Position [] path){
                //todo: print a given path
        	System.out.println("This path length is"+path.length);
        	for(int k=path.length-1; k>=0;k--){
        		Position now= path[k];
        		System.out.println("["+now.i+"],["+now.j+"]");
        	}
        }
	
	/**
	 * Reads maze file in format:
	 * N  -- size of maze
	 * 0 1 0 1 0 1 -- space-separated 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static char [][] readMaze(String filename) throws IOException{
		char [][] maze;
		Scanner scanner;
		try{
			scanner = new Scanner(new FileInputStream(filename));
		}
		catch(IOException ex){
			System.err.println("*** Invalid filename: " + filename);
			return null;
		}
		
		int N = scanner.nextInt();
		scanner.nextLine();
		maze = new char[N][N];
		int i=0;
		while(i < N && scanner.hasNext()){
			String line =  scanner.nextLine();
			String [] tokens = line.split("\\s+");
			int j = 0;
			for (; j< tokens.length; j++){
				maze[i][j] = tokens[j].charAt(0);
			}
			if(j!=N){
				System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
				return null;
			}
			i++;
		}
		if(i!=N){
			System.err.println("*** Invalid file: has wrong number of rows: " + i);
			return null;
		}
		return maze;
	}
	
	public static void printMaze(char[][] maze){
		
		if(maze==null || maze[0] == null){
			System.err.println("*** Invalid maze array");
			return;
		}
		
		for(int i=0; i< maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				System.out.print(maze[i][j] + " ");	
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
}