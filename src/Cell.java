public class Cell {

    public final Position position;

    Cell(Position pos) {position = pos;}

    public void display(){
        String str = "<" + position.row + ", " + position.col + ">";
        System.out.print(str);
    }

    public static Cell typeFromChar(char c, Position pos){
        switch (c){
            case '#':
                return new Wall(pos.row, pos.col);
            case ' ':
                return new EmptyCell(pos.row, pos.col);
            case 'B':
                return new TerminationCell(pos.row, pos.col, TerminationCell.Type.Begin);
            case 'D':
                return new TerminationCell(pos.row, pos.col, TerminationCell.Type.Destination);
            case 'S':
                return new Station(pos.row, pos.col);
            case 'P':
                return new Pokemon(pos.row, pos.col);
            default:
                return null;
        }

    }
}

