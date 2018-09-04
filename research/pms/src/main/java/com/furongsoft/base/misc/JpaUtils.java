package com.furongsoft.base.misc;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * JPA工具
 *
 * @author Alex
 */
public class JpaUtils {
    public static <T> Specification<T> generateSpecification(Map<String, Object> params, Class<T> clazz) {
        Map<String, Field> fields = getAllFields(clazz);

        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = null;
            for (String name : params.keySet()) {
                if (!fields.containsKey(name)) {
                    continue;
                }

                MatchType type = MatchType.none;
                Annotation annotation = fields.get(name).getAnnotation(QueryField.class);
                if (annotation != null) {
                    type = ((QueryField) annotation).type();
                }

                Predicate p = getPredicate(criteriaBuilder, name, fields.get(name), params, root, type);
                if (predicate == null) {
                    predicate = p;
                } else if (p != null) {
                    predicate = criteriaBuilder.and(predicate, p);
                }
            }

            return predicate;
        };
    }

    /**
     * 获取所有带有QueryField注解的字段
     *
     * @param clazz 类型
     * @return 字段列表
     */
    private static Map<String, Field> getAllFields(Class<?> clazz) {
        Map<String, Field> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        if ((fields != null) && (fields.length > 0)) {
            Stream.of(fields).filter(field -> field.getAnnotation(QueryField.class) != null).forEach(field -> map.put(field.getName(), field));
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == Object.class) {
            return map;
        }

        // 递归查询父类的field列表
        Map<String, Field> superFields = getAllFields(superClass);
        if ((superFields != null) && !superFields.isEmpty()) {
            map.putAll(superFields);
        }

        return map;
    }

    /**
     * 获取查询条件
     *
     * @param criteriaBuilder 条件构造器
     * @param name            字段名称
     * @param field           字段描述
     * @param params          参数
     * @param root            根
     * @param type            匹配类型
     * @param <T>             实体类型
     * @return 查询条件
     */
    private static <T> Predicate getPredicate(
            CriteriaBuilder criteriaBuilder,
            String name,
            Field field,
            Map<String, Object> params,
            Root<T> root,
            MatchType type) {
        if (field.getType().equals(String.class)) {
            switch (type) {
                case equal:
                    return criteriaBuilder.equal(root.get(name), params.get(name));
                case noEqual:
                    return criteriaBuilder.notEqual(root.get(name), params.get(name));
                case like:
                    return criteriaBuilder.like(root.get(name), "%" + params.get(name) + "%");
                case notLike:
                    return criteriaBuilder.notLike(root.get(name), "%" + params.get(name) + "%");
                default:
                    break;
            }
        } else if (field.getType().equals(Date.class)) {
            switch (type) {
                case equal:
                    return criteriaBuilder.equal(root.get(name), (Date) params.get(name));
                case noEqual:
                    return criteriaBuilder.notEqual(root.get(name), (Date) params.get(name));
                case gt:
                    return criteriaBuilder.greaterThan(root.get(name), (Date) params.get(name));
                case ge:
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(name), (Date) params.get(name));
                case lt:
                    return criteriaBuilder.lessThan(root.get(name), (Date) params.get(name));
                case le:
                    return criteriaBuilder.lessThanOrEqualTo(root.get(name), (Date) params.get(name));
                default:
                    break;
            }
        } else if (field.getType().equals(Boolean.class)) {
            switch (type) {
                case equal:
                    return criteriaBuilder.equal(root.get(name), (Boolean) params.get(name));
                case noEqual:
                    return criteriaBuilder.notEqual(root.get(name), (Boolean) params.get(name));
                default:
                    break;
            }
        } else {
            switch (type) {
                case equal:
                    return criteriaBuilder.equal(root.get(name), (Number) params.get(name));
                case noEqual:
                    return criteriaBuilder.notEqual(root.get(name), (Number) params.get(name));
                case gt:
                    return criteriaBuilder.gt(root.get(name), (Number) params.get(name));
                case ge:
                    return criteriaBuilder.ge(root.get(name), (Number) params.get(name));
                case lt:
                    return criteriaBuilder.lt(root.get(name), (Number) params.get(name));
                case le:
                    return criteriaBuilder.le(root.get(name), (Number) params.get(name));
                default:
                    break;
            }
        }

        return null;
    }

    /**
     * 比较类型
     */
    public enum MatchType {
        equal,
        noEqual,
        like,
        notLike,
        gt,
        ge,
        lt,
        le,
        none
    }

    /**
     * 查询字段
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface QueryField {
        /**
         * @return 比较类型
         */
        MatchType type() default MatchType.equal;

        /**
         * @return 是否可空
         */
        boolean nullable() default false;

        /**
         * @return 是否允许空白
         */
        boolean allowEmpty() default false;
    }
}
