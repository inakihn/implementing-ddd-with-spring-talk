package library;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import static org.assertj.core.api.Assertions.assertThat;

class SpringModulithTests {

    ApplicationModules modules = ApplicationModules.of(LibraryApplication.class);

    @Test
    void verifyModules() {
        modules.verify();

        modules.forEach(System.out::println);
    }

    @Test
    void writeDocumentationSnippets() {

        Documenter documenter = new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml()
                .writeModuleCanvases();

        assertThat(documenter).isNotNull();
    }
}