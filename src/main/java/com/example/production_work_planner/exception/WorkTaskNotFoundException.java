package com.example.production_work_planner.exception;

public class WorkTaskNotFoundException extends RuntimeException {

    public WorkTaskNotFoundException(Long id) {
        super("Work task not found with id: " + id);
    }
}
