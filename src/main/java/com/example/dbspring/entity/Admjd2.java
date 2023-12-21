package com.example.dbspring.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity(name = "admjd1_new")
public class Admjd2 {

    @Id
    @JsonProperty("adm_kod")
    private String admKod;

    @JsonProperty("version_no")
    private String versionNo;

    @JsonProperty("active_flag")
    private String activeFlag;

    @JsonProperty("operation_type")
    private String operationType;

    @JsonProperty("sname_lat")
    private String snameLat;

    @JsonProperty("system_user_stamp")
    private String systemUserStamp;

    @JsonProperty("external_id")
    private String externalId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @JsonProperty("date_end")
    private String dateEnd;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @JsonProperty("system_create_stamp")
    private String systemCreateStamp;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @JsonProperty("date_begin")
    private String dateBegin;

    @JsonProperty("sname_rus")
    private String snameRus;

    @JsonProperty("record_union_no")
    private String recordUnionNo;

    @JsonProperty("name")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
    @JsonProperty("system_update_stamp")
    private String systemUpdateStamp;

    @JsonProperty("status")
    private String status;

    @JsonProperty("type")
    private String type;

    @JsonProperty("stran_kod")
    private String stran_kod;

    @JsonProperty("blocked_by_user")
    private String blocked_by_user;

    @JsonProperty("system_author_stamp")
    private String system_author_stamp;

    @JsonProperty("record_uuid")
    private String record_uuid;

}