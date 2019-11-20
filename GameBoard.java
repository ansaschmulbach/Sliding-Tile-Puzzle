public class GameBoard {

		public int[][] board;
		public static int size;
		public int[] coordinates = { 3 , 3 };

		public GameBoard(int size) {
			this.size = size;
			
			board = new int[size][size];
			
			//does not take in a specific size
			for (int i = 1; i <= 7; i += 2) {
				board[0][i / 2] = i;
			}
			for (int i = 9; i <= 15; i += 2) {
				board[1][i / 2 % 4] = i;
			}
			for (int i = 2; i <= 8; i += 2) {
				board[2][i / 2 - 1] = i;
			}
			for (int i = 10; i <= 14; i += 2) {
				board[3][i / 2 % 4 - 1] = i;
			}
			board[3][3] = 0;
		}

		public GameBoard(int[][] board, int[] coordinates) {
			this.board = new int[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					this.board[i][j] = board[i][j];
				}
			}
			this.coordinates = new int[2];
			this.coordinates[0] = coordinates[0];
			this.coordinates[1] = coordinates[1];
		}
		
		public void printBoard() {
			System.out.print("[");
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size - 1; j++) {
					System.out.print(board[i][j] + ", \t");
				}
				System.out.println(board[i][size - 1] + "]");
				if (i != size - 1) {
					System.out.print("[");
				}
			}
		}
		
		//not used in graph. mostly used in debugging.
		public int moveTile(Direction direction) {
			if (direction.equals(Direction.UP)) {
				if (coordinates[0] == 0) {
					return -1;
				}

				int temp = board[coordinates[0] - 1][coordinates[1]];
				board[coordinates[0] - 1][coordinates[1]] = board[coordinates[0]][coordinates[1]];
				board[coordinates[0]][coordinates[1]] = temp;
				coordinates[0]--;
				return 1;
			} else if (direction.equals(Direction.DOWN)) {
				if (coordinates[0] == size - 1) {
					return -1;
				}

				int temp = board[coordinates[0] + 1][coordinates[1]];
				board[coordinates[0] + 1][coordinates[1]] = board[coordinates[0]][coordinates[1]];
				board[coordinates[0]][coordinates[1]] = temp;
				coordinates[0]++;
				return 1;
			} else if (direction.equals(Direction.LEFT)) {
				if (coordinates[1] == 0) {
					return -1;
				}

				int temp = board[coordinates[0]][coordinates[1] - 1];
				board[coordinates[0]][coordinates[1] - 1] = board[coordinates[0]][coordinates[1]];
				board[coordinates[0]][coordinates[1]] = temp;
				coordinates[1]--;
				return 1;
			} else {
				if (coordinates[1] == size - 1) {
					return -1;
				}

				int temp = board[coordinates[0]][coordinates[1] + 1];
				board[coordinates[0]][coordinates[1] + 1] = board[coordinates[0]][coordinates[1]];
				board[coordinates[0]][coordinates[1]] = temp;
				coordinates[1]++;
				return 1;
			}
		}
		
		//equals if board is equal (for hash table function)
		@Override
		public boolean equals(Object other) {
			if (!(other == null) && other instanceof GameBoard) {
				return equals((GameBoard)(other));
			}
			return false;
		}
		
		public boolean equals(GameBoard other) {
	        for (int i = 0; i < size; i++){
	            for (int j = 0; j < size; j++) {
	                if (this.board[i][j] != other.board[i][j]) return false;
	            }
	        }
	        return true;
	    }
		

	}
