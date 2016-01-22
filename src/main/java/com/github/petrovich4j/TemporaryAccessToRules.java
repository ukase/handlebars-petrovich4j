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

package com.github.petrovich4j;

import java.util.Arrays;
import static com.github.petrovich4j.Library.PATRONYMIC_NAME_RULES;

/**
 * This class is for temporary access to rules set in Petrovich4j library
 * TODO remove after pull request accepted or fork released.
 */
public class TemporaryAccessToRules {
    private static Rule DEFAULT_RULE = new Rule(Gender.Male, null, null);

    public static Gender resolve(String patronymic) {
        if (isException(patronymic)) {
            return Gender.Male;
        } else {
            return findRule(patronymic, PATRONYMIC_NAME_RULES.suffixes, DEFAULT_RULE).gender;
        }
    }

    private static boolean isException(String patronymic) {
        return findRule(patronymic, PATRONYMIC_NAME_RULES.exceptions, null) != null;
    }

    private static Rule findRule(String patronymic, Rule[] rules, Rule defaultRule) {
        String lcName = patronymic.toLowerCase();
        return Arrays.stream(rules)
                .filter(rule -> checkRule(lcName, rule))
                .findAny().orElse(defaultRule);
    }

    private static boolean checkRule(String patronymic, Rule rule) {
        return Arrays.stream(rule.test).
                anyMatch(test ->
                        test.startsWith("^")
                                ? test.substring(1).equals(patronymic)
                                : patronymic.endsWith(test));
    }
}
