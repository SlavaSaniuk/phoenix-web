package com.phoenix.controllers.ajax;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AjaxPostsController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxPostsController.class);

    @RequestMapping(method = RequestMethod.POST, path = "/async_create_post")
    public LocalDate createPost() {

        LOGGER.debug("Request {" +
                "path: /async_create_post" +
                "method: post" +
                "type: async" +
                "}");

        return LocalDate.now();
    }

}
