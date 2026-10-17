package com.asheef.hrms;

import com.asheef.hrms.entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, String> healthCheck() {

        Employee emp = new Employee();

        return Map.of(
                "status", "UP",
                "application", "ai-hrms-assistant"
        );
    }

}
