package com.korotkov.main.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MappedExceptionAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final transient Map<String, String> exceptionMappings = new ConcurrentHashMap<>(5);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        final String url = exceptionMappings.get(exception.getClass().getName());
        if (url == null) {
            super.onAuthenticationFailure(request, response, exception);
        } else {
            super.saveException(request, exception);
            if (super.isUseForward()) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                getRedirectStrategy().sendRedirect(request, response, url);
            }
        }
    }

    public static Builder builder() {
        return new MappedExceptionAuthFailureHandler().new Builder();
    }

    public class Builder {

        public MappedExceptionAuthFailureHandler build() {
            return MappedExceptionAuthFailureHandler.this;
        }

        public Builder forwardToDestination(final boolean forwardToDestination) {
            MappedExceptionAuthFailureHandler.this.setUseForward(forwardToDestination);
            return this;
        }

        public Builder setDefaultFailureUrl(final String urlMapping) {
            Assert.isTrue(UrlUtils.isValidRedirectUrl(urlMapping), "URL mapping is invalid");
            MappedExceptionAuthFailureHandler.this.setDefaultFailureUrl(urlMapping);
            return this;
        }

        @SuppressWarnings("PMD.AccessorMethodGeneration")
        public <E extends AuthenticationException> Builder addMapping(final Class<E> exceptionClass,
                                                                      final String urlMapping) {
            Assert.notNull(exceptionClass, "exceptionClass must be specified");
            final String exceptionClassName = exceptionClass.getName();
            Assert.isTrue(UrlUtils.isValidRedirectUrl(urlMapping), "Not a valid redirect URL: " + urlMapping);
            MappedExceptionAuthFailureHandler.this.exceptionMappings.put(exceptionClassName, urlMapping);
            return this;
        }
    }
}
