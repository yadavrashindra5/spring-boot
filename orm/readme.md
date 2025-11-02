# How to configure Database with project.

1. To confiure database with project we have to add these code into our configuration file ie application.properties

```
spring.datasource.url=jdbc:mysql://localhost:3306/orm
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

- `spring.datasource.url=jdbc:mysql://localhost:3306/orm`
  ![img.png](img.png)
- `spring.datasource.username=root`
    - This is username for connecting to the database server


- `spring.datasource.password=root`
    - This password for connecting to database server


- `spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`
    - Specifies the `JDBC driver class` for MySQl
    - It required because java does not natively support database communication. it relies on database-specific drivers
      to interact with different databases like MySQL, PostgreSQL, Oracle etc
    - Required only in `older versions` of spring boot(Spring Boot 2+ usually detects it automatically)
    - It tells spring Boot which database driver to use when connecting to MySQL.

```
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

- `spring.jpa.hibernate.ddl-auto=update`
    - This property `controls how Hibernate updates the database schema`(`tables, columns, constraints`) when your
      spring boot application starts.
    - ddl-auto stands for `Data Definition Language (DDL) Auto`
    - default value is **none**</span>
    - **Possible values**:-
        - none - **No changes** to the database schema.
        - update - **Updates the schema** without dropping existing tables.
        - create - **Creates the schema** every time the app starts (deletes old data).
        - create-drop - Creates the schema **on startup**, then **drops it** on shutdown.
        - validate - **Only validates** the schema (does not modify it).


- `spring.jpa.show-sql=true`
    - Enables SQL query logging in the console
    - when set to true, spring boot logs all SQL queries executed by JPA


- `spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect`
    - This property **tells Hibernate how to generate SQL queries** for your database.
    - Different database (`MySQL, PostgreSQL, Oracle` etc) have different SQL syntax.
    - Hibernate `needs to know which database you are using` so, it can generate the correct SQL queries.
    - ```aiignore
    1. MySQL uses LIMIT for pagination.
    2. Oracle uses ROWNUM instead.
    3. PostgreSQL uses OFFSET and LIMIT.
    ```

**Note**:-If Hibernate doesn't know the database type, it might generate incorrect SQL queries.


# One-to-One Relationship in Spring Data JPA
A One-to-One relationship means one entity is associated with exactly one instance of another entity.

# Example: A User has one Profile.

🧩 Key Annotation
```
@OneToOne
```
```Note:- It is used to define a one-to-one mapping between two entities.```

# 🧱 Example

# ✅ Example 1: Unidirectional One-to-One
User → Profile (User knows Profile, but Profile doesn’t know User)

```
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
}

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String phone;
}

```
# 📝 Explanation:
- `@JoinColumn(name = "profile_id")` → creates a foreign key in the `users` table.
- `cascade = CascadeType.ALL` → saves/deletes `Profile` automatically with `User`.


# ✅ Example 2: Bidirectional One-to-One
Both entities know each other.

```
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
}

