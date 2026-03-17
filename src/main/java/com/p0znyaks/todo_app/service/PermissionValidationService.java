package com.p0znyaks.todo_app.service;

import com.p0znyaks.todo_app.entity.Task;

public interface PermissionValidationService {

    /**
     * Проверяет, имеет ли пользователь доступ к {@link Task}. В случае, если пользователь имеет доступ, метод
     * успешно завершается. В случае, если пользователь не имеет доступа метод бросает exception. Для получения информации
     * о сессии использовать {@link com.p0znyaks.todo_app.security.UserPrincipal}.
     *
     * @param task задача, к которой необходимо проверить доступ
     * @throws com.p0znyaks.todo_app.exception.AccessForbiddenException в случае, если пользователь не имеет доступа
     */
    void checkIfHasAccess(Task task);

    /**
     * Проверяет, имеет ли пользователь доступ к {@link Task}. Возвращает булево значение, указывающее, имеет ли
     * пользователь доступ к ресурсу.
     *
     * @param task задача, к которой необходимо проверить доступ
     *
     * @return булево значение, указывающее, имеет ли пользователь доступ к ресурсу
     */
    boolean hasAccesss(Task task);
}
