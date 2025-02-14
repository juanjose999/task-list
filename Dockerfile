FROM amazoncorretto:21-alpine-jdk
LABEL authors="juancho"
ENV DB_URL=mongodb+srv://taskList_app:taskList_app@tasklist.uhrjq.mongodb.net/?retryWrites=true&w=majority&appName=taskList
ENV DB_NAME=tasklistdb
ENV SECRET=87d92b5ec0d86d63fa11c7f5927bd54a0054e7c9da76ec833cb28c5e660dac93
COPY target/task-list-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]