import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Game {
	private Map map;
	private Player player;
	private boolean[][] visited;
	public Game(File inputFile) throws Exception {
		initialize(inputFile);
		visited = new boolean[map.getRows()][map.getCols()];
		this.player = new Player(this.map.getBeginPos());
	}

	public void initialize(File inputFile) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(inputFile));

		// Read the first of the input file
		String line = br.readLine();
		int M = Integer.parseInt(line.split(" ")[0]);
		int N = Integer.parseInt(line.split(" ")[1]);

		// TODO: define a map
		map = new Map(M, N);

		// Read the following M lines of the Map
		for (int i = 0; i < M; i++) {
			line = br.readLine();
			// TODO: Read the map line by line

			for (int j = 0; j < N; j++){
				char c = line.charAt(j);
				this.map.cells[i][j] = Cell.typeFromChar(c, new Position(i, j));
				if (this.map.cells[i][j] instanceof TerminationCell){
					TerminationCell cell = (TerminationCell) this.map.cells[i][j];
					if (cell.type == TerminationCell.Type.Begin) this.map.setBeginCell(cell);
					else if (cell.type == TerminationCell.Type.Destination) this.map.setDestCell(cell);
				}
				else if (this.map.cells[i][j] instanceof Pokemon) { this.map.addPokemon(new Position(i, j)); }
				else if (this.map.cells[i][j] instanceof Station) { this.map.addStation(new Position(i, j));}
			}
		}

		// TODO:
		// Find the number of stations and pokemons in the map
		// Continue read the information of all the stations and pokemons by using br.readLine();

		int countPokemon = 0;
		int countSupply = 0;

		while((line=br.readLine())!=null){

			if (countPokemon != this.map.getNumPokemons()){
				String pos = (line.split(", ")[0]);
				Scanner sc = new Scanner(pos);
				sc.useDelimiter("[^0-9]+"); // note we use + not *
				List<Integer> position = new ArrayList<>();
				while (sc.hasNextInt()){
					position.add(sc.nextInt());
				}
				int row = position.get(0);
				int col = position.get(1);
				String name = line.split(", ")[1];
				String type = line.split(", ")[2];
				int power = Integer.parseInt(line.split(", ")[3]);
				int numBalls = Integer.parseInt(line.split(", ")[4]);

				this.map.cells[row][col] = new Pokemon(name, type, new Position(row, col), power, numBalls);

				countPokemon++;
			}
			else if (countSupply != this.map.getNumStation()){
				String pos = (line.split(", ")[0]);
				Scanner sc = new Scanner(pos);
				sc.useDelimiter("[^0-9]+"); // note we use + not *
				List<Integer> position = new ArrayList<>();
				while (sc.hasNextInt()){
					position.add(sc.nextInt());
				}
				int row = position.get(0);
				int col = position.get(1);
				int num = Integer.parseInt(line.split(", ")[1]);

				this.map.cells[row][col] = new Station(new Position(row, col), num);
				countSupply++;
			}
		}
		br.close();
	}
	/**
	* Check this movement is valid or not
	* @param move the movement needs to be checked
	 */
	public boolean validMove(Position move){
		if (!move.outOfmap(this.map.getRows(), this.map.getCols())){
			Cell cell = this.map.cells[move.row][move.col];
			if (cell instanceof Wall) return false;
			else if(cell.position == this.map.getDestPos()){
				//reached = true;
				return true;
			}
			else return true;
		}
		return false;
	}

	/**
	 * Find a path from {@param source} to {@param target}
	 * @param source
	 * @param target
	 * @return a shortest path from {@param source} to {@param target}
	 */

	public Path moveTo(Position source, Position target) {
		LinkedList<Position> nextToVisit = new LinkedList<>();
		;
		nextToVisit.add(source);

		while (!nextToVisit.isEmpty()) {
			Position cur = nextToVisit.remove();

			if (!validMove(cur) || this.visited[cur.row][cur.col]) {
				continue;
			}

			if (this.map.cells[cur.row][cur.col] instanceof Wall) {
				this.visited[cur.row][cur.col] = true;
				continue;
			}

			if (cur.equal(target)) {
				return backtrackPath(cur);
			}

			Position right = new Position(cur.moveRight(), cur);
			nextToVisit.add(right);
			this.visited[cur.row][cur.col] = true;

			Position left = new Position(cur.moveLeft(), cur);
			nextToVisit.add(left);
			this.visited[cur.row][cur.col] = true;

			Position down = new Position(cur.moveDown(), cur);
			nextToVisit.add(down);
			this.visited[cur.row][cur.col] = true;

			Position up = new Position(cur.moveUp(), cur);
			nextToVisit.add(up);
			this.visited[cur.row][cur.col] = true;

		}
		return new Path();
	}

	/**
	 * Trace back the path from {@param cur} to its root
	 * @param cur
	 * @return
	 */
	private Path backtrackPath(Position cur) {
		Path path = new Path();
		Position iter = cur;

		while (iter != null) {
			iter = iter.getParent();
			if(iter!=null)path.add(iter);
		}
		path.reverse();
		return path;
	}
	public void reset() {
		for (int i = 0; i < visited.length; i++)
			Arrays.fill(visited[i], false);
	}
	public static void main(String[] args) throws Exception{

		File inputFile = new File("./sampleInput.txt");
		File outputFile = new File("./sampleOut.txt");

		if (args.length > 0) {
			inputFile = new File(args[0]);
		}

		if (args.length > 1) {
			outputFile = new File(args[1]);
		}

		// TODO:
		// Read the configures of the map and pokemons from the file inputFile
		// and output the results to the file outputFile
		Game game = new Game(inputFile);

		//find the nearest supply station first
		Position next_position = new Position(game.map.getBeginPos());
		int index = 0;
		List<Position> Stations = game.map.getStationList();
		int minimum  = 100;

		List<Path> paths = new ArrayList<>();

		Position source = new Position(game.player.getPosition());
		for (Position item : Stations) {
			game.reset();
			int steps =game.moveTo(source, item).size();
			if (steps < minimum) {
				minimum = steps;
				next_position = item;
				index = Stations.indexOf(item);
			}
		}
		game.reset();
		paths.add(game.moveTo(source, next_position));
		game.player.setPosition(next_position);
		Stations.remove(index);

		List<Position> nodes = game.map.getPokemons();
		nodes.addAll(Stations);
		nodes.add(game.map.getDestPos());

		int min = 100;

		boolean reached = false;

		while(!reached) {
			index = 0;
			min = 100;
			Position start = new Position(game.player.getPosition());
			for (Position item : nodes) {
				game.reset();
				int steps =game.moveTo(start, item).size();
				if (steps < min) {
					min = steps;
					next_position = item;
					index = nodes.indexOf(item);
				}
			}
			game.reset();
			paths.add(game.moveTo(start, next_position));
			nodes.remove(index);
			Cell cell = game.map.cells[next_position.row][next_position.col];
			if (cell instanceof TerminationCell) reached = true;
			game.player.addStep(next_position);
			game.player.setPosition(next_position);
		}

		game.player.path.clear();
		game.player.setPosition(game.map.getBeginPos());

		for (Path item: paths) {
			List<Position> list = item.getPath();
			for (Position pos: list){
				Cell cell = game.map.cells[pos.row][pos.col];
				if (cell instanceof Station){
					game.player.addBall((Station)cell);
				}
				else if (cell instanceof Pokemon){
					game.player.addPokemon((Pokemon) cell);
				}
				game.player.addStep(pos);
				game.player.setPosition(pos);
			}
		}
		game.player.addStep(game.map.getDestPos());

		game.player.setPosition(game.map.getDestPos());


		System.out.println(game.player.totalScore());

		System.out.println(game.player.scoreboard());

		System.out.println(game.player.path.display());

		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		writer.write(game.player.totalScore());
		writer.newLine();
		writer.write(game.player.scoreboard());
		writer.newLine();
		writer.write(game.player.path.display());
		writer.close();


	}
}

