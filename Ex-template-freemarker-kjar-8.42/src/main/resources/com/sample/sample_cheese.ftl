package com.sample;

import com.sample.Person;
import com.sample.Cheese;

global java.util.List list;

<#list valueMapList as row>

rule "Cheese fans_${row?index}"
when
    Person(age == ${row.age})
    Cheese(type == "${row.type}")
then
    list.add("${row.log}");
end

</#list>
