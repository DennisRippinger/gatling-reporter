/*
 * (C) Copyright 2018 Nuxeo SA (http://nuxeo.com/) and contributors.
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
 *     bdelbosc
 */

package org.nuxeo.tools.gatling.report;

import java.io.File;
import java.util.List;

/**
 * Gatling 2.3.1 simulation format
 */
public class SimulationParserV23 extends SimulationParser {

    public SimulationParserV23(File file, Float apdexT) {
        super(file, apdexT);
    }

    public SimulationParserV23(File file) {
        super(file);
    }

    protected String getSimulationName(List<String> line) {
        return line.get(3);
    }

    protected String getSimulationStart(List<String> line) {
        return line.get(4);
    }

    protected String getScenario(List<String> line) {
        return line.get(1);
    }

    protected String getType(List<String> line) {
        return line.get(0);
    }

    protected String getUserType(List<String> line) {
        return line.get(3);
    }

    protected String getRequestName(List<String> line) {
        return line.get(4);
    }

    protected Long getRequestStart(List<String> line) {
        return Long.parseLong(line.get(5));
    }

    protected Long getRequestEnd(List<String> line) {
        return Long.parseLong(line.get(6));
    }

    protected boolean getRequestSuccess(List<String> line) {
        return OK.equals(line.get(7));
    }
}
