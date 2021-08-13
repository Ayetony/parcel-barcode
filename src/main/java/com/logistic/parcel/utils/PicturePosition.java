package com.logistic.parcel.utils;

public class PicturePosition {

    private int dx1;//the x coordinate within the first cell 第一个单元格内的x坐标
    private int dy1;//the y coordinate within the first cell 第一个单元格内的y坐标
    private int dx2;//the x coordinate within the second cell 第二个单元格中的x坐标
    private int dy2;//the y coordinate within the second cell 第二个单元格中的y坐标
    private short col1;//the column (0 based) of the first cell 第一个单元格的列（基于0）
    private int row1;//the row (0 based) of the first cell 第一个单元格的行（基于0）
    private short col2;//the column (0 based) of the second cell 第二个单元格的列（基于0）
    private int row2;//the row (0 based) of the second cell 第二个单元格的行（基于0）

    public PicturePosition() {
        super();
    }
    public PicturePosition(int dx1, int dy1, int dx2, int dy2, short col1, int row1, short col2, int row2) {
        super();
        this.dx1 = dx1;
        this.dy1 = dy1;
        this.dx2 = dx2;
        this.dy2 = dy2;
        this.col1 = col1;
        this.row1 = row1;
        this.col2 = col2;
        this.row2 = row2;
    }
    public int getDx1() {
        return dx1;
    }
    public void setDx1(int dx1) {
        this.dx1 = dx1;
    }
    public int getDy1() {
        return dy1;
    }
    public void setDy1(int dy1) {
        this.dy1 = dy1;
    }
    public int getDx2() {
        return dx2;
    }
    public void setDx2(int dx2) {
        this.dx2 = dx2;
    }
    public int getDy2() {
        return dy2;
    }
    public void setDy2(int dy2) {
        this.dy2 = dy2;
    }
    public short getCol1() {
        return col1;
    }
    public void setCol1(short col1) {
        this.col1 = col1;
    }
    public int getRow1() {
        return row1;
    }
    public void setRow1(int row1) {
        this.row1 = row1;
    }
    public short getCol2() {
        return col2;
    }
    public void setCol2(short col2) {
        this.col2 = col2;
    }
    public int getRow2() {
        return row2;
    }
    public void setRow2(int row2) {
        this.row2 = row2;
    }


}
