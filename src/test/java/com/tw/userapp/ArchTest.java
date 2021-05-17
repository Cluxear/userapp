package com.tw.userapp;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.tw.userapp");

        noClasses()
            .that()
                .resideInAnyPackage("com.tw.userapp.service..")
            .or()
                .resideInAnyPackage("com.tw.userapp.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.tw.userapp.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
