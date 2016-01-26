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

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.TagType;

import java.util.HashMap;
import java.util.Map;

class HelperTest {
    Map<String, Object> setHash(String nameCase, String name, String lastName, String patronymic) {
        Map<String, Object> hash = new HashMap<>();
        hash.put("case", nameCase);
        hash.put("firstName", name);
        hash.put("lastName", lastName);
        hash.put("patronymic", patronymic);

        return hash;
    }

    Options getOptions(Object context, Map<String, Object> hash, String... params) {
        return new Options(null,
                "petrovich",
                TagType.VAR,
                Context.newContext(context),
                null,
                null,
                params,
                hash,
                null);
    }
}
