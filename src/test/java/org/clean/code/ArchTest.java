package org.clean.code;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@QuarkusTest
class ArchTest {

    JavaClasses importedClasses = new ClassFileImporter().importPackages("org.clean.code");

    @Test
    public void testDomainShouldNotAccessAnyOtherLayer() {
        ArchRule rule = noClasses().that()
                .resideInAPackage("..domain..")
                .should().accessClassesThat()
                .resideInAnyPackage("..infrastructure..", "..presentation..", "..data..", "..main..");

        rule.check(importedClasses);
    }

    @Test
    public void testinfrastructureShouldOnlyBeAccessedByDataAndMain() {
        ArchRule rule = layeredArchitecture()
                .layer("infrastructure").definedBy("org.clean.code.infrastructure..")
                .layer("Main").definedBy("org.clean.code.main..")
                .layer("Domain").definedBy("org.clean.code.domain..")
                .layer("Presentation").definedBy("org.clean.code.presentation..")
                .layer("Data").definedBy("org.clean.code.data..")
                .whereLayer("infrastructure").mayOnlyBeAccessedByLayers("Main");

        rule.check(importedClasses);
    }

    @Test
    public void testDataShouldOnlyBeAccessedByinfrastructureAndMain() {
        ArchRule rule = layeredArchitecture()
                .layer("infrastructure").definedBy("org.clean.code.infrastructure..")
                .layer("Main").definedBy("org.clean.code.main..")
                .layer("Domain").definedBy("org.clean.code.domain..")
                .layer("Presentation").definedBy("org.clean.code.presentation..")
                .layer("Data").definedBy("org.clean.code.data..")
                .whereLayer("Data").mayOnlyBeAccessedByLayers("Main", "infrastructure");

        rule.check(importedClasses);
    }

    @Test
    public void testMainShouldOnlyBeAccessedByPresentation() {
        ArchRule rule = layeredArchitecture()
                .layer("infrastructure").definedBy("org.clean.code.infrastructure..")
                .layer("Main").definedBy("org.clean.code.main..")
                .layer("Domain").definedBy("org.clean.code.domain..")
                .layer("Presentation").definedBy("org.clean.code.presentation..")
                .layer("Data").definedBy("org.clean.code.data..")
                .whereLayer("Main").mayOnlyBeAccessedByLayers("Presentation");

        rule.check(importedClasses);
    }

    @Test
    public void testPresentationShouldNotBeAccessedByAnyLayer() {
        ArchRule rule = layeredArchitecture()
                .layer("infrastructure").definedBy("org.clean.code.infrastructure..")
                .layer("Main").definedBy("org.clean.code.main..")
                .layer("Domain").definedBy("org.clean.code.domain..")
                .layer("Presentation").definedBy("org.clean.code.presentation..")
                .layer("Data").definedBy("org.clean.code.data..")
                .whereLayer("Presentation").mayNotBeAccessedByAnyLayer();

        rule.check(importedClasses);
    }
}