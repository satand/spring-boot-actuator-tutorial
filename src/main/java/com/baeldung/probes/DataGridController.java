package com.baeldung.probes;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentMap;

@RestController
public class DataGridController {
    @Autowired
    private HazelcastInstance hazelcastInstance;

    private ConcurrentMap<String,String> retrieveMap() {
        return hazelcastInstance.getMap("map");
    }

    @PostMapping("/put")
    public ResponseEntity<String> put(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        String oldValue = retrieveMap().put(key, value);
        return ResponseEntity.ok(oldValue);
    }

    @GetMapping("/get")
    public ResponseEntity<String> get(@RequestParam(value = "key") String key) {
        String value = retrieveMap().get(key);
        if (value == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(value);
    }
}
