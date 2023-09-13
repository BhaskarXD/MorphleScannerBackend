package com.morphle.backend.utils;

public class MatrixAndCurrentCell {
    private String[][] matrix;
    private int[] currentCell;

    public MatrixAndCurrentCell(GridState[][] matrix, int[] currentCell) {
        String[][] stringMatrix = new String[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                stringMatrix[i][j] = matrix[i][j].toString(); // Convert GridState to string
            }
        }
        this.matrix = stringMatrix;
        this.currentCell = currentCell;
    }
// Constructors, getters, and setters

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public void setCurrentCell(int[] currentCell) {
        this.currentCell = currentCell;
    }

    public int[] getCurrentCell() {
        return currentCell;
    }
}