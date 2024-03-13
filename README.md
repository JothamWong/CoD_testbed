## Command for building

```bash
mvn clean install -DskipTests -Dmaven.test.skip -Dcheckstyle.skip -Drat.skip=true -Denforcer.skip=true
```

to generate the jar in the target folder. Then use CliFrontend to receive the jar job, specifying the entry class point.