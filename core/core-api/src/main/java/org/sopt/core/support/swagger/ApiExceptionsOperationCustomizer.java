package org.sopt.core.support.swagger;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.sopt.core.error.ErrorCode;
import org.sopt.core.support.response.ErrorResponse;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.web.method.HandlerMethod;

public class ApiExceptionsOperationCustomizer implements GlobalOperationCustomizer {

    private static final String JSON_MEDIA_TYPE = "application/json";
    private static final String EXAMPLE_REQUEST_PATH = "";

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiException[] apiExceptions = handlerMethod.getMethod().getAnnotationsByType(ApiException.class);
        if (apiExceptions.length == 0) {
            return operation;
        }

        Map<Integer, List<ExampleHolder>> holdersByStatus = Arrays.stream(apiExceptions)
                .flatMap(this::toExampleHolders)
                .collect(Collectors.groupingBy(ExampleHolder::status));

        addExamples(operation.getResponses(), holdersByStatus);
        return operation;
    }

    private Stream<ExampleHolder> toExampleHolders(ApiException apiException) {
        Class<? extends ErrorCode> enumType = apiException.value();
        ErrorCode[] constants = enumType.getEnumConstants();
        if (constants == null) {
            throw new IllegalArgumentException("@ApiException은 enum ErrorCode에만 사용할 수 있습니다: " + enumType.getName());
        }

        Set<String> wantedNames = Set.of(apiException.codes());
        validateCodeNames(enumType, constants, wantedNames);

        return Arrays.stream(constants)
                .filter(code -> wantedNames.isEmpty() || wantedNames.contains(((Enum<?>) code).name()))
                .map(this::toExampleHolder);
    }

    private void validateCodeNames(Class<? extends ErrorCode> enumType, ErrorCode[] constants, Set<String> wantedNames) {
        Set<String> available = Arrays.stream(constants)
                .map(code -> ((Enum<?>) code).name())
                .collect(Collectors.toSet());
        for (String name : wantedNames) {
            if (!available.contains(name)) {
                throw new IllegalArgumentException(
                        "존재하지 않는 에러 코드입니다: " + enumType.getSimpleName() + "." + name);
            }
        }
    }

    private ExampleHolder toExampleHolder(ErrorCode errorCode) {
        Example example = new Example()
                .description(errorCode.getMessage())
                .value(ErrorResponse.of(errorCode, EXAMPLE_REQUEST_PATH));
        return new ExampleHolder(errorCode.getStatus().value(), errorCode.getCode(), example);
    }

    private void addExamples(ApiResponses responses, Map<Integer, List<ExampleHolder>> holdersByStatus) {
        holdersByStatus.forEach((status, holders) -> {
            String statusCode = String.valueOf(status);

            ApiResponse apiResponse = responses.get(statusCode);
            if (apiResponse == null) {
                apiResponse = new ApiResponse().description("에러 응답");
                responses.addApiResponse(statusCode, apiResponse);
            }

            Content content = apiResponse.getContent();
            if (content == null) {
                content = new Content();
                apiResponse.setContent(content);
            }

            MediaType mediaType = content.computeIfAbsent(JSON_MEDIA_TYPE, key -> new MediaType());
            holders.forEach(holder -> mediaType.addExamples(holder.name(), holder.example()));
        });
    }

    private record ExampleHolder(int status, String name, Example example) {
    }
}