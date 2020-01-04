import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Piece  extends StackPane {

    private PieceType type;

    private double mouseX, mouseY;
    private double oldX, oldY;
    private boolean king;

    public boolean getKing() {
        return king;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public PieceType getType() {
        return type;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public void setOldX(double oldX) {
        this.oldX = oldX;
    }

    public void setOldY(double oldY) {
        this.oldY = oldY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }


    public Piece(PieceType type, boolean king, int x, int y) {
        this.type = type;
        this.king = king;
        move( x,  y);
        Ellipse pieceEllipse = new Ellipse(CheckersApp.TILE_SIZE * 0.3125 ,CheckersApp.TILE_SIZE * 0.26);
        pieceEllipse.setFill(type == PieceType.RED ? Color.valueOf("c40003") : Color.valueOf("fff9f4"));
        pieceEllipse.setTranslateX(CheckersApp.TILE_SIZE * 0.5 - CheckersApp.TILE_SIZE * 0.3125);
        pieceEllipse.setTranslateY(CheckersApp.TILE_SIZE * 0.5 - CheckersApp.TILE_SIZE * 0.26);
        pieceEllipse.setStroke(Color.BLACK);
        pieceEllipse.setStrokeWidth(CheckersApp.TILE_SIZE * 0.03);
        Ellipse bg = new Ellipse(CheckersApp.TILE_SIZE * 0.3125 ,CheckersApp.TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);
        bg.setTranslateX(CheckersApp.TILE_SIZE * 0.5 - CheckersApp.TILE_SIZE * 0.3125);
        bg.setTranslateY(CheckersApp.TILE_SIZE * 0.5 - CheckersApp.TILE_SIZE * 0.26 + CheckersApp.TILE_SIZE * 0.07);
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(CheckersApp.TILE_SIZE * 0.03);
        Ellipse kingEllipse =  new Ellipse(0,0);
        if(king){
            kingEllipse = new Ellipse(CheckersApp.TILE_SIZE * 0.3125 ,CheckersApp.TILE_SIZE * 0.26);
            kingEllipse.setFill(type == PieceType.RED ? Color.valueOf("c40003") : Color.valueOf("fff9f4"));
            kingEllipse.setTranslateX(CheckersApp.TILE_SIZE * 0.5 - CheckersApp.TILE_SIZE * 0.3125);
            kingEllipse.setTranslateY(CheckersApp.TILE_SIZE * 0.5 - CheckersApp.TILE_SIZE * 0.26 - CheckersApp.TILE_SIZE * 0.1);
            kingEllipse.setStroke(Color.BLACK);
            kingEllipse.setStrokeWidth(CheckersApp.TILE_SIZE * 0.03);
        }


        setOnMouseDragged(e-> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
            relocate(mouseX, mouseY);
        });
        getChildren().addAll(bg, pieceEllipse,kingEllipse);
    }

    public void move(int x ,int y){
        oldX = x * CheckersApp.TILE_SIZE;
        oldY = y * CheckersApp.TILE_SIZE;
        relocate(oldX, oldY);
    }
    public void abortMove(){
        relocate(oldX, oldY);
    }
}
