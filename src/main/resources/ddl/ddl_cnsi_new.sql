CREATE TABLE IF NOT EXISTS main.admjd1_new
(
    admKod              varchar(255) not null
        primary key,
    activeFlag          varchar(255) null,
    blocked_by_user     varchar(255) null,
    dateBegin           varchar(255) null,
    dateEnd             varchar(255) null,
    externalId          varchar(255) null,
    name                varchar(255) null,
    operationType       varchar(255) null,
    recordUnionNo       varchar(255) null,
    record_uuid         varchar(255) null,
    snameLat            varchar(255) null,
    snameRus            varchar(255) null,
    status              varchar(255) null,
    stran_kod           varchar(255) null,
    systemCreateStamp   varchar(255) null,
    systemUpdateStamp   varchar(255) null,
    systemUserStamp     varchar(255) null,
    system_author_stamp varchar(255) null,
    type                varchar(255) null,
    versionNo           varchar(255) null
);