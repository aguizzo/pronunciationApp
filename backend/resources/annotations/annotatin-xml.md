# Annotations and xml

> Spring Boot has significantly simplified configuration through extensive use of annotations, reducing the need for XML-based configuration.
> 
> This shift towards annotation-based configuration became <mark>more prominent with Spring 2.1</mark> and has continued to evolve in subsequent versions.

Here's a simple example comparing XML-based configuration with annotation-based configuration in Spring Boot:

XML Configuration:

```xml
<bean id="myService" class="com.example.MyService">
    <property name="repository" ref="myRepository"/>
</bean>

<bean id="myRepository" class="com.example.MyRepository"/>
```

Equivalent Annotation-based Configuration:

```java
@Service
public class MyService {
    @Autowired
    private MyRepository repository;
}

@Repository
public class MyRepository {
    // Implementation
}
```

In this example, the `@Service` and `@Repository` annotations replace the XML bean definitions, while `@Autowired` handles dependency injection[3](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html).

#### Key Annotations in Spring Boot

1. **@SpringBootApplication**: This annotation combines `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. It's typically used on the main class of a Spring Boot application1.

2. **@Component**: A generic stereotype for any Spring-managed component[3](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html).

3. **@Controller**, **@Service**, **@Repository**: Specializations of `@Component` for more specific use cases[3](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html).

4. **@Autowired**: Used for automatic dependency injection[3](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html).

5. **@Configuration**: Indicates that a class declares one or more `@Bean` methods and may be processed by the Spring container to generate bean definitions1.

6. **@Bean**: Indicates that a method produces a bean to be managed by the Spring container1.

7. **@Value**: Used to inject values from properties files into components[3](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html).

#### Advantages of Annotation-based Configuration

1. **Reduced boilerplate**: Annotations significantly reduce the amount of configuration code needed1.

2. **Type safety**: Annotations are checked at compile-time, reducing runtime errors[3](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html).

3. **Improved readability**: Annotations make the code more self-documenting1.

4. **Easier refactoring**: Changes to class names or package structures are automatically reflected in the configuration[3](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html).

While annotations have become the preferred method of configuration in Spring Boot, it's still possible to use XML configuration when needed, especially for legacy systems or specific use cases. Spring Boot allows for mixing both approaches, providing flexibility in configuration strategies[2](https://www.springboottutorial.com/spring-boot-java-xml-context-configuration)[4](https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/xsd-configuration.html).

### Citations:

1. [Spring Boot Tutorial: Mixing XML and Annotation Configuration - YouTube](https://www.youtube.com/watch?v=6arSdLciC_k)
2. [Java Annotation and XML Bean Configurations with Spring Boot | Spring Boot Tutorial](https://www.springboottutorial.com/spring-boot-java-xml-context-configuration)
3. [Annotation-based Container Configuration :: Spring Framework](https://docs.spring.io/spring-framework/reference/core/beans/annotation-config.html)
4. [XML Schema-based configuration](https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/xsd-configuration.html)
5. [Do we need xml file for doing Spring configuration using Annotation? - Stack Overflow](https://stackoverflow.com/questions/38322967/do-we-need-xml-file-for-doing-spring-configuration-using-annotation)
6. [Spring Boot XML Configuration Example](https://www.javaguides.net/2018/09/spring-boot-xml-configuration-example.html)
7. [Spring Annotation and XML Based Configuration - Studytonight](https://www.studytonight.com/spring-framework/spring-annotation-and-xml-based-configuration)
8. [How can I use Spring Boot auto-configured beans in XML configuration files? - Stack Overflow](https://stackoverflow.com/questions/25495629/how-can-i-use-spring-boot-auto-configured-beans-in-xml-configuration-files)

## Example

```java
@JoinTable(name = "USER_PRODUCT"
joinColumns = @JoinColumn(name = "USER_FK"),
inverseJoinColumns = @JoinColumn(name = "PRODUCT_FK"))
public Set<Product> products
```

To configure the equivalent of the given `@JoinTable` annotation in `XML` for Spring MVC, you would use the following XML configuration:

```xml
<bean id="yourEntityClass" class="com.eample.YourEntityClass">
    <property name="products">
        <set>
            <bean class="org.hibernate.annotations.CollectionOfElements">
                <property name="table" value="USER_PRODUCT"/>
                <property name="joinColumns">
                    <bean class="javax.persistence.JoinColumn">
                        <property name="name" value="USER_FK"/>
                    </bean>
                </property>
                <property name="targetElement" value="com.example.Product"/>
                <property name="inverseJoinColumns">
                    <bean class="javax.persistence.JoinColumn">
                        <property name="name" value="PRODUCT_FK"/>
                    </bean>
                </property>
            </bean>
        </set>
    </property>
</bean>
```

This `XML` configuration creates a <mark>many-to-many</mark> relationship between the `User` and `Product` entities using a <mark>join table</mark> named "`USER_PRODUCT`". The join column for the User entity is "`USER_FK`", and the inverse join column for the Product entity is "`PRODUCT_FK`"[1](https://stackoverflow.com/questions/30454300/spring-mvc-xml-configuration)[3](https://dzone.com/articles/spring-mvc-and-java-based-configuration-1).

### Citations:

1. [java - Spring MVC xml configuration - Stack Overflow](https://stackoverflow.com/questions/30454300/spring-mvc-xml-configuration)
2. [SQL INNER JOIN](https://www.w3schools.com/sql/sql_join_inner.asp)
3. https://dzone.com/articles/spring-mvc-and-java-based-configuration-1
4. [Introduction to Spring Data JPA Part 8: Many-to-Many Bidirectional](https://dzone.com/articles/introduction-to-spring-data-jpa-part-8-many-to-man)
5. https://proliferay.com/xml-configuration-files-for-spring-mvc/
6. [EclipseLink/Examples/JPA/2.0/ElementCollections - Eclipsepedia](https://wiki.eclipse.org/EclipseLink/Examples/JPA/2.0/ElementCollections)
7. [Spring MVC Basic Example Without Any XML Configuration.](https://codedeal.wixsite.com/codedeal/single-post/spring-mvc-basic-example-without-any-xml-configuration)
8. [Spring MVC :: Spring Boot](https://docs.spring.io/spring-boot/how-to/spring-mvc.html)
