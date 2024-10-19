package com.project_management.domain.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String fileType;
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
