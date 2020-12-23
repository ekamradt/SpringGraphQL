package com.pull.law.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ExConfig {

    @Autowired
    Environment env;

//    @Value("${avro.schemadirectory}")
//    private String avroSchemaDirectory;
//
//    @Value("${testconfig.child001.value001}")
//    private String _childValue001;
//
//    public String childValue001() {
//        return this._childValue001;
//    }
//
//    public String avroSchemaDirectory() {
//        return this.avroSchemaDirectory;
//    }
}
