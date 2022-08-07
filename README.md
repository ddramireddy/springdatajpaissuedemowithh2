# springdatajpaissuedemowithh2

This project is demo project to show Transaction rollback issue in Spring Data JPA library from 2.5.6 onwards. Changing the dependency to less than 2.5.5 shows project is successful.

<dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-jpa</artifactId>
            <version>2.5.6</version>
        </dependency>
