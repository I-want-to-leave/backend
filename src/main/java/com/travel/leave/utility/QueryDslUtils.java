package com.travel.leave.utility;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import java.sql.Timestamp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class QueryDslUtils {
    public static OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable, EntityPathBase<?> qEntity) {
        PathBuilder<?> pathBuilder = new PathBuilder<>(qEntity.getType(), qEntity.getMetadata().getName());

        return pageable.getSort().stream()
                .map(order -> {
                    Class<?> fieldType = getFieldType(qEntity.getType(), order.getProperty());
                    return createOrderSpecifier(pathBuilder, order, fieldType);
                })
                .toArray(OrderSpecifier[]::new);
    }

    private static OrderSpecifier<?> createOrderSpecifier(PathBuilder<?> pathBuilder, Sort.Order order, Class<?> fieldType) {
        Order sortOrder = getSortOrder(order);
        String property = order.getProperty();

        if (fieldType == String.class) {
            return new OrderSpecifier<>(sortOrder, pathBuilder.getString(property));
        } else if (fieldType == Integer.class) {
            return new OrderSpecifier<>(sortOrder, pathBuilder.getNumber(property, Integer.class));
        } else if (fieldType == Long.class) {
            return new OrderSpecifier<>(sortOrder, pathBuilder.getNumber(property, Long.class));
        } else if (fieldType == Timestamp.class) {
            return new OrderSpecifier<>(sortOrder, pathBuilder.getDate(property, Timestamp.class));
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static Order getSortOrder(Sort.Order order) {
        if(order.isAscending()){
            return Order.ASC;
        }
        return Order.DESC;
    }

    private static Class<?> getFieldType(Class<?> entityType, String fieldName) {
        try {
            return entityType.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException();
        }
    }
}
