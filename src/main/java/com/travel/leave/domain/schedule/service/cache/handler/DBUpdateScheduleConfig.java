package com.travel.leave.domain.schedule.service.cache.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
public class DBUpdateScheduleConfig {   //비동기 저장을 위한 설정
}
