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
 *     bdelbosc
 */
package org.nuxeo.tools.gatling.report;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TestParser {
    protected static final String SIM_SMALL_V2_1 = "simulation-small.log";

    protected static final String SIM_V2_3 = "simulation-v2.3.log";

    protected static final String SIM_SMALL_V3 = "simulation-small-v3.log";

    protected static final String SIM_SMALL_V3_2 = "simulation-small-v3.2.log";

    protected static final String SIM_SMALL_V3_3 = "simulation-small-v3.3.log";

    protected static final String SIM_SMALL_V3_3_1 = "simulation-v3.3.1.log";

    protected static final String SIM_V3_2_GZ = "simulation-v3.2.log.gz";

    @Test
    public void parseSimpleSimulationVersion21() throws Exception {
        SimulationContext ret = ParserFactory.getParser(getRessourceFile(SIM_SMALL_V2_1)).parse();
        assertThat(ret.getSimulationName()).isEqualTo("sim80reindexall");
        assertThat(ret.getSimStat().getCount()).isEqualTo(2);
        assertThat(ret.toString()).contains("_all");
    }

    @Test
    public void parseSimulationVersion23() throws Exception {
        SimulationContext ret = ParserFactory.getParser(getRessourceFile(SIM_V2_3)).parse();
        // System.out.println(ret);
        assertThat(ret.getSimulationName()).isEqualTo("sim20createdocuments");
        assertThat(ret.getSimStat().getCount()).isEqualTo(1000);
        assertThat(ret.toString()).contains("_all");
    }

    @Test
    public void parseSimpleSimulationVersion3() throws Exception {
        SimulationContext ret = ParserFactory.getParser(getRessourceFile(SIM_SMALL_V3)).parse();
        // System.out.println(ret);
        assertThat(ret.getSimulationName()).isEqualTo("sim80reindexall");
        assertThat(ret.getSimStat().getCount()).isEqualTo(2);
        assertThat(ret.toString()).contains("_all");
    }

    @Test
    public void parseSimpleSimulationVersion32Small() throws Exception {
        SimulationContext ret = ParserFactory.getParser(getRessourceFile(SIM_SMALL_V3_2)).parse();
        // System.out.println(ret);
        assertThat(ret.getSimulationName()).isEqualTo("sim80reindexall");
        assertThat(ret.getSimStat().getCount()).isEqualTo(2);
        assertThat(ret.toString()).contains("_all");
    }

    @Test
    public void parseSimpleSimulationVersion33Small() throws Exception {
        SimulationContext ret = ParserFactory.getParser(getRessourceFile(SIM_SMALL_V3_3)).parse();
        // System.out.println(ret);
        assertThat(ret.getSimulationName()).isEqualTo("sim80reindexall");
        assertThat(ret.getSimStat().getCount()).isEqualTo(2);
        assertThat(ret.toString()).contains("_all");
    }

    @Test
    public void parseSimpleSimulationVersion331() throws Exception {
        SimulationContext ret = ParserFactory.getParser(getRessourceFile(SIM_SMALL_V3_3_1)).parse();
        // System.out.println(ret);
        assertThat(ret.getSimulationName()).isEqualTo("Simulation");
        assertThat(ret.getSimStat().getCount()).isEqualTo(40);
        assertThat(ret.toString()).contains("_all");
    }

    protected File getRessourceFile(String filename) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader.getResource(filename) == null) {
            throw new FileNotFoundException(filename);
        }
        return new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
    }

}
