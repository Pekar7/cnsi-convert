package com.example.dbspring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Field {

    @JsonProperty("version_no")
    private Integer versionNo;

    @JsonProperty("adm_kod")
    private Integer admKod;

    @JsonProperty("active_flag")
    private Boolean activeFlag;

    @JsonProperty("operation_type")
    private String operationType;

    @JsonProperty("sname_lat")
    private String snameLat;

    @JsonProperty("system_user_stamp")
    private String systemUserStamp;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("date_end")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private String dateEnd;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @JsonProperty("system_create_stamp")
    private String systemCreateStamp;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    @JsonProperty("date_begin")
    private String dateBegin;

    @JsonProperty("sname_rus")
    private String snameRus;

    @JsonProperty("record_union_no")
    private String recordUnionNo;

    @JsonProperty("name")
    private String name;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @JsonProperty("system_update_stamp")
    private String systemUpdateStamp;

    @JsonProperty("status")
    private String status;

    @JsonProperty("type")
    private String type;

    @JsonProperty("stran_kod")
    private String stranKod;

    @JsonProperty("blocked_by_user")
    private String blockedByUser;

    @JsonProperty("system_author_stamp")
    private String systemAuthorStamp;

    @JsonProperty("record_uuid")
    private String recordUuid;

    @JsonProperty("revision")
    private Integer revision;

    @JsonProperty("protected_flag")
    private Integer protectedFlag;

    @JsonProperty("original_record_no")
    private String originalRecordNo;

    /*
    @Id
    @JsonProperty("record_no")
    private String recordNo;

     */
}