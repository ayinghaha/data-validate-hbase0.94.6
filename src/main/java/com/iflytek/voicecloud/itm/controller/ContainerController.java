package com.iflytek.voicecloud.itm.controller;

import com.iflytek.voicecloud.itm.dto.ContainerDto;
import com.iflytek.voicecloud.itm.dto.Message;
import com.iflytek.voicecloud.itm.entity.Container;
import com.iflytek.voicecloud.itm.service.ContainerService;
import com.iflytek.voicecloud.itm.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by jdshao on 2017/4/6
 */
@Controller
@RequestMapping("/container")
public class ContainerController {

    @Autowired
    ContainerService containerService;

    /**
     * container 类型
     */
    public static final String[] containerType = {"Web", "IOS", "Android"};

    @RequestMapping("/add")
    public void addContainer(HttpServletRequest request, HttpServletResponse response, Container container) throws Exception {

        Message message = new Message(-1, "");
        List<String> typeList = Arrays.asList(containerType);
        if (container.getItmID() == null || container.getContainerID() == null || container.getType() == null ) {
            message.setData("参数不全");
            ResponseUtil.setResponseJson(response, message);
            return ;
        } else if (!typeList.contains(container.getType())) {
            message.setData("类型不正确");
            ResponseUtil.setResponseJson(response, message);
            return ;
        }

        // 检测相同itmid下名称和是否重复
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("itmID", container.getItmID());
        condition.put("containerID", container.getContainerID());
        List<Container> detectContainer = containerService.getContainerList(condition);
        if (detectContainer.size() > 0) {
            message.setData("当前ITM账号下存在同名Container");
            ResponseUtil.setResponseJson(response, message);
            return ;
        }

        // 添加Container
        container.setRegistTime(new Date());
        container.setUpdateTime(new Date());
        int resKey = containerService.addContainer(container);
        if (resKey < 0) {
            message.setData("添加失败");
        } else {
            message.setState(1);
            message.setData("添加成功");
        }
        ResponseUtil.setResponseJson(response, message);
    }

    @RequestMapping("/get")
    public void getContainer(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String itmID = request.getParameter("itmID");
        String containerID = request.getParameter("containerID");
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("itmID", itmID);
        condition.put("containerID", containerID);

        List<Container> containers = containerService.getContainerList(condition);
        List<Map<String, Object>> resMapList = new ArrayList<Map<String, Object>>();
        for (Container container : containers) {
            resMapList.add(ContainerDto.formatContainerJson(container));
        }

        Message message = new Message(1, resMapList);
        ResponseUtil.setResponseJson(response, message);
    }

}
