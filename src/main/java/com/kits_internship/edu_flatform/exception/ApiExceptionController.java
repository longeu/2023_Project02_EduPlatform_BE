package com.kits_internship.edu_flatform.exception;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * @author MyAdmin
 */
@RestControllerAdvice
@Slf4j
public class ApiExceptionController {
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> unauthoriedPointerException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildSuccessFailed(401,"Invalid token"));
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> unprocessableEntityExceptionException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(buildSuccessFailed(400, ((UnprocessableEntityException) ex).getMessageResult()));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> notFoundEntityExceptionException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildSuccessFailed(404, ((NotFoundException) ex).getMessageResult()));
    }

    private static JSONObject buildSuccessFailed(int status, Object result) {
        log.error(String.valueOf(result));
        JSONObject response = new JSONObject();
        putObject(response, "status", status);
        putObject(response, "error", result);
        return response;
    }

    private static void putObject(JSONObject jsonObject, String key, Object value) {
        try {
            if (jsonObject != null) {
                jsonObject.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
