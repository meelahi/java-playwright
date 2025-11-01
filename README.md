# Playwright + TestNG + Allure Example Project

Default settings:
- headless=true (as requested)
- screenshots captured automatically for every test and stored under `target/screenshots/`

Build & run:
```bash
mvn clean test
# View Allure report:
allure serve target/allure-results
```

You can override config via CLI, for example:
```bash
mvn test -Dbrowser=firefox -Dheadless=false
```

Project structure is under `src/main/java` and `src/test/java`.
