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

import com.github.jknack.handlebars.Options;
import org.junit.Test;

import static org.junit.Assert.*;

public class IsGenderHelperTest extends HelperTest {
    private static final String MALE_PATRONYMIC = "Петрович";
    private static final String FEMALE_PATRONYMIC = "Олеговна";
    private static final String DEFAULT_PATRONYMIC = "Штопсель";
    private static final IsGenderHelper HELPER = new IsGenderHelper();
    private static final String GENDER_MALE = "male";
    private static final String GENDER_WRONG = "wrong";
    private static final String GENDER_FEMALE = "female";

    @Test
    public void testMaleEquals() throws Exception {
        testEquals(MALE_PATRONYMIC, GENDER_MALE);
    }

    @Test
    public void testMaleNotEquals() throws Exception {
        testNotEqualsOrWrong(MALE_PATRONYMIC, GENDER_FEMALE);
    }

    @Test
    public void testMaleWrong() throws Exception {
        testNotEqualsOrWrong(MALE_PATRONYMIC, GENDER_WRONG);
    }

    @Test
    public void testFemaleEquals() throws Exception {
        testEquals(FEMALE_PATRONYMIC, GENDER_FEMALE);
    }

    @Test
    public void testFemaleNotEquals() throws Exception {
        testNotEqualsOrWrong(FEMALE_PATRONYMIC, GENDER_MALE);
    }

    @Test
    public void testFemaleWrong() throws Exception {
        testNotEqualsOrWrong(FEMALE_PATRONYMIC, GENDER_WRONG);
    }

    @Test
    public void testDefaultEquals() throws Exception {
        testEquals(DEFAULT_PATRONYMIC, GENDER_MALE);
    }

    @Test
    public void testDefaultNotEquals() throws Exception {
        testNotEqualsOrWrong(DEFAULT_PATRONYMIC, GENDER_FEMALE);
    }

    @Test
    public void testDefaultWrong() throws Exception {
        testNotEqualsOrWrong(DEFAULT_PATRONYMIC, GENDER_WRONG);
    }

    private void testEquals(String patronymic, String gender) throws Exception {
        Options options = getOptions(patronymic, null, gender);
        assertEquals("Wrong render", "yes", HELPER.apply(patronymic, options));
    }

    private void testNotEqualsOrWrong(String patronymic, String gender) throws Exception {
        Options options = getOptions(patronymic, null, gender);
        assertNull("Wrong render", HELPER.apply(patronymic, options));
    }
}