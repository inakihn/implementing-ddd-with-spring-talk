package library;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

class ArchUnitTests {

    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("library");

    @Test
    void onionArchitecture_isRespected() {
        onionArchitecture()
                .domainModels("..domain..")
                .domainServices("..domain..")
                .applicationServices("..application..")
                .adapter("rest", "..web..")
                .adapter("openlibrary", "..openlibrary..")
                .check(classes);
    }
}