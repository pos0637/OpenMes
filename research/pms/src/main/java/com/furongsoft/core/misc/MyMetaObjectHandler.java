package com.furongsoft.core.misc;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createUser = getFieldValByName("createUser", metaObject);
        if (createUser == null) {
            // TODO: get user from shiro
            setFieldValByName("createUser", "0", metaObject);
        }

        Object lastModifyUser = getFieldValByName("lastModifyUser", metaObject);
        if (lastModifyUser == null) {
            // TODO: get user from shiro
            setFieldValByName("lastModifyUser", "0", metaObject);
        }

        Object createTime = getFieldValByName("createTime", metaObject);
        if (createTime == null) {
            setFieldValByName("createTime", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // TODO: get user from shiro
        setFieldValByName("lastModifyUser", "0", metaObject);
    }
}
