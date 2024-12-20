package com.travel.leave.domain.schedule.service.cache.handler;

import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.UpdateTravelLocationGeographicMessage;
import com.travel.leave.domain.schedule.service.cache.TravelCache;
import com.travel.leave.subdomain.travel.entity.Travel;
import com.travel.leave.subdomain.travellocaion.entity.TravelLocation;
import com.travel.leave.subdomain.travelpreparation.entity.TravelPreparation;
import com.travel.leave.subdomain.travellocaion.repository.TravelLocationRepository;
import com.travel.leave.subdomain.travel.repository.TravelRepository;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.preparation.UpdateTravelPreparationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.timeline.UpdateTravelLocationMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel.UpdateTravelContentMessage;
import com.travel.leave.domain.schedule.controller.socket.messageFormat.message.travel.UpdateTravelNameMessage;
import com.travel.leave.subdomain.travelpreparation.repository.TravelPreparationRepository;
import com.travel.leave.subdomain.usertravel.repository.UserTravelRepository;

import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TravelCacheHandlerImpl implements TravelCacheHandler {
    private static final ConcurrentHashMap<Long, TravelCache> travelCaches = new ConcurrentHashMap<>();
    private final TravelRepository travelRepository;
    private final TravelLocationRepository travelLocationRepository;
    private final TravelPreparationRepository travelPreparationRepository;
    private final UserTravelRepository userTravelRepository;
    private final TravelCacheFactory travelCacheFactory;
    private final CacheToEntityMapper cacheToEntityMapper;


    @Override
    public TravelCache loadTravel(Long travelCode, boolean hasTravelCache) {
        log.info("loadTravel");
        if(hasTravelCache){
            return travelCaches.get(travelCode);
        }
        TravelCache travelCache = findTravelAndCreateCache(travelCode);
        travelCaches.put(travelCache.getTravelCode(), travelCache);
        return travelCache;
    }

    @Override
    public void updateTravelContent(UpdateTravelContentMessage message) {
        TravelCache travelCache = getTravelCache(message.travelCode());
        TravelCache updatedTravelCache = travelCacheFactory.createUpdatedTravelContent(travelCache, message);

        travelCaches.put(message.travelCode(), updatedTravelCache);
    }

    @Override
    public void updateTravelPreparation(UpdateTravelPreparationMessage message) {
        TravelCache travelCache = getTravelCache(message.travelCode());
        TravelCache updatedTravelCache = travelCacheFactory.createUpdatedTravelPreparation(travelCache, message);

        travelCaches.put(message.travelCode(), updatedTravelCache);
    }

    @Override
    public void updateTravelLocation(UpdateTravelLocationMessage message) {
        TravelCache travelCache = getTravelCache(message.travelCode());
        TravelCache updatedTravelCache = travelCacheFactory.createUpdatedTravelLocation(travelCache, message);

        travelCaches.put(message.travelCode(), updatedTravelCache);
    }

    @Override
    public void updateTravelName(UpdateTravelNameMessage message) {
        TravelCache travelCache = getTravelCache(message.travelCode());
        TravelCache updatedTravelCache = travelCacheFactory.createUpdatedTravelName(travelCache, message);

        travelCaches.put(message.travelCode(), updatedTravelCache);
    }

    @Override
    public void updateTravelLocationGeographic(UpdateTravelLocationGeographicMessage message) {
        TravelCache travelCache = getTravelCache(message.travelCode());
        TravelCache updatedTravelLocationCache = travelCacheFactory.createUpdatedTravelLocationGeograhpic(travelCache, message);

        travelCaches.put(message.travelCode(), updatedTravelLocationCache);
    }

    @Override
    public boolean hasTravel(Long travelCode) {
        return travelCaches.containsKey(travelCode);
    }

    @Scheduled(fixedRate = 600000)
    public void triggerUpdate(){
        updateTravelCaches();
    }

    @Async
    public void updateTravelCaches() {
        //업데이트 로직 + caches 비우기
        for(Entry<Long, TravelCache> travelCache : travelCaches.entrySet()){
            travelRepository.save(cacheToEntityMapper.mapToTravel(travelCache.getValue()));
            travelLocationRepository.saveAll(cacheToEntityMapper.mapToTravelLocation(travelCache.getValue().getSchedule(), travelCache.getValue().getGeographicMessages(), travelCache.getValue().getTravelCode()));
            travelPreparationRepository.saveAll(cacheToEntityMapper.mapToTravelPreparation(travelCache.getValue().getTravelCode(), travelCache.getValue().getPreparation()));
        }
        travelCaches.clear();
    }


    private TravelCache getTravelCache(Long travelCode){
        return travelCaches.get(travelCode);
    }

    private TravelCache findTravelAndCreateCache(Long travelCode) {
        return travelCacheFactory.createTravelCache(
                getTravelEntity(travelCode),
                getTravelLocationEntities(travelCode),
                getTravelPreparationEntities(travelCode),
                getUserNickNames(travelCode));
    }

    private List<String> getUserNickNames(Long travelCode) {
        return userTravelRepository.findUsersInTravel(travelCode);
    }

    private List<TravelPreparation> getTravelPreparationEntities(Long travelCode) {
        return travelPreparationRepository.findAllByTravelCodeAndIsDeletedIsFalse(travelCode);
    }

    private List<TravelLocation> getTravelLocationEntities(Long travelCode) {
        return travelLocationRepository.findTravelLocations(travelCode);
    }

    private Travel getTravelEntity(Long travelCode) {
        Optional<Travel> travel = travelRepository.findById(travelCode);
        if(travel.isPresent()){
            return travel.get();
        }
        throw new IllegalArgumentException();
    }
}

