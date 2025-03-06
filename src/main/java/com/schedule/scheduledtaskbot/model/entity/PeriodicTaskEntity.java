package com.schedule.scheduledtaskbot.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PERIODIC_TASK")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodicTaskEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
            generator="periodic_task_id_seq")
    @SequenceGenerator(name="periodic_task_id_seq",
            sequenceName="periodic_task_id_seq", allocationSize=1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "method_name", nullable = false)
    private String methodName;

    @Column(name = "interval_seconds", nullable = false)
    private Integer intervalSeconds;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
