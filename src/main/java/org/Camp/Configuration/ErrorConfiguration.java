package org.Camp.Configuration;

import org.Camp.Exception.*;
import org.Camp.Model.Response.ErrorResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Configuration
public class ErrorConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
                resolvers.add(new ExceptionResolver());
            }
        };
    }

    private static class ExceptionResolver extends AbstractHandlerExceptionResolver {

        @Override
        protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            if (ex instanceof NotFoundException) {
                return handleDataNotFound((NotFoundException) ex, response);
            } else if (ex instanceof RatingException || ex instanceof CampException || ex instanceof ReservationException || ex instanceof UserException) {
                return handleBadRequestError(ex, response);
            } else {
                return handleAllException(ex, response);
            }
        }

        private ModelAndView handleAllException(Exception e, HttpServletResponse response) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            ErrorResponse errorResponse = new ErrorResponse("01", e.getMessage());
            return new ModelAndView(new MappingJackson2JsonView(), "errorResponse", errorResponse);
        }

        private ModelAndView handleDataNotFound(NotFoundException e, HttpServletResponse response) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            ErrorResponse errorResponse = new ErrorResponse("02", e.getMessage());
            return new ModelAndView(new MappingJackson2JsonView(), "errorResponse", errorResponse);
        }

        private ModelAndView handleBadRequestError(Exception e, HttpServletResponse response) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            ErrorResponse errorResponse = new ErrorResponse("03", e.getMessage());
            return new ModelAndView(new MappingJackson2JsonView(), "errorResponse", errorResponse);
        }
    }
}

