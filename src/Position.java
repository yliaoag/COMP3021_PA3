public class Position {
    public int row;
    public int col;
    private Position parent;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
        this.parent = null;
    }

    public Position(Position pos){
        this.row = pos.row;
        this.col = pos.col;
        this.parent = null;
    }
    public Position(Position pos, Position parent){
        this.row = pos.row;
        this.col = pos.col;
        this.parent = parent;
    }
    public Position getParent() {return parent;}

    /**
     * Move the position to different direction
     * @return
     */
    public Position moveUp(){
        return new Position(row-1, col);
    }

    public Position moveDown(){
        return new Position(row+1, col);
    }

    public Position moveLeft(){
        return new Position(row, col-1);
    }

    public Position moveRight(){
        return new Position(row, col+1);
    }

    /**
     * Check whether the position is out of the bound
     * @param row
     * @param col
     * @return
     */
    public boolean outOfmap(int row, int col){
        if (this.row < 0 || this.row >= row || this.col < 0 || this.col >= col) return true;
        else return false;
    }
    public int row_distance (Position pos) { return (this.row - pos.row);}

    public int col_distance (Position pos) { return (this.col - pos.col);}

    public boolean equal(Position pos) { return this.row == pos.row && this.col == pos.col; }

    public void display(){
        System.out.print(row + " ");
        System.out.println(col);
    }
}

