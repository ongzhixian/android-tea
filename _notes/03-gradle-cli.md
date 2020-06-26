# Gradle CLI

Gradle 6.5

1.  Initializing new projects

## 1.  Initializing new projects

The following 3 commands create project for a:

1.  Java application

gradle init --dsl groovy --package com.emptool.tea --project-name tea --test-framework junit --type java-application

2.  Java library

gradle init --dsl groovy --package com.emptool.tea --project-name tea --test-framework junit --type java-library

3.  Basic (generic) Gradle build

gradle init --dsl groovy --project-name tea --type basic

