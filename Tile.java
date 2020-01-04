import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class Tile extends Rectangle {

    private Piece piece;


    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasPiece(){
        return piece != null;
    }

    public Tile(boolean light, int x, int y)
    {
        setWidth(CheckersApp.TILE_SIZE);
        setHeight(CheckersApp.TILE_SIZE);
        setFill(light ? Color.valueOf("feb") : Color.valueOf("582"));
        relocate(CheckersApp.TILE_SIZE * x, CheckersApp.TILE_SIZE * y);
    }
}
