#ifndef STUDENT_MANAGER_H
#define STUDENT_MANAGER_H

typedef struct {
    char name[50];
    int id;
    float* grades;
    int grades_count;
    float average;
} Student;

typedef struct {
    Student* students;
    int count;
    int capacity;
} StudentManager;

// Функция для создания менеджера студентов
StudentManager* create_manager(int capacity);

// Функция для добавления студента
void add_student(StudentManager* manager, const char* name, int id);

// Функция для добавления оценки студенту
void add_grade(StudentManager* manager, int student_id, float grade);

// Функция для поиска студента по ID
Student* find_student(StudentManager* manager, int student_id);

// Функция для вывода информации о студенте
void print_student(StudentManager* manager, int student_id);

// Функция для освобождения памяти
void free_manager(StudentManager* manager);

// Демонстрационная функция
void demonstrate();

#endif