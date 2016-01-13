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
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.TagType;
import com.github.jknack.handlebars.Template;

import java.io.IOException;
import java.util.ArrayList;

class HelperTest {
    static final String FN_TEXT = "some text";
    static final String INVERSE_TEXT = "some other text";
    private static final Handlebars HANDLEBARS = new Handlebars();
    private static final Template FN;
    private static final Template INVERSE;

    static {
        try {
            FN = HANDLEBARS.compileInline(FN_TEXT);
            INVERSE = HANDLEBARS.compileInline(INVERSE_TEXT);
        } catch (IOException e) {
            throw new IllegalStateException("wrong configuration/sources");
        }
    }

    private final Helper<Object> helper;

    HelperTest(Helper<Object> helper) {
        this.helper = helper;
    }

    Options getOptions(Object context, String... params) {
        return new Options(null,
                "equals",
                TagType.VAR,
                Context.newContext(context),
                FN,
                INVERSE,
                params,
                null,
                new ArrayList<>());
    }

    String getResult(Object context, Options options) throws IOException {
        CharSequence result = helper.apply(context, options);
        return result.subSequence(0, result.length()).toString();
    }
}
