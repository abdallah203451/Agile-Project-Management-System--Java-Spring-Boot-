package com.project_management.application.Enum;

public enum Status {
    TO_DO,              // Task is yet to be started
    IN_PROGRESS,        // Task is being worked on
    TESTING,          // Task is completed but under review (optional)
    DONE,               // Task is completed
    BLOCKED             // Task cannot proceed due to external dependencies
}
