package com.p0znyaks.todo_app.service;

import com.p0znyaks.todo_app.dto.TaskRequest;
import com.p0znyaks.todo_app.dto.TaskResponse;
import com.p0znyaks.todo_app.entity.Task;
import com.p0znyaks.todo_app.entity.User;
import com.p0znyaks.todo_app.exception.TaskNotFoundException;
import com.p0znyaks.todo_app.exception.UserNotFoundException;
import com.p0znyaks.todo_app.mappers.TaskMapper;
import com.p0znyaks.todo_app.repository.TaskRepository;
import com.p0znyaks.todo_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponse> getTasksByUserId(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new UserNotFoundException();
        }

        return taskRepository.findByUserId(userId)
                .stream()
                .map(taskMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTaskByUserId(Long userId, Long taskId) {
        Task task = taskRepository.findByUserIdAndId(userId, taskId)
                .orElseThrow(TaskNotFoundException::new);

        return taskMapper.convertToResponse(task);
    }

    public TaskResponse createTask(Long userId, TaskRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Task task = taskMapper.convertToEntity(request);
        task.setUser(user);

        return taskMapper.convertToResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(Long userId, Long taskId, TaskRequest request) {
        Task task = taskRepository.findByUserIdAndId(userId, taskId)
                .orElseThrow(TaskNotFoundException::new);

        taskMapper.updateEntityFromRequest(request, task);

        return taskMapper.convertToResponse(taskRepository.save(task));
    }

    public void deleteTask(Long userId, Long taskId) {
        Task task = taskRepository.findByUserIdAndId(userId, taskId)
                .orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }
}