@Entity
@Table(name = "profiles")
public class Profile {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String address;

@OneToOne
@JoinColumn(name = "user_id", referencedColumnName = "id")
private User user;
}
```
# 📝 Explanation:
- `mappedBy = "user"` → makes `Profile` the owning side (it contains the foreign key).
- The `User` entity is the inverse side.

# ⚙️ Cascade Types (Commonly Used)
| Cascade Type | Description                                   |
| ------------ | --------------------------------------------- |
| `PERSIST`    | When parent is saved, child is also saved     |
| `REMOVE`     | When parent is deleted, child is also deleted |
| `ALL`        | Applies all cascading options                 |
| `MERGE`      | When parent is updated, child is also merged  |

# ⚡ Fetch Types
By default, @OneToOne uses EAGER fetching.
| Fetch Type | Description                                |
| ---------- | ------------------------------------------ |
| `EAGER`    | Loads associated entity immediately        |
| `LAZY`     | Loads associated entity only when accessed |

# Example
```
@OneToOne(fetch = FetchType.LAZY)
private Profile profile;
```

# 🌿 @OneToMany Relationship in Spring Data JPA (Full Notes)
A One-to-Many relationship means one parent entity is associated with multiple child entities.

👉 Example:
- A `Department` can have `many Employees`.
- A `Category` can have `many Products`.

**It’s one of the most common relationships in relational databases.**

🧩 Key Annotation

`
@OneToMany
`
`Used to define a one-to-many association between two entities.`

# 🧱 1️⃣ Unidirectional One-to-Many
`Parent knows the child`, but the `child doesn’t know the parent`.

# ✅ Example

# 🏢 Department (Parent)

```
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One Department has many Employees
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private List<Employee> employees = new ArrayList<>();

    // Getters and Setters
}
```
# 👨‍💼 Employee (Child)
```
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
}
```
# 🧩 How it Works
# ⚙️ Foreign Key
- The foreign key (department_id) is created in the child table (employees).
- Because of @JoinColumn, JPA does not create an extra join table.
- The employees table will have a column department_id that links each employee to a department.

# 🧱 2️⃣ Bidirectional One-to-Many
Both parent and child know each other.
- The child owns the relationship (foreign key resides in child table).
- The parent just references it through mappedBy.

# ✅ Example

# 🏢 Department (Parent)
```
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Bidirectional mapping
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    // Helper methods to manage both sides
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }
}
```

# 👨‍💼 Employee (Child)
```
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;

    // Many employees belong to one department
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
```

# 🧩 How it Works
⚙️ Foreign Key
- The foreign key (department_id) is created in the child table (employees).
- The child is the owning side (it actually stores the foreign key).
- The parent is the inverse side (it just references the relationship through mappedBy).

# 🔁 @ManyToOne Relationship in Spring Data JPA
A Many-to-One relationship means that multiple child entities are associated with a single parent entity.

# 👉 Example:
- Many Employees belong to one Department
- Many Products belong to one Category
- Many Orders belong to one Customer

`This is one of the most common relationships in relational databases.`

# 🧩 1️⃣ Unidirectional @ManyToOne

# 💡 Concept

In a unidirectional @ManyToOne relationship,
only the child entity knows about the parent entity.
The parent does not have any reference to the child.

`✅ The foreign key column is created in the child table.`

# Example

# 👨‍💼 Employee (Child)
```
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Many employees belong to one department
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    // Getters and Setters
}
```

# 🏢 Department (Parent)

```
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // No reference to Employee here (Unidirectional)
}
```

🧾 Database Tables
# departments
| id | name |
| -- | ---- |
| 1  | IT   |
| 2  | HR   |

# employees

| id | name | department_id |
| -- | ---- | ------------- |
| 1  | John | 1             |
| 2  | Alex | 1             |
| 3  | Tina | 2             |

# ✅ Foreign Key:
employees.department_id → departments.id

# 🔍 Key Points
- Foreign key is always in the child table (employees).
- Employee is the owning side (since it holds the foreign key).
- The relationship is unidirectional, so you cannot access employees from the Department side.
- Best suited when navigation is needed only one way (e.g., from Employee → Department).

# 🧩 2️⃣ Bidirectional @ManyToOne + @OneToMany
# 💡 Concept
- In a bidirectional relationship:
- Both entities are aware of each other.
- Navigation is possible both ways — from Employee → Department and from Department → Employee.

# However:
- The owning side is still the child (@ManyToOne).
- The inverse side (@OneToMany) uses mappedBy to indicate it doesn’t own the foreign key.

✅ Foreign key remains in the child table.

# 🧱 Example
# 👨‍💼 Employee (Child - Owning Side)

```
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Owning side
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // Getters and Setters
}

```
# 🏢 Department (Parent - Inverse Side)

```
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Inverse side of relationship
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    // Helper methods to maintain both sides
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }

    // Getters and Setters
}

