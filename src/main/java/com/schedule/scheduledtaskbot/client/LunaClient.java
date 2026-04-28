package com.schedule.scheduledtaskbot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        value = "lunaClient",
        url = "https://b979328.yclients.com",
        path = "/api/v1")
public interface LunaClient {

    @GetMapping(value = "/activity/909532/filters")
    String getStaff(@RequestHeader("Authorization") String authHeader);

    @GetMapping(value = "/activity/909532/search?count=1000&till=9999-01-01")
    String getActivity(@RequestHeader("Authorization") String authHeader);
}
