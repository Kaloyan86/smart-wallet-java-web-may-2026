package app.config;

import app.exception.ApplicationException;
import app.model.dto.exception.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleApplicationException(ApplicationException ex) {
        log.error("ApplicationException occurred: {}", ex.getMessage(), ex);

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("errorTitle", ex.getErrorTitle());
        modelAndView.addObject("errorCode", ex.getErrorCode());

        return modelAndView;
    }

    @ExceptionHandler(FeignException.FeignClientException.class)
    public ModelAndView handleFeignException(FeignException ex) throws IOException {
        log.error("ApplicationException occurred: {}", ex.getMessage(), ex);

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("errorTitle", "Feign Exception");
        modelAndView.addObject("errorCode", ex.status());

        return modelAndView;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.debug("Static resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        log.error("ApplicationException occurred: {}", ex.getMessage(), ex);

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "Unexpected error occurred");
        modelAndView.addObject("errorTitle", "Internal Server Error");
        modelAndView.addObject("errorCode", "500");

        return modelAndView;
    }
}
