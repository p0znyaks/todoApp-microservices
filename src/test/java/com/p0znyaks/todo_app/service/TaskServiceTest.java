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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;
    private User testUser;
    private Long userId;
    private Long taskId;

    @BeforeEach
    void setUp() {
        // given
        userId = 1L;
        taskId = 1L;
        testUser = new User(1L, "testUser", "test@gmail.com", "password", new ArrayList<>());
        testTask = new Task(1L, "Test Task", "Test Description", false, testUser);
    }

    @Test
    void getTaskByUserId_ReturnTask_WhenTaskExists() {
        TaskResponse expectedResponse = new TaskResponse(1L, "Test Task", "Test Description", false);

        when(taskRepository.findByUserIdAndId(userId, taskId)).thenReturn(Optional.of(testTask));
        when(taskMapper.convertToResponse(testTask)).thenReturn(expectedResponse);

        TaskResponse result = taskService.getTaskByUserId(userId, taskId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(taskId);
        assertThat(result.getTitle()).isEqualTo(testTask.getTitle());
        assertThat(result.getDescription()).isEqualTo(testTask.getDescription());
        assertThat(result.isCompleted()).isEqualTo(testTask.isCompleted());
    }

    @Test
    void getTaskByUserId_ShouldThrownException_WhenTaskNotFound() {
        Long nonExistentUserId = 0L;

        when(taskRepository.findByUserIdAndId(nonExistentUserId, taskId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskByUserId(nonExistentUserId, taskId))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    void createTask_ShouldSaveTask_WhenUserExists() {
        TaskRequest testRequest = new TaskRequest("New Task", "New Desc", false);

        Task taskToSave = new Task();
        taskToSave.setTitle(testRequest.getTitle());
        taskToSave.setDescription(testRequest.getDescription());
        taskToSave.setCompleted(testRequest.isCompleted());
        taskToSave.setUser(testUser);

        Task savedTask = new Task(1L, "New Task", "New Desc", false, testUser);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(taskMapper.convertToEntity(testRequest)).thenReturn(taskToSave);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(taskMapper.convertToResponse(savedTask)).thenReturn(new TaskResponse(
                1L, taskToSave.getTitle(), taskToSave.getDescription(), taskToSave.isCompleted()
        ));

        TaskResponse result = taskService.createTask(userId, testRequest);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(testRequest.getTitle());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_ShouldThrownException_WhenUserNotFound() {
        Long nonExistentUserId = 0L;
        TaskRequest testRequest = new TaskRequest("New Task", "New Desc", false);

        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.createTask(nonExistentUserId, testRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found");

        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void updateTask_ShouldUpdateTask_WhenTaskExist() {
        TaskRequest testRequest = new TaskRequest("Updated Task", "Updated Desc", true);

        Task updatedTask = new Task(1L, "Updated Task", "Updated Desc", true, testUser);
        TaskResponse taskResponse = new TaskResponse(1L, "Updated Task", "Updated Desc", true);

        when(taskRepository.findByUserIdAndId(userId, taskId)).thenReturn(Optional.of(testTask));
        doNothing().when(taskMapper).updateEntityFromRequest(testRequest, testTask);
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        when(taskMapper.convertToResponse(updatedTask)).thenReturn(taskResponse);
        TaskResponse result = taskService.updateTask(userId, taskId, testRequest);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(testRequest.getTitle());
        assertThat(result.isCompleted()).isEqualTo(testRequest.isCompleted());
    }
}