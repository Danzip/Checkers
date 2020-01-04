import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class CheckersApp extends Application {
    public static final int TILE_SIZE = 100;
    public static final int HEIGHT = 8;
    public static final int WIDTH = 8;

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Tile[][] board = new Tile[WIDTH][HEIGHT];
    private Parent createContent() {
        for (int y = 0; y < HEIGHT; y++){
            for (int x = 0; x < WIDTH; x++){
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                tileGroup.getChildren().add(tile);
                board[x][y] = tile;
                Piece piece = null;
                if (y <= 2 && (x + y) % 2 == 1){
                    piece = makePiece(PieceType.RED,false, x, y);
                }
                if (y >= 5 && (x + y) % 2 == 1){
                    piece = makePiece(PieceType.WHITE,false, x, y);
                }
                if (piece != null){
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        Pane root = new Pane();
        root.setPrefSize(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
        root.getChildren().addAll(tileGroup, pieceGroup);
        return root;
    }

    public int toBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;

    }

    private MoveResult tryMove(Piece piece,int newX,int newY) {
        System.out.println("Trying move");
        if ((newX + newY) % 2 == 0 || board[newX][newY].hasPiece()) {
            return new MoveResult(MoveType.NONE);
        }
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());
        int x1 = x0 + (newX - x0) / 2;
        int y1 = y0 + (newY - y0) / 2;
        System.out.println("Checking King");
        if (piece.getKing()) {
            System.out.println("King");
            if (newY >= y0) {
                System.out.println("going down");

                for (int step = 1; step <= newY - y0; step++) {
                    System.out.println("checking Tiles");
                    if (board[x0 + step][y0 + step].hasPiece() && board[x0 + step][y0 + step].getPiece().getType()
                            != piece.getType()) {
                        if (newY == y0 + step + 1) {
                            return new MoveResult(MoveType.KILL, piece, board[x0 + step][y0 + step].getPiece());
                        } else {
                            return new MoveResult(MoveType.NONE);
                        }
                    }
                }
                System.out.println("No pieces to kill");
                return new MoveResult(MoveType.NORMAL);
            } else {
                System.out.println("going up");
                for (int step = -1; step >= newY - y0; step--) {
                    System.out.println("checking Tiles");
                    if (board[x0 + step][y0 + step].hasPiece() && board[x0 + step][y0 + step].getPiece().getType()
                            != piece.getType()) {
                        if (newY == y0 + step - 1) {
                            return new MoveResult(MoveType.KILL, piece, board[x0 + step][y0 + step].getPiece());
                        } else {
                            return new MoveResult(MoveType.NONE);
                        }
                    }

                    return new MoveResult(MoveType.NORMAL);
                }
            }

        } else {
            System.out.println("not King");
            if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
                return new MoveResult(MoveType.NORMAL);
            }


            if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2 &&
                    board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        } return new MoveResult(MoveType.NONE);
    }

    public Piece makePiece(PieceType type,boolean king, int x, int y){
        Piece piece = new Piece(type,king, x, y);

        piece.setOnMouseReleased(e->{
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());
            MoveResult result;
            if (newX < 0 || newX >= WIDTH || newX < 0 || newX >= HEIGHT){
                result = new MoveResult(MoveType.NONE);
            }
            else {
                if (piece.getKing()){
                    System.out.println("king");
                }
                result = tryMove(piece, newX, newY);
            }
            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());
            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[newX][newY].setPiece(piece);
                    board[x0][y0].setPiece(null);
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[newX][newY].setPiece(piece);
                    board[x0][y0].setPiece(null);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }
            if(piece.getType() == PieceType.RED && newY == HEIGHT - 1 || piece.getType() == PieceType.WHITE && newY == 0 ){
                System.out.println("Created King");
                Piece kPiece = makePiece(piece.getType(),true, newX, newY);
                pieceGroup.getChildren().remove(piece);
                board[newX][newY].setPiece(kPiece);
                pieceGroup.getChildren().add(kPiece);
            }
        });
        return piece;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("CheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}