package com.github.sebastian4j.pojoxlsx;

import java.io.InputStream;

public class ImageSheet {
    private final InputStream image;
    private final String sheetName;
    private final int col1;
    private final int col2;
    private final int row1;
    private final int row2;

    public ImageSheet(InputStream image, String sheetName, int col1, int col2, int row1, int row2) {
        this.image = image;
        this.sheetName = sheetName;
        this.col1 = col1;
        this.col2 = col2;
        this.row1 = row1;
        this.row2 = row2;
    }

    public InputStream getImage() {
        return image;
    }

    public String getSheetName() {
        return sheetName;
    }

    public int getCol1() {
        return col1;
    }

    public int getCol2() {
        return col2;
    }

    public int getRow1() {
        return row1;
    }

    public int getRow2() {
        return row2;
    }
}
