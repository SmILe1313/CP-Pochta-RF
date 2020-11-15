# CP-Pochta-RF
Реализовано на: Java Spring, Java Spring JPA, Vue.js, PostgreSql
Для запуска фронта (без деплоя отдельным приложением): views -> npm run serve
Бэк лучше запускать на Tomcat (jar сборка).
БД - посгрес. 
В application.properties прописан путь к БД (по-умлочанию "app",таблицы создаст jpa автоматически).
Для настройки vue - файл BackSrvice.js (по умолчанию указаны локальные апи из нашей впн).