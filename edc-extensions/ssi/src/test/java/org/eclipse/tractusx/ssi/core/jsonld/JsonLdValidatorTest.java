package org.eclipse.tractusx.ssi.core.jsonld;

import com.apicatalog.jsonld.loader.DocumentLoader;
import foundation.identity.jsonld.ConfigurableDocumentLoader;
import foundation.identity.jsonld.JsonLDObject;
import lombok.SneakyThrows;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdValidator;
import org.eclipse.tractusx.ssi.extensions.core.jsonLd.JsonLdValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLdValidatorTest {
    private JsonLdValidator validator;

    @BeforeEach
    public void setUp(){
      validator = new JsonLdValidatorImpl();
    }

    @Test
    public void validateTestFail(){
      JsonLDObject toTest = loadInvalidjsonLDObject();
      String expectedException = "Undefined JSON-LD term: publicKeyMultibase";
      Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
        validator.validate(toTest);
      });
      Assertions.assertTrue(exception.getMessage().contains(expectedException));
    }

    @Test
    public void validateTestSuccess(){
      JsonLDObject toTest = loadValidjsonLDObject();
      Assertions.assertTrue(validator.validate(toTest));
    }

    @SneakyThrows
    private JsonLDObject loadInvalidjsonLDObject(){
      String didJsonLd = Files.readString(Paths.get(getClass().getClassLoader().getResource("./jsonld/invalidJsonLd.json").toURI()));
      DocumentLoader loader = new ConfigurableDocumentLoader();
      ((ConfigurableDocumentLoader) loader).setEnableHttps(true);
      JsonLDObject object = JsonLDObject.fromJson(didJsonLd);
      object.setDocumentLoader(loader);
      return object;
    }

    @SneakyThrows
    private JsonLDObject loadValidjsonLDObject(){
      String didJsonLd = Files.readString(Paths.get(getClass().getClassLoader().getResource("./jsonld/validJsonLd.json").toURI()));
      DocumentLoader loader = new ConfigurableDocumentLoader();
      ((ConfigurableDocumentLoader) loader).setEnableHttps(true);
      JsonLDObject object = JsonLDObject.fromJson(didJsonLd);
      object.setDocumentLoader(loader);
      return object;
    }
}
