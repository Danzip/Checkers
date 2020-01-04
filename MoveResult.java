public class MoveResult {
    MoveType type;
    Piece piece;
    Piece otherPiece;

    public Piece getOtherPiece() {
        return otherPiece;
    }

    public Piece getPiece() {
        return piece;
    }

    public MoveType getType() {
        return type;
    }
    public MoveResult(MoveType type, Piece piece, Piece otherPiece){
        this.type = type;
        this.piece = piece;
        this.otherPiece = otherPiece;
    }
    public MoveResult(MoveType type, Piece piece){
        this(type, piece,null);
       ;
    }

    public MoveResult(MoveType type){
        this(type, null,null);
    }
}
