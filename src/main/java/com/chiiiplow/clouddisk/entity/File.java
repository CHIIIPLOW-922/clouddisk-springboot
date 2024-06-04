package com.chiiiplow.clouddisk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件
 *
 * @author CHIIIPLOW
 * @date 2024/06/03
 */
@Data
@TableName("file")
public class File implements Serializable {

    /**
     * file_id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * user id
     */
    private Long userId;

    /**
     * file name
     */
    private String fileName;

    /**
     * file size
     */
    private Long fileSize;

    /**
     * file type
     */
    private Integer fileType;

    /**
     * file path
     */
    private String filePath;

    /**
     * is folder?
     */
    private Integer folderBool;

    /**
     * file folder
     */
    private Long fileFolder;

    /**
     * upload_status
     */
    private Integer uploadStatus;

    /**
     * upload time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;

    /**
     * modify time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * recycling time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recyclingTime;

    /**
     * delete flag 0:normal、1:recycling、2:deleted
     */
    private Integer deleteFlag;

}
