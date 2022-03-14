package org.generation.TodoList.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title, description;

    @Column(name="target_dt")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime targetDateTime;

    @Column(name="last_update_dt")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime lastUpdateDateTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTargetDateTime() {
        return targetDateTime;
    }

    public void setTargetDateTime(LocalDateTime targetDateTime) {
        this.targetDateTime = targetDateTime;
    }

    public LocalDateTime getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", targetDateTime=" + targetDateTime +
                ", lastUpdateDateTime=" + lastUpdateDateTime +
                '}';
    }
}