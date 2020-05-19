/*
 * (C) Copyright 2015 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Kris Geusebroek
 */
package org.nuxeo.tools.gatling.report;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gatling 3.2 simulation format
 */
public class SimulationParserV32 extends SimulationParser {

    protected final Map<String, String> userScenario = new HashMap<>();

    public SimulationParserV32(File file) {
        super(file);
    }

    protected String getSimulationName(List<String> line) {
        return line.get(2);
    }

    protected String getSimulationStart(List<String> line) {
        return line.get(3);
    }

    protected String getScenario(List<String> line) {
        String user;
        if (USER.equals(line.get(0))) {
            user = line.get(2);
            if (START.equals(line.get(3))) {
                String ret = line.get(1);
                userScenario.put(user, ret);
            }
        } else if (RUN.equals(line.get(0))) {
            return line.get(1);
        } else {
            user = line.get(1);
        }
        return userScenario.get(user);
    }

    protected String getType(List<String> line) {
        return line.get(0);
    }

    protected String getUserType(List<String> line) {
        return line.get(3);
    }

    protected String getRequestName(List<String> line) {
        return line.get(3);
    }

    protected Long getRequestStart(List<String> line) {
        return Long.parseLong(line.get(4));
    }

    protected Long getRequestEnd(List<String> line) {
        return Long.parseLong(line.get(5));
    }

    protected boolean getRequestSuccess(List<String> line) {
        return OK.equals(line.get(6));
    }
}
