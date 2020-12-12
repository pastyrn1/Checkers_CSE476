package edu.msu.pastyrn1.project2.Cloud.Models;

import androidx.annotation.Nullable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "checkers")
public class BoardResult {
    @Attribute
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Attribute(name = "msg", required = false)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @ElementList(name = "checkertable", inline = true, required = false, type = TablePiece.class)
    private List<TablePiece> pieces;

    public List<TablePiece> getPieces() {
        return pieces;
    }

    public void setPieces(List<TablePiece>  piece) {
        this.pieces = pieces;
    }

    public BoardResult() {}

    public BoardResult(String status, ArrayList<TablePiece> pieces, @Nullable String msg) {
        this.status = status;
        this.message = msg;
        this.pieces = pieces;
    }
}
