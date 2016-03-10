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

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PetrovichHelperTest extends HelperTest {
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
        Map<String, Object> hash = setHash("Dative", "Иван", "Петров", "Иванович");
        hash.put("gender", "MALE");

        Options options = getOptions(context, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Ивану(И.И.Петрову)", HELPER.apply(context, options));
    }

    @Test
    public void testDativeMaleFormatStringAsContext() throws Exception {
        Map<String, Object> hash = setHash("Dative", "Иван", "Петров", "Иванович");
        hash.put("gender", "MALE");

        Options options = getOptions(TEST_FORMAT, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Ивану(И.И.Петрову)", HELPER.apply(TEST_FORMAT, options));
    }

    @Test
    public void testResolveMaleGender() throws Exception {
        Map<String, Object> hash = setHash("Dative", "Геннадий", "Сидоров", "Петрович");
        hash.put("gender", "resolve");
        Options options = getOptions(TEST_FORMAT, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Геннадию(Г.П.Сидорову)", HELPER.apply(TEST_FORMAT, options));
    }

    @Test
    public void testResolveFemaleGender() throws Exception {
        Map<String, Object> hash = setHash("Dative", "Татьяна", "Сидорова", "Игоревна");
        hash.put("gender", "resolve");
        Options options = getOptions(TEST_FORMAT, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Татьяне(Т.И.Сидоровой)", HELPER.apply(TEST_FORMAT, options));
    }

    @Test
    public void testCannotResolveGender() throws Exception {
        Map<String, Object> hash = setHash("Dative", "Геннадий", "Сидоров", "Петровичь");
        hash.put("gender", "resolve");
        Options options = getOptions(TEST_FORMAT, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Геннадию(Г.П.Сидорову)", HELPER.apply(TEST_FORMAT, options));
    }

    @Test
    public void testHyphenInNames() throws Exception {
        Map<String, Object> context = new HashMap<>();
        Map<String, Object> hash = setHash("Dative", "Иван-Олег", "Петров-Мухов", "Монгуш-Иванович");
        hash.put("gender", "MALE");

        Options options = getOptions(context, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Ивану-Олегу(И.М.Петрову-Мухову)", HELPER.apply(context, options));
    }

    @Test
    public void testCapitalizeName() throws Exception {
        Map<String, Object> context = new HashMap<>();
        Map<String, Object> hash = setHash("Dative", "иВаН", "ИВаНоВ", "ИВаНоВиЧ");
        hash.put("gender", "MALE");
        hash.put("transform", "capitalize");

        Options options = getOptions(context, hash, TEST_FORMAT);

        assertEquals("Wrong render", "{{'}Ивану(И.И.Иванову)", HELPER.apply(context, options));
    }
}
