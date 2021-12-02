package ar.edu.um.isa.space;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.edu.um.isa.space");

        noClasses()
            .that()
            .resideInAnyPackage("ar.edu.um.isa.space.service..")
            .or()
            .resideInAnyPackage("ar.edu.um.isa.space.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..ar.edu.um.isa.space.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
