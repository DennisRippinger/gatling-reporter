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
 *     Benoit Delbosc
 */
package org.nuxeo.tools.gatling.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.max;

public class SimulationContext {
    public static final String ALL_REQUESTS = "_all";

    protected final String filePath;

    protected final RequestStat simStat;

    protected final Map<String, RequestStat> reqStats = new HashMap<>();

    protected final Map<String, CountMax> users = new HashMap<>();

    protected String simulationName;

    protected String scenarioName;

    protected List<String> scripts = new ArrayList<>();

    protected int maxUsers;

    protected long start;

    public SimulationContext(String filePath) {
        this.filePath = filePath;
        this.simStat = new RequestStat(ALL_REQUESTS, ALL_REQUESTS, ALL_REQUESTS, 0);
    }

    public String getSimulationName() {
        return simulationName;
    }

    public void setSimulationName(String name) {
        this.simulationName = name;
        simStat.setSimulationName(name);
    }

    public RequestStat getSimStat() {
        return simStat;
    }

    public List<RequestStat> getRequests() {
        List<RequestStat> ret = new ArrayList<>(reqStats.values());
        ret.sort((a, b) -> (int) (1000 * (a.avg - b.avg)));
        return ret;
    }

    public void addRequest(String scenario, String requestName, long start, long end, boolean success) {
        RequestStat request = reqStats.computeIfAbsent(requestName,
                n -> new RequestStat(simulationName, scenario, n, this.start));
        request.add(start, end, success);
        simStat.add(start, end, success);
    }

    public void computeStat() {
        maxUsers = users.values().stream().mapToInt(CountMax::getMax).sum();
        simStat.computeStat(maxUsers);
        reqStats.values()
                .forEach(request -> request.computeStat(simStat.duration, users.get(request.scenario).maximum));
    }

    public void setScenarioName(String name) {
        this.scenarioName = name;
        simStat.setScenario(name);
    }

    public void setStart(long start) {
        this.start = start;
        simStat.setStart(start);
    }

    @Override
    public String toString() {
        return simStat.toString() + "\n"
                + getRequests().stream().map(RequestStat::toString).collect(Collectors.joining("\n"));
    }

    public void addUser(String scenario) {
        CountMax count = users.computeIfAbsent(scenario, k -> new CountMax());
        count.incr();
    }

    public void endUser(String scenario) {
        CountMax count = users.get(scenario);
        if (count != null) {
            count.decr();
        }
    }

    class CountMax {
        int current = 0, maximum = 0;

        public void incr() {
            current += 1;
            maximum = max(current, maximum);
        }

        public void decr() {
            current -= 1;
        }

        public int getMax() {
            return maximum;
        }
    }

}
