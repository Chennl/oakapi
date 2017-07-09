package com.oak.controller;

import com.oak.weixin.WorkWeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Chennl on 7/7/2017.
 */
@RestController
@RequestMapping(value="api/wkwx")
public class WorkWeixinController {
    @Autowired
    WorkWeiXinService workWeiXinService;
    @RequestMapping(value="send")
    public void sendTextMessage(){
        workWeiXinService.sendTextMessage("chennl","hello");
    }
}
