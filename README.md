# handlebars-helpers-petrovich4j
Custom {{handlebars.java}} helper that enables Petrovich4j library usage 

## Petrovich-java
usage:
{{petrovich object formatString case='Accusative' gender=gender [firstName=name lastName=surname patronymic=patronymic]}}
or
{{petrovich formatString case='Dative' gender=gender firstName=name lastName=surname patronymic=patronymic}}
([*] - optional parameters)
formatString accepts:
 - {F} - surname in full form
 - {f} - surname in initials form
 - {I} - name in full form
 - {i} - name in initials form
 - {O} - patronymic in full form
 - {o} - patronymic in initials form
 - other character sequences keeps their state

By default petrovich helper tries to get next fields from object:
 - firstName (as name)
 - lastName (as surname)
 - patronymic (as patronymic)
Can be override by directly passed hash-parameters (with such types) 
 
Possible gender values:
 - MALE
 - FEMALE
 - any other will be passed to Petrovich as Gender.Both type


## License
Project is available under Apache License 2
