package com.chiiiplow.clouddisk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chiiiplow.clouddisk.dao.FileMapper;
import com.chiiiplow.clouddisk.entity.File;
import com.chiiiplow.clouddisk.service.FileService;
import org.springframework.stereotype.Service;


@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
}
