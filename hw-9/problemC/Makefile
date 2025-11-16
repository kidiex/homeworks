CC=gcc
CFLAGS=-g -Wall -Wextra
TARGET=student_manager
SOURCES=student_manager.c main.c
OBJECTS=$(SOURCES:.c=.o)
HEADERS=student_manager.h

$(TARGET): $(OBJECTS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJECTS)

%.o: %.c $(HEADERS)
	$(CC) $(CFLAGS) -c $< -o $@

debug: CFLAGS += -O0 -DDEBUG
debug: $(TARGET)

release: CFLAGS += -O2
release: $(TARGET)

clean:
	rm -f $(TARGET) $(OBJECTS)

.PHONY: clean debug release