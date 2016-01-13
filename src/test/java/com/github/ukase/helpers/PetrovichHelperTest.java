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
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.TagType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PetrovichHelperTest {
    private static final String TEST_FORMAT = "{{'}{I}({i}{o}{F})";
    private static final Helper<Object> HELPER = new PetrovichHelper();

    @Test
    public void testNullCaseFemale() throws Exception {
        Map<String, Object> context = new HashMap<>();
        context.put("firstName", "Иоанна");
        context.put("lastName", "Петрова");
        context.put("patronymic", "Олеговна");
        Map<String, Object> hash = new HashMap<>();
        hash.put("gender", "FEMALE");

        Options options = getOptions(context, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Иоанна(И.О.Петрова)", HELPER.apply(context, options));
    }

    @Test
    public void testDativeMale() throws Exception {
        Map<String, Object> context = new HashMap<>();
        Map<String, Object> hash = new HashMap<>();
        hash.put("case", "Dative");
        hash.put("firstName", "Иван");
        hash.put("lastName", "Петров");
        hash.put("patronymic", "Иванович");
        hash.put("gender", "MALE");

        Options options = getOptions(context, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Ивану(И.И.Петрову)", HELPER.apply(context, options));
    }

    @Test
    public void testDativeMaleFormatStringAsContext() throws Exception {
        Map<String, Object> hash = new HashMap<>();
        hash.put("case", "Dative");
        hash.put("firstName", "Иван");
        hash.put("lastName", "Петров");
        hash.put("patronymic", "Иванович");
        hash.put("gender", "MALE");

        Options options = getOptions(TEST_FORMAT, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Ивану(И.И.Петрову)", HELPER.apply(TEST_FORMAT, options));
    }

    private Options getOptions(Object context, Map<String, Object> hash, String... params) {
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