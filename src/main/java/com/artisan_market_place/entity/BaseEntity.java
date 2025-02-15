package com.artisan_market_place.entity;

import com.artisan_market_place.utils.DateTimeUtil;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity implements Cloneable{

        @Column(name = "creation_date")
        protected Date creationDate;

        @Column(name = "created_by")
        protected String createdBy;

        @Column(name = "last_update_date")
        protected Date lastUpdateDate;

        @Column(name = "last_updated_by")
        protected String lastUpdatedBy;

        public void setAuditInfo(String username) {
            Date now = DateTimeUtil.getCurrentUTCDate();
            if (creationDate == null || createdBy == null) {
                creationDate = now;
                createdBy = username;
            }
            lastUpdateDate = now;
            lastUpdatedBy = username;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
