/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.kenyaemr.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CorsInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("REST: Now handling CORS");
        String controllerPackage = handler.getClass().getPackage().getName();
        System.out.println("REST: Handler: " + controllerPackage);
        System.out.println("REST: Handler string: " + handler.toString());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            System.out.println("REST: Handler method: " + handlerMethod.toString());
            
            EnableCors enableCorsAnnotation = handlerMethod.getMethodAnnotation(EnableCors.class);
            if (enableCorsAnnotation != null) {
                System.out.println("REST: enabling CORS");
                String[] origins = enableCorsAnnotation.origins();
                if (origins.length > 0) {
                    response.setHeader("Access-Control-Allow-Origin", String.join(",", origins));
                }
                String[] methods = enableCorsAnnotation.methods();
                if (methods.length > 0) {
                    response.setHeader("Access-Control-Allow-Methods", String.join(",", methods));
                }
                String[] headers = enableCorsAnnotation.headers();
                if (headers.length > 0) {
                    response.setHeader("Access-Control-Allow-Headers", String.join(",", headers));
                }
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Max-Age", "3600");
            }
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept, Authorization, *");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600");
        }

        // Handle OPTIONS request
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("REST: Now handling CORS preflight OPTIONS");
            response.setStatus(HttpServletResponse.SC_OK);
            return false; // Stop further processing
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // Not needed
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // Not needed
    }
}
