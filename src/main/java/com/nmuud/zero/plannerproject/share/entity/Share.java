package com.nmuud.zero.plannerproject.share.entity;

import com.nmuud.zero.plannerproject.share.dto.ShareReplyType;
import com.nmuud.zero.plannerproject.common.enums.RequestStatus;
import com.nmuud.zero.plannerproject.user.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@Table(name = "shares")
@NoArgsConstructor
@AllArgsConstructor
public class Share extends BaseEntity {
    private Long fromUserId;
    private Long toUserId;
    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;
    @Enumerated(value = EnumType.STRING)
    private Direction direction;

    public Share reply(ShareReplyType type) {
        switch (type) {
            case ACCEPT:
                this.requestStatus = RequestStatus.ACCEPTED;
                break;
            case REJECT:
                this.requestStatus = RequestStatus.REJECTED;
                break;
        }
        return this;
    }

    public enum Direction {
        BIDIRECTIONAL, UNIDIRECTIONAL
    }
}
