package su.funk.openapi.sampler

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import io.swagger.v3.parser.OpenAPIV3Parser
import org.junit.jupiter.api.Test

import java.nio.file.Paths

class OpenApiSamplerTest {

    @Test
    void test() {
        def data = [
                ["petstore-3-with-examples.json", "Customer", "petstore-customer-pre.json"],
                ["petstore-3-no-examples.json", "Customer", "petstore-customer-auto.json"],
                ["petstore-3-no-examples.json", "Order", "petstore-order-auto.json"],
        ]
        for (Object row : data) {
            String specFile = getResourcePath(row[0])
            String goldenFile = getResourcePath(row[2])
            var spec = new OpenAPIV3Parser().read(specFile)
            var schema = spec.getComponents().getSchemas().get(row[1])
            def actualSample = new OpenApiSampler().getSchemaExample(spec, schema)
            def expectedSample = new JsonSlurper().parse(new File(goldenFile))
            //println JsonOutput.toJson(actualSample)
            assert expectedSample == actualSample
        }
    }

    private static String getResourcePath(String path) {
        def file = Paths.get("src", "test", "resources", path).toFile()
        assert file.exists()
        return file.getAbsolutePath()
    }
}