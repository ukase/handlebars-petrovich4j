# handlebars-helpers-petrovich4j
Custom {{handlebars.java}} helper that enables Petrovich4j library usage 

## Petrovich-java
usage:
```
{{petrovich object formatString case='Accusative' gender=gender [firstName=name lastName=surname patronymic=patronymic]}}
```
or
```
{{petrovich formatString case='Dative' gender=gender firstName=name lastName=surname patronymic=patronymic}}
```
([*] - optional parameters)

Sample:
```
{{petrovich '{F} {I} {O}' case='Accusative' gender=user.gender
                    patronymic=user.middleName
                    lastName=user.lastName
                    firstName=user.name}}
```
will be rendered to user name in Accusative case with given format 

formatString accepts:
 - {F} - surname in full form
 - {f} - surname in initials form
 - {I} - name in full form
 - {i} - name in initials form
 - {O} - patronymic in full form
 - {o} - patronymic in initials form
 - other character sequences keeps their state

Sample: '{F} {I} {O}' will rendered to string 'LastName Name Patronymic',
'{F}&nbsp;{i}{o}' will rendered to string 'LastName&nbsp;N.P.'

By default petrovich helper tries to get next fields from object:
 - firstName (as name)
 - lastName (as surname)
 - patronymic (as patronymic)
Can be override by directly passed hash-parameters (with such types) 
 
Possible gender values:
 - MALE
 - FEMALE
 - resolve (case insensitive) - try to resolve gender by patronymic (Male will turn if cannot 
 - any other will be passed to Petrovich as Gender.Both type

## Petrovich gender conditional
Usage:
```
{{#if (is_gender patronymic_value test_gender)}}
  gender is equals
{{else}}
  gender not equals
{{/if}}
```

Where
- `patranymic_value` is string with patronymic only (other parts of name cannot be used to resolve gender).
- `test_gender` - string value with gender for equals block (`male` or `female`, case insensitive)

Some notes:
- if gender is not correct - always else block will executed
- if patronymic cannot be resolved for gender - male will be used as default

## License
Project is available under Apache License 2
