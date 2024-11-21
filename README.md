# REST API для CRUD операций над объектом телефон на чистой JavaEE

## Стек:
- Java 17
- PostgreSQL
- log4j
- jackson
- jakarta.servlet-api
- Junit/Mockito
- Tomcat 10.1.15

## Устанвка и запуск
1. Склонировать репозиторий.
2. Склонировать заполнить в файл [***application.properties***](src/main/resources/application.properties) данные для подключения к БД Postgresql
3. Установить [***Tomcat 10.1.15***](https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.15/)
4. Настроить IDE для запуска tomcat и приложения в нем:
    * Инструкция для Intellij IDEA Ultimate
        * -> **Run/Debug Configuration**
        * -> **+ Add new configuration**
        * -> Tomcat Server -> Local
        * -> application server, нажать на кнопку *"Configure"*
        * -> указать путь до разархивированного каталога с *tomcat-10.1.15*
        * -> перейти на вкладку **Deployment** и добавить артифакт
        * -> нажать ***'Apply'***
        * -> Подключить IDE к вашей БД и выполнить скрипт [***init.sql***](src/main/resources/sql/init.sql)
        * Запустить проект кнопкой 'Run ▶'