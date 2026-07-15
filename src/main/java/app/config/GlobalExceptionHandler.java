package app.config;

import app.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Global exception handler for all application exceptions.
 * Maps exceptions to error view with appropriate error details.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Handles all ApplicationException subclasses.
     * Routes to unified error.html template with dynamic error details.
     *
     * @param ex the caught ApplicationException
     * @return ModelAndView with error details
     */
    @ExceptionHandler(ApplicationException.class)
    public ModelAndView handleApplicationException(ApplicationException ex) {
        log.error("Application exception occurred: {}", ex.getMessage(), ex);

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorCode", ex.getErrorCode());
        modelAndView.addObject("errorTitle", ex.getErrorTitle());
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("timestamp", LocalDateTime.now().format(DATE_FORMATTER));

        return modelAndView;
    }

    /**
     * Handles any unexpected runtime exceptions not caught by specific handlers.
     *
     * @param ex the caught Exception
     * @return ModelAndView with generic error details
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorCode", "500");
        modelAndView.addObject("errorTitle", "Internal Server Error");
        modelAndView.addObject("errorMessage", "An unexpected error occurred. Please try again later.");
        modelAndView.addObject("timestamp", LocalDateTime.now().format(DATE_FORMATTER));

        return modelAndView;
    }
}

