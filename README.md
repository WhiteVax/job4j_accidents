## Автонарушители
Пользователи добавляют автонарушение. Указывая название, адрес, описание нарушения, статьи, категорию.

### Стек технологий:

![java](https://img.shields.io/badge/java-17-red)![Spring boot](https://img.shields.io/badge/Spring%20boot-2.7.4-green)![Thymeleaf](https://img.shields.io/badge/Spring%20boot%20starter%20thymeleaf-2.7.5-green)
![Thymeleaf](https://img.shields.io/badge/Spring%20boot%20starter%20web-2.7.5-green)
![Thymeleaf](https://img.shields.io/badge/Spring%20data%20jpa-2.7.3-green)
![Thymeleaf](https://img.shields.io/badge/Spring%20Security-2.7.4-green)
![Thymeleaf](https://img.shields.io/badge/Spring%20orm-5.3.23-green)
![Thymeleaf](https://img.shields.io/badge/Spring%20data%20jpa-2.7.5-green)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-ff69b4)
![Postgresql](https://img.shields.io/badge/Postgresql-14-blue)
![Postgresql](https://img.shields.io/badge/H2-2.1.214-blue)
![Lombok](https://img.shields.io/badge/Lombok-1.18.24-red)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red)

### Требования к окружению :

- ![java](https://img.shields.io/badge/java-17+-red)
- ![Maven](https://img.shields.io/badge/Maven-4.0.0-red)
- ![Postgresql](https://img.shields.io/badge/Postgresql-14-blue)

### Запуск приложения

Запуск с помощью командной строки:

1. Создать базу данных cars.

```CREATE DATABASE auto_crash;```

2. Перейти в папку с проектом.
3. Выполнить команду: mvn liquibase:update
4. Выполнить команду: mvn clean install
5. Выполнить команду: mvn spring-boot:run
6. Перейти по ссылке: http://localhost:8080

### Виды

#### Вид входа
![входа](imgs/log.png)

#### Вид регистрации
![регистрации вид](imgs/regUser.png)

#### Вид добавления инцидента
![добавления инцидента](imgs/addTask.png)

#### Вид редактирования инцидента
![Редактирования вид](imgs/editTask.png)

#### Главный вид
![главный вид](imgs/all.png)


### Расширения приложения

Реализовать статусы заявок, добавить возможность добавления фотографии.

### Контакты: @WhiteVax