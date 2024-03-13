## Command for building

```bash
mvn clean install -DskipTests -Dmaven.test.skip -Dcheckstyle.skip -Drat.skip=true -Denforcer.skip=true
```

to generate the jar in the target folder. Then use CliFrontend to receive the jar job, specifying the entry class point.

## Running DataStreamGenerator

Simply press run. Then using FlinkCLIFRontend, accept the jar and specify the entry class point.
Now should see that the stream connection is established via port 9000 and begins to continously write nonsense to the
stream while Flink tries to process it.