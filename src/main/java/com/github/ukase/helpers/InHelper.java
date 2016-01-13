/*
 * Copyright (c) 2016 Konstantin Lepa <konstantin+ukase@lepabox.net>
 *
 * This file is part of Handlebars Helpers.
 *
 * Ukase is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License 2 as
 * published by the Free Software Foundation, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License 2 for more details.
 *
 * You should have received a copy of the Apache License 2
 * along with this program. If not, see <http://www.apache.org/licenses/>.
 */

package com.github.ukase.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.util.Arrays;

public class InHelper implements Helper<Object> {
    @Override
    public CharSequence apply(Object context, Options options) throws IOException {
        Options.Buffer buffer = options.buffer();

        boolean found = Arrays.stream(options.params)
                .map(param -> param != null && param.equals(context))
                .filter(equals -> equals)
                .findAny().orElse(false);

        if (found) {
            buffer.append(options.fn());
        } else {
            buffer.append(options.inverse());
        }

        return buffer;
    }
}
