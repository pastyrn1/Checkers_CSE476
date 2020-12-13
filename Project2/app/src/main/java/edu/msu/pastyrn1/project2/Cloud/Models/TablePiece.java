package edu.msu.pastyrn1.project2.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "checkertable")
public class TablePiece {
    @Attribute
    private int player;

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    @Attribute
    private int xidx;

    public int getXIdx() {
        return xidx;
    }

    public void setXIdx(int xidx) {
        this.xidx = xidx;
    }

    @Attribute
    private int yidx;

    public int getYIdx() {
        return yidx;
    }

    public void setYIdx(int yidx) { this.yidx = yidx; }

    @Attribute
    private int king;

    public int getAngle() {
        return king;
    }

    public void setAngle(int king) {
        this.king = king;
    }

    public TablePiece() {}

    public TablePiece(int player, int xidx, int yidx, int king) {
        this.player = player;
        this.xidx = xidx;
        this.yidx = yidx;
        this.player = player;
    }

}
