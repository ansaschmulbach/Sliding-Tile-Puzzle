//Viansa Schmulbach
//Homework #1
//AI
//27 September 2019

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.Map;

public class Graph {

	Node initialState;
	Node goalState;
	PriorityQueue<Node> frontier;
	Hashtable<Node, Node> visited;

	public Graph(Node initialState, Node goalState) {
		this.initialState = initialState;
		this.goalState = goalState;
		frontier = new PriorityQueue<Node>();
		frontier.add(this.initialState);
		visited = new Hashtable<>();
		visited.put(this.initialState, this.initialState);

	}

	//in each direction when it is expanded, you create the node and potentially add the node
	public void expand(Node n) {

		//expands up 
		if (n.state.coordinates[0] != 0) {
			GameBoard up = new GameBoard(n.state.board, n.state.coordinates);
			up.board[n.state.coordinates[0]
					- 1][n.state.coordinates[1]] = n.state.board[n.state.coordinates[0]][n.state.coordinates[1]];
			up.board[n.state.coordinates[0]][n.state.coordinates[1]] = n.state.board[n.state.coordinates[0]
					- 1][n.state.coordinates[1]];
			up.coordinates[0]--;

			Node upNeighbor = new Node(up, n, n.g + 1);
			addNode(n, upNeighbor, 0);
			
		}

		//expands down
		if (n.state.coordinates[0] != n.size - 1) {
			GameBoard down = new GameBoard(n.state.board, n.state.coordinates);
			down = new GameBoard(n.state.board, n.state.coordinates);
			down.board[n.state.coordinates[0]
					+ 1][n.state.coordinates[1]] = n.state.board[n.state.coordinates[0]][n.state.coordinates[1]];
			down.board[n.state.coordinates[0]][n.state.coordinates[1]] = n.state.board[n.state.coordinates[0]
					+ 1][n.state.coordinates[1]];
			down.coordinates[0]++;
			Node downNeighbor = new Node(down, n, n.g + 1);
			addNode(n, downNeighbor, 1);
			
			
		}

		//expands left
		if (n.state.coordinates[1] != 0) {
			GameBoard left = new GameBoard(n.state.board, n.state.coordinates);
			left = new GameBoard(n.state.board, n.state.coordinates);
			left.board[n.state.coordinates[0]][n.state.coordinates[1]
					- 1] = n.state.board[n.state.coordinates[0]][n.state.coordinates[1]];
			left.board[n.state.coordinates[0]][n.state.coordinates[1]] = n.state.board[n.state.coordinates[0]][n.state.coordinates[1]
					- 1];
			left.coordinates[1]--;
			Node leftNeighbor = new Node(left, n, n.g + 1);
			addNode(n, leftNeighbor, 2);
		}

		//expands right
		if (n.state.coordinates[1] != n.size - 1) {
			GameBoard right = new GameBoard(n.state.board, n.state.coordinates);
			right = new GameBoard(n.state.board, n.state.coordinates);
			right.board[n.state.coordinates[0]][n.state.coordinates[1]
					+ 1] = n.state.board[n.state.coordinates[0]][n.state.coordinates[1]];
			right.board[n.state.coordinates[0]][n.state.coordinates[1]] = n.state.board[n.state.coordinates[0]][n.state.coordinates[1]
					+ 1];
			right.coordinates[1]++;
			Node rightNeighbor = new Node(right, n, n.g + 1);
			addNode(n, rightNeighbor, 3);
		}

	}
	
	
	//if a node exists in a certain directon, it can be added
	//if it's in the frontier, you don't do anything with it
	//it's added onto the hash if it's added onto the frontier
	public void addNode(Node n, Node neighbor, int index) {
		if (!visited.contains(neighbor)) { //not in frontier or visited
			//add it to the frontier
			//add it to hash table
			//add it to neighbors
			frontier.add(neighbor);
			visited.put(neighbor, neighbor);
			
			n.neighbors[index] = neighbor;
			neighbor.previous = n;
			
		} else if (frontier.contains(neighbor)) { //if it's in the frontier
			//change value on frontier
			//add it to neighbors 
			
			//retrieve from hash table (old value of the node)
			Node previous = visited.get(neighbor);
			
			//f(n) is less
			if (previous.compareTo(neighbor) > 0) {
				visited.remove(previous);
				visited.put(neighbor, neighbor);
				frontier.remove(previous);
				frontier.add(neighbor);
				n.neighbors[index] = neighbor;
				
				//change value of previous
				neighbor.previous = n;
				
			}
			
		}
	}
	
	public ArrayList<Node> search() {
		/* 1. pop head off frontier
		 * 2. check to see if goal
		 * 3. if not, expand
		 */
		
		//continue until we get to the goal state
		while (!frontier.isEmpty()) {
			Node current = frontier.poll();
			
			if (current.equals(goalState)) {
				goalState.previous = current;
				break;
			} else {
				expand(current);
			}
			
		}
		
		//traverse over list of previous
		ArrayList<Node> path = new ArrayList<Node>();
		Node head = goalState;
		while (head != null) {
			
			path.add(0, head);
			head = head.previous;
		}
		
		return path;
		
	}

	public static void main(String[] args) {
		
		//defaults to a size of 4
		int size = 4;
		
		int[] coords = new int[2];
		Node n;
		if (args.length > 0) {
			
			size = (int) Math.sqrt(args.length);
			int[][] initial = new int[size][size];
			for (int i = 0; i < args.length; i++) {
				initial[i/size][i%size] = Integer.parseInt(args[i]);
				if (Integer.parseInt(args[i]) == 0) {
					coords[0] = i/size;
					coords[1] = i%size;
				}
			}
			
			GameBoard.size = size;
		
			n = new Node(new GameBoard(initial, coords), null, 0);
			Node.size = size;
		} else {
			n = new Node(4);
		}
		
		int[][] array = new int[size][size];
		for (int i = 0; i < Math.pow(size, 2); i++) {
			array[i / size][i % size] = i;
		}

		int[] coordinates = { 0, 0 };
		Node n1 = new Node(new GameBoard(array, coordinates), null);
	
		Graph g = new Graph(n, n1);
		
		int count = 0;
		for (Node node : g.search()) {
			node.state.printBoard();
			System.out.println();
			count ++;
		}
		
		System.out.println(count - 1);
		
	}

}
