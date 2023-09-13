package com.morphle.backend.controller;

import com.morphle.backend.service.CameraService;
import com.morphle.backend.utils.GridState;
import com.morphle.backend.utils.MatrixAndCurrentCell;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MatrixController {
    private final CameraService cameraService;

    public MatrixController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @GetMapping("/getMatrix")
    public MatrixAndCurrentCell getMatrix() {
        GridState[][] matrix = cameraService.getMatrix();
        int[] currentCell = cameraService.getCurrentPosition();
        return new MatrixAndCurrentCell(matrix, currentCell);
    }

    @PostMapping("/arrowKeyPress")
    public ResponseEntity<String> handleArrowKeyPress(@RequestBody Map<String, String> request) {
        String direction = request.get("direction");
       cameraService.updateCurrentPosition(direction);
        return ResponseEntity.ok("Arrow key press received: " + direction);
    }
}
