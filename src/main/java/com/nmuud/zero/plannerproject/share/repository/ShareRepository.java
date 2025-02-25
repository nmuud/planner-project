package com.nmuud.zero.plannerproject.share.repository;

import com.nmuud.zero.plannerproject.share.entity.Share;
import com.nmuud.zero.plannerproject.common.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long> {

    @Query("SELECT s FROM Share s WHERE (s.fromUserId = ?1 or s.toUserId = ?1) and s.requestStatus = ?2 and s.direction = ?3")
    List<Share> findAllByBiDirection(Long userId,
                                     RequestStatus requestStatus,
                                     Share.Direction direction
    );

    List<Share> findAllByToUserIdAndRequestStatusAndDirection(Long userId,
                                                              RequestStatus requestStatus,
                                                              Share.Direction direction
    );
}
