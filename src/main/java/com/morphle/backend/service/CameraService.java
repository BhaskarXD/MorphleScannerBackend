package com.morphle.backend.service;

import com.morphle.backend.utils.GridState;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CameraService {

    final int numMatrixRows=20;
    final int numMatrixColumns=60;
    private GridState[][] matrix;
    private int[] currentPosition;
    private int[] cameraPosition;

    public CameraService() {
        initializeMatrix();
        currentPosition = new int[]{0, 0}; // Set initial position
        cameraPosition = new int[]{0, 0}; // Set initial camera position
    }

    @PostConstruct
    public void init() {
        startContinuousCheck();
    }
    public void startContinuousCheck() {
        Thread checkThread = new Thread(() -> {
            while (true) {
                if (!Arrays.equals(cameraPosition, currentPosition)) {
                    int[] focusingPosition=currentPosition.clone();
                    setCameraPosition(focusingPosition);
                    setMatrix(focusingPosition,GridState.FOCUSING);
                    focus(focusingPosition);
                    if (Arrays.equals(cameraPosition, currentPosition)) {
                        setMatrix(focusingPosition,GridState.CAPTURING);
                        capture(focusingPosition);
                    }
                }
                try {
                    Thread.sleep(100); // Adjust this as needed for the desired checking frequency
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        checkThread.start();
    }

    public void updateCurrentPosition(String direction) {
        int newRow = currentPosition[0];
        int newCol = currentPosition[1];

        switch (direction) {
            case "up" -> newRow=((newRow-1)+numMatrixRows)%numMatrixRows;
            case "down" -> newRow=(newRow+1)%numMatrixRows;
            case "left" -> newCol=((newCol-1)+numMatrixColumns)%numMatrixColumns;
            case "right" -> newCol=(newCol+1)%numMatrixColumns;
            default -> { // can use a logger to log this incident or return a response to the front end denoting incorrect input
            }
        }
        setCurrentPosition(new int[]{newRow,newCol});
    }

    private void focus(int[] focusingPosition) {
        // Simulate focusing, takes 3 seconds
        System.out.println("Focusing on: " + focusingPosition[0] + ", " + focusingPosition[1]);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        setMatrix(focusingPosition,GridState.FOCUSED);
    }

    private void capture(int[] capturingPosition) {
        // Simulate capturing, takes 2 seconds
        System.out.println("Capturing image at: " + capturingPosition[0] + ", " + capturingPosition[1]);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        setMatrix(capturingPosition,GridState.CAPTURED);
    }



    private void initializeMatrix() {
        matrix = new GridState[numMatrixRows][numMatrixColumns];
        for (int i = 0; i < numMatrixRows; i++) {
            for (int j = 0; j < numMatrixColumns; j++) {
                matrix[i][j] = GridState.UNVISITED;
            }
        }
        matrix[0][0]=GridState.CAPTURED;
    }

    public GridState[][] getMatrix() {
        return matrix;
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    public int[] getCameraPosition() {
        return cameraPosition;
    }

    public void setCurrentPosition(int[] tempCurrentPosition) {
        this.currentPosition = tempCurrentPosition;

        if(matrix[tempCurrentPosition[0]][tempCurrentPosition[1]]==GridState.UNVISITED){
            setMatrix(tempCurrentPosition,GridState.VISITED);
        }
    }

    public void setMatrix(int[] positionToSet, GridState stateToSet) {
        this.matrix[positionToSet[0]][positionToSet[1]]=stateToSet;
    }

    public void setCameraPosition(int[] cameraPosition) {
        this.cameraPosition = cameraPosition;

    }
}
