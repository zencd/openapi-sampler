# OpenAPI Sampler

Generates OpenAPI v3 (swagger) examples.
Tries to find pre-defined ones first.

Example:

    var spec = new OpenAPIV3Parser().read("https://petstore3.swagger.io/api/v3/openapi.json");
    var schema = spec.getComponents().getSchemas().get("Customer");
    var sample = new OpenApiSampler().getSchemaExample(spec, schema);

Output:

    {
      "address": [
        {
          "zip": "string",
          "city": "string",
          "street": "string",
          "state": "string"
        }
      ],
      "id": 0,
      "username": "string"
    }
