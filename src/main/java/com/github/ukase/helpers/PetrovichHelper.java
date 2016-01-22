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
import com.github.petrovich4j.Case;
import com.github.petrovich4j.Gender;
import com.github.petrovich4j.NameType;
import com.github.petrovich4j.Petrovich;
import com.github.petrovich4j.TemporaryAccessToRules;
import lombok.Getter;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PetrovichHelper implements Helper<Object> {
    private static final Petrovich PETROVICH = new Petrovich();
    private static final String ATTR_FIRST_NAME = "firstName";
    private static final String ATTR_LAST_NAME = "lastName";
    private static final String ATTR_PATRONYMIC = "patronymic";

    @Override
    public CharSequence apply(Object context, Options options) throws IOException {
        Gender gender = parseGender(options);
        Case nameCase = parseCase(options);
        String format;
        if (context instanceof String) {
            format = (String) context;
        } else {
            format = options.param(0, "{F} {I} {O}");
        }
        StringBuilder sb = new StringBuilder();

        int start = 0;
        int pos = format.indexOf('{');
        while (pos > -1 && (pos + 2) < format.length()) {
            sb.append(format, start, pos);
            String elementValue = format.substring(pos, pos + 3);
            NamePart element = resolveElement(elementValue);
            if (element == null) {
                sb.append('{');
                start++;
            } else {
                String fieldValue = options.hash(element.getAttributeName(), null);
                if (fieldValue == null) {
                    fieldValue = options.get(element.getAttributeName(), "");
                }
                sb.append(applyName(element, fieldValue, gender, nameCase));
                start = pos + 3;
            }
            pos = format.indexOf('{', start);
        }
        sb.append(format.substring(start));

        return sb.toString();
    }

    private Gender parseGender(Options options) {
        String gender = options.hash("gender");

        if ("resolve".equalsIgnoreCase(gender)) {
            String patronymic = options.hash(ATTR_PATRONYMIC, null);
            if (patronymic == null) {
                options.get(ATTR_PATRONYMIC, "");
            }
            return TemporaryAccessToRules.resolve(patronymic);
        } else if ("MALE".equals(gender)) {
            return Gender.Male;
        } else if ("FEMALE".equals(gender)) {
            return Gender.Female;
        }
        return Gender.Both;
    }

    private Case parseCase(Options options) {
        String nameCase = options.hash("case");
        if (nameCase == null) {
            return null;
        }
        return Arrays.stream(Case.values()).
                filter(aCase -> aCase.name().equals(nameCase)).
                findFirst().orElse(null);
    }

    private String applyName(NamePart namePart, String value, Gender gender, Case nameCase) {
        if (value.isEmpty()) {
            return "";
        }
        if (namePart.isShortName()) {
            return Arrays.stream(value.split("-")).
                    map(name -> name.substring(0, 1)).
                    collect(Collectors.joining(".-", "", "."));
        }
        if (nameCase == null) {
            return value;
        }
        return PETROVICH.say(value, namePart.getType(), gender, nameCase);
    }

    private NamePart resolveElement(String element) {
        return Arrays.stream(NamePart.values()).
                filter(val -> val.getElement().equals(element)).
                findFirst().orElse(null);
    }

    @Getter
    private enum NamePart {
        FirstName("{I}", false, ATTR_FIRST_NAME, NameType.FirstName),
        FirstNameShorten("{i}", true, ATTR_FIRST_NAME, NameType.FirstName),
        LastName("{F}", false, ATTR_LAST_NAME, NameType.LastName),
        LastNameShorten("{f}", true, ATTR_LAST_NAME, NameType.LastName),
        Patronymic("{O}", false, ATTR_PATRONYMIC, NameType.PatronymicName),
        PatronymicShorten("{o}", true, ATTR_PATRONYMIC, NameType.PatronymicName);

        private final String element;
        private final boolean shortName;
        private final String attributeName;
        private final NameType type;

        NamePart(String element, boolean shortName, String attributeName, NameType type) {
            this.element = element;
            this.shortName = shortName;
            this.attributeName = attributeName;
            this.type = type;
        }
    }
}
