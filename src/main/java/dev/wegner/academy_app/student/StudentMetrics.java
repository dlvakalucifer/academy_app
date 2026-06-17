package dev.wegner.academy_app.student;

import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.model.registry.PrometheusRegistry;
import org.springframework.stereotype.Component;

@Component
public class StudentMetrics
{
    private final Counter createdCounter;

    public StudentMetrics( PrometheusRegistry registry )
    {
        this.createdCounter = Counter.builder()
                .name("academy.students.build")
                .help("Academy Student Created Counter")
                .register(registry);
    }

    public void studentCreated()
    {
        createdCounter.inc();
    }
}
