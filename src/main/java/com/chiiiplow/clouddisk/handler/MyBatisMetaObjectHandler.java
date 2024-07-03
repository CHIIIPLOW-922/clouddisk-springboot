package com.chiiiplow.clouddisk.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *  MyBatis 元对象处理器
 *
 * @author CHIIIPLOW
 * @date 2024/06/05
 */
@Component
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createTime";
    private static final String REGISTER_TIME = "registerTime";
    private static final String UPLOAD_TIME = "uploadTime";
    private static final String MODIFY_TIME = "modifyTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, UPLOAD_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, REGISTER_TIME, LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, MODIFY_TIME, LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, MODIFY_TIME, LocalDateTime.class, LocalDateTime.now());

    }
}
