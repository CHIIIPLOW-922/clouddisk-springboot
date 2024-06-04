package com.chiiiplow.clouddisk.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chiiiplow.clouddisk.entity.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<File> {

}
