Maven uses **different classpaths for different phases** of the build lifecycle, such as compile-time, runtime, and test phases. These classpaths are configured based on the **scope** of your dependencies and the current phase of the project lifecycle.

---

### **Classpaths for Different Phases in a Maven Project**

#### **1. Compile-Time Classpath**
- **Purpose:** 
  - Used during the **`compile`** phase to compile your source code.
- **Includes:**
  - Dependencies with the following scopes:
    - **`compile`**: The default scope for dependencies.
    - **`provided`**: Dependencies needed at compile-time but not included in the final artifact (e.g., Servlet APIs in a web app provided by the container).
  - **Your source files:** Located in the `src/main/java` directory.
- **Excludes:**
  - Dependencies with **`runtime`** and **`test`** scopes.

- **Command:**
  ```bash
  mvn compile
  ```

---

#### **2. Runtime Classpath**
- **Purpose:**
  - Used when running the application during the **`run`** or **`package`** phases.
- **Includes:**
  - **Compiled classes**: Located in `target/classes`.
  - Dependencies with the following scopes:
    - **`compile`**
    - **`runtime`**: Dependencies required only at runtime (e.g., database drivers).
- **Excludes:**
  - Dependencies with **`provided`** (already available in the runtime environment, like a web container).
  - Dependencies with **`test`** scope.

- **Command:**
  ```bash
  mvn exec:java -Dexec.mainClass="com.example.Main"
  ```

---

#### **3. Test Classpath**
- **Purpose:**
  - Used during the **`test`** phase to execute unit and integration tests.
- **Includes:**
  - All files in the **compile-time classpath** (everything needed to build the application).
  - **Test classes**: Located in `target/test-classes`.
  - Dependencies with the following scopes:
    - **`test`**: Testing libraries like JUnit or Mockito.
- **Excludes:**
  - Dependencies with **`provided`** or **`runtime`** scope (unless they are required for tests).

- **Command:**
  ```bash
  mvn test
  ```

---

#### **4. System Classpath**
- **Purpose:**
  - Used for dependencies that are explicitly specified with a local file path (e.g., a JAR outside Maven repositories).
- **Includes:**
  - JARs specified with the **`system`** scope in the `pom.xml`.
- **Usage:**
  - Rarely used; considered bad practice because it requires hardcoding file paths.

---

### **Summary of Dependency Scopes and Classpaths**

| **Scope**      | **Compile-Time Classpath** | **Runtime Classpath** | **Test Classpath** | **Packaged in Final Artifact** |
|-----------------|----------------------------|-----------------------|--------------------|---------------------------------|
| `compile`       | ✔                          | ✔                     | ✔                  | ✔                               |
| `runtime`       | ✘                          | ✔                     | ✔                  | ✔                               |
| `test`          | ✘                          | ✘                     | ✔                  | ✘                               |
| `provided`      | ✔                          | ✘                     | ✔                  | ✘                               |
| `system`        | ✔                          | ✔                     | ✔                  | ✘                               |

---

### **How Maven Manages Classpaths**
- Maven uses **phases** to determine the classpath configuration dynamically:
  - **`compile` phase:** Includes only `compile` and `provided` dependencies.
  - **`test` phase:** Extends the compile classpath by adding `test` dependencies.
  - **`run` phase:** Includes `compile` and `runtime` dependencies.

- **Build Directory Structure:**
  - Maven organizes the outputs of different phases:
    - `src/main/java`: Source files.
    - `src/test/java`: Test files.
    - `target/classes`: Compiled classes for runtime.
    - `target/test-classes`: Compiled test classes.

- **IDE Integration:**
  - IDEs like IntelliJ IDEA or Eclipse sync with Maven to configure separate classpaths for different phases.

---

### **Example**
#### **`pom.xml`:**
```xml
<dependencies>
    <!-- Compile scope (default) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>3.2.0</version>
    </dependency>

    <!-- Runtime scope -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.34</version>
        <scope>runtime</scope>
    </dependency>

    <!-- Test scope -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>

    <!-- Provided scope -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

#### **Commands and Classpaths:**
1. **Compile Phase (`mvn compile`):**
   - Classpath includes:
     - `spring-boot-starter-web`
     - `javax.servlet-api`
   - Excludes:
     - `mysql-connector-java` (runtime).
     - `junit-jupiter-api` (test).

2. **Test Phase (`mvn test`):**
   - Classpath includes:
     - All from the compile phase.
     - `junit-jupiter-api`.

3. **Run Phase (`mvn exec:java` or `mvn spring-boot:run`):**
   - Classpath includes:
     - All from the compile phase.
     - `mysql-connector-java`.

---

Would you like a demo of a specific Maven lifecycle phase, or more details on dependency management?