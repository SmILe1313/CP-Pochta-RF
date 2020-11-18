# CPRF

Сервис нормализации почтовых адресов. Подготовлен в рамках хакатона Цифровой прорыв 2020.

### Реализовано на
  - Java Spring
  - Java Spring JPA
  - PostgreSql
  - Vue.js

### Установка

#### Backend
Бэк лучше запускать на Tomcat (jar сборка). БД - посгрес.
В application.properties прописан путь к БД (по-умлочанию "app",таблицы создаст jpa автоматически).
    
#### Frontend
```sh
$ cd views
$ npm install
$ npm run serve
```

Конфигурация в файле view/src/service/BackService.js

```js
const backLink = 'http://192.169.6.10:9000'
```
Указать ip сервера бекенда или заменить на localhost
