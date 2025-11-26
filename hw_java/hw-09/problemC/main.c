#include <stdio.h>
#include "student_manager.h"

// Демонстрационная функция
void demonstrate() {
    printf("=== Демонстрация работы менеджера студентов ===\n");
    
    StudentManager* manager = create_manager(5);
    
    add_student(manager, "Иван Иванов", 1);
    add_student(manager, "Петр Петров", 2);
    add_student(manager, "Мария Сидорова", 3);
    
    add_grade(manager, 1, 4.5f);
    add_grade(manager, 1, 3.8f);
    add_grade(manager, 1, 4.2f);
    
    add_grade(manager, 2, 3.2f);
    add_grade(manager, 2, 4.8f);
    
    add_grade(manager, 3, 5.0f);
    
    printf("\n=== Информация о студентах ===\n");
    print_student(manager, 1);
    print_student(manager, 2);
    print_student(manager, 3);
    
    print_student(manager, 10);
    
    free_manager(manager);
}

int main() {
    demonstrate();
    return 0;
}