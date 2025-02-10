package com.natixis.todoapp.adapters.persistance.jpa;

import com.natixis.todoapp.adapters.persistance.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {
}