```

# 🧾 Database Tables

# departments

| id | name |
| -- | ---- |
| 1  | IT   |
| 2  | HR   |

# employees
| id | name | department_id |
| -- | ---- | ------------- |
| 1  | John | 1             |
| 2  | Alex | 1             |
| 3  | Tina | 2             |

# ✅ Foreign Key:
employees.department_id → departments.id

# 🧩 Ownership:
- Employee owns the relationship (foreign key).
- Department is inverse (mappedBy).

# 🧠 Important Notes
**Ownership**
- The owning side is always the one with the foreign key (@ManyToOne).
- @OneToMany(mappedBy = "fieldName") is not the owning side.

# 🔗 @ManyToMany Relationship in Spring Data JPA (Full Notes)
A Many-to-Many relationship exists when multiple entities from one side are associated with multiple entities from the other side.

# 👉 Examples:
- A Student can enroll in multiple Courses
- A Course can have multiple Students
- An Employee can work on multiple Projects, and each Project can have multiple Employees

# ⚙️ How it works internally
In a Many-to-Many relationship, a third table (Join Table) is automatically created to map the relationships between the two entities.

# For example:

- students table
- courses table
- A join table (e.g., student_course) holds foreign keys for both.

# 🧩 1️⃣ Unidirectional @ManyToMany

In unidirectional @ManyToMany,
only one entity (say, Student) knows about the relationship —
the other (Course) has no reference back.

# ✅ JPA automatically creates a join table with two foreign key columns.

# 🧱 Example
# 🎓 Student (Owning Side)

```
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // A student can enroll in multiple courses
    @ManyToMany
    @JoinTable(
        name = "student_course", // Join table name
        joinColumns = @JoinColumn(name = "student_id"), // FK for this entity
        inverseJoinColumns = @JoinColumn(name = "course_id") // FK for the other entity
    )
    private List<Course> courses = new ArrayList<>();

    // Getters and Setters
}
```
# 📘 Course (Inverse Side)
```
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // No reference back to Student (Unidirectional)
}

```

# 🧾 Database Tables
# students
| id | name  |
| -- | ----- |
| 1  | John  |
| 2  | Alice |

# courses

| id | title   |
| -- | ------- |
| 1  | Math    |
| 2  | Science |
| 3  | English |

# student_course (Join Table)

| student_id | course_id |
| ---------- | --------- |
| 1          | 1         |
| 1          | 2         |
| 2          | 2         |
| 2          | 3         |


`✅ Join table maps the relationship between Student and Course.`

# 🧠 Key Notes for Unidirectional
- The owning side creates and manages the join table.
- The foreign keys of both entities are stored in the join table.
- The target entity (Course) doesn’t know about the relationship.
- Best suited when you don’t need backward navigation (e.g., from Course → Student).

# 🧩 2️⃣ Bidirectional @ManyToMany

- Both entities have references to each other.
- Navigation is possible both ways.
- Only one side owns the relationship (the side with @JoinTable).

# 🧱 Example
```
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    // Helper method to keep both sides in sync
    public void addCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        course.getStudents().remove(this);
    }

    // Getters and Setters
}

```

# 📘 Course (Inverse Side)
```
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Inverse side
    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    // Getters and Setters
}

```

# 🧾 Database Tables
# students
| id | name  |
| -- | ----- |
| 1  | John  |
| 2  | Alice |


# courses

| id | title   |
| -- | ------- |
| 1  | Math    |
| 2  | Science |

# student_course (Join Table)
| student_id | course_id |
| ---------- | --------- |
| 1          | 1         |
| 1          | 2         |
| 2          | 1         |

# ✅ The join table structure is the same as in unidirectional.

# References

1. https://medium.com/@yadavsunil9699/a-comprehensive-guide-to-annotations-in-spring-boot-jpa-950a05b5eb1b (All
   annotations used in spring JPA)
2. https://medium.com/@himani.prasad016/validations-in-spring-boot-e9948aa6286b (All annotations used for validation)
3. https://www.geeksforgeeks.org/spring-boot-validation-using-hibernate-validator/ (All annotations used for validation)
4. https://medium.com/@AlexanderObregon/understanding-springs-jsonbackreference-and-jsonmanagedreference-annotations-783090468572 (avoid infinite recursion loop)



