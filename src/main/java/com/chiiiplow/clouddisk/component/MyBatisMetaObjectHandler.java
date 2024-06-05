package com.chiiiplow.clouddisk.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        this.strictInsertFill(metaObject, CREATE_TIME, Date.class, new Date());
        this.strictInsertFill(metaObject, UPLOAD_TIME, Date.class, new Date());
        this.strictInsertFill(metaObject, REGISTER_TIME, Date.class, new Date());
        this.strictInsertFill(metaObject, MODIFY_TIME, Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, MODIFY_TIME, Date.class, new Date());

    }
}
