package com.travel.leave.schedule.service.model.cache.handler;

import com.travel.leave.schedule.controller.socket.messageFormat.preparation.UpdateTravelPreparationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.timeline.UpdateTravelLocationMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.travel.UpdateTravelContentMessage;
import com.travel.leave.schedule.controller.socket.messageFormat.travel.UpdateTravelNameMessage;
import com.travel.leave.schedule.repository.TravelPreparationRepository;

import com.travel.leave.schedule.service.model.cache.TravelCache;
import com.travel.leave.schedule.service.model.cache.factory.TravelCacheFactory;
import com.travel.leave.travel.entity.Travel;
import com.travel.leave.travel.entity.TravelLocation;
import com.travel.leave.travel.entity.TravelPreparation;
import com.travel.leave.travel.repository.TravelLocationRepository;
import com.travel.leave.travel.repository.TravelRepository;
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
    public void updateTravelContent(UpdateTravelContentMessage updateTravelContentMessage) {
        TravelCache travelCache = getTravelCache(updateTravelContentMessage.travelCode());
        TravelCache updatedTravelCache = travelCacheFactory
                .createUpdatedTravelContent(travelCache, updateTravelContentMessage);

        travelCaches.put(updateTravelContentMessage.travelCode(), updatedTravelCache);
    }

    @Override
    public void updateTravelPreparation(UpdateTravelPreparationMessage updateTravelPreparationMessage) {
        TravelCache travelCache = getTravelCache(updateTravelPreparationMessage.travelCode());
        TravelCache updatedTravelCache = travelCacheFactory
                .createUpdatedTravelPreparation(travelCache, updateTravelPreparationMessage);

        travelCaches.put(updateTravelPreparationMessage.travelCode(), updatedTravelCache);
    }

    @Override
    public void updateTravelLocation(UpdateTravelLocationMessage updateTravelLocationMessage) {
        TravelCache travelCache = getTravelCache(updateTravelLocationMessage.travelCode());
        TravelCache updatedTravelCache = travelCacheFactory
                .createUpdatedTravelLocation(travelCache, updateTravelLocationMessage);

        travelCaches.put(updateTravelLocationMessage.travelCode(), updatedTravelCache);
    }

    @Override
    public void updateTravelName(UpdateTravelNameMessage updateTravelNameMessage) {
        TravelCache travelCache = getTravelCache(updateTravelNameMessage.travelCode());
        TravelCache updatedTravelCache = travelCacheFactory
                .createUpdatedTravelName(travelCache, updateTravelNameMessage);

        travelCaches.put(updateTravelNameMessage.travelCode(), updatedTravelCache);
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
        //업데이트 로직 + caches비우기
        for(Entry<Long, TravelCache> travelCache : travelCaches.entrySet()){
            travelRepository.save(cacheToEntityMapper.mapToTravel(travelCache.getValue()));
            travelLocationRepository.saveAll(cacheToEntityMapper.mapToTravelLocation(travelCache.getValue().getSchedule()));
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
                getTravelPreparationEntities(travelCode));
    }

    private List<TravelPreparation> getTravelPreparationEntities(Long travelCode) {
        return travelPreparationRepository.findAllByTravelCodeAndIsDeletedIsFalse(travelCode);
    }

    private List<TravelLocation> getTravelLocationEntities(Long travelCode) {
        return travelLocationRepository.findAllByTravelCode(travelCode);
    }

    private Travel getTravelEntity(Long travelCode) {
        Optional<Travel> travel = travelRepository.findById(travelCode);
        if(travel.isPresent()){
            return travel.get();
        }
        throw new IllegalArgumentException();
    }
}

