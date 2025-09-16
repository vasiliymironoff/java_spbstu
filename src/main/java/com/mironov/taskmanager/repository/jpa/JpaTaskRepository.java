package com.mironov.taskmanager.repository.jpa;

import com.mironov.taskmanager.model.Task;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
@Profile("h2")
public interface JpaTaskRepository extends JpaRepository<Task, Long> {
    // Метод save наследуется от JpaRepository, его не нужно переопределять
    // Task save(Task task);

    // Получение всех задач - используем стандартное имя метода
    @Override
    List<Task> findAll();

    // Альтернативный вариант с кастомным запросом (если нужен особый порядок сортировки)
    @Query("SELECT t FROM Task t")
    List<Task> findAllTasks();

    // Поиск задач по пользователю и статусу pending
    List<Task> findByUserIdAndPending(Long userId, Boolean pending);

    // Метод deleteById наследуется от JpaRepository, его не нужно переопределять
    // void deleteById(Long taskId);
}