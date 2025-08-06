# Code Coverage with JaCoCo

This project uses [JaCoCo](https://www.jacoco.org/) (Java Code Coverage) to measure and report code coverage metrics.

## Overview

JaCoCo has been configured to:
- Automatically instrument code during test execution
- Generate coverage reports in multiple formats (HTML, XML, CSV)
- Support both unit tests and integration tests
- Provide merged coverage reports combining all test types

## Configuration

The JaCoCo Maven plugin is configured in `pom.xml` with the following executions:

1. **prepare-agent**: Prepares the JaCoCo agent for unit tests
2. **prepare-agent-integration**: Prepares the JaCoCo agent for integration tests
3. **report**: Generates unit test coverage reports
4. **report-integration**: Generates integration test coverage reports
5. **merge-results**: Merges unit and integration test coverage data
6. **report-merged**: Generates a combined coverage report

## Running Coverage Analysis

### Quick Coverage Report
```bash
# Run tests and generate coverage reports
./run-coverage.sh
```

### Manual Commands
```bash
# Run only unit tests with coverage
./mvnw clean test

# Run full lifecycle with integration tests and merged reports
./mvnw clean verify
```

## Generated Reports

After running tests, coverage reports are generated in the following locations:

### HTML Reports (Interactive)
- **Unit Tests**: `target/site/jacoco/index.html`
- **Merged (All Tests)**: `target/site/jacoco-merged/index.html`

### XML Reports (CI/CD Integration)
- **Unit Tests**: `target/site/jacoco/jacoco.xml`
- **Merged (All Tests)**: `target/site/jacoco-merged/jacoco.xml`

### CSV Reports (Data Analysis)
- **Unit Tests**: `target/site/jacoco/jacoco.csv`
- **Merged (All Tests)**: `target/site/jacoco-merged/jacoco.csv`

### Execution Data Files
- **Unit Tests**: `target/jacoco.exec`
- **Integration Tests**: `target/jacoco-it.exec`
- **Merged**: `target/jacoco-merged.exec`

## Understanding Coverage Metrics

JaCoCo provides several coverage metrics:

- **Instructions**: Individual Java bytecode instructions
- **Branches**: if/else and switch statement branches
- **Lines**: Source code lines
- **Complexity**: Cyclomatic complexity
- **Methods**: Method coverage
- **Classes**: Class coverage

## Coverage Thresholds

Currently, the project achieves:
- **Instructions**: ~95% coverage
- **Branches**: ~62% coverage
- **Lines**: ~94% coverage

## Viewing Reports

### HTML Reports
Open the HTML reports in your browser for an interactive view:
```bash
# macOS
open target/site/jacoco/index.html

# Linux
xdg-open target/site/jacoco/index.html

# Windows
start target/site/jacoco/index.html
```

The HTML reports provide:
- Package-level coverage overview
- Class-level detailed coverage
- Line-by-line coverage highlighting
- Branch coverage visualization

### XML Reports
XML reports are ideal for:
- CI/CD pipeline integration
- Automated coverage analysis
- Integration with code quality tools
- Coverage trend tracking

### CSV Reports
CSV reports are useful for:
- Data analysis and reporting
- Coverage metrics extraction
- Custom reporting tools
- Historical trend analysis

## Integration with CI/CD

The XML reports can be integrated with various CI/CD platforms:

### GitHub Actions
```yaml
- name: Generate Coverage Report
  run: ./mvnw clean verify

- name: Upload Coverage to Codecov
  uses: codecov/codecov-action@v3
  with:
    file: target/site/jacoco/jacoco.xml
```

### SonarQube
```bash
mvn sonar:sonar -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

## Best Practices

1. **Run coverage regularly** during development
2. **Focus on meaningful tests** rather than just coverage numbers
3. **Review uncovered code** to identify missing test scenarios
4. **Use branch coverage** to ensure all code paths are tested
5. **Exclude generated code** from coverage analysis when appropriate

## Troubleshooting

### No Coverage Data
If no coverage data is generated:
- Ensure tests are running successfully
- Check that the JaCoCo agent is properly configured
- Verify that `target/jacoco.exec` file is created

### Low Coverage Numbers
- Review the HTML report to identify uncovered lines
- Add tests for uncovered code paths
- Consider if uncovered code is actually needed

### Performance Impact
JaCoCo has minimal performance impact, but if needed:
- Use separate profiles for coverage analysis
- Skip coverage during development builds
- Run coverage only in CI/CD pipelines
