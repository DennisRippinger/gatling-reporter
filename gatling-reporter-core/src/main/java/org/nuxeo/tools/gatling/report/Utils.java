/* (C) Copyright 2015 Nuxeo SA (http://nuxeo.com/) and contributors.
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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Utils {


    public static String getIdentifier(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i)))
                sb.append(str.charAt(i));
        }
        return sb.toString().toLowerCase();
    }

    public static Reader getReaderFor(File file) throws IOException {
        return new FileReader(file);
    }


}
