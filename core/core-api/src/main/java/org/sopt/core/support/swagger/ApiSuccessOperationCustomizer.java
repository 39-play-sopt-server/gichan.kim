package org.sopt.core.support.swagger;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.sopt.core.support.code.SuccessCode;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;

public class ApiSuccessOperationCustomizer implements GlobalOperationCustomizer {

    private static final String JSON_MEDIA_TYPE = "application/json";

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        ApiSuccess[] apiSuccesses = handlerMethod.getMethod().getAnnotationsByType(ApiSuccess.class);
        if (apiSuccesses.length == 0) {
            return operation;
        }

        List<SuccessCode> codes = Arrays.stream(apiSuccesses)
                .flatMap(this::toSuccessCodes)
                .toList();
        attachExamples(operation.getResponses(), successStatus(handlerMethod), codes);
        return operation;
    }

    private HttpStatus successStatus(HandlerMethod handlerMethod) {
        ResponseStatus responseStatus = handlerMethod.getMethodAnnotation(ResponseStatus.class);
        return responseStatus == null ? HttpStatus.OK : responseStatus.value();
    }

    private Stream<SuccessCode> toSuccessCodes(ApiSuccess apiSuccess) {
        Class<? extends SuccessCode> enumType = apiSuccess.value();
        SuccessCode[] constants = enumType.getEnumConstants();
        if (constants == null) {
            throw new IllegalArgumentException("@ApiSuccess는 enum SuccessCode에만 사용할 수 있습니다: " + enumType.getName());
        }

        Set<String> wantedNames = Set.of(apiSuccess.codes());
        validateCodeNames(enumType, constants, wantedNames);

        return Arrays.stream(constants)
                .filter(code -> wantedNames.isEmpty() || wantedNames.contains(((Enum<?>) code).name()));
    }

    private void validateCodeNames(Class<? extends SuccessCode> enumType, SuccessCode[] constants,
                                   Set<String> wantedNames) {
        Set<String> available = Arrays.stream(constants)
                .map(code -> ((Enum<?>) code).name())
                .collect(Collectors.toSet());
        for (String name : wantedNames) {
            if (!available.contains(name)) {
                throw new IllegalArgumentException(
                        "존재하지 않는 성공 코드입니다: " + enumType.getSimpleName() + "." + name);
            }
        }
    }

    private void attachExamples(ApiResponses responses, HttpStatus status, List<SuccessCode> codes) {
        String statusCode = String.valueOf(status.value());

        ApiResponse apiResponse = responses.get(statusCode);
        if (apiResponse == null) {
            apiResponse = new ApiResponse();
            responses.addApiResponse(statusCode, apiResponse);
        }
        apiResponse.setDescription(codes.stream()
                .map(SuccessCode::getMessage)
                .collect(Collectors.joining(" / ")));

        Content content = apiResponse.getContent();
        if (content == null) {
            content = new Content();
            apiResponse.setContent(content);
        }

        MediaType mediaType = content.computeIfAbsent(JSON_MEDIA_TYPE, key -> new MediaType());
        for (SuccessCode code : codes) {
            Example example = new Example()
                    .description(code.getMessage() + " (data 구조는 Schema 탭 참조)")
                    .value(toExampleValue(code));
            mediaType.addExamples(code.getCode(), example);
        }
    }

    private ObjectNode toExampleValue(SuccessCode code) {
        ObjectNode value = JsonNodeFactory.instance.objectNode();
        value.put("success", true);
        value.put("code", code.getCode());
        value.put("message", code.getMessage());
        value.putNull("data");
        return value;
    }
}