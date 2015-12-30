/*
 * Copyright (c) 2015 Konstantin Lepa <konstantin+ukase@lepabox.net>
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
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class EqualsHelperTest {
    private static final Helper<Object> HELPER = new EqualsHelper();
    private static final Handlebars HANDLEBARS = new Handlebars();
    private static final String INVERSE_TEXT = "some other text";
    private static final String FN_TEXT = "some text";
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

    @Test
    public void testEquals() throws Exception {
        Object context = "testString";

        Options options = getOptions(context, "testString");

        assertEquals("Wrong render", FN_TEXT, getResult(context, options));
    }

    @Test
    public void testNotEquals() throws Exception {
        Object context = "testString";

        Options options = getOptions(context, "notTestString");

        assertEquals("Wrong render", INVERSE_TEXT, getResult(context, options));
    }

    @Test
    public void testNulls() throws Exception {
        Options options = getOptions(null, "someValue");

        assertEquals("Wrong render", INVERSE_TEXT, getResult(null, options));
    }

    private Options getOptions(Object context, String... params) {
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

    private String getResult(Object context, Options options) throws IOException {
        CharSequence result = HELPER.apply(context, options);
        return result.subSequence(0, result.length()).toString();
    }
}