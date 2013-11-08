/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gopivotal.cloudfoundry.test;

import com.gopivotal.cloudfoundry.test.core.DataSourceUtils;
import com.gopivotal.cloudfoundry.test.core.HealthUtils;
import com.gopivotal.cloudfoundry.test.core.RuntimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RestController
final class ApplicationController {

    private final HealthUtils healthUtils;

    private final DataSource datasource;

    private final DataSourceUtils dataSourceUtils;

    private final RuntimeUtils runtimeUtils;

    @Autowired
    ApplicationController(DataSource dataSource, DataSourceUtils dataSourceUtils, HealthUtils healthUtils,
                          RuntimeUtils runtimeUtils) {
        this.datasource = dataSource;
        this.dataSourceUtils = dataSourceUtils;
        this.runtimeUtils = runtimeUtils;
        this.healthUtils = healthUtils;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    String health() {
        return this.healthUtils.health();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/class-path")
    List<String> classPath() {
        return this.runtimeUtils.classPath();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/datasource-classname")
    String dataSourceClassName() {
        return this.dataSourceUtils.getClassName(this.datasource);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/environment-variables")
    Map<String, String> environmentVariables() {
        return this.runtimeUtils.environmentVariables();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/input-arguments")
    List<String> inputArguments() {
        return this.runtimeUtils.inputArguments();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/system-properties")
    Map<Object, Object> systemProperties() {
        return this.runtimeUtils.systemProperties();
    }

}
