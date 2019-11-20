import java.util.ArrayList;

public class Node implements Comparable<Node>{

	public GameBoard state;
	public Node[] neighbors;
	
	//previous node in order to keep track of 
	public Node previous;
	
	
	public int h;
	public int g;
	
	public static int size;

	//regular node
	public Node(GameBoard state, Node previous, int cost) {
		this.state = state;
		neighbors = new Node[4];
		this.h = h();
		this.g = cost;
		this.previous = previous;
	}

	//end state
	public Node(GameBoard state, Node previous) {
		this.state = state;
		this.h = h();
		this.g = 0;
		this.previous = previous;
	}
	
	//starting state
	public Node(int size) {
		this.state = new GameBoard(size);
		Node.size = size;
		neighbors = new Node[4];
		this.h = h();
		this.g = 0;
		
		this.previous = null;
		
	}

	public int h() { //heuristic estimate to goal
		int sum = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				//distance in x
				//System.out.println(state.board[i][j] % 4 - i);
				sum += Math.abs(state.board[i][j] % size - j);
				//distance in y
				//System.out.println(state.board[i][j] / 4 - i);
				sum += Math.abs(state.board[i][j] / size - i);
				
			}
		}
		return sum;
	}
	
	public int f() { //g(n) + h(n)
		return h + g;
	}
	
	@Override
	public int compareTo(Node other) {
		return this.f() - other.f();
	}
	
	@Override
	public int hashCode() {
		int code = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				code += this.state.board[i][j] * i * j;
			}
		}
		
		return code;
		
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other == null) && other instanceof Node) {
			return equals((Node)(other));
		}
		return false;
		
	}
	
	public boolean equals(Node n) {
		return (this.state.equals(n.state));
	}
	

}
