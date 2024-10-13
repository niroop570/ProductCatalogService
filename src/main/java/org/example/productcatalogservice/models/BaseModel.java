package org.example.productcatalogservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CurrentTimestamp(event = EventType.INSERT)
    private Date createdAt;
    @CurrentTimestamp(event = {EventType.INSERT,EventType.UPDATE})
    private Date updatedAt;
    private State state;
}
